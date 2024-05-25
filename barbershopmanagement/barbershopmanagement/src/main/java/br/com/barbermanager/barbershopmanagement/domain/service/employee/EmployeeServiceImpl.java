package br.com.barbermanager.barbershopmanagement.domain.service.employee;

import br.com.barbermanager.barbershopmanagement.domain.model.Employee;
import br.com.barbermanager.barbershopmanagement.domain.repository.EmployeeRepository;
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

    @Override
    public Boolean employeeExists(Integer employeeId) {
        return this.employeeRepository.existsById(employeeId);
    }

    @Transactional
    @Override
    public Employee createEmployee(Employee newEmployee) {
        if ((this.employeeRepository.findByCpf(newEmployee.getCpf())) == null) {
            return this.employeeRepository.save(newEmployee);
        }
        return null;
    }

    @Override
    public List<Employee> allEmployees() {
        return this.employeeRepository.findAll();
    }

    @Override
    public Employee EmployeeById(Integer employeeId) {
        if (this.employeeRepository.existsById(employeeId)) {
            return this.employeeRepository.getById(employeeId);
        }
        return null;
    }

    @Override
    public List<Employee> employeesByBarberShop(Integer barberShopId) {
        List<Employee> employees = new ArrayList<>();
        for (Employee employee : this.employeeRepository.findAll()) {
            if ((employee.getBarberShop() != null)) {
                if ((employee.getBarberShop().getBarberShopId().equals(barberShopId))) {
                    employees.add(employee);
                }
            }
        }
        return employees;

    }

    @Override
    public Boolean deleteEmployee(Integer employeeId) {
        if (this.employeeRepository.existsById(employeeId)) {
            this.employeeRepository.deleteById(employeeId);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public Employee updateEmployee(Integer employeeId, Employee updatedEmployee) {
        if (this.employeeExists(employeeId)) {
            Employee employee = this.EmployeeById(employeeId);
            BeanUtils.copyProperties(updatedEmployee, employee, searchEmptyFields(updatedEmployee));
            return this.employeeRepository.save(employee);
        }
        return null;
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
