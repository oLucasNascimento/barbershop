package br.com.barbermanager.barbershopmanagement.api.controller;

import br.com.barbermanager.barbershopmanagement.api.request.scheduling.SchedulingRequest;
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


}
