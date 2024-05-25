package br.com.barbermanager.barbershopmanagement.domain.service.employee;

import br.com.barbermanager.barbershopmanagement.domain.model.Employee;

import java.util.List;

public interface EmployeeService {

    Boolean employeeExists(Integer employeeId);

    Employee createEmployee(Employee newEmployee);

    List<Employee> allEmployees();

    Employee EmployeeById(Integer employeeId);

    List<Employee> employeesByBarberShop(Integer barberShopId);

    Boolean deleteEmployee(Integer employeeId);

    Employee updateEmployee(Integer employeeId, Employee updatedEmployee);

}
