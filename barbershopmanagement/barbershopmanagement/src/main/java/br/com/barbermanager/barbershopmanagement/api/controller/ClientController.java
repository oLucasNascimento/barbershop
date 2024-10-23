package br.com.barbermanager.barbershopmanagement.api.controller;

import br.com.barbermanager.barbershopmanagement.api.request.client.ClientRequest;
import br.com.barbermanager.barbershopmanagement.api.response.client.ClientResponse;
import br.com.barbermanager.barbershopmanagement.api.response.client.ClientSimple;
import br.com.barbermanager.barbershopmanagement.domain.model.StatusEnum;
import br.com.barbermanager.barbershopmanagement.domain.model.validations.ClientCreate;
import br.com.barbermanager.barbershopmanagement.domain.model.validations.ClientUpdate;
import br.com.barbermanager.barbershopmanagement.domain.service.client.ClientService;
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

    @PostMapping("/new")
    public ResponseEntity<ClientResponse> newClient(@RequestBody @Validated(ClientCreate.class) ClientRequest newClient) {
        ClientResponse response = this.clientService.createClient(newClient);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getClientId())
                .toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ClientSimple>> allClients(@RequestParam(required = false) StatusEnum status) {
        return ResponseEntity.ok(this.clientService.allClients(status));
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<ClientResponse> clientById(@PathVariable Integer clientId) {
        return ResponseEntity.ok(this.clientService.clientById(clientId));
    }

    @GetMapping("/barber-shop/{barberShopId}")
    public ResponseEntity<List<ClientSimple>> clientsByBarberShop(@RequestParam(required = false) StatusEnum status, @PathVariable Integer barberShopId) {
        return ResponseEntity.ok(this.clientService.clientsByBarberShop(barberShopId, status));
    }

    @DeleteMapping("/delete/{clientId}")
    public ResponseEntity deleteClient(@PathVariable Integer clientId) {
        this.clientService.deleteClient(clientId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/active-client/{clientId}")
    public ResponseEntity activeClient(@PathVariable Integer clientId) {
        this.clientService.activeClient(clientId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/update/{clientId}")
    public ResponseEntity<ClientResponse> updateClient(@PathVariable Integer clientId, @RequestBody @Validated(ClientUpdate.class) ClientRequest updatedClient) {
        this.clientService.updateClient(clientId, updatedClient);
        return ResponseEntity.ok(this.clientService.clientById(clientId));
    }
}