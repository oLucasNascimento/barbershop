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
        SchedulingResponse schedulingResponse = this.schedulingService.newScheduling(newScheduling);
        if ( schedulingResponse != null) {
            return ResponseEntity.ok(schedulingResponse);
        }
        return ResponseEntity.status(409).build();

    }

    @GetMapping("/all")
    public ResponseEntity<List<SchedulingResponse>> allSchedulings(){
        List<SchedulingResponse> schedulings = this.schedulingService.allSchedulings();
        if(!(schedulings.isEmpty())){
            return ResponseEntity.ok(schedulings);
        }
        return ResponseEntity.notFound().build();
    }


}
