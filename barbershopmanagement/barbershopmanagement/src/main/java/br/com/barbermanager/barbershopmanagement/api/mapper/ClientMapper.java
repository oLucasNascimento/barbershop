package br.com.barbermanager.barbershopmanagement.api.mapper;

import br.com.barbermanager.barbershopmanagement.api.request.client.ClientRequest;
import br.com.barbermanager.barbershopmanagement.api.response.client.ClientResponse;
import br.com.barbermanager.barbershopmanagement.domain.model.Client;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ClientMapper {

    private final ModelMapper mapper;

    public ClientMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public Client toClient(Object client){
        return this.mapper.map(client, Client.class);
    }

    public ClientRequest toClientRequest(Object client){
        return this.mapper.map(client, ClientRequest.class);
    }

    public ClientResponse toClientResponse(Object client){
        return this.mapper.map(client, ClientResponse.class);
    }

    public List<ClientResponse> toClientResponseList(List<Client> clientList){
        return clientList.stream().map(this::toClientResponse).collect(Collectors.toList());
    }

}
