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
        EmployeeResponse employeeResponse = this.employeeService.createEmployee(newEmployee);
        if ((employeeResponse != null)) {
            return ResponseEntity.ok(employeeResponse);
        }
        return ResponseEntity.status(409).build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<EmployeeResponse>> allEmployees() {
        List<EmployeeResponse> employees = this.employeeService.allEmployees();
        if (employees.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/barbershop/{barberShopId}")
    public ResponseEntity<List<EmployeeResponse>> employeesByBarberShop(@PathVariable Integer barberShopId){
        List<EmployeeResponse> employees = this.employeeService.employeesByBarberShop(barberShopId);
        if(employees.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(employees);
    }

    @DeleteMapping("/delete/{employeeId}")
    public ResponseEntity deleteEmployee(@PathVariable Integer employeeId) {
        if (this.employeeService.deleteEmployee(employeeId)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PatchMapping("/update/{employeeId}")
    public ResponseEntity<EmployeeResponse> updateEmployee(@PathVariable Integer employeeId, @RequestBody EmployeeRequest updatedEmployee) {
        if ((this.employeeService.updateEmployee(employeeId, updatedEmployee)) != null) {
            return ResponseEntity.ok(this.employeeService.EmployeeById(employeeId));
        }
        return ResponseEntity.badRequest().build();
    }

}
