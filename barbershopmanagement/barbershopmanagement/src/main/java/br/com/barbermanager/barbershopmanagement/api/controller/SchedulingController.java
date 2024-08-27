package br.com.barbermanager.barbershopmanagement.api.controller;

import br.com.barbermanager.barbershopmanagement.api.request.item.ItemRequest;
import br.com.barbermanager.barbershopmanagement.api.request.scheduling.SchedulingRequest;
import br.com.barbermanager.barbershopmanagement.api.response.barbershop.BarberShopSimple;
import br.com.barbermanager.barbershopmanagement.api.response.item.ItemResponse;
import br.com.barbermanager.barbershopmanagement.api.response.scheduling.SchedulingResponse;
import br.com.barbermanager.barbershopmanagement.domain.model.StatusEnum;
import br.com.barbermanager.barbershopmanagement.domain.service.scheduling.SchedulingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/scheduling")
public class SchedulingController {

    @Autowired
    private SchedulingService schedulingService;

    @PostMapping("/new")
    public ResponseEntity<SchedulingResponse> newScheduling(@RequestBody SchedulingRequest newScheduling) {
        return ResponseEntity.ok(this.schedulingService.newScheduling(newScheduling));
    }

    @GetMapping("/all")
    public ResponseEntity<List<SchedulingResponse>> allSchedulings(@RequestParam(required = false) StatusEnum status) {
        if (status == null) {
            return ResponseEntity.ok(this.schedulingService.allSchedulings());
        }
        return ResponseEntity.ok(this.schedulingService.allSchedulingsByStatus(status));
    }

    @GetMapping("/{schedulingId}")
    public ResponseEntity<SchedulingResponse> schedulingById(@PathVariable Integer schedulingId) {
        return ResponseEntity.ok(this.schedulingService.schedulingById(schedulingId));
    }

    @GetMapping("/barbershop/{barberShopId}")
    public ResponseEntity<List<SchedulingResponse>> schedulingsByBarberShop(@RequestParam(required = false) StatusEnum status, @PathVariable Integer barberShopId) {
        return ResponseEntity.ok(this.schedulingService.schedulingsByBarberShop(barberShopId, status));
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<SchedulingResponse>> schedulingsByClient(@RequestParam(required = false) StatusEnum status, @PathVariable Integer clientId) {
        return ResponseEntity.ok(this.schedulingService.schedulingsByClient(clientId, status));
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<SchedulingResponse>> schedulingsByEmployee(@RequestParam(required = false) StatusEnum status, @PathVariable Integer employeeId) {
        return ResponseEntity.ok(this.schedulingService.schedulingsByEmployee(employeeId, status));
    }

    @GetMapping("/item/{itemId}")
    public ResponseEntity<List<SchedulingResponse>> schedulingsByItem(@RequestParam(required = false) StatusEnum status, @PathVariable Integer itemId) {
        return ResponseEntity.ok(this.schedulingService.schedulingsByItem(itemId, status));
    }

    @DeleteMapping("/cancel/{schedulingId}")
    public ResponseEntity cancelScheduling(@PathVariable Integer schedulingId) {
        this.schedulingService.cancelScheduling(schedulingId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/update/{schedulingId}")
    public ResponseEntity updateScheduling(@PathVariable Integer schedulingId, @RequestBody SchedulingRequest schedulingUpdated) {
        this.schedulingService.updateScheduling(schedulingId, schedulingUpdated);
        return ResponseEntity.ok(this.schedulingService.schedulingById(schedulingId));
    }

    @PatchMapping("/finish/{schedulingId}")
    public ResponseEntity finishScheduling(@PathVariable Integer schedulingId) {
        this.schedulingService.finishScheduling(schedulingId);
        return ResponseEntity.ok().build();
    }

}
