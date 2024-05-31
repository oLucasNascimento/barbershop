package br.com.barbermanager.barbershopmanagement.api.controller;

import br.com.barbermanager.barbershopmanagement.api.request.barbershop.BarberShopRequest;
import br.com.barbermanager.barbershopmanagement.api.response.barbershop.BarberShopResponse;
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
        BarberShopResponse barberShop = this.barberShopService.createBarberShop(newBarberShop);
        if ((barberShop != null)) {
            return ResponseEntity.ok(barberShop);
        }
        return ResponseEntity.status(409).build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<BarberShopResponse>> allBarberShops() {
        List<BarberShopResponse> barberShops = this.barberShopService.allBarberShops();
        if (barberShops.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(barberShops);
    }

    @DeleteMapping("/delete/{barberShopId}")
    public ResponseEntity deleteBarberShop(@PathVariable Integer barberShopId) {
        if ((this.barberShopService.barberShopExists(barberShopId))) {
            this.barberShopService.deleteBarberShop(barberShopId);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/dismiss/{barberShopId}/{employeeId}")
    public ResponseEntity dismissEmployee(@PathVariable Integer barberShopId, @PathVariable Integer employeeId) {
        if ((this.barberShopService.barberShopExists(barberShopId))) {
            if (this.barberShopService.dismissEmployee(barberShopId, employeeId)) {
                return ResponseEntity.ok().build();
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/update/{barberShopId}")
    public ResponseEntity<BarberShopResponse> updateBarberShop(@PathVariable Integer barberShopId, @RequestBody BarberShopRequest updatedBarberShop) {
        if (((this.barberShopService.updateBarberShop(barberShopId, updatedBarberShop)) != null)) {
            return ResponseEntity.ok(this.barberShopService.barberShopById(barberShopId));
        }
        return ResponseEntity.badRequest().build();
    }

    @PatchMapping("/insert-client/{barberShopId}")
    public ResponseEntity<BarberShopResponse> insertNewClient(@PathVariable Integer barberShopId, @RequestBody BarberShopRequest updatedBarberShop) {
        if (this.barberShopService.barberShopExists(barberShopId)) {
            BarberShopResponse barberShopResponse = this.barberShopService.udpateClientAtBarberShop(barberShopId, updatedBarberShop);
            if ((barberShopResponse != null)) {
                return ResponseEntity.ok(barberShopResponse);
            }
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/remove-client/{barberShopId}/{clientId}")
    public ResponseEntity removeClient(@PathVariable Integer barberShopId, @PathVariable Integer clientId) {
        if ((this.barberShopService.barberShopExists(barberShopId))) {
            this.barberShopService.removeClient(barberShopId, clientId);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
