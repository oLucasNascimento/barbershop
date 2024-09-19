package br.com.barbermanager.barbershopmanagement.api.mapper;

import br.com.barbermanager.barbershopmanagement.api.request.client.ClientRequest;
import br.com.barbermanager.barbershopmanagement.api.response.client.ClientResponse;
import br.com.barbermanager.barbershopmanagement.api.response.client.ClientSimple;
import br.com.barbermanager.barbershopmanagement.domain.model.Client;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring")
public interface ClientMapper {

    Client toClient(ClientRequest client);
    Client toClient(ClientResponse client);

    ClientRequest toClientRequest(Client client);

    ClientResponse toClientResponse(Client client);

    ClientSimple toClientSimple(Client client);

    List<ClientResponse> toClientResponseList(List<Client> clientList);

    List<ClientSimple> toClientSimpleList(List<Client> clientList);

}
