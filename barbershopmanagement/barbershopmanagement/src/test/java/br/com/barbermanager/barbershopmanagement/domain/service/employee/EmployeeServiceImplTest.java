package br.com.barbermanager.barbershopmanagement.domain.service.employee;

import br.com.barbermanager.barbershopmanagement.api.mapper.EmployeeMapper;
import br.com.barbermanager.barbershopmanagement.api.request.employee.EmployeeRequest;
import br.com.barbermanager.barbershopmanagement.api.response.employee.EmployeeResponse;
import br.com.barbermanager.barbershopmanagement.api.response.employee.EmployeeSimple;
import br.com.barbermanager.barbershopmanagement.domain.model.BarberShop;
import br.com.barbermanager.barbershopmanagement.domain.model.Employee;
import br.com.barbermanager.barbershopmanagement.domain.model.StatusEnum;
import br.com.barbermanager.barbershopmanagement.domain.repository.EmployeeRepository;
import br.com.barbermanager.barbershopmanagement.exception.AlreadyActiveException;
import br.com.barbermanager.barbershopmanagement.exception.AlreadyExistsException;
import br.com.barbermanager.barbershopmanagement.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class EmployeeServiceImplTest {

    public static final int ID = 1;
    public static final String NAME = "Luquinha";
    public static final String CPF = "99988877766";
    public static final String MAIL = "luquinha@mail.com";
    public static final String PHONE = "99887766";
    public static final String PASSWORD = "1234";
    public static final StatusEnum STATUS_ACTIVE = StatusEnum.ACTIVE;
    public static final StatusEnum STATUS_INACTIVE = StatusEnum.INACTIVE;

    private Employee employee = new Employee();
    private EmployeeRequest employeeRequest = new EmployeeRequest();
    private EmployeeResponse employeeResponse = new EmployeeResponse();
    private EmployeeSimple employeeSimple = new EmployeeSimple();

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeMapper employeeMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.startUser();
    }

    @Test
    void whenEmployeeExistsThenReturnTrue() {
        when(this.employeeRepository.existsById(anyInt())).thenReturn(true);

        Boolean exists = this.employeeService.employeeExists(ID);

        assertTrue(exists);
    }

    @Test
    void whenEmployeeExistsThenReturnFalse() {
        when(this.employeeRepository.existsById(anyInt())).thenReturn(false);

        Boolean exists = this.employeeService.employeeExists(ID);

        assertFalse(exists);
    }

    @Test
    void whenCreateEmployeeThenReturnSuccess() {
        this.employeeRequest.setStatus(STATUS_INACTIVE);
        when(this.employeeRepository.findByCpf(anyString())).thenReturn(null);
        when(this.employeeMapper.toEmployee((EmployeeRequest) any())).thenReturn(this.employee);
        when(this.employeeMapper.toEmployeeResponse(any())).thenReturn(this.employeeResponse);

        EmployeeResponse response = this.employeeService.createEmployee(this.employeeRequest);

        assertNotNull(response);
        assertEquals(EmployeeResponse.class, response.getClass());
        assertEquals(STATUS_ACTIVE, this.employeeRequest.getStatus());
        verify(this.employeeRepository, times(1)).save(any());
    }

    @Test
    void whenCreateEmployeeThenThrowAnAlreadyExistsException() {
        when(this.employeeRepository.findByCpf(anyString())).thenReturn(this.employee);

        try {
            this.employeeService.createEmployee(this.employeeRequest);
        } catch (Exception ex) {
            assertEquals(AlreadyExistsException.class, ex.getClass());
            assertEquals("Employee with CPF '" + CPF + "' already exists.", ex.getMessage());
        }
    }

    @Test
    void whenFindAllEmployeesThenReturnAnListOfEmployees() {
        when(this.employeeRepository.findAll()).thenReturn(List.of(this.employee));
        when(this.employeeMapper.toEmployeeSimpleList(any())).thenReturn(List.of(this.employeeSimple));

        List<EmployeeSimple> response = this.employeeService.allEmployees(null);

        assertNotNull(response);
        assertEquals(EmployeeSimple.class, response.get(0).getClass());
        assertEquals(1, response.size());
    }

    @Test
    void whenFindAllEmployeesWithStatusThenReturnAnListOfEmployees() {
        when(this.employeeRepository.findAll()).thenReturn(List.of(this.employee));
        when(this.employeeRepository.findEmployeesByStatus(any())).thenReturn(List.of(this.employee));
        when(this.employeeMapper.toEmployeeSimpleList(any())).thenReturn(List.of(this.employeeSimple));

        List<EmployeeSimple> response = this.employeeService.allEmployees(STATUS_ACTIVE);

        assertNotNull(response);
        assertEquals(EmployeeSimple.class, response.get(0).getClass());
        assertEquals(1, response.size());
    }

    @Test
    void whenFindAllEmployeesThenThrowAnNotFoundException() {
        when(this.employeeRepository.findAll()).thenReturn(new ArrayList<>());
        when(this.employeeMapper.toEmployeeSimpleList(anyList())).thenReturn(new ArrayList<>());

        try {
            this.employeeService.allEmployees(null);
        } catch (Exception ex) {
            assertEquals(NotFoundException.class, ex.getClass());
            assertEquals("There aren't employees to show.", ex.getMessage());
        }
    }

    @Test
    void whenFindAllEmployeesWithStatusThenThrowAnNotFoundException() {
        when(this.employeeRepository.findAll()).thenReturn(List.of(this.employee));
        when(this.employeeRepository.findEmployeesByStatus(StatusEnum.INACTIVE)).thenReturn(new ArrayList<>());
        when(this.employeeMapper.toEmployeeSimpleList(anyList())).thenReturn(List.of(this.employeeSimple)).thenReturn(new ArrayList<>());

        try {
            this.employeeService.allEmployees(StatusEnum.INACTIVE);
        } catch (Exception ex) {
            assertEquals(NotFoundException.class, ex.getClass());
            assertEquals("There aren't employees with status '" + STATUS_INACTIVE + "' to show.", ex.getMessage());
        }
    }

    @Test
    void whenFindEmployeeByIdThenReturnAnEmployee() {
        when(this.employeeRepository.existsById(anyInt())).thenReturn(true);
        when(this.employeeRepository.getById(anyInt())).thenReturn(this.employee);
        when(this.employeeMapper.toEmployeeResponse(any())).thenReturn(this.employeeResponse);

        EmployeeResponse response = this.employeeService.employeeById(ID);

        assertNotNull(response);
        assertEquals(EmployeeResponse.class, response.getClass());
        assertEquals(ID, response.getEmployeeId());
    }

    @Test
    void whenFindEmployeeByIdThenThrowAnNotFoundException() {
        when(this.employeeRepository.existsById(anyInt())).thenReturn(false);

        try {
            this.employeeService.employeeById(ID);
        } catch (Exception ex) {
            assertEquals(NotFoundException.class, ex.getClass());
            assertEquals("Employee with ID '" + ID + "' not found.", ex.getMessage());
        }
    }

    @Test
    void whenFindEmployeesByBarberShopThenReturnAnListOfEmployees() {
        when(this.employeeRepository.findEmployeesByBarberShop(ID)).thenReturn(List.of(this.employee));
        when(this.employeeMapper.toEmployeeSimpleList(anyList())).thenReturn(List.of(this.employeeSimple));

        List<EmployeeSimple> response = this.employeeService.employeesByBarberShop(ID, null);

        assertNotNull(response);
        assertEquals(EmployeeSimple.class, response.get(0).getClass());
        assertEquals(ID, response.get(0).getEmployeeId());
    }

    @Test
    void whenFindEmployeesByBarberShopWithStatusThenReturnAnListOfEmployees() {
        when(this.employeeRepository.findEmployeesByBarberShop(ID)).thenReturn(List.of(this.employee));
        when(this.employeeMapper.toEmployeeSimpleList(anyList())).thenReturn(List.of(this.employeeSimple));

        List<EmployeeSimple> response = this.employeeService.employeesByBarberShop(ID, STATUS_ACTIVE);

        assertNotNull(response);
        assertEquals(EmployeeSimple.class, response.get(0).getClass());
        assertEquals(ID, response.get(0).getEmployeeId());
        assertEquals(STATUS_ACTIVE, response.get(0).getStatus());
    }

    @Test
    void whenFindEmployeesByBarberShopThenThrowAnNotFoundException() {
        when(this.employeeRepository.findEmployeesByBarberShop(ID)).thenReturn(List.of(this.employee));
        when(this.employeeMapper.toEmployeeSimpleList(anyList())).thenReturn(List.of(this.employeeSimple));

        try {
            this.employeeService.employeesByBarberShop(ID, StatusEnum.INACTIVE);
        } catch (Exception ex) {
            assertEquals(NotFoundException.class, ex.getClass());
            assertEquals("There aren't employees with status '" + STATUS_INACTIVE + "' at this barbershop.", ex.getMessage());
        }
    }

    @Test
    void whenFindEmployeesByBarberShopWithStatusThenThrowAnNotFoundException() {
        when(this.employeeRepository.findEmployeesByBarberShop(ID)).thenReturn(List.of(this.employee));
        when(this.employeeMapper.toEmployeeSimpleList(anyList())).thenReturn(new ArrayList<>());

        try {
            this.employeeService.employeesByBarberShop(ID, null);
        } catch (Exception ex) {
            assertEquals(NotFoundException.class, ex.getClass());
            assertEquals("There aren't employees at this barbershop.", ex.getMessage());
        }
    }

    @Test
    void whenDeleteEmployeeThenReturnSuccess() {
        when(this.employeeRepository.existsById(anyInt())).thenReturn(true);
        when(this.employeeRepository.getById(anyInt())).thenReturn(this.employee);

        this.employeeService.deleteEmployee(ID);

        assertEquals(STATUS_INACTIVE, this.employee.getStatus());
        verify(this.employeeRepository, times(1)).save(any());
    }

    @Test
    void whenDeleteEmployeeThenThrowAnNotFoundException() {
        when(this.employeeRepository.existsById(anyInt())).thenReturn(false);

        try {
            this.employeeService.deleteEmployee(ID);
        } catch (Exception ex) {
            assertEquals(NotFoundException.class, ex.getClass());
            assertEquals("Employee with ID '" + ID + "' not found.", ex.getMessage());
        }
    }

    @Test
    void whenUpdateEmployeeThenReturnSuccess() {
        Employee employeeUpdt = new Employee();
        employeeUpdt.setName("luquinhas updt");
        when(this.employeeRepository.existsById(anyInt())).thenReturn(true);
        when(this.employeeRepository.getById(anyInt())).thenReturn(this.employee);
        when(this.employeeMapper.toEmployee((EmployeeRequest) any())).thenReturn(employeeUpdt);
        when(this.employeeMapper.toEmployeeResponse(any())).thenReturn(this.employeeResponse);

        EmployeeResponse response = this.employeeService.updateEmployee(ID, this.employeeRequest);

        assertNotNull(response);
        assertEquals(EmployeeResponse.class, response.getClass());
        assertEquals(ID, response.getEmployeeId());
        assertEquals("luquinhas updt", this.employee.getName());
        verify(this.employeeRepository, times(1)).save(any());
    }

    @Test
    void whenUpdateEmployeeThenThrowAnNotFoundException() {
        when(this.employeeRepository.existsById(anyInt())).thenReturn(false);

        try {
            this.employeeService.updateEmployee(ID, this.employeeRequest);
        } catch (Exception ex) {
            assertEquals(NotFoundException.class, ex.getClass());
            assertEquals("Employee with ID '" + ID + "' not found.", ex.getMessage());
        }
    }

    @Test
    void whenRemoveBarberShopThenReturnSuccess() {
        this.employee.setBarberShop(new BarberShop());
        when(this.employeeRepository.getById(anyInt())).thenReturn(this.employee);

        this.employeeService.removeBarberShop(ID);

        assertEquals(STATUS_INACTIVE, this.employee.getStatus());
        assertNull(this.employee.getBarberShop());
        verify(this.employeeRepository, times(1)).save(any());
    }

    @Test
    void whenActiveEmployeeThenReturnSuccess() {
        this.employee.setStatus(STATUS_INACTIVE);
        when(this.employeeRepository.existsById(anyInt())).thenReturn(true);
        when(this.employeeRepository.getById(anyInt())).thenReturn(this.employee);

        this.employeeService.activeEmployee(ID);

        assertEquals(STATUS_ACTIVE, this.employee.getStatus());
        verify(this.employeeRepository, times(1)).save(any());
    }

    @Test
    void whenActiveEmployeeThenThrowAnAlreadyActiveException() {
        when(this.employeeRepository.existsById(anyInt())).thenReturn(true);
        when(this.employeeRepository.getById(anyInt())).thenReturn(this.employee);

        try {
            this.employeeService.activeEmployee(ID);
        } catch (Exception ex) {
            assertEquals(AlreadyActiveException.class, ex.getClass());
            assertEquals("Employee with ID '" + ID + "' is already active.", ex.getMessage());
        }
    }

    @Test
    void whenActiveEmployeeThenThrowAnNotFoundException() {
        when(this.employeeRepository.existsById(anyInt())).thenReturn(false);

        try {
            this.employeeService.activeEmployee(ID);
        } catch (Exception ex) {
            assertEquals(NotFoundException.class, ex.getClass());
            assertEquals("Employee with ID '" + ID + "' not found.", ex.getMessage());
        }
    }

    private void startUser() {
        this.employee = new Employee(ID, NAME, CPF, PHONE, STATUS_ACTIVE, null, new ArrayList<>());
        this.employeeRequest = new EmployeeRequest(ID, NAME, CPF, PHONE, PASSWORD, STATUS_ACTIVE, null);
        this.employeeResponse = new EmployeeResponse(ID, NAME, CPF, PHONE, STATUS_ACTIVE, null, new ArrayList<>());
        this.employeeSimple = new EmployeeSimple(ID, NAME, PHONE, STATUS_ACTIVE);
    }
}