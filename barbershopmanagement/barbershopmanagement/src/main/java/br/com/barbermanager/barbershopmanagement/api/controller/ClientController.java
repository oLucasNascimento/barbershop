package br.com.barbermanager.barbershopmanagement.api.controller;

import br.com.barbermanager.barbershopmanagement.api.request.client.ClientRequest;
import br.com.barbermanager.barbershopmanagement.api.response.client.ClientResponse;
import br.com.barbermanager.barbershopmanagement.api.response.client.ClientSimple;
import br.com.barbermanager.barbershopmanagement.domain.model.StatusEnum;
import br.com.barbermanager.barbershopmanagement.domain.model.validations.ClientCreate;
import br.com.barbermanager.barbershopmanagement.domain.model.validations.ClientUpdate;
import br.com.barbermanager.barbershopmanagement.domain.service.client.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@Validated
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Operation(summary = "Criar Cliente", description = "Cria um novo Cliente", tags = "Cliente")
    @PostMapping("/new")
    public ResponseEntity<ClientResponse> newClient(@RequestBody @Validated(ClientCreate.class) ClientRequest newClient) {
        ClientResponse response = this.clientService.createClient(newClient);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getClientId())
                .toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @Operation(summary = "Buscar Clientes", description = "Busca Todos os Clientes", tags = "Cliente")
    @GetMapping("/all")
    public ResponseEntity<List<ClientSimple>> allClients(
            @Parameter(description = "Status dos Clientes", example = "ACTIVE")
            @RequestParam(required = false) StatusEnum status) {
        return ResponseEntity.ok(this.clientService.allClients(status));
    }

    @Operation(summary = "Buscar por ID", description = "Busca Cliente por ID", tags = "Cliente")
    @GetMapping("/{clientId}")
    public ResponseEntity<ClientResponse> clientById(@Parameter(description = "ID do Cliente", example = "1")
                                                     @PathVariable Integer clientId) {
        return ResponseEntity.ok(this.clientService.clientById(clientId));
    }

    @Operation(summary = "Buscar Clientes por Barbearia", description = "Busca os Clientes de uma Barbearia", tags = "Cliente")
    @GetMapping("/barber-shop/{barberShopId}")
    public ResponseEntity<List<ClientSimple>> clientsByBarberShop(
            @Parameter(description = "Status do Cliente", example = "ACTIVE")
            @RequestParam(required = false) StatusEnum status,
            @Parameter(description = "ID da Barbearia", example = "1")
            @PathVariable Integer barberShopId) {
        return ResponseEntity.ok(this.clientService.clientsByBarberShop(barberShopId, status));
    }

    @Operation(summary = "Deletar Cliente", description = "Apaga o Cliente informado", tags = "Cliente")
    @DeleteMapping("/delete/{clientId}")
    public ResponseEntity deleteClient(@Parameter(description = "ID do Cliente", example = "1")
                                       @PathVariable Integer clientId) {
        this.clientService.deleteClient(clientId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Ativar Cliente", description = "Ativa o Cliente informado", tags = "Cliente")
    @PatchMapping("/active-client/{clientId}")
    public ResponseEntity activeClient(
            @Parameter(description = "ID do Cliente", example = "1")
            @PathVariable Integer clientId) {
        this.clientService.activeClient(clientId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Atualizar Cliente", description = "Atualiza os dados do Cliente", tags = "Cliente")
    @PatchMapping("/update/{clientId}")
    public ResponseEntity<ClientResponse> updateClient(
            @Parameter(description = "ID do Cliente", example = "1")
            @PathVariable Integer clientId,
            @RequestBody @Validated(ClientUpdate.class) ClientRequest updatedClient) {
        this.clientService.updateClient(clientId, updatedClient);
        return ResponseEntity.ok(this.clientService.clientById(clientId));
    }
}