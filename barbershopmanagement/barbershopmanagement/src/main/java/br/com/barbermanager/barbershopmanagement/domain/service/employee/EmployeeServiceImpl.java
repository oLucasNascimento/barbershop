package br.com.barbermanager.barbershopmanagement.domain.service.employee;

import br.com.barbermanager.barbershopmanagement.api.mapper.EmployeeMapper;
import br.com.barbermanager.barbershopmanagement.api.request.employee.EmployeeRequest;
import br.com.barbermanager.barbershopmanagement.api.response.employee.EmployeeResponse;
import br.com.barbermanager.barbershopmanagement.api.response.employee.EmployeeSimple;
import br.com.barbermanager.barbershopmanagement.domain.model.Employee;
import br.com.barbermanager.barbershopmanagement.domain.repository.EmployeeRepository;
import br.com.barbermanager.barbershopmanagement.exception.AlreadyExistsException;
import br.com.barbermanager.barbershopmanagement.exception.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public Boolean employeeExists(Integer employeeId) {
        return this.employeeRepository.existsById(employeeId);
    }

    @Transactional
    @Override
    public EmployeeResponse createEmployee(EmployeeRequest newEmployee) {
        if ((this.employeeRepository.findByCpf(newEmployee.getCpf())) == null) {
            return this.employeeMapper.toEmployeeResponse((this.employeeRepository.save((this.employeeMapper.toEmployee(newEmployee)))));
        }
        throw new AlreadyExistsException("Employee with CPF '" + newEmployee.getCpf() + "' already exists.");
    }

    @Override
    public List<EmployeeSimple> allEmployees() {
        List<EmployeeSimple> employeeResponses = this.employeeMapper.toEmployeeSimpleList((this.employeeRepository.findAll()));
        if (employeeResponses.isEmpty()) {
            throw new NotFoundException("There aren't employees to show.");
        }
        return employeeResponses;
    }

    @Override
    public EmployeeResponse employeeById(Integer employeeId) {
        if (this.employeeRepository.existsById(employeeId)) {
            return this.employeeMapper.toEmployeeResponse((this.employeeRepository.getById(employeeId)));
        }
        throw new NotFoundException("Employee with ID '" + employeeId + "' not found.");
    }

    @Override
    public List<EmployeeSimple> employeesByBarberShop(Integer barberShopId) {
        List<Employee> employees = new ArrayList<>();
        for (Employee employee : this.employeeRepository.findAll()) {
            if ((employee.getBarberShop() != null)) {
                if ((employee.getBarberShop().getBarberShopId().equals(barberShopId))) {
                    employees.add(employee);
                }
            }
        }
        if (employees.isEmpty()) {
            throw new NotFoundException("There aren't employees at this barbershop.");
        }
        return this.employeeMapper.toEmployeeSimpleList(employees);
    }

    @Override
    public void deleteEmployee(Integer employeeId) {
        if (this.employeeRepository.existsById(employeeId)) {
            this.employeeRepository.deleteById(employeeId);
            return;
        }
        throw new NotFoundException("Employee with ID '" + employeeId + "' not found.");
    }

    @Override
    @Transactional
    public EmployeeResponse updateEmployee(Integer employeeId, EmployeeRequest updatedEmployee) {
        if (this.employeeExists(employeeId)) {
            Employee employee = this.employeeRepository.getById(employeeId);
            BeanUtils.copyProperties((this.employeeMapper.toEmployee(updatedEmployee)), employee, this.searchEmptyFields(updatedEmployee));
            return this.employeeMapper.toEmployeeResponse((this.employeeRepository.save(employee)));
        }
        throw new NotFoundException("Employee with ID '" + employeeId + "' not found.");
    }

    @Override
    public void removeBarberShop(Integer employeeId){
        Employee employee = this.employeeRepository.getById(employeeId);
        employee.setBarberShop(null);
        this.employeeRepository.save(employee);
    }

    private String[] searchEmptyFields(Object source) {
        BeanWrapper src = new BeanWrapperImpl(source);
        Set<String> emptyFields = new HashSet<>();
        for (PropertyDescriptor pd : src.getPropertyDescriptors()) {
            if (src.getPropertyValue(pd.getName()) == null) {
                emptyFields.add(pd.getName());
            }
        }
        String[] result = new String[emptyFields.size()];
        return emptyFields.toArray(result);
    }
}
