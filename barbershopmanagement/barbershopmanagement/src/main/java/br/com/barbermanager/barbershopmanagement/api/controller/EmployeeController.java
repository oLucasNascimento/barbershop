package br.com.barbermanager.barbershopmanagement.api.controller;


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
    public ResponseEntity<Employee> newEmployee(@RequestBody Employee newEmployee) {
        if ((this.employeeService.createEmployee(newEmployee)) != null) {
            return ResponseEntity.ok(newEmployee);
        }
        return ResponseEntity.status(409).build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<Employee>> allEmployees() {
        List<Employee> employees = this.employeeService.allEmployees();
        if (employees.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/barberShop/{barberShopId}")
    public ResponseEntity<List<Employee>> employeesByBarberShop(@PathVariable Integer barberShopId){
        List<Employee> employees = this.employeeService.employeesByBarberShop(barberShopId);
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
    public ResponseEntity<Employee> atualizarFuncionario(@PathVariable Integer employeeId, @RequestBody Employee updatedEmployee) {
        if ((this.employeeService.updateEmployee(employeeId, updatedEmployee)) != null) {
            return ResponseEntity.ok(this.employeeService.EmployeeById(employeeId));
        }
        return ResponseEntity.badRequest().build();
    }

}
