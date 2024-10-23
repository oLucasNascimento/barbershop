package br.com.barbermanager.barbershopmanagement.api.controller;


import br.com.barbermanager.barbershopmanagement.api.request.employee.EmployeeRequest;
import br.com.barbermanager.barbershopmanagement.api.response.employee.EmployeeResponse;
import br.com.barbermanager.barbershopmanagement.api.response.employee.EmployeeSimple;
import br.com.barbermanager.barbershopmanagement.domain.model.StatusEnum;
import br.com.barbermanager.barbershopmanagement.domain.model.validations.EmployeeCreate;
import br.com.barbermanager.barbershopmanagement.domain.model.validations.EmployeeUpdate;
import br.com.barbermanager.barbershopmanagement.domain.service.employee.EmployeeService;
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
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/new")
    public ResponseEntity<EmployeeResponse> newEmployee(@RequestBody @Validated(EmployeeCreate.class) EmployeeRequest newEmployee) {
        EmployeeResponse response = this.employeeService.createEmployee(newEmployee);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getEmployeeId())
                .toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<EmployeeSimple>> allEmployees(@RequestParam(required = false) StatusEnum status) {
        return ResponseEntity.ok(this.employeeService.allEmployees(status));
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeResponse> employeeById(@PathVariable Integer employeeId) {
        return ResponseEntity.ok(this.employeeService.employeeById(employeeId));
    }

    @GetMapping("/barbershop/{barberShopId}")
    public ResponseEntity<List<EmployeeSimple>> employeesByBarberShop(@RequestParam(required = false) StatusEnum status, @PathVariable Integer barberShopId) {
        return ResponseEntity.ok(this.employeeService.employeesByBarberShop(barberShopId, status));
    }

    @DeleteMapping("/delete/{employeeId}")
    public ResponseEntity deleteEmployee(@PathVariable Integer employeeId) {
        this.employeeService.deleteEmployee(employeeId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/active-employee/{employeeId}")
    public ResponseEntity activeEmployee(@PathVariable Integer employeeId) {
        this.employeeService.activeEmployee(employeeId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/update/{employeeId}")
    public ResponseEntity<EmployeeResponse> updateEmployee(@PathVariable Integer employeeId, @RequestBody @Validated(EmployeeUpdate.class) EmployeeRequest updatedEmployee) {
        this.employeeService.updateEmployee(employeeId, updatedEmployee);
        return ResponseEntity.ok(this.employeeService.employeeById(employeeId));
    }

}
