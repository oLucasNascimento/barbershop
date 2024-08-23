package br.com.barbermanager.barbershopmanagement.api.controller;

import br.com.barbermanager.barbershopmanagement.api.request.barbershop.BarberShopRequest;
import br.com.barbermanager.barbershopmanagement.api.response.barbershop.BarberShopResponse;
import br.com.barbermanager.barbershopmanagement.api.response.barbershop.BarberShopSimple;
import br.com.barbermanager.barbershopmanagement.api.response.client.ClientSimple;
import br.com.barbermanager.barbershopmanagement.api.response.item.ItemSimple;
import br.com.barbermanager.barbershopmanagement.domain.model.Scheduling;
import br.com.barbermanager.barbershopmanagement.domain.model.StatusEnum;
import br.com.barbermanager.barbershopmanagement.domain.service.barbershop.BarberShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/barbershop")
public class BarberShopController {

    @Autowired
    private BarberShopService barberShopService;

    @PostMapping("/new")
    public ResponseEntity<BarberShopResponse> newBarberShop(@RequestBody BarberShopRequest newBarberShop) {
        return ResponseEntity.ok(this.barberShopService.createBarberShop(newBarberShop));
    }

    @GetMapping("/all")
    public ResponseEntity<List<BarberShopSimple>> allBarberShops() {
        return ResponseEntity.ok(this.barberShopService.allBarberShops());
    }

    @GetMapping("/all/status")
    public ResponseEntity<List<BarberShopSimple>> barberShopsByStatus(@RequestParam StatusEnum status){
        return ResponseEntity.ok(this.barberShopService.allBarberShopsByStatus(status));
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<BarberShopSimple>> clientsByBarberShop(@PathVariable Integer clientId){
        return ResponseEntity.ok(this.barberShopService.barberShopsByClient(clientId));
    }

    @GetMapping("/client/{clientId}/status")
    public ResponseEntity<List<BarberShopSimple>> clientsByBarberShopAndStatus(@PathVariable Integer clientId, @RequestParam StatusEnum status){
        return ResponseEntity.ok(this.barberShopService.barberShopsByClientAndStatus(clientId, status));
    }

    @GetMapping("/{barberShopId}")
    public ResponseEntity<BarberShopResponse> barberShopById(@PathVariable Integer barberShopId) {
        return ResponseEntity.ok(this.barberShopService.barberShopById(barberShopId));
    }

    @DeleteMapping("/delete/{barberShopId}")
    public ResponseEntity deleteBarberShop(@PathVariable Integer barberShopId) {
        this.barberShopService.deleteBarberShop(barberShopId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/active-barbershop/{barberShopId}")
    public ResponseEntity activeBarberShop(@PathVariable Integer barberShopId){
        this.barberShopService.activeBarberShop(barberShopId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/dismiss/{barberShopId}/{employeeId}")
    public ResponseEntity dismissEmployee(@PathVariable Integer barberShopId, @PathVariable Integer employeeId) {
        this.barberShopService.dismissEmployee(barberShopId, employeeId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/update/{barberShopId}")
    public ResponseEntity<BarberShopResponse> updateBarberShop(@PathVariable Integer barberShopId, @RequestBody BarberShopRequest updatedBarberShop) {
        this.barberShopService.updateBarberShop(barberShopId, updatedBarberShop);
        return ResponseEntity.ok(this.barberShopService.barberShopById(barberShopId));
    }

    @PatchMapping("/insert-client/{barberShopId}")
    public ResponseEntity<BarberShopResponse> insertNewClient(@PathVariable Integer barberShopId, @RequestBody BarberShopRequest updatedBarberShop) {
        return ResponseEntity.ok(this.barberShopService.udpateClientAtBarberShop(barberShopId, updatedBarberShop));
    }


    @DeleteMapping("/remove-client/{barberShopId}/{clientId}")
    public ResponseEntity removeClient(@PathVariable Integer barberShopId, @PathVariable Integer clientId) {
        this.barberShopService.removeClient(barberShopId, clientId);
        return ResponseEntity.ok().build();
    }
}
