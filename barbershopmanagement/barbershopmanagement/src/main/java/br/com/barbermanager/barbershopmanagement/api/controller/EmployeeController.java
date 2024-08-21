package br.com.barbermanager.barbershopmanagement.api.controller;


import br.com.barbermanager.barbershopmanagement.api.request.employee.EmployeeRequest;
import br.com.barbermanager.barbershopmanagement.api.response.employee.EmployeeResponse;
import br.com.barbermanager.barbershopmanagement.api.response.employee.EmployeeSimple;
import br.com.barbermanager.barbershopmanagement.domain.model.StatusEnum;
import br.com.barbermanager.barbershopmanagement.domain.service.employee.EmployeeService;
import br.com.barbermanager.barbershopmanagement.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/new")
    public ResponseEntity<EmployeeResponse> newEmployee(@RequestBody EmployeeRequest newEmployee) {
        return ResponseEntity.ok(this.employeeService.createEmployee(newEmployee));
    }

    @GetMapping("/all")
    public ResponseEntity<List<EmployeeSimple>> allEmployees() {
        return ResponseEntity.ok(this.employeeService.allEmployees());
    }

    @GetMapping("/all/status")
    public ResponseEntity<List<EmployeeSimple>> employeesByStatus(@RequestParam StatusEnum status){
        return ResponseEntity.ok(this.employeeService.allEmployeesByStatus(status));
        }

    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeResponse> employeeById(@PathVariable Integer employeeId) {
        return ResponseEntity.ok(this.employeeService.employeeById(employeeId));
    }

    @GetMapping("/barbershop/{barberShopId}")
    public ResponseEntity<List<EmployeeSimple>> employeesByBarberShop(@PathVariable Integer barberShopId) {
        return ResponseEntity.ok(this.employeeService.employeesByBarberShop(barberShopId));
    }

    @DeleteMapping("/delete/{employeeId}")
    public ResponseEntity deleteEmployee(@PathVariable Integer employeeId) {
        this.employeeService.deleteEmployee(employeeId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/update/{employeeId}")
    public ResponseEntity<EmployeeResponse> updateEmployee(@PathVariable Integer employeeId, @RequestBody EmployeeRequest updatedEmployee) {
        this.employeeService.updateEmployee(employeeId, updatedEmployee);
        return ResponseEntity.ok(this.employeeService.employeeById(employeeId));
    }

}
