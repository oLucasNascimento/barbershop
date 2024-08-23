package br.com.barbermanager.barbershopmanagement.api.controller;

import br.com.barbermanager.barbershopmanagement.api.request.client.ClientRequest;
import br.com.barbermanager.barbershopmanagement.api.response.barbershop.BarberShopResponse;
import br.com.barbermanager.barbershopmanagement.api.response.client.ClientResponse;
import br.com.barbermanager.barbershopmanagement.api.response.client.ClientSimple;
import br.com.barbermanager.barbershopmanagement.api.response.item.ItemSimple;
import br.com.barbermanager.barbershopmanagement.domain.model.Client;
import br.com.barbermanager.barbershopmanagement.domain.model.StatusEnum;
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
        return ResponseEntity.ok(this.clientService.createClient(newClient));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ClientSimple>> allClients() {
        return ResponseEntity.ok(this.clientService.allClients());
    }

    @GetMapping("/all/status")
    public ResponseEntity<List<ClientSimple>> clientsByStatus(@RequestParam StatusEnum status){
        return ResponseEntity.ok(this.clientService.allclientsByStatus(status));
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<ClientResponse> barberShopById(@PathVariable Integer clientId) {
        return ResponseEntity.ok(this.clientService.clientById(clientId));
    }

    @GetMapping("/barber-shop/{barberShopId}")
    public ResponseEntity<List<ClientSimple>> clientsByBarberShop(@PathVariable Integer barberShopId){
        return ResponseEntity.ok(this.clientService.clientsByBarberShop(barberShopId));
    }

    @GetMapping("/barber-shop/{barberShopId}/status")
    public ResponseEntity<List<ClientSimple>> clientsByBarberShopAndStatus(@PathVariable Integer barberShopId, @RequestParam StatusEnum status){
        return ResponseEntity.ok(this.clientService.clientsByBarberShopAndStatus(barberShopId, status));
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
    public ResponseEntity<ClientResponse> updateClient(@PathVariable Integer clientId, @RequestBody ClientRequest updatedClient) {
        this.clientService.updateClient(clientId, updatedClient);
        return ResponseEntity.ok(this.clientService.clientById(clientId));
    }
}