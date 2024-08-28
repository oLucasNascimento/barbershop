package br.com.barbermanager.barbershopmanagement.domain.service.client;

import br.com.barbermanager.barbershopmanagement.api.mapper.BarberShopMapper;
import br.com.barbermanager.barbershopmanagement.api.mapper.ClientMapper;
import br.com.barbermanager.barbershopmanagement.api.request.client.ClientRequest;
import br.com.barbermanager.barbershopmanagement.api.response.client.ClientResponse;
import br.com.barbermanager.barbershopmanagement.api.response.client.ClientSimple;
import br.com.barbermanager.barbershopmanagement.api.response.item.ItemSimple;
import br.com.barbermanager.barbershopmanagement.domain.model.BarberShop;
import br.com.barbermanager.barbershopmanagement.domain.model.Client;
import br.com.barbermanager.barbershopmanagement.domain.model.Item;
import br.com.barbermanager.barbershopmanagement.domain.model.StatusEnum;
import br.com.barbermanager.barbershopmanagement.domain.repository.ClientRepository;
import br.com.barbermanager.barbershopmanagement.domain.service.barbershop.BarberShopService;
import br.com.barbermanager.barbershopmanagement.exception.AlreadyActiveException;
import br.com.barbermanager.barbershopmanagement.exception.AlreadyExistsException;
import br.com.barbermanager.barbershopmanagement.exception.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private BarberShopService barberShopService;

    @Autowired
    private BarberShopMapper barberShopMapper;

    @Autowired
    private ClientMapper clientMapper;


    @Override
    public Boolean clientExists(Integer clientId) {
        return this.clientRepository.existsById(clientId);
    }

    @Override
    public ClientResponse createClient(ClientRequest newClient) {
        if ((this.clientRepository.findByCpf(newClient.getCpf())) == null) {
            newClient.setStatus(StatusEnum.ACTIVE);
            return this.clientMapper.toClientResponse((this.clientRepository.save((this.clientMapper.toClient(newClient)))));
        }
        throw new AlreadyExistsException("Client with CPF '" + newClient.getCpf() + "' already exists.");
    }

    @Override
    public List<ClientSimple> allClients(StatusEnum status) {
        List<ClientSimple> clients = this.clientMapper.toClientSimpleList((this.clientRepository.findAll()));
        if (clients.isEmpty()) {
            throw new NotFoundException("There aren't clients to show.");
        } else if (status != null) {
            List<ClientSimple> clientsByStatus = this.clientMapper.toClientSimpleList((this.clientRepository.findClientsByStatus(status)));
            if (clientsByStatus.isEmpty()) {
                throw new NotFoundException("There aren't clients with status '" + status + "'.");
            }
            return clientsByStatus;
        }
        return clients;
    }


    @Override
    public ClientResponse clientById(Integer clientId) {
        if (this.clientRepository.existsById(clientId)) {
            return this.clientMapper.toClientResponse((this.clientRepository.getById(clientId)));
        }
        throw new NotFoundException("Client with ID '" + clientId + "' not found.");
    }

    @Override
    public List<ClientSimple> clientsByBarberShop(Integer barberShopId, StatusEnum status) {
        List<ClientSimple> clients = this.clientMapper.toClientSimpleList(this.clientRepository.findClientsByBarberShop(barberShopId));
        if (clients.isEmpty()) {
            throw new NotFoundException("There aren't clients at this barbershop.");
        } else if (status != null) {
            List<ClientSimple> clientsByStatus = new ArrayList<>();
            for (ClientSimple client : clients) {
                if ((client.getStatus().equals(status))) {
                    clientsByStatus.add(client);
                }
            }
            if (clientsByStatus.isEmpty()) {
                throw new NotFoundException("There aren't clients with status '" + status + "' at this barbershop.");
            }
            return clientsByStatus;
        }
        return clients;
    }

    @Override
    public void deleteClient(Integer clientId) {
        if (this.clientRepository.existsById(clientId)) {
            Client client = this.clientRepository.getById(clientId);
            client.setStatus(StatusEnum.INACTIVE);
            this.clientRepository.save(client);
            return;
        }
        throw new NotFoundException("Client with ID '" + clientId + "' not found.");
    }

    @Override
    public void activeClient(Integer clientId) {
        if (this.clientExists(clientId)) {
            Client client = this.clientRepository.getById(clientId);
            if (client.getStatus() != StatusEnum.ACTIVE) {
                client.setStatus(StatusEnum.ACTIVE);
                this.clientRepository.save(client);
                return;
            }
            throw new AlreadyActiveException("Client with ID '" + clientId + "' is already active.");
        }
        throw new NotFoundException("Client with ID '" + clientId + "' not found.");
    }

    @Override
    public ClientResponse updateClient(Integer clientId, ClientRequest updatedClient) {
        if (this.clientExists(clientId)) {
            if (this.clientRepository.findByCpf(updatedClient.getCpf()) == null) {
                Client client = this.clientRepository.getById(clientId);
                if (updatedClient.getBarberShops() != null) {
                    client = this.updateBarberShops(clientId, (this.clientMapper.toClient(updatedClient)));
                }
                BeanUtils.copyProperties((this.clientMapper.toClient(updatedClient)), client, this.searchEmptyFields(updatedClient));
                return this.clientMapper.toClientResponse((this.clientRepository.save(client)));
            }
            throw new AlreadyExistsException("Client with CPF '" + updatedClient.getCpf() + "' exists.");
        }
        throw new NotFoundException("Client with ID '" + clientId + "' not found.");
    }

    private Client updateBarberShops(Integer clientId, Client updatedClient) {
        Client client = this.clientRepository.getById(clientId);
        for (BarberShop barberShopUpdt : updatedClient.getBarberShops()) {
            List<Client> clients = new ArrayList<>();
            clients.add(client);
            BarberShop barberShop = this.barberShopMapper.toBarberShop((this.barberShopService.barberShopById((barberShopUpdt.getBarberShopId()))));
            barberShop.setClients(clients);
            this.barberShopService.udpateClientAtBarberShop(barberShop.getBarberShopId(), this.barberShopMapper.toBarberShopRequest(barberShop));
        }
        return client;
    }

    private String[] searchEmptyFields(Object source) {
        BeanWrapper src = new BeanWrapperImpl(source);
        Set<String> emptyFields = new HashSet<>();
        for (PropertyDescriptor pd : src.getPropertyDescriptors()) {
            if (src.getPropertyValue(pd.getName()) == null) {
                emptyFields.add(pd.getName());
            }
        }
        String[] result = new String[emptyFields.size()];
        return emptyFields.toArray(result);
    }
}
