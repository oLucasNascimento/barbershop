package br.com.barbermanager.barbershopmanagement.api.controller;

import br.com.barbermanager.barbershopmanagement.api.request.client.ClientRequest;
import br.com.barbermanager.barbershopmanagement.api.response.client.ClientResponse;
import br.com.barbermanager.barbershopmanagement.domain.model.Client;
import br.com.barbermanager.barbershopmanagement.domain.service.client.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/new")
    public ResponseEntity<ClientResponse> newClient(@RequestBody ClientRequest newClient) {
        ClientResponse clientResponse = this.clientService.createClient(newClient);
        if (clientResponse != null) {
            return ResponseEntity.ok(clientResponse);
        }
        return ResponseEntity.status(409).build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<ClientResponse>> allClients() {
        List<ClientResponse> clients = this.clientService.allClients();
        if (clients.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(clients);
    }

    @DeleteMapping("/delete/{clientId}")
    public ResponseEntity deleteClient(@PathVariable Integer clientId) {
        if (this.clientService.deleteClient(clientId)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PatchMapping("/update/{clientId}")
    public ResponseEntity<ClientResponse> updateClient(@PathVariable Integer clientId, @RequestBody ClientRequest updatedClient) {
        if ((this.clientService.updateClient(clientId, updatedClient)) != null) {
            return ResponseEntity.ok(this.clientService.clientById(clientId));
        }
        return ResponseEntity.badRequest().build();
    }
}