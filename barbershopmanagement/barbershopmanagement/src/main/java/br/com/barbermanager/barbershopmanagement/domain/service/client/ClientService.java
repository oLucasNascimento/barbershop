package br.com.barbermanager.barbershopmanagement.domain.service.client;

import br.com.barbermanager.barbershopmanagement.api.request.client.ClientRequest;
import br.com.barbermanager.barbershopmanagement.api.response.client.ClientResponse;
import br.com.barbermanager.barbershopmanagement.domain.model.Client;

import java.util.List;

public interface ClientService {

    Boolean clientExists(Integer clientId);

    ClientResponse createClient(ClientRequest newClient);

    List<ClientResponse> allClients();

    ClientResponse clientById(Integer clientId);

    void deleteClient(Integer clientId);

    ClientResponse updateClient(Integer clientId, ClientRequest updatedClient);

}
