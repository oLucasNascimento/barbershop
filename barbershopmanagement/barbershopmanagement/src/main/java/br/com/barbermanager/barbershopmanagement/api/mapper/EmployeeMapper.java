package br.com.barbermanager.barbershopmanagement.api.mapper;

import br.com.barbermanager.barbershopmanagement.api.request.employee.EmployeeRequest;
import br.com.barbermanager.barbershopmanagement.api.response.employee.EmployeeResponse;
import br.com.barbermanager.barbershopmanagement.api.response.employee.EmployeeSimple;
import br.com.barbermanager.barbershopmanagement.domain.model.Employee;
import org.mapstruct.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    Employee toEmployee(EmployeeRequest employeeRequest);
    Employee toEmployee(EmployeeResponse employeeRequest);

    EmployeeRequest toEmployeeRequest(Employee employee);

    EmployeeResponse toEmployeeResponse(Employee employee);

    EmployeeSimple toEmployeeSimple(Employee employee);

    List<EmployeeResponse> toEmployeeResponseList(List<Employee> employeeList);

    List<EmployeeSimple> toEmployeeSimpleList(List<Employee> employeeList);

}
