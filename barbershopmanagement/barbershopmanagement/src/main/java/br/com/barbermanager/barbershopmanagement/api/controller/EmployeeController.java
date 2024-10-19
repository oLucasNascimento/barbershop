package br.com.barbermanager.barbershopmanagement.api.controller;


import br.com.barbermanager.barbershopmanagement.api.request.employee.EmployeeRequest;
import br.com.barbermanager.barbershopmanagement.api.response.employee.EmployeeResponse;
import br.com.barbermanager.barbershopmanagement.api.response.employee.EmployeeSimple;
import br.com.barbermanager.barbershopmanagement.domain.model.StatusEnum;
import br.com.barbermanager.barbershopmanagement.domain.model.validations.AssociatedUpdate;
import br.com.barbermanager.barbershopmanagement.domain.model.validations.OnCreate;
import br.com.barbermanager.barbershopmanagement.domain.service.employee.EmployeeService;
import br.com.barbermanager.barbershopmanagement.exception.NotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/new")
    @Validated(OnCreate.class)
    public ResponseEntity<EmployeeResponse> newEmployee(@RequestBody @Valid EmployeeRequest newEmployee) {
        return ResponseEntity.ok(this.employeeService.createEmployee(newEmployee));
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

    @PatchMapping("/update/{employeeId}")
    @Validated(AssociatedUpdate.class)
    public ResponseEntity<EmployeeResponse> updateEmployee(@PathVariable Integer employeeId, @RequestBody @Valid EmployeeRequest updatedEmployee) {
        this.employeeService.updateEmployee(employeeId, updatedEmployee);
        return ResponseEntity.ok(this.employeeService.employeeById(employeeId));
    }

    @PatchMapping("/active-employee/{employeeId}")
    public ResponseEntity activeEmployee(@PathVariable Integer employeeId) {
        this.employeeService.activeEmployee(employeeId);
        return ResponseEntity.ok().build();
    }

}
