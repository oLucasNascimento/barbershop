package br.com.barbermanager.barbershopmanagement.api.controller;

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
    public ResponseEntity<Client> newClient(@RequestBody Client newClient) {
        if ((this.clientService.createClient(newClient)) != null) {
            return ResponseEntity.ok(newClient);
        }
        return ResponseEntity.status(409).build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<Client>> allClients() {
        List<Client> clients = this.clientService.allClients();
        if (clients.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(clients);
    }

    @DeleteMapping("/delete/{clientId}")
    public ResponseEntity excluirCliente(@PathVariable Integer clientId) {
        if (this.clientService.deleteClient(clientId)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PatchMapping("/update/{clientId}")
    public ResponseEntity<Client> atualizarCliente(@PathVariable Integer clientId, @RequestBody Client updatedClient) {
        if ((this.clientService.updateClient(clientId, updatedClient)) != null) {
            return ResponseEntity.ok(this.clientService.clientById(clientId));
        }
        return ResponseEntity.badRequest().build();
    }
}