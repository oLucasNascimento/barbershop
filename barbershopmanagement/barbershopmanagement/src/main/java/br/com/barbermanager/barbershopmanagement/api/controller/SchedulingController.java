package br.com.barbermanager.barbershopmanagement.api.controller;

import br.com.barbermanager.barbershopmanagement.api.request.item.ItemRequest;
import br.com.barbermanager.barbershopmanagement.api.request.scheduling.SchedulingRequest;
import br.com.barbermanager.barbershopmanagement.api.response.item.ItemResponse;
import br.com.barbermanager.barbershopmanagement.api.response.scheduling.SchedulingResponse;
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
    public ResponseEntity<List<SchedulingResponse>> allSchedulings() {
        return ResponseEntity.ok(this.schedulingService.allSchedulings());
    }

    @GetMapping("/{schedulingId}")
    public ResponseEntity<SchedulingResponse> schedulingById(@PathVariable Integer schedulingId){
        return ResponseEntity.ok(this.schedulingService.schedulingById(schedulingId));
    }

    @DeleteMapping("/delete/{schedulingId}")
    public ResponseEntity deleteScheduling(@PathVariable Integer schedulingId){
        this.schedulingService.deleteScheduling(schedulingId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/update/{schedulingId}")
    public ResponseEntity updateScheduling(@PathVariable Integer schedulingId, @RequestBody SchedulingRequest schedulingUpdated){
        this.schedulingService.updateScheduling(schedulingId,schedulingUpdated);
        return ResponseEntity.ok(this.schedulingService.schedulingById(schedulingId));
    }

}
