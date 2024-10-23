package br.com.barbermanager.barbershopmanagement.api.controller;

import br.com.barbermanager.barbershopmanagement.api.request.barbershop.BarberShopRequest;
import br.com.barbermanager.barbershopmanagement.api.response.barbershop.BarberShopResponse;
import br.com.barbermanager.barbershopmanagement.api.response.barbershop.BarberShopSimple;
import br.com.barbermanager.barbershopmanagement.domain.model.StatusEnum;
import br.com.barbermanager.barbershopmanagement.domain.model.validations.BarberShopCreate;
import br.com.barbermanager.barbershopmanagement.domain.model.validations.BarberShopUpdate;
import br.com.barbermanager.barbershopmanagement.domain.model.validations.ClientInBarberShop;
import br.com.barbermanager.barbershopmanagement.domain.model.validations.SchedulingUpdate;
import br.com.barbermanager.barbershopmanagement.domain.service.barbershop.BarberShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@Validated
@RequestMapping("/barbershop")
public class BarberShopController {

    @Autowired
    private BarberShopService barberShopService;

    @PostMapping("/new")
    public ResponseEntity<BarberShopResponse> newBarberShop(@RequestBody @Validated(BarberShopCreate.class) BarberShopRequest newBarberShop) {
        BarberShopResponse response = this.barberShopService.createBarberShop(newBarberShop);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getBarberShopId())
                .toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<BarberShopSimple>> allBarberShops(@RequestParam(required = false) StatusEnum status) {
        return ResponseEntity.ok(this.barberShopService.allBarberShops(status));
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<BarberShopSimple>> barberShopsByClient(@RequestParam(required = false) StatusEnum status, @PathVariable Integer clientId){
        return ResponseEntity.ok(this.barberShopService.barberShopsByClient(clientId, status));
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
    @Validated(SchedulingUpdate.class)
    public ResponseEntity<BarberShopResponse> updateBarberShop(@PathVariable Integer barberShopId, @RequestBody @Validated(BarberShopUpdate.class) BarberShopRequest updatedBarberShop) {
        this.barberShopService.updateBarberShop(barberShopId, updatedBarberShop);
        return ResponseEntity.ok(this.barberShopService.barberShopById(barberShopId));
    }

    @PatchMapping("/insert-client/{barberShopId}")
    public ResponseEntity<BarberShopResponse> insertNewClient(@PathVariable Integer barberShopId, @RequestBody @Validated(ClientInBarberShop.class) BarberShopRequest updatedBarberShop) {
        return ResponseEntity.ok(this.barberShopService.updateClientAtBarberShop(barberShopId, updatedBarberShop));
    }

    @DeleteMapping("/remove-client/{barberShopId}/{clientId}")
    public ResponseEntity removeClient(@PathVariable Integer barberShopId, @PathVariable Integer clientId) {
        this.barberShopService.removeClient(barberShopId, clientId);
        return ResponseEntity.ok().build();
    }
}
