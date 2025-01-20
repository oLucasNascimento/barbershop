package br.com.barbermanager.barbershopmanagement.api.controller;

import br.com.barbermanager.barbershopmanagement.api.request.barbershop.BarberShopRequest;
import br.com.barbermanager.barbershopmanagement.api.request.client.ClientRequest;
import br.com.barbermanager.barbershopmanagement.api.request.employee.EmployeeRequest;
import br.com.barbermanager.barbershopmanagement.api.request.item.ItemRequest;
import br.com.barbermanager.barbershopmanagement.api.response.barbershop.BarberShopResponse;
import br.com.barbermanager.barbershopmanagement.api.response.barbershop.BarberShopSimple;
import br.com.barbermanager.barbershopmanagement.api.response.client.ClientSimple;
import br.com.barbermanager.barbershopmanagement.api.response.employee.EmployeeResponse;
import br.com.barbermanager.barbershopmanagement.api.response.employee.EmployeeSimple;
import br.com.barbermanager.barbershopmanagement.api.response.item.ItemSimple;
import br.com.barbermanager.barbershopmanagement.domain.model.StatusEnum;
import br.com.barbermanager.barbershopmanagement.domain.service.barbershop.BarberShopService;
import br.com.barbermanager.barbershopmanagement.domain.service.employee.EmployeeService;
import br.com.barbermanager.barbershopmanagement.exception.handler.RestErrorMessage;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {


    public static final Integer ID = 1;
    public static final Integer ID2 = 2;
    public static final String NAME_BARBER = "El Bigodon";
    public static final String ZIP_CODE = "55000000";
    public static final String ADRESS = "Rua Macaparana";
    public static final String MAIL = "el@bigodon.hair";
    public static final String NUMBER = "99887766";
    public static final String PASSWORD = "1234";
    public static final LocalTime OPENING = LocalTime.of(9, 0);
    public static final LocalTime CLOSING = LocalTime.of(18, 0);
    public static final String NAME = "Lucas";
    public static final String CPF = "71290252475";
    public static final String PHONE = "99887766";
    public static final StatusEnum STATUS_ACTIVE = StatusEnum.ACTIVE;

    private BarberShopSimple barberShopSimple = new BarberShopSimple();
    private BarberShopRequest barberShopRequest = new BarberShopRequest();

    private EmployeeRequest employeeRequest = new EmployeeRequest();
    private EmployeeResponse employeeResponse = new EmployeeResponse();
    private EmployeeSimple employeeSimple = new EmployeeSimple();

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private EmployeeController employeeController;

    @MockBean
    private EmployeeService employeeService;

    private ObjectMapper objectMapper = new ObjectMapper();


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.startUser();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void whenCreateNewEmployeeThenReturnSuccess() throws Exception {
        when(this.employeeService.createEmployee(any())).thenReturn(this.employeeResponse);
        String userJson = this.objectMapper.writeValueAsString(this.employeeRequest);
        String responseContent = mockMvc.perform(post("/employee/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();

        EmployeeResponse response = this.objectMapper.readValue(responseContent, EmployeeResponse.class);

        assertNotNull(response);
        assertEquals(ID, response.getEmployeeId());
        verify(this.employeeService, times(1)).createEmployee(any());
    }

    @Test
    void whenCreateNewEmployeeWithoutBarberShopThenThrowAnBadRequestException() throws Exception {
        this.employeeRequest.setBarberShop(null);
        String userJson = this.objectMapper.writeValueAsString(this.employeeRequest);
        String responseContent = mockMvc.perform(post("/employee/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();

        RestErrorMessage restError = this.objectMapper.readValue(responseContent, RestErrorMessage.class);

        assertEquals("The BarberShop field cannot be null.", restError.getMessage());
        assertEquals("VALIDATION_ERROR", restError.getErrorCode());
        assertEquals(HttpStatus.BAD_REQUEST, restError.getStatus());
        assertEquals("/employee/new", restError.getPath());
        verify(this.employeeService, times(0)).createEmployee(any());
    }

    @Test
    void whenCreateNewEmployeeWithBarberShopIdNullThenThrowAnBadRequestException() throws Exception {
        this.barberShopRequest.setBarberShopId(null);
        String userJson = this.objectMapper.writeValueAsString(this.employeeRequest);
        String responseContent = mockMvc.perform(post("/employee/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();

        RestErrorMessage restError = this.objectMapper.readValue(responseContent, RestErrorMessage.class);

        assertEquals("The BarberShop ID field cannot be null.", restError.getMessage());
        assertEquals("VALIDATION_ERROR", restError.getErrorCode());
        assertEquals(HttpStatus.BAD_REQUEST, restError.getStatus());
        assertEquals("/employee/new", restError.getPath());
        verify(this.employeeService, times(0)).createEmployee(any());
    }

    @Test
    void whenCreateNewEmployeeWithNameNullThenThrowAnBadRequestException() throws Exception {
        this.employeeRequest.setName(null);
        String userJson = this.objectMapper.writeValueAsString(this.employeeRequest);
        String responseContent = mockMvc.perform(post("/employee/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();

        RestErrorMessage restError = this.objectMapper.readValue(responseContent, RestErrorMessage.class);

        assertEquals("The Name field cannot be null.", restError.getMessage());
        assertEquals("VALIDATION_ERROR", restError.getErrorCode());
        assertEquals(HttpStatus.BAD_REQUEST, restError.getStatus());
        assertEquals("/employee/new", restError.getPath());
        verify(this.employeeService, times(0)).createEmployee(any());
    }

    @Test
    void whenCreateNewEmployeeWithCPFNullThenThrowAnBadRequestException() throws Exception {
        this.employeeRequest.setCpf(null);
        String userJson = this.objectMapper.writeValueAsString(this.employeeRequest);
        String responseContent = mockMvc.perform(post("/employee/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();

        RestErrorMessage restError = this.objectMapper.readValue(responseContent, RestErrorMessage.class);

        assertEquals("The CPF field cannot be null.", restError.getMessage());
        assertEquals("VALIDATION_ERROR", restError.getErrorCode());
        assertEquals(HttpStatus.BAD_REQUEST, restError.getStatus());
        assertEquals("/employee/new", restError.getPath());
        verify(this.employeeService, times(0)).createEmployee(any());
    }

//    @Test
//    void whenCreateNewEmployeeWithEmailNullThenThrowAnBadRequestException() throws Exception {
//        this.employeeRequest.setEmail(null);
//        String userJson = this.objectMapper.writeValueAsString(this.employeeRequest);
//        String responseContent = mockMvc.perform(post("/employee/new")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(userJson))
//                .andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();
//
//        RestErrorMessage restError = this.objectMapper.readValue(responseContent, RestErrorMessage.class);
//
//        assertEquals("The Email field cannot be null.", restError.getMessage());
//        assertEquals("VALIDATION_ERROR", restError.getErrorCode());
//        assertEquals(HttpStatus.BAD_REQUEST, restError.getStatus());
//        assertEquals("/employee/new", restError.getPath());
//        verify(this.employeeService, times(0)).createEmployee(any());
//    }

    @Test
    void whenCreateNewEmployeeWithPhoneNullThenThrowAnBadRequestException() throws Exception {
        this.employeeRequest.setPhone(null);
        String userJson = this.objectMapper.writeValueAsString(this.employeeRequest);
        String responseContent = mockMvc.perform(post("/employee/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();

        RestErrorMessage restError = this.objectMapper.readValue(responseContent, RestErrorMessage.class);

        assertEquals("The Phone field cannot be null.", restError.getMessage());
        assertEquals("VALIDATION_ERROR", restError.getErrorCode());
        assertEquals(HttpStatus.BAD_REQUEST, restError.getStatus());
        assertEquals("/employee/new", restError.getPath());
        verify(this.employeeService, times(0)).createEmployee(any());
    }

    @Test
    void whenFindAllEmployeesThenReturnAnListOfEmployees() throws Exception {
        when(this.employeeService.allEmployees(any())).thenReturn(List.of(this.employeeSimple));
        String userJson = this.objectMapper.writeValueAsString(this.employeeRequest);
        String responseContent = mockMvc.perform(get("/employee/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        List<EmployeeSimple> response = this.objectMapper.readValue(responseContent, new TypeReference<List<EmployeeSimple>>() {
        });

        assertEquals(1, response.size());
        assertEquals(ID, response.get(0).getEmployeeId());
        verify(this.employeeService, times(1)).allEmployees(any());
    }

    @Test
    void whenFindEmployeeByIdThenReturnAnEmployee() throws Exception {
        when(this.employeeService.employeeById(anyInt())).thenReturn(this.employeeResponse);
        String userJson = this.objectMapper.writeValueAsString(this.employeeRequest);
        String responseContent = mockMvc.perform(get("/employee/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        EmployeeResponse response = this.objectMapper.readValue(responseContent, EmployeeResponse.class);

        assertNotNull(response);
        assertEquals(ID, response.getEmployeeId());
        verify(this.employeeService, times(1)).employeeById(anyInt());
    }

    @Test
    void whenFindEmployeesByBarberShopThenReturnAnListOfEmployees() throws Exception {
        when(this.employeeService.employeesByBarberShop(anyInt(), any())).thenReturn(List.of(this.employeeSimple));

        String userJson = this.objectMapper.writeValueAsString(this.employeeRequest);
        String responseContent = mockMvc.perform(get("/employee/barbershop/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        List<EmployeeSimple> response = this.objectMapper.readValue(responseContent, new TypeReference<List<EmployeeSimple>>() {
        });

        assertEquals(1, response.size());
        assertEquals(ID, response.get(0).getEmployeeId());
        verify(this.employeeService, times(1)).employeesByBarberShop(anyInt(), any());
    }

    @Test
    void whenDeleteEmployeeThenReturnSuccess() throws Exception {
        doNothing().when(this.employeeService).deleteEmployee(anyInt());
        String userJson = this.objectMapper.writeValueAsString(this.employeeRequest);
        mockMvc.perform(delete("/employee/delete/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk());
        verify(this.employeeService, times(1)).deleteEmployee(anyInt());
    }

    @Test
    void whenActiveEmployeeThenReturnSuccess() throws Exception {
        doNothing().when(this.employeeService).activeEmployee(anyInt());
        String userJson = this.objectMapper.writeValueAsString(this.employeeRequest);
        mockMvc.perform(patch("/employee/active-employee/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk());
        verify(this.employeeService, times(1)).activeEmployee(anyInt());
    }

    @Test
    void whenUpdateEmployeeThenReturnSuccess() throws Exception {
        when(this.employeeService.updateEmployee(anyInt(), any())).thenReturn(this.employeeResponse);
        when(this.employeeService.employeeById(anyInt())).thenReturn(this.employeeResponse);
        String userJson = this.objectMapper.writeValueAsString(this.employeeRequest);
        String responseContent = mockMvc.perform(patch("/employee/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        EmployeeResponse response = this.objectMapper.readValue(responseContent, EmployeeResponse.class);

        assertNotNull(response);
        assertEquals(ID, response.getEmployeeId());
        verify(this.employeeService, times(1)).updateEmployee(anyInt(), any());
    }

    private void startUser() {
        this.barberShopSimple = new BarberShopSimple(ID, NAME_BARBER, ADRESS, NUMBER, OPENING, CLOSING, STATUS_ACTIVE);
        this.barberShopRequest = new BarberShopRequest(ID, NAME_BARBER, ZIP_CODE, ADRESS, MAIL, PASSWORD, NUMBER, OPENING, CLOSING, STATUS_ACTIVE, null, null, null);

        this.employeeRequest = new EmployeeRequest(null, NAME, CPF, PHONE, PASSWORD, STATUS_ACTIVE, this.barberShopRequest);
        this.employeeResponse = new EmployeeResponse(ID, NAME, CPF, PHONE, STATUS_ACTIVE, this.barberShopSimple, List.of());
        this.employeeSimple = new EmployeeSimple(ID, NAME, PHONE, STATUS_ACTIVE);
    }
}