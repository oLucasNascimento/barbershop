package br.com.barbermanager.barbershopmanagement.domain.service.employee;

import br.com.barbermanager.barbershopmanagement.api.mapper.EmployeeMapper;
import br.com.barbermanager.barbershopmanagement.api.request.employee.EmployeeRequest;
import br.com.barbermanager.barbershopmanagement.api.response.employee.EmployeeResponse;
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
        return null;
    }

    @Override
    public List<EmployeeResponse> allEmployees() {
        return this.employeeMapper.toEmployeeResponseList((this.employeeRepository.findAll()));
    }

    @Override
    public EmployeeResponse EmployeeById(Integer employeeId) {
        if (this.employeeRepository.existsById(employeeId)) {
            return this.employeeMapper.toEmployeeResponse((this.employeeRepository.getById(employeeId)));
        }
        return null;
    }

    @Override
    public List<EmployeeResponse> employeesByBarberShop(Integer barberShopId) {
        List<Employee> employees = new ArrayList<>();
        for (Employee employee : this.employeeRepository.findAll()) {
            if ((employee.getBarberShop() != null)) {
                if ((employee.getBarberShop().getBarberShopId().equals(barberShopId))) {
                    employees.add(employee);
                }
            }
        }
        return this.employeeMapper.toEmployeeResponseList(employees);

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
    public EmployeeResponse updateEmployee(Integer employeeId, EmployeeRequest updatedEmployee) {
        if (this.employeeExists(employeeId)) {
            Employee employee = this.employeeRepository.getById(employeeId);
            BeanUtils.copyProperties((this.employeeMapper.toEmployee(updatedEmployee)), employee, searchEmptyFields(updatedEmployee));
            return this.employeeMapper.toEmployeeResponse((this.employeeRepository.save(employee)));
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
