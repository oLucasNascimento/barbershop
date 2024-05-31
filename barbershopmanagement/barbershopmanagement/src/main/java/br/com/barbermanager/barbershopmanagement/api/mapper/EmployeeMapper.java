package br.com.barbermanager.barbershopmanagement.api.mapper;

import br.com.barbermanager.barbershopmanagement.api.request.employee.EmployeeRequest;
import br.com.barbermanager.barbershopmanagement.api.response.employee.EmployeeResponse;
import br.com.barbermanager.barbershopmanagement.domain.model.Employee;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EmployeeMapper {

    private final ModelMapper mapper;

    public EmployeeMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public Employee toEmployee(Object employeeRequest){
        return this.mapper.map(employeeRequest, Employee.class);
    }

    public EmployeeRequest toEmployeeRequest(Object employee){
        return this.mapper.map(employee, EmployeeRequest.class);
    }

    public EmployeeResponse toEmployeeResponse(Object employee){
        return this.mapper.map(employee, EmployeeResponse.class);
    }

    public List<EmployeeResponse> toEmployeeResponseList(List<Employee> employeeList){
        return employeeList.stream().map(this::toEmployeeResponse).collect(Collectors.toList());
    }

}
