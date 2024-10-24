package br.com.barbermanager.barbershopmanagement.api.controller;

import br.com.barbermanager.barbershopmanagement.api.request.scheduling.SchedulingRequest;
import br.com.barbermanager.barbershopmanagement.api.response.scheduling.SchedulingResponse;
import br.com.barbermanager.barbershopmanagement.domain.model.StatusEnum;
import br.com.barbermanager.barbershopmanagement.domain.model.validations.SchedulingCreate;
import br.com.barbermanager.barbershopmanagement.domain.model.validations.SchedulingUpdate;
import br.com.barbermanager.barbershopmanagement.domain.service.scheduling.SchedulingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@Validated
@RequestMapping("/scheduling")
public class SchedulingController {

    @Autowired
    private SchedulingService schedulingService;

    @PostMapping("/new")
    public ResponseEntity<SchedulingResponse> newScheduling(@RequestBody @Validated(SchedulingCreate.class) SchedulingRequest newScheduling) {
        SchedulingResponse response = this.schedulingService.newScheduling(newScheduling);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getSchedulingId())
                .toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<SchedulingResponse>> allSchedulings(@RequestParam(required = false) StatusEnum status) {
        return ResponseEntity.ok(this.schedulingService.allSchedulings(status));
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
    public ResponseEntity<SchedulingResponse> updateScheduling(@PathVariable Integer schedulingId, @RequestBody @Validated(SchedulingUpdate.class) SchedulingRequest schedulingUpdated) {
        this.schedulingService.updateScheduling(schedulingId, schedulingUpdated);
        return ResponseEntity.ok(this.schedulingService.schedulingById(schedulingId));
    }

    @PatchMapping("/finish/{schedulingId}")
    public ResponseEntity finishScheduling(@PathVariable Integer schedulingId) {
        this.schedulingService.finishScheduling(schedulingId);
        return ResponseEntity.ok().build();
    }

}
