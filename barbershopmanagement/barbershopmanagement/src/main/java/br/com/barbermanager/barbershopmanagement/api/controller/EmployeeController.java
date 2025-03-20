package br.com.barbermanager.barbershopmanagement.api.controller;


import br.com.barbermanager.barbershopmanagement.api.request.employee.EmployeeRequest;
import br.com.barbermanager.barbershopmanagement.api.response.employee.EmployeeResponse;
import br.com.barbermanager.barbershopmanagement.api.response.employee.EmployeeSimple;
import br.com.barbermanager.barbershopmanagement.config.security.SecurityConfiguration;
import br.com.barbermanager.barbershopmanagement.domain.model.StatusEnum;
import br.com.barbermanager.barbershopmanagement.domain.model.validations.EmployeeCreate;
import br.com.barbermanager.barbershopmanagement.domain.model.validations.EmployeeUpdate;
import br.com.barbermanager.barbershopmanagement.domain.service.employee.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@Validated
@RequestMapping("/employee")
@SecurityRequirement(name = SecurityConfiguration.SECURITY)
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Operation(summary = "Criar Funcionário", description = "Cria um novo Funcionário", tags = "Funcionário")
    @Parameter(description = "Necessário informar a Barbearia do Funcionário após o campo 'phone'",example = ",\"barberShop\":{ \"barberShopId\": \"1\" }")
    @PostMapping("/new")
    public ResponseEntity<EmployeeResponse> newEmployee(@RequestBody @Validated(EmployeeCreate.class) EmployeeRequest newEmployee) {
        EmployeeResponse response = this.employeeService.createEmployee(newEmployee);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getEmployeeId())
                .toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @Operation(summary = "Buscar Funcionários", description = "Busca todos os Funcionários", tags = "Funcionário")
    @GetMapping("/all")
    public ResponseEntity<List<EmployeeSimple>> allEmployees(
            @Parameter(description = "Status dos Funcionários",example = "ACTIVE")
            @RequestParam(required = false) StatusEnum status) {
        return ResponseEntity.ok(this.employeeService.allEmployees(status));
    }

    @Operation(summary = "Buscar por ID", description = "Busca Funcionários por ID", tags = "Funcionário")
    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeResponse> employeeById(
            @Parameter(description = "ID do Funcionário", example = "1")
            @PathVariable Integer employeeId) {
        return ResponseEntity.ok(this.employeeService.employeeById(employeeId));
    }

    @Operation(summary = "Buscar por Barbearia", description = "Busca Funcionários por Barbearia", tags = "Funcionário")
    @GetMapping("/barbershop/{barberShopId}")
    public ResponseEntity<List<EmployeeSimple>> employeesByBarberShop(
            @Parameter(description = "Status da Barbearia",example = "ACTIVE")
            @RequestParam(required = false) StatusEnum status,
            @Parameter(description = "ID da Barbearia", example = "1")
            @PathVariable Integer barberShopId) {
        return ResponseEntity.ok(this.employeeService.employeesByBarberShop(barberShopId, status));
    }

    @Operation(summary = "Deletar Funcionário", description = "Deleta um Funcionário específico", tags = "Funcionário")
    @DeleteMapping("/delete/{employeeId}")
    public ResponseEntity deleteEmployee(
            @Parameter(description = "ID do Funcionário", example = "1")
            @PathVariable Integer employeeId) {
        this.employeeService.deleteEmployee(employeeId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Ativar Funcionário", description = "Ativa um Funcionário específico", tags = "Funcionário")
    @PatchMapping("/active-employee/{employeeId}")
    public ResponseEntity activeEmployee(
            @Parameter(description = "ID do Funcionário", example = "1")
            @PathVariable Integer employeeId) {
        this.employeeService.activeEmployee(employeeId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Atualizar Funcionário", description = "Atualiza um Funcionário específico", tags = "Funcionário")
    @PatchMapping("/update/{employeeId}")
    public ResponseEntity<EmployeeResponse> updateEmployee(
            @Parameter(description = "ID do Funcionário", example = "1")
            @PathVariable Integer employeeId,
            @RequestBody @Validated(EmployeeUpdate.class) EmployeeRequest updatedEmployee) {
        this.employeeService.updateEmployee(employeeId, updatedEmployee);
        return ResponseEntity.ok(this.employeeService.employeeById(employeeId));
    }
}