package br.com.barbermanager.barbershopmanagement.domain.service.client;

import br.com.barbermanager.barbershopmanagement.api.mapper.BarberShopMapper;
import br.com.barbermanager.barbershopmanagement.api.mapper.ClientMapper;
import br.com.barbermanager.barbershopmanagement.api.request.client.ClientRequest;
import br.com.barbermanager.barbershopmanagement.api.response.client.ClientResponse;
import br.com.barbermanager.barbershopmanagement.domain.model.BarberShop;
import br.com.barbermanager.barbershopmanagement.domain.model.Client;
import br.com.barbermanager.barbershopmanagement.domain.repository.ClientRepository;
import br.com.barbermanager.barbershopmanagement.domain.service.barbershop.BarberShopService;
import br.com.barbermanager.barbershopmanagement.exception.AlreadyExistsException;
import br.com.barbermanager.barbershopmanagement.exception.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.PropertyDescriptor;
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
            return this.clientMapper.toClientResponse((this.clientRepository.save((this.clientMapper.toClient(newClient)))));
        }
        throw new AlreadyExistsException("Client with CPF '" + newClient.getCpf() + "' already exists.");
    }

    @Override
    public List<ClientResponse> allClients() {
        List<ClientResponse> clientResponses = this.clientMapper.toClientResponseList((this.clientRepository.findAll()));
        if (clientResponses.isEmpty()) {
            throw new NotFoundException("There aren't clients to show.");
        }
        return clientResponses;
    }

    @Override
    public ClientResponse clientById(Integer clientId) {
        if (this.clientRepository.existsById(clientId)) {
            return this.clientMapper.toClientResponse((this.clientRepository.getById(clientId)));
        }
        throw new NotFoundException("Client with ID '" + clientId + "' not found.");
    }

    @Override
    public void deleteClient(Integer clientId) {
        if (this.clientRepository.existsById(clientId)) {
            this.clientRepository.deleteById(clientId);
            return;
        }
        throw new NotFoundException("Client with ID '" + clientId + "' not found.");
    }

    @Override
    public ClientResponse updateClient(Integer clientId, ClientRequest updatedClient) {
        if (this.clientExists(clientId)) {
            Client client = this.clientRepository.getById(clientId);
            if (this.clientRepository.findByCpf(updatedClient.getCpf()) == null) {
                if (updatedClient.getBarberShops() != null) {
                    client = this.updateBarberShops(clientId, (this.clientMapper.toClient(updatedClient)));
                }
                BeanUtils.copyProperties((this.clientMapper.toClient(updatedClient)), client, searchEmptyFields(updatedClient));
                return this.clientMapper.toClientResponse((this.clientRepository.save(client)));
            }
            throw new AlreadyExistsException("Client with CPF '" + updatedClient.getCpf() + "' exists.");
        }
        throw new NotFoundException("Client with ID '" + clientId + "' not found.");
    }

    private Client updateBarberShops(Integer clientId, Client updatedClient) {
        Client client = this.clientRepository.getById(clientId);
        for (BarberShop barberShopUpdt : updatedClient.getBarberShops()) {
            Set<Client> clients = new HashSet<>();
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
