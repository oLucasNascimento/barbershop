package br.com.barbermanager.barbershopmanagement.domain.service.client;

import br.com.barbermanager.barbershopmanagement.domain.model.BarberShop;
import br.com.barbermanager.barbershopmanagement.domain.model.Client;
import br.com.barbermanager.barbershopmanagement.domain.repository.ClientRepository;
import br.com.barbermanager.barbershopmanagement.domain.service.barbershop.BarberShopService;
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

    @Override
    public Boolean clientExists(Integer clientId) {
        return this.clientRepository.existsById(clientId);
    }

    @Override
    public Client createClient(Client newClient) {
        if ((this.clientRepository.findByCpf(newClient.getCpf())) == null) {
            return this.clientRepository.save(newClient);
        }
        return null;
    }

    @Override
    public List<Client> allClients() {
        return this.clientRepository.findAll();
    }

    @Override
    public Client clientById(Integer clientId) {
        if (this.clientRepository.existsById(clientId)) {
            return this.clientRepository.getById(clientId);

        }
        return null;
    }

    @Override
    public Boolean deleteClient(Integer clientId) {
        if (this.clientRepository.existsById(clientId)) {
            this.clientRepository.deleteById(clientId);
            return true;
        }
        return false;
    }
    @Override
    public Client updateClient(Integer clientId, Client updatedClient) {
        if (this.clientExists(clientId)) {
            Client client = this.clientById(clientId);
            if (this.clientRepository.findByCpf(updatedClient.getCpf()) == null) {
                if (updatedClient.getBarberShops() != null) {
                    client = this.updateBarberShops(clientId, updatedClient);
                }
                BeanUtils.copyProperties(updatedClient, client, searchEmptyFields(updatedClient));
                return this.clientRepository.save(client);
            }
        }
        return null;
    }

    private Client updateBarberShops(Integer clientId, Client updatedClient) {
        Client client = this.clientById(clientId);
        for (BarberShop barberShopUpdt : updatedClient.getBarberShops()) {
            Set<Client> clients = new HashSet<>();
            clients.add(client);
            BarberShop barberShop = this.barberShopService.barberShopById(barberShopUpdt.getBarberShopId());
            barberShop.setClients(clients);
            this.barberShopService.udpateClientAtBarberShop(barberShop.getBarberShopId(), barberShop);
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
