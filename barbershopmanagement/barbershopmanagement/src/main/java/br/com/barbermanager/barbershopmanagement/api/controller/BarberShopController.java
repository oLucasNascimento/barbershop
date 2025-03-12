package br.com.barbermanager.barbershopmanagement.api.controller;

import br.com.barbermanager.barbershopmanagement.api.request.barbershop.BarberShopRequest;
import br.com.barbermanager.barbershopmanagement.api.response.barbershop.BarberShopResponse;
import br.com.barbermanager.barbershopmanagement.api.response.barbershop.BarberShopSimple;
import br.com.barbermanager.barbershopmanagement.domain.model.StatusEnum;
import br.com.barbermanager.barbershopmanagement.domain.model.validations.BarberShopCreate;
import br.com.barbermanager.barbershopmanagement.domain.model.validations.BarberShopUpdate;
import br.com.barbermanager.barbershopmanagement.domain.model.validations.ClientInBarberShop;
import br.com.barbermanager.barbershopmanagement.domain.service.barbershop.BarberShopService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @Operation(summary = "Criar Barbearia", description = "Cria uma nova Barbearia", tags = "Barbearia")
    @PostMapping("/new")
    public ResponseEntity<BarberShopResponse> newBarberShop(
            @RequestBody @Validated(BarberShopCreate.class) BarberShopRequest newBarberShop) {
        BarberShopResponse response = this.barberShopService.createBarberShop(newBarberShop);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getBarberShopId())
                .toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @Operation(summary = "Buscar Barbearia", description = "Busca todas as Barbearias", tags = "Barbearia")
    @GetMapping("/all")
    public ResponseEntity<List<BarberShopSimple>> allBarberShops(
            @Parameter(description = "Status das Barbearias", example = "ACTIVE")
            @RequestParam(required = false) StatusEnum status) {
        return ResponseEntity.ok(this.barberShopService.allBarberShops(status));
    }

    @Operation(summary = "Buscar por Cliente", description = "Busca as Barbearias de um Cliente", tags = "Barbearia")
    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<BarberShopSimple>> barberShopsByClient(
            @Parameter(description = "Status da Barbearia", example = "ACTIVE")
            @RequestParam(required = false) StatusEnum status,
            @Parameter(description = "ID do Cliente", example = "1")
            @PathVariable Integer clientId) {
        return ResponseEntity.ok(this.barberShopService.barberShopsByClient(clientId, status));
    }

    @Operation(summary = "Buscar por ID", description = "Busca a Barbearia pelo ID informado", tags = "Barbearia")
    @GetMapping("/{barberShopId}")
    public ResponseEntity<BarberShopResponse> barberShopById(
            @Parameter(description = "ID da Barbearia", example = "1")
            @PathVariable Integer barberShopId) {
        return ResponseEntity.ok(this.barberShopService.barberShopById(barberShopId));
    }

    @Operation(summary = "Deletar Barbearia", description = "Exclui a Barbearia com o ID informado", tags = "Barbearia")
    @DeleteMapping("/delete/{barberShopId}")
    public ResponseEntity deleteBarberShop(
            @Parameter(description = "ID da Barbearia para ser deletada", example = "1")
            @PathVariable Integer barberShopId) {
        this.barberShopService.deleteBarberShop(barberShopId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Ativar Barbearia", description = "Ativa a Barbearia especificada", tags = "Barbearia")
    @PatchMapping("/active-barbershop/{barberShopId}")
    public ResponseEntity activeBarberShop(@Parameter(description = "ID da Barbearia para ser ativada", example = "1")
                                           @PathVariable Integer barberShopId) {
        this.barberShopService.activeBarberShop(barberShopId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Demitir Funcionário", description = "Demite o Funcionário de uma Barbearia", tags = "Barbearia")
    @DeleteMapping("/dismiss/{barberShopId}/{employeeId}")
    public ResponseEntity dismissEmployee(
            @Parameter(description = "ID da Barbearia para demitir o Funcionário", example = "1")
            @PathVariable Integer barberShopId,
            @Parameter(description = "ID do Funcionário a ser demitido", example = "1")
            @PathVariable Integer employeeId) {
        this.barberShopService.dismissEmployee(barberShopId, employeeId);
        return ResponseEntity.ok().build();
    }


    @Operation(summary = "Atualizar Barbearia", description = "Atualiza os campos da Barbearia", tags = "Barbearia")
    @PatchMapping("/update/{barberShopId}")
    public ResponseEntity<BarberShopResponse> updateBarberShop(
            @Parameter(description = "ID da barbearia para atualizar", example = "1")
            @PathVariable Integer barberShopId,
            @RequestBody @Validated(BarberShopUpdate.class) BarberShopRequest updatedBarberShop) {
        this.barberShopService.updateBarberShop(barberShopId, updatedBarberShop);
        return ResponseEntity.ok(this.barberShopService.barberShopById(barberShopId));
    }

    @Operation(summary = "Inserir Cliente", description = "Insere o Cliente em uma Barbearia", tags = "Barbearia")
    @PatchMapping("/insert-client/{barberShopId}")
    public ResponseEntity<BarberShopResponse> insertNewClient(
            @Parameter(description = "ID da barbearia para inserir o Cliente", example = "1")
            @PathVariable Integer barberShopId,
            @RequestBody @Validated(ClientInBarberShop.class) BarberShopRequest updatedBarberShop) {
        return ResponseEntity.ok(this.barberShopService.updateClientAtBarberShop(barberShopId, updatedBarberShop));
    }

    @Operation(summary = "Remover Cliente", description = "Exclui o Cliente de uma Barbearia", tags = "Barbearia")
    @DeleteMapping("/remove-client/{barberShopId}/{clientId}")
    public ResponseEntity removeClient(
            @Parameter(description = "ID da Barbearia para remover o Cliente", example = "1")
            @PathVariable Integer barberShopId,
            @Parameter(description = "ID do Cliente a ser removido", example = "1")
            @PathVariable Integer clientId) {
        this.barberShopService.removeClient(barberShopId, clientId);
        return ResponseEntity.ok().build();
    }
}