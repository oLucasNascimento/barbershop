package br.com.barbermanager.barbershopmanagement.api.controller;

import br.com.barbermanager.barbershopmanagement.api.request.barbershop.BarberShopRequest;
import br.com.barbermanager.barbershopmanagement.api.request.client.ClientRequest;
import br.com.barbermanager.barbershopmanagement.api.request.employee.EmployeeRequest;
import br.com.barbermanager.barbershopmanagement.api.request.item.ItemRequest;
import br.com.barbermanager.barbershopmanagement.api.request.scheduling.SchedulingRequest;
import br.com.barbermanager.barbershopmanagement.api.response.barbershop.BarberShopSimple;
import br.com.barbermanager.barbershopmanagement.api.response.client.ClientSimple;
import br.com.barbermanager.barbershopmanagement.api.response.employee.EmployeeSimple;
import br.com.barbermanager.barbershopmanagement.api.response.item.ItemSimple;
import br.com.barbermanager.barbershopmanagement.api.response.scheduling.SchedulingResponse;
import br.com.barbermanager.barbershopmanagement.domain.model.StatusEnum;
import br.com.barbermanager.barbershopmanagement.domain.service.employee.EmployeeService;
import br.com.barbermanager.barbershopmanagement.domain.service.scheduling.SchedulingService;
import br.com.barbermanager.barbershopmanagement.exception.handler.RestErrorMessage;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SchedulingControllerTest {

    public static final Integer ID = 1;
    public static final Integer ID2 = 2;
    public static final String NAME_BARBER = "El Bigodon";
    public static final String ZIP_CODE = "55000000";
    public static final String ADRESS = "Rua Macaparana";
    public static final String MAIL = "el@bigodon.hair";
    public static final String NUMBER = "99887766";
    public static final LocalTime OPENING = LocalTime.of(9, 0);
    public static final LocalTime CLOSING = LocalTime.of(18, 0);
    public static final LocalDateTime DATE_10 = LocalDateTime.of(2024, 10, 5, 10, 0);
    public static final String NAME = "Lucas";
    public static final String CPF = "123456789";
    public static final String PHONE = "99887766";
    public static final double PRICE = 20.0;
    public static final int TIME = 70;
    public static final StatusEnum STATUS_ACTIVE = StatusEnum.ACTIVE;

    private BarberShopRequest barberShopRequest = new BarberShopRequest();
    private BarberShopSimple barberShopSimple = new BarberShopSimple();

    private ClientRequest clientRequest = new ClientRequest();
    private ClientSimple clientSimple = new ClientSimple();

    private EmployeeRequest employeeRequest = new EmployeeRequest();
    private EmployeeSimple employeeSimple = new EmployeeSimple();

    private ItemRequest itemRequest = new ItemRequest();
    private ItemSimple itemSimple = new ItemSimple();

    private SchedulingRequest schedulingRequest = new SchedulingRequest();
    private SchedulingResponse schedulingResponse = new SchedulingResponse();

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private SchedulingController schedulingController;

    @MockBean
    private SchedulingService schedulingService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.startUser();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void whenCreateNewSchedulingThenReturnSuccess() throws Exception {
        when(this.schedulingService.newScheduling(any())).thenReturn(this.schedulingResponse);
        String userJson = this.objectMapper.writeValueAsString(this.schedulingRequest);

        String responseContent = mockMvc.perform(post("/scheduling/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();

        SchedulingResponse response = this.objectMapper.readValue(responseContent, SchedulingResponse.class);

        assertNotNull(response);
        assertEquals(ID, response.getSchedulingId());
    }

    @Test
    void whenCreateNewSchedulingWithBarberShopNullThenThrowAnBadRequestException() throws Exception {
        this.schedulingRequest.setBarberShop(null);
        String userJson = this.objectMapper.writeValueAsString(this.schedulingRequest);

        String responseContent = mockMvc.perform(post("/scheduling/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();
        RestErrorMessage restError = this.objectMapper.readValue(responseContent, RestErrorMessage.class);

        assertEquals("The BarberShop field cannot be null.", restError.getMessage());
        assertEquals("VALIDATION_ERROR", restError.getErrorCode());
        assertEquals(HttpStatus.BAD_REQUEST, restError.getStatus());
        assertEquals("/scheduling/new", restError.getPath());
    }

    @Test
    void whenCreateNewSchedulingWithBarberShopIdNullThenThrowAnBadRequestException() throws Exception {
        this.schedulingRequest.getBarberShop().setBarberShopId(null);
        String userJson = this.objectMapper.writeValueAsString(this.schedulingRequest);

        String responseContent = mockMvc.perform(post("/scheduling/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();
        RestErrorMessage restError = this.objectMapper.readValue(responseContent, RestErrorMessage.class);

        assertEquals("The BarberShop ID field cannot be null.", restError.getMessage());
        assertEquals("VALIDATION_ERROR", restError.getErrorCode());
        assertEquals(HttpStatus.BAD_REQUEST, restError.getStatus());
        assertEquals("/scheduling/new", restError.getPath());
    }

    @Test
    void whenCreateNewSchedulingWithClientNullThenThrowAnBadRequestException() throws Exception {
        this.schedulingRequest.setClient(null);
        String userJson = this.objectMapper.writeValueAsString(this.schedulingRequest);

        String responseContent = mockMvc.perform(post("/scheduling/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();
        RestErrorMessage restError = this.objectMapper.readValue(responseContent, RestErrorMessage.class);

        assertEquals("The Client field cannot be null.", restError.getMessage());
        assertEquals("VALIDATION_ERROR", restError.getErrorCode());
        assertEquals(HttpStatus.BAD_REQUEST, restError.getStatus());
        assertEquals("/scheduling/new", restError.getPath());
    }

    @Test
    void whenCreateNewSchedulingWithClientIdNullThenThrowAnBadRequestException() throws Exception {
        this.schedulingRequest.getClient().setClientId(null);
        String userJson = this.objectMapper.writeValueAsString(this.schedulingRequest);

        String responseContent = mockMvc.perform(post("/scheduling/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();
        RestErrorMessage restError = this.objectMapper.readValue(responseContent, RestErrorMessage.class);

        assertEquals("The Client ID field cannot be null.", restError.getMessage());
        assertEquals("VALIDATION_ERROR", restError.getErrorCode());
        assertEquals(HttpStatus.BAD_REQUEST, restError.getStatus());
        assertEquals("/scheduling/new", restError.getPath());
    }

    @Test
    void whenCreateNewSchedulingWithItemsNullThenThrowAnBadRequestException() throws Exception {
        this.schedulingRequest.setItems(null);
        String userJson = this.objectMapper.writeValueAsString(this.schedulingRequest);

        String responseContent = mockMvc.perform(post("/scheduling/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();
        RestErrorMessage restError = this.objectMapper.readValue(responseContent, RestErrorMessage.class);

        assertEquals("The Items field cannot be null.", restError.getMessage());
        assertEquals("VALIDATION_ERROR", restError.getErrorCode());
        assertEquals(HttpStatus.BAD_REQUEST, restError.getStatus());
        assertEquals("/scheduling/new", restError.getPath());
    }

    @Test
    void whenCreateNewSchedulingWithItemIdNullThenThrowAnBadRequestException() throws Exception {
        this.schedulingRequest.getItems().get(0).setItemId(null);
        String userJson = this.objectMapper.writeValueAsString(this.schedulingRequest);

        String responseContent = mockMvc.perform(post("/scheduling/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();
        RestErrorMessage restError = this.objectMapper.readValue(responseContent, RestErrorMessage.class);

        assertEquals("The Item ID field cannot be null.", restError.getMessage());
        assertEquals("VALIDATION_ERROR", restError.getErrorCode());
        assertEquals(HttpStatus.BAD_REQUEST, restError.getStatus());
        assertEquals("/scheduling/new", restError.getPath());
    }

    @Test
    void whenCreateNewSchedulingWithEmployeeNullThenThrowAnBadRequestException() throws Exception {
        this.schedulingRequest.setEmployee(null);
        String userJson = this.objectMapper.writeValueAsString(this.schedulingRequest);

        String responseContent = mockMvc.perform(post("/scheduling/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();
        RestErrorMessage restError = this.objectMapper.readValue(responseContent, RestErrorMessage.class);

        assertEquals("The Employee field cannot be null.", restError.getMessage());
        assertEquals("VALIDATION_ERROR", restError.getErrorCode());
        assertEquals(HttpStatus.BAD_REQUEST, restError.getStatus());
        assertEquals("/scheduling/new", restError.getPath());
    }

    @Test
    void whenCreateNewSchedulingWithEmployeeIDNullThenThrowAnBadRequestException() throws Exception {
        this.schedulingRequest.getEmployee().setEmployeeId(null);
        String userJson = this.objectMapper.writeValueAsString(this.schedulingRequest);

        String responseContent = mockMvc.perform(post("/scheduling/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();
        RestErrorMessage restError = this.objectMapper.readValue(responseContent, RestErrorMessage.class);

        assertEquals("The Employee ID field cannot be null.", restError.getMessage());
        assertEquals("VALIDATION_ERROR", restError.getErrorCode());
        assertEquals(HttpStatus.BAD_REQUEST, restError.getStatus());
        assertEquals("/scheduling/new", restError.getPath());
    }

    @Test
    void whenCreateNewSchedulingWithSchedulingTimeNullThenThrowAnBadRequestException() throws Exception {
        this.schedulingRequest.setSchedulingTime(null);
        String userJson = this.objectMapper.writeValueAsString(this.schedulingRequest);

        String responseContent = mockMvc.perform(post("/scheduling/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();
        RestErrorMessage restError = this.objectMapper.readValue(responseContent, RestErrorMessage.class);

        assertEquals("The Scheduling Time field cannot be null.", restError.getMessage());
        assertEquals("VALIDATION_ERROR", restError.getErrorCode());
        assertEquals(HttpStatus.BAD_REQUEST, restError.getStatus());
        assertEquals("/scheduling/new", restError.getPath());
    }

    @Test
    void whenFindAllSchedulingsThenReturnAnListOfSchedulings() throws Exception {
        when(this.schedulingService.allSchedulings(any())).thenReturn(List.of(this.schedulingResponse));
        String responseContent = mockMvc.perform(get("/scheduling/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        List<SchedulingResponse> responseList = this.objectMapper.readValue(responseContent, new TypeReference<List<SchedulingResponse>>() {
        });

        assertEquals(1, responseList.size());
        assertEquals(DATE_10, responseList.get(0).getSchedulingTime());
    }

    @Test
    void whenFindSchedulingByIdThenReturnAnScheduling() throws Exception {
        when(this.schedulingService.schedulingById(any())).thenReturn(this.schedulingResponse);
        String responseContent = mockMvc.perform(get("/scheduling/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        SchedulingResponse response = this.objectMapper.readValue(responseContent, SchedulingResponse.class);

        assertNotNull(response);
        assertEquals(ID, response.getSchedulingId());
    }

    @Test
    void whenFindSchedulingsByBarberShopThenReturnAnListOfSchedulings() throws Exception {
        when(this.schedulingService.schedulingsByBarberShop(anyInt(), any())).thenReturn(List.of(this.schedulingResponse));
        String responseContent = mockMvc.perform(get("/scheduling/barbershop/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        List<SchedulingResponse> responseList = this.objectMapper.readValue(responseContent, new TypeReference<List<SchedulingResponse>>() {
        });

        assertEquals(1, responseList.size());
        assertEquals(DATE_10, responseList.get(0).getSchedulingTime());
    }

    @Test
    void whenFindSchedulingsByClientThenReturnAnListOfSchedulings() throws Exception {
        when(this.schedulingService.schedulingsByClient(anyInt(), any())).thenReturn(List.of(this.schedulingResponse));
        String responseContent = mockMvc.perform(get("/scheduling/client/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        List<SchedulingResponse> responseList = this.objectMapper.readValue(responseContent, new TypeReference<List<SchedulingResponse>>() {
        });

        assertEquals(1, responseList.size());
        assertEquals(DATE_10, responseList.get(0).getSchedulingTime());
    }

    @Test
    void whenFindSchedulingsByEmployeeThenReturnAnListOfSchedulings() throws Exception {
        when(this.schedulingService.schedulingsByEmployee(anyInt(), any())).thenReturn(List.of(this.schedulingResponse));
        String responseContent = mockMvc.perform(get("/scheduling/employee/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        List<SchedulingResponse> responseList = this.objectMapper.readValue(responseContent, new TypeReference<List<SchedulingResponse>>() {
        });

        assertEquals(1, responseList.size());
        assertEquals(DATE_10, responseList.get(0).getSchedulingTime());
    }

    @Test
    void whenFindSchedulingsByItemThenReturnAnListOfSchedulings() throws Exception {
        when(this.schedulingService.schedulingsByItem(anyInt(), any())).thenReturn(List.of(this.schedulingResponse));
        String responseContent = mockMvc.perform(get("/scheduling/item/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        List<SchedulingResponse> responseList = this.objectMapper.readValue(responseContent, new TypeReference<List<SchedulingResponse>>() {
        });

        assertEquals(1, responseList.size());
        assertEquals(DATE_10, responseList.get(0).getSchedulingTime());
    }

    @Test
    void whenCancelSchedulingThenReturnSuccess() throws Exception {
        doNothing().when(this.schedulingService).cancelScheduling(anyInt());
        String responseContent = mockMvc.perform(delete("/scheduling/cancel/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        verify(this.schedulingService, times(1)).cancelScheduling(anyInt());
    }

    @Test
    void whenUpdateSchedulingThenReturnSuccess() throws Exception {
        String userJson = this.objectMapper.writeValueAsString(this.schedulingRequest);
        when(this.schedulingService.updateScheduling(anyInt(), any())).thenReturn(this.schedulingResponse);
        mockMvc.perform(patch("/scheduling/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk());

        verify(this.schedulingService, times(1)).updateScheduling(anyInt(), any());
    }

    @Test
    void whenUpdateSchedulingWithoutSomeFieldsFilledThenReturnSuccess() throws Exception {
        this.schedulingRequest.setItems(null);
        this.schedulingRequest.setEmployee(null);
        this.schedulingRequest.setClient(null);
        this.schedulingRequest.setBarberShop(null);

        String userJson = this.objectMapper.writeValueAsString(this.schedulingRequest);
        when(this.schedulingService.updateScheduling(anyInt(), any())).thenReturn(this.schedulingResponse);
        mockMvc.perform(patch("/scheduling/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk());

        verify(this.schedulingService, times(1)).updateScheduling(anyInt(), any());
    }

    @Test
    void whenUpdateSchedulingWithItemFilledButItemIdIsNullThenThrowAnBadRequestException() throws Exception {
        this.schedulingRequest.getItems().get(0).setItemId(null);

        String userJson = this.objectMapper.writeValueAsString(this.schedulingRequest);
        when(this.schedulingService.updateScheduling(anyInt(), any())).thenReturn(this.schedulingResponse);
        String responseContent = mockMvc.perform(patch("/scheduling/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();
        RestErrorMessage restError = this.objectMapper.readValue(responseContent, RestErrorMessage.class);

        assertEquals("The Item ID field cannot be null.", restError.getMessage());
        assertEquals("VALIDATION_ERROR", restError.getErrorCode());
        assertEquals(HttpStatus.BAD_REQUEST, restError.getStatus());
        assertEquals("/scheduling/update/1", restError.getPath());
    }

    @Test
    void whenUpdateSchedulingWithEmployeeFilledButEmployeeIdIsNullThenThrowAnBadRequestException() throws Exception {
        this.schedulingRequest.getEmployee().setEmployeeId(null);

        String userJson = this.objectMapper.writeValueAsString(this.schedulingRequest);
        when(this.schedulingService.updateScheduling(anyInt(), any())).thenReturn(this.schedulingResponse);
        String responseContent = mockMvc.perform(patch("/scheduling/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();
        RestErrorMessage restError = this.objectMapper.readValue(responseContent, RestErrorMessage.class);

        assertEquals("The Employee ID field cannot be null.", restError.getMessage());
        assertEquals("VALIDATION_ERROR", restError.getErrorCode());
        assertEquals(HttpStatus.BAD_REQUEST, restError.getStatus());
        assertEquals("/scheduling/update/1", restError.getPath());
    }

    @Test
    void whenUpdateSchedulingWithClientFilledButClientIdIsNullThenThrowAnBadRequestException() throws Exception {
        this.schedulingRequest.getClient().setClientId(null);

        String userJson = this.objectMapper.writeValueAsString(this.schedulingRequest);
        when(this.schedulingService.updateScheduling(anyInt(), any())).thenReturn(this.schedulingResponse);
        String responseContent = mockMvc.perform(patch("/scheduling/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();
        RestErrorMessage restError = this.objectMapper.readValue(responseContent, RestErrorMessage.class);

        assertEquals("The Client ID field cannot be null.", restError.getMessage());
        assertEquals("VALIDATION_ERROR", restError.getErrorCode());
        assertEquals(HttpStatus.BAD_REQUEST, restError.getStatus());
        assertEquals("/scheduling/update/1", restError.getPath());
    }

    @Test
    void whenUpdateSchedulingWithBarberShopFilledButBarberShopIdIsNullThenThrowAnBadRequestException() throws Exception {
        this.schedulingRequest.getBarberShop().setBarberShopId(null);

        String userJson = this.objectMapper.writeValueAsString(this.schedulingRequest);
        when(this.schedulingService.updateScheduling(anyInt(), any())).thenReturn(this.schedulingResponse);
        String responseContent = mockMvc.perform(patch("/scheduling/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();
        RestErrorMessage restError = this.objectMapper.readValue(responseContent, RestErrorMessage.class);

        assertEquals("The BarberShop ID field cannot be null.", restError.getMessage());
        assertEquals("VALIDATION_ERROR", restError.getErrorCode());
        assertEquals(HttpStatus.BAD_REQUEST, restError.getStatus());
        assertEquals("/scheduling/update/1", restError.getPath());
    }

    @Test
    void whenFinishSchedulingThenReturnSucess() throws Exception {
        doNothing().when(this.schedulingService).finishScheduling(anyInt());
        String responseContent = mockMvc.perform(patch("/scheduling/finish/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        verify(this.schedulingService, times(1)).finishScheduling(anyInt());
    }

    private void startUser() {
        this.barberShopRequest = new BarberShopRequest(ID, NAME_BARBER, ZIP_CODE, ADRESS, MAIL, NUMBER, OPENING, CLOSING, STATUS_ACTIVE, List.of(this.itemRequest), List.of(this.employeeRequest), List.of(this.clientRequest));
        this.barberShopSimple = new BarberShopSimple(ID, NAME_BARBER, ADRESS, NUMBER, OPENING, CLOSING, STATUS_ACTIVE);

        this.clientRequest = new ClientRequest(ID, NAME, CPF, PHONE, STATUS_ACTIVE, List.of(this.barberShopRequest), new ArrayList<>());
        this.clientSimple = new ClientSimple(ID, NAME, PHONE, STATUS_ACTIVE);

        this.employeeRequest = new EmployeeRequest(ID, NAME, CPF, MAIL, PHONE, STATUS_ACTIVE, this.barberShopRequest);
        this.employeeSimple = new EmployeeSimple(ID, NAME, PHONE, STATUS_ACTIVE);

        this.itemRequest = new ItemRequest(ID, NAME, PRICE, TIME, STATUS_ACTIVE, this.barberShopRequest);
        this.itemSimple = new ItemSimple(ID, NAME, PRICE, TIME, STATUS_ACTIVE);

        this.schedulingRequest = new SchedulingRequest(null, this.clientRequest, this.barberShopRequest, this.employeeRequest, List.of(this.itemRequest), DATE_10, null);
        this.schedulingResponse = new SchedulingResponse(ID, this.clientSimple, this.barberShopSimple, this.employeeSimple, List.of(this.itemSimple), DATE_10, null);
    }

}