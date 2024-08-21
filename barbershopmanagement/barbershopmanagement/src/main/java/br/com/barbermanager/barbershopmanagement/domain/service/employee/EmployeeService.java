package br.com.barbermanager.barbershopmanagement.domain.service.employee;

import br.com.barbermanager.barbershopmanagement.api.request.employee.EmployeeRequest;
import br.com.barbermanager.barbershopmanagement.api.response.employee.EmployeeResponse;
import br.com.barbermanager.barbershopmanagement.api.response.employee.EmployeeSimple;
import br.com.barbermanager.barbershopmanagement.domain.model.StatusEnum;

import java.util.List;

public interface EmployeeService {

    Boolean employeeExists(Integer employeeId);

    EmployeeResponse createEmployee(EmployeeRequest newEmployee);

    List<EmployeeSimple> allEmployees();

    EmployeeResponse employeeById(Integer employeeId);

    List<EmployeeSimple> employeesByBarberShop(Integer barberShopId);

    void deleteEmployee(Integer employeeId);

    EmployeeResponse updateEmployee(Integer employeeId, EmployeeRequest updatedEmployee);

    void removeBarberShop(Integer employeeId);

    List<EmployeeSimple> allEmployeesByStatus(StatusEnum status);
}
