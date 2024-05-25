package br.com.barbermanager.barbershopmanagement.domain.service.client;

import br.com.barbermanager.barbershopmanagement.domain.model.Client;

import java.util.List;

public interface ClientService {

    Boolean clientExists(Integer clientId);

    Client createClient(Client newClient);

    List<Client> allClients();

    Client clientById(Integer clientId);

    Boolean deleteClient(Integer clientId);

    Client updateClient(Integer clientId, Client updatedClient);

}
