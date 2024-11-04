package br.com.barbermanager.barbershopmanagement.api.controller;

import br.com.barbermanager.barbershopmanagement.api.request.scheduling.SchedulingRequest;
import br.com.barbermanager.barbershopmanagement.api.response.scheduling.SchedulingResponse;
import br.com.barbermanager.barbershopmanagement.domain.model.StatusEnum;
import br.com.barbermanager.barbershopmanagement.domain.model.validations.SchedulingCreate;
import br.com.barbermanager.barbershopmanagement.domain.model.validations.SchedulingUpdate;
import br.com.barbermanager.barbershopmanagement.domain.service.scheduling.SchedulingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

    @Operation(summary = "Criar Agendamento", description = "Cria um novo Agendamento", tags = "Agendamento")
    @PostMapping("/new")
    public ResponseEntity<SchedulingResponse> newScheduling(@RequestBody @Validated(SchedulingCreate.class) SchedulingRequest newScheduling) {
        SchedulingResponse response = this.schedulingService.newScheduling(newScheduling);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getSchedulingId())
                .toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @Operation(summary = "Buscar Agendamentos", description = "Busca todos Agendamentos", tags = "Agendamento")
    @GetMapping("/all")
    public ResponseEntity<List<SchedulingResponse>> allSchedulings(
            @Parameter(description = "Status dos Agendamentos", example = "SCHEDULED")
            @RequestParam(required = false) StatusEnum status) {
        return ResponseEntity.ok(this.schedulingService.allSchedulings(status));
    }

    @Operation(summary = "Agendamento por ID", description = "Busca Agendamento por ID", tags = "Agendamento")
    @GetMapping("/{schedulingId}")
    public ResponseEntity<SchedulingResponse> schedulingById(
            @Parameter(description = "ID do Agendamento", example = "1")
            @PathVariable Integer schedulingId) {
        return ResponseEntity.ok(this.schedulingService.schedulingById(schedulingId));
    }

    @Operation(summary = "Agendamentos por Barbearia", description = "Busca Agendamentos por Barbearia", tags = "Agendamento")
    @GetMapping("/barbershop/{barberShopId}")
    public ResponseEntity<List<SchedulingResponse>> schedulingsByBarberShop(
            @Parameter(description = "Status dos Agendamentos", example = "SCHEDULED")
            @RequestParam(required = false) StatusEnum status,
            @Parameter(description = "ID da Barbearia", example = "1")
            @PathVariable Integer barberShopId) {
        return ResponseEntity.ok(this.schedulingService.schedulingsByBarberShop(barberShopId, status));
    }

    @Operation(summary = "Agendamentos por Cliente", description = "Busca Agendamentos por Cliente", tags = "Agendamento")
    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<SchedulingResponse>> schedulingsByClient(
            @Parameter(description = "Status dos Agendamentos", example = "SCHEDULED")
            @RequestParam(required = false) StatusEnum status,
            @Parameter(description = "ID do Cliente", example = "1")
            @PathVariable Integer clientId) {
        return ResponseEntity.ok(this.schedulingService.schedulingsByClient(clientId, status));
    }

    @Operation(summary = "Agendamentos por Funcionário", description = "Busca Agendamentos por Funcionário", tags = "Agendamento")
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<SchedulingResponse>> schedulingsByEmployee(
            @Parameter(description = "Status dos Agendamentos", example = "SCHEDULED")
            @RequestParam(required = false) StatusEnum status,
            @Parameter(description = "ID do Funcionário", example = "1")
            @PathVariable Integer employeeId) {
        return ResponseEntity.ok(this.schedulingService.schedulingsByEmployee(employeeId, status));
    }

    @Operation(summary = "Agendamentos por Serviço", description = "Busca Agendamentos por Serviço", tags = "Agendamento")
    @GetMapping("/item/{itemId}")
    public ResponseEntity<List<SchedulingResponse>> schedulingsByItem(
            @Parameter(description = "Status dos Agendamentos", example = "SCHEDULED")
            @RequestParam(required = false) StatusEnum status,
            @Parameter(description = "ID do Serviço", example = "1")
            @PathVariable Integer itemId) {
        return ResponseEntity.ok(this.schedulingService.schedulingsByItem(itemId, status));
    }

    @Operation(summary = "Cancelar Agendamento", description = "Cancela um Agendamento específico", tags = "Agendamento")
    @DeleteMapping("/cancel/{schedulingId}")
    public ResponseEntity cancelScheduling(
            @Parameter(description = "ID do Agendamento", example = "1")
            @PathVariable Integer schedulingId) {
        this.schedulingService.cancelScheduling(schedulingId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Atualizar Agendamento", description = "Atualiza um Agendamento específico", tags = "Agendamento")
    @PatchMapping("/update/{schedulingId}")
    public ResponseEntity<SchedulingResponse> updateScheduling(
            @Parameter(description = "ID do Agendamento", example = "1")
            @PathVariable Integer schedulingId,
            @RequestBody @Validated(SchedulingUpdate.class) SchedulingRequest schedulingUpdated) {
        this.schedulingService.updateScheduling(schedulingId, schedulingUpdated);
        return ResponseEntity.ok(this.schedulingService.schedulingById(schedulingId));
    }

    @Operation(summary = "Finalizar Agendamento", description = "Finaliza um Agendamento específico", tags = "Agendamento")
    @PatchMapping("/finish/{schedulingId}")
    public ResponseEntity finishScheduling(
            @Parameter(description = "ID do Agendamento", example = "1")
            @PathVariable Integer schedulingId) {
        this.schedulingService.finishScheduling(schedulingId);
        return ResponseEntity.ok().build();
    }

}
