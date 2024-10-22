package br.com.barbermanager.barbershopmanagement.api.controller;

import br.com.barbermanager.barbershopmanagement.api.request.client.ClientRequest;
import br.com.barbermanager.barbershopmanagement.api.response.client.ClientResponse;
import br.com.barbermanager.barbershopmanagement.api.response.client.ClientSimple;
import br.com.barbermanager.barbershopmanagement.domain.model.StatusEnum;
import br.com.barbermanager.barbershopmanagement.domain.model.validations.SchedulingUpdate;
import br.com.barbermanager.barbershopmanagement.domain.model.validations.OnCreate;
import br.com.barbermanager.barbershopmanagement.domain.service.client.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/new")
    @Validated(OnCreate.class)
    public ResponseEntity<ClientResponse> newClient(@RequestBody @Valid ClientRequest newClient) {
        return ResponseEntity.ok(this.clientService.createClient(newClient));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ClientSimple>> allClients(@RequestParam(required = false) StatusEnum status) {
        return ResponseEntity.ok(this.clientService.allClients(status));
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<ClientResponse> barberShopById(@PathVariable Integer clientId) {
        return ResponseEntity.ok(this.clientService.clientById(clientId));
    }

    @GetMapping("/barber-shop/{barberShopId}")
    public ResponseEntity<List<ClientSimple>> clientsByBarberShop(@RequestParam(required = false) StatusEnum status, @PathVariable Integer barberShopId){
        return ResponseEntity.ok(this.clientService.clientsByBarberShop(barberShopId, status));
    }

    @DeleteMapping("/delete/{clientId}")
    public ResponseEntity deleteClient(@PathVariable Integer clientId) {
        this.clientService.deleteClient(clientId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/active-client/{clientId}")
    public ResponseEntity activeClient(@PathVariable Integer clientId){
        this.clientService.activeClient(clientId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/update/{clientId}")
    @Validated(SchedulingUpdate.class)
    public ResponseEntity<ClientResponse> updateClient(@PathVariable Integer clientId, @RequestBody @Valid ClientRequest updatedClient) {
        this.clientService.updateClient(clientId, updatedClient);
        return ResponseEntity.ok(this.clientService.clientById(clientId));
    }
}