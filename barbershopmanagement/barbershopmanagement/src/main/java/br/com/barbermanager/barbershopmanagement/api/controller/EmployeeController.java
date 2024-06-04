package br.com.barbermanager.barbershopmanagement.api.controller;


import br.com.barbermanager.barbershopmanagement.api.request.employee.EmployeeRequest;
import br.com.barbermanager.barbershopmanagement.api.response.employee.EmployeeResponse;
import br.com.barbermanager.barbershopmanagement.domain.model.Employee;
import br.com.barbermanager.barbershopmanagement.domain.service.employee.EmployeeService;
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
    public ResponseEntity<List<EmployeeResponse>> allEmployees() {
        return ResponseEntity.ok(this.employeeService.allEmployees());
    }

    @GetMapping("/barbershop/{barberShopId}")
    public ResponseEntity<List<EmployeeResponse>> employeesByBarberShop(@PathVariable Integer barberShopId) {
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
        return ResponseEntity.ok(this.employeeService.EmployeeById(employeeId));
    }

}
