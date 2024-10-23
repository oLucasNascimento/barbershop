package br.com.barbermanager.barbershopmanagement.api.controller;

import br.com.barbermanager.barbershopmanagement.api.request.barbershop.BarberShopRequest;
import br.com.barbermanager.barbershopmanagement.api.request.client.ClientRequest;
import br.com.barbermanager.barbershopmanagement.api.request.employee.EmployeeRequest;
import br.com.barbermanager.barbershopmanagement.api.request.item.ItemRequest;
import br.com.barbermanager.barbershopmanagement.api.response.barbershop.BarberShopResponse;
import br.com.barbermanager.barbershopmanagement.api.response.barbershop.BarberShopSimple;
import br.com.barbermanager.barbershopmanagement.api.response.client.ClientSimple;
import br.com.barbermanager.barbershopmanagement.api.response.employee.EmployeeSimple;
import br.com.barbermanager.barbershopmanagement.api.response.item.ItemSimple;
import br.com.barbermanager.barbershopmanagement.domain.model.StatusEnum;
import br.com.barbermanager.barbershopmanagement.domain.service.barbershop.BarberShopService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BarberShopControllerTest {

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

    private BarberShopSimple barberShopSimple = new BarberShopSimple();
    private BarberShopRequest barberShopRequest = new BarberShopRequest();
    private BarberShopResponse barberShopResponse = new BarberShopResponse();

    private ClientRequest clientRequest = new ClientRequest();
    private ClientSimple clientSimple = new ClientSimple();

    private EmployeeRequest employeeRequest = new EmployeeRequest();
    private EmployeeSimple employeeSimple = new EmployeeSimple();

    private ItemRequest itemRequest = new ItemRequest();
    private ItemSimple itemSimple = new ItemSimple();

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private BarberShopController barberShopController;

    @MockBean
    private BarberShopService barberShopService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.startUser();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void whenCreateNewBarberShopThenReturnSuccess() throws Exception {
        when(this.barberShopService.createBarberShop(any())).thenReturn(this.barberShopResponse);
        String userJson = this.objectMapper.writeValueAsString(this.barberShopRequest);
        String responseContent = mockMvc.perform(post("/barbershop/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();

        BarberShopResponse response = this.objectMapper.readValue(responseContent, BarberShopResponse.class);

        assertNotNull(response);
        assertEquals(ID, response.getBarberShopId());
        verify(this.barberShopService,times(1)).createBarberShop(any());
    }

    @Test
    void whenCreateNewBarberShopWithNameFieldNullThenThrowAnBadRequestException() throws Exception {
        this.barberShopRequest.setName(null);
        String userJson = this.objectMapper.writeValueAsString(this.barberShopRequest);

        String responseContent = mockMvc.perform(post("/barbershop/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();

        RestErrorMessage restError = this.objectMapper.readValue(responseContent, RestErrorMessage.class);

        assertEquals("The Name field cannot be null.", restError.getMessage());
        assertEquals("VALIDATION_ERROR", restError.getErrorCode());
        assertEquals(HttpStatus.BAD_REQUEST, restError.getStatus());
        assertEquals("/barbershop/new", restError.getPath());
        verify(this.barberShopService,times(0)).createBarberShop(any());
    }

    @Test
    void whenCreateNewBarberShopWithZipCodeFieldNullThenThrowAnBadRequestException() throws Exception {
        this.barberShopRequest.setZipCode(null);
        String userJson = this.objectMapper.writeValueAsString(this.barberShopRequest);

        String responseContent = mockMvc.perform(post("/barbershop/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();

        RestErrorMessage restError = this.objectMapper.readValue(responseContent, RestErrorMessage.class);

        assertEquals("The ZipCode field cannot be null.", restError.getMessage());
        assertEquals("VALIDATION_ERROR", restError.getErrorCode());
        assertEquals(HttpStatus.BAD_REQUEST, restError.getStatus());
        assertEquals("/barbershop/new", restError.getPath());
        verify(this.barberShopService,times(0)).createBarberShop(any());
    }

    @Test
    void whenCreateNewBarberShopWithAdressFieldNullThenThrowAnBadRequestException() throws Exception {
        this.barberShopRequest.setAdress(null);
        String userJson = this.objectMapper.writeValueAsString(this.barberShopRequest);

        String responseContent = mockMvc.perform(post("/barbershop/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();

        RestErrorMessage restError = this.objectMapper.readValue(responseContent, RestErrorMessage.class);

        assertEquals("The Adress field cannot be null.", restError.getMessage());
        assertEquals("VALIDATION_ERROR", restError.getErrorCode());
        assertEquals(HttpStatus.BAD_REQUEST, restError.getStatus());
        assertEquals("/barbershop/new", restError.getPath());
        verify(this.barberShopService,times(0)).createBarberShop(any());
    }

    @Test
    void whenCreateNewBarberShopWithEmailFieldNullThenThrowAnBadRequestException() throws Exception {
        this.barberShopRequest.setEmail(null);
        String userJson = this.objectMapper.writeValueAsString(this.barberShopRequest);

        String responseContent = mockMvc.perform(post("/barbershop/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();

        RestErrorMessage restError = this.objectMapper.readValue(responseContent, RestErrorMessage.class);

        assertEquals("The Email field cannot be null.", restError.getMessage());
        assertEquals("VALIDATION_ERROR", restError.getErrorCode());
        assertEquals(HttpStatus.BAD_REQUEST, restError.getStatus());
        assertEquals("/barbershop/new", restError.getPath());
        verify(this.barberShopService,times(0)).createBarberShop(any());
    }

//    @Test
//    void whenCreateNewBarberShopWithEmailFieldNotCorrectlyFormattedThenThrowAnBadRequestException() throws Exception {
//        this.barberShopRequest.setEmail("#lucas#ema,ilcom");
//        String userJson = this.objectMapper.writeValueAsString(this.barberShopRequest);
//
//        String responseContent = mockMvc.perform(post("/barbershop/new")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(userJson))
//                .andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();
//
//        RestErrorMessage restError = this.objectMapper.readValue(responseContent, RestErrorMessage.class);
//
//        assertEquals("Incorrect email formatting.", restError.getMessage());
//        assertEquals("VALIDATION_ERROR", restError.getErrorCode());
//        assertEquals(HttpStatus.BAD_REQUEST, restError.getStatus());
//        assertEquals("/barbershop/new", restError.getPath());
//    }

    @Test
    void whenCreateNewBarberShopWithPhoneFieldNullThenThrowAnBadRequestException() throws Exception {
        this.barberShopRequest.setPhone(null);
        when(this.barberShopService.createBarberShop(any())).thenReturn(this.barberShopResponse);
        String userJson = this.objectMapper.writeValueAsString(this.barberShopRequest);

        String responseContent = mockMvc.perform(post("/barbershop/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();

        RestErrorMessage restError = this.objectMapper.readValue(responseContent, RestErrorMessage.class);

        assertEquals("The Phone field cannot be null.", restError.getMessage());
        assertEquals("VALIDATION_ERROR", restError.getErrorCode());
        assertEquals(HttpStatus.BAD_REQUEST, restError.getStatus());
        assertEquals("/barbershop/new", restError.getPath());
        verify(this.barberShopService,times(0)).createBarberShop(any());
    }

    @Test
    void whenCreateNewBarberShopWithOpeningTimeFieldNullThenThrowAnBadRequestException() throws Exception {
        this.barberShopRequest.setOpeningTime(null);
        String userJson = this.objectMapper.writeValueAsString(this.barberShopRequest);

        String responseContent = mockMvc.perform(post("/barbershop/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();

        RestErrorMessage restError = this.objectMapper.readValue(responseContent, RestErrorMessage.class);

        assertEquals("The Opening Time field cannot be null.", restError.getMessage());
        assertEquals("VALIDATION_ERROR", restError.getErrorCode());
        assertEquals(HttpStatus.BAD_REQUEST, restError.getStatus());
        assertEquals("/barbershop/new", restError.getPath());
        verify(this.barberShopService,times(0)).createBarberShop(any());
    }

    @Test
    void whenCreateNewBarberShopWithClosingTimeFieldNullThenThrowAnBadRequestException() throws Exception {
        this.barberShopRequest.setClosingTime(null);
        String userJson = this.objectMapper.writeValueAsString(this.barberShopRequest);

        String responseContent = mockMvc.perform(post("/barbershop/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();

        RestErrorMessage restError = this.objectMapper.readValue(responseContent, RestErrorMessage.class);

        assertEquals("The Closing Time field cannot be null.", restError.getMessage());
        assertEquals("VALIDATION_ERROR", restError.getErrorCode());
        assertEquals(HttpStatus.BAD_REQUEST, restError.getStatus());
        assertEquals("/barbershop/new", restError.getPath());
        verify(this.barberShopService,times(0)).createBarberShop(any());
    }

    @Test
    void whenCreateNewBarberShopWithItemFieldFilledThenThrowAnBadRequestException() throws Exception {
        this.barberShopRequest.setItems(List.of(this.itemRequest));
        String userJson = this.objectMapper.writeValueAsString(this.barberShopRequest);

        String responseContent = mockMvc.perform(post("/barbershop/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();

        RestErrorMessage restError = this.objectMapper.readValue(responseContent, RestErrorMessage.class);

        assertEquals("The Item field must be null.", restError.getMessage());
        assertEquals("VALIDATION_ERROR", restError.getErrorCode());
        assertEquals(HttpStatus.BAD_REQUEST, restError.getStatus());
        assertEquals("/barbershop/new", restError.getPath());
        verify(this.barberShopService,times(0)).createBarberShop(any());
    }

    @Test
    void whenCreateNewBarberShopWithEmployeeFieldFilledThenThrowAnBadRequestException() throws Exception {
        this.barberShopRequest.setEmployees(List.of(this.employeeRequest));
        String userJson = this.objectMapper.writeValueAsString(this.barberShopRequest);

        String responseContent = mockMvc.perform(post("/barbershop/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();

        RestErrorMessage restError = this.objectMapper.readValue(responseContent, RestErrorMessage.class);

        assertEquals("The Employee field must be null.", restError.getMessage());
        assertEquals("VALIDATION_ERROR", restError.getErrorCode());
        assertEquals(HttpStatus.BAD_REQUEST, restError.getStatus());
        assertEquals("/barbershop/new", restError.getPath());
        verify(this.barberShopService,times(0)).createBarberShop(any());
    }

    @Test
    void whenCreateNewBarberShopWithClientFieldFilledThenThrowAnBadRequestException() throws Exception {
        this.barberShopRequest.setClients(List.of(this.clientRequest));
        String userJson = this.objectMapper.writeValueAsString(this.barberShopRequest);

        String responseContent = mockMvc.perform(post("/barbershop/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();

        RestErrorMessage restError = this.objectMapper.readValue(responseContent, RestErrorMessage.class);

        assertEquals("The Client field must be null.", restError.getMessage());
        assertEquals("VALIDATION_ERROR", restError.getErrorCode());
        assertEquals(HttpStatus.BAD_REQUEST, restError.getStatus());
        assertEquals("/barbershop/new", restError.getPath());
        verify(this.barberShopService,times(0)).createBarberShop(any());
    }

    @Test
    void whenFindAllBarberShopsThenReturnAnListOfBarberShops() throws Exception {
        when(this.barberShopService.allBarberShops(any())).thenReturn(List.of(this.barberShopSimple));

        String responseContent = mockMvc.perform(get("/barbershop/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        List<BarberShopSimple> response = this.objectMapper.readValue(responseContent, new TypeReference<List<BarberShopSimple>>() {
        });

        assertEquals(1, response.size());
        assertEquals(ID, response.get(0).getBarberShopId());
        verify(this.barberShopService,times(1)).allBarberShops(any());
    }

    @Test
    void whenFindBarberShopsByClientThenReturnAnListOfBarberShops() throws Exception {
        when(this.barberShopService.barberShopsByClient(anyInt(), any())).thenReturn(List.of(this.barberShopSimple));

        String responseContent = mockMvc.perform(get("/barbershop/client/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        List<BarberShopSimple> response = this.objectMapper.readValue(responseContent, new TypeReference<List<BarberShopSimple>>() {
        });

        assertEquals(1, response.size());
        assertEquals(ID, response.get(0).getBarberShopId());
        verify(this.barberShopService,times(1)).barberShopsByClient(anyInt(),any());
    }

    @Test
    void whenFindBarberShopByIdThenReturnAnBarberShop() throws Exception {
        when(this.barberShopService.barberShopById(anyInt())).thenReturn(this.barberShopResponse);

        String responseContent = mockMvc.perform(get("/barbershop/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        BarberShopResponse response = this.objectMapper.readValue(responseContent, BarberShopResponse.class);

        assertNotNull(response);
        assertEquals(ID, response.getBarberShopId());

        verify(this.barberShopService,times(1)).barberShopById(anyInt());
    }

    @Test
    void whenDeleteBarberShopThenReturnSuccess() throws Exception {
        doNothing().when(this.barberShopService).deleteBarberShop(anyInt());
        mockMvc.perform(delete("/barbershop/delete/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(this.barberShopService, times(1)).deleteBarberShop(anyInt());
    }

    @Test
    void whenActiveBarberShopThenReturnSuccess() throws Exception {
        doNothing().when(this.barberShopService).deleteBarberShop(anyInt());
        mockMvc.perform(patch("/barbershop/active-barbershop/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(this.barberShopService, times(1)).activeBarberShop(anyInt());
    }

    @Test
    void whenDismissEmployeeThenReturnSuccess() throws Exception {
        doNothing().when(this.barberShopService).deleteBarberShop(anyInt());
        mockMvc.perform(delete("/barbershop/dismiss/1/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(this.barberShopService, times(1)).dismissEmployee(anyInt(), anyInt());
    }

    @Test
    void whenUpdateBarberShopEqualsCreatedThenReturnSuccess() throws Exception {
        when(this.barberShopService.updateBarberShop(anyInt(), any())).thenReturn(this.barberShopResponse);
        when(this.barberShopService.barberShopById(anyInt())).thenReturn(this.barberShopResponse);
        String userJson = this.objectMapper.writeValueAsString(this.barberShopRequest);
        String responseContent = mockMvc.perform(patch("/barbershop/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        BarberShopResponse response = this.objectMapper.readValue(responseContent, BarberShopResponse.class);

        assertNotNull(response);
        assertEquals(ID, response.getBarberShopId());
        verify(this.barberShopService,times(1)).updateBarberShop(anyInt(), any());
    }

    @Test
    void whenUpdateBarberShopWithAllFieldFilledThenReturnSuccess() throws Exception {
        this.barberShopRequest.setClients(List.of(this.clientRequest));
        this.barberShopRequest.setItems(List.of(this.itemRequest));
        this.barberShopRequest.setEmployees(List.of(this.employeeRequest));
        when(this.barberShopService.updateBarberShop(anyInt(), any())).thenReturn(this.barberShopResponse);
        when(this.barberShopService.barberShopById(anyInt())).thenReturn(this.barberShopResponse);
        String userJson = this.objectMapper.writeValueAsString(this.barberShopRequest);
        String responseContent = mockMvc.perform(patch("/barbershop/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        BarberShopResponse response = this.objectMapper.readValue(responseContent, BarberShopResponse.class);

        assertNotNull(response);
        assertEquals(ID, response.getBarberShopId());
        verify(this.barberShopService,times(1)).updateBarberShop(anyInt(), any());
        verify(this.barberShopService,times(1)).barberShopById(anyInt());
    }

    @Test
    void whenUpdateBarberShopWithItemIdNullThenThrowAnBadRequestException() throws Exception {
        this.itemRequest.setItemId(null);
        this.barberShopRequest.setItems(List.of(this.itemRequest));
        String userJson = this.objectMapper.writeValueAsString(this.barberShopRequest);
        String responseContent = mockMvc.perform(patch("/barbershop/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();

        RestErrorMessage restError = this.objectMapper.readValue(responseContent, RestErrorMessage.class);

        assertEquals("The Item ID field cannot be null.", restError.getMessage());
        assertEquals("VALIDATION_ERROR", restError.getErrorCode());
        assertEquals(HttpStatus.BAD_REQUEST, restError.getStatus());
        assertEquals("/barbershop/update/1", restError.getPath());
        verify(this.barberShopService,times(0)).updateBarberShop(anyInt(), any());
    }

    @Test
    void whenUpdateBarberShopWithEmployeeIdNullThenThrowAnBadRequestException() throws Exception {
        this.employeeRequest.setEmployeeId(null);
        this.barberShopRequest.setEmployees(List.of(this.employeeRequest));
        when(this.barberShopService.updateBarberShop(anyInt(), any())).thenReturn(this.barberShopResponse);
        when(this.barberShopService.barberShopById(anyInt())).thenReturn(this.barberShopResponse);
        String userJson = this.objectMapper.writeValueAsString(this.barberShopRequest);
        String responseContent = mockMvc.perform(patch("/barbershop/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();

        RestErrorMessage restError = this.objectMapper.readValue(responseContent, RestErrorMessage.class);

        assertEquals("The Employee ID field cannot be null.", restError.getMessage());
        assertEquals("VALIDATION_ERROR", restError.getErrorCode());
        assertEquals(HttpStatus.BAD_REQUEST, restError.getStatus());
        assertEquals("/barbershop/update/1", restError.getPath());
        verify(this.barberShopService,times(0)).updateBarberShop(anyInt(), any());
    }

    @Test
    void whenUpdateBarberShopWithClientIdNullThenThrowAnBadRequestException() throws Exception {
        this.clientRequest.setClientId(null);
        this.barberShopRequest.setClients(List.of(this.clientRequest));
        when(this.barberShopService.updateBarberShop(anyInt(), any())).thenReturn(this.barberShopResponse);
        when(this.barberShopService.barberShopById(anyInt())).thenReturn(this.barberShopResponse);
        String userJson = this.objectMapper.writeValueAsString(this.barberShopRequest);
        String responseContent = mockMvc.perform(patch("/barbershop/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();

        RestErrorMessage restError = this.objectMapper.readValue(responseContent, RestErrorMessage.class);

        assertEquals("The Client ID field cannot be null.", restError.getMessage());
        assertEquals("VALIDATION_ERROR", restError.getErrorCode());
        assertEquals(HttpStatus.BAD_REQUEST, restError.getStatus());
        assertEquals("/barbershop/update/1", restError.getPath());
        verify(this.barberShopService,times(0)).updateBarberShop(anyInt(), any());
    }

    @Test
    void whenInsertNewClientThenReturnSuccess() throws Exception {
        this.barberShopRequest.setClients(List.of(this.clientRequest));
        when(this.barberShopService.updateClientAtBarberShop(anyInt(), any())).thenReturn(this.barberShopResponse);
        String userJson = this.objectMapper.writeValueAsString(this.barberShopRequest);
        String responseContent = mockMvc.perform(patch("/barbershop/insert-client/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        BarberShopResponse response = this.objectMapper.readValue(responseContent, BarberShopResponse.class);

        verify(this.barberShopService, times(1)).updateClientAtBarberShop(anyInt(),any());
        assertNotNull(response);
        assertEquals(ID, response.getBarberShopId());
    }

    @Test
    void whenInsertNewClientWithoutClientFilledThenThrowAnBadRequestException() throws Exception {
        when(this.barberShopService.updateClientAtBarberShop(anyInt(), any())).thenReturn(this.barberShopResponse);
        String userJson = this.objectMapper.writeValueAsString(this.barberShopRequest);
        String responseContent = mockMvc.perform(patch("/barbershop/insert-client/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();

        RestErrorMessage restError = this.objectMapper.readValue(responseContent, RestErrorMessage.class);

        assertEquals("The Client field cannot be null.", restError.getMessage());
        assertEquals("VALIDATION_ERROR", restError.getErrorCode());
        assertEquals(HttpStatus.BAD_REQUEST, restError.getStatus());
        assertEquals("/barbershop/insert-client/1", restError.getPath());
        verify(this.barberShopService, times(0)).updateClientAtBarberShop(anyInt(),any());
    }

    @Test
    void whenInsertNewClientWithClientIdNullThenThrowAnBadRequestException() throws Exception {
        this.clientRequest.setClientId(null);
        this.barberShopRequest.setClients(List.of(this.clientRequest));
        when(this.barberShopService.updateClientAtBarberShop(anyInt(), any())).thenReturn(this.barberShopResponse);
        String userJson = this.objectMapper.writeValueAsString(this.barberShopRequest);
        String responseContent = mockMvc.perform(patch("/barbershop/insert-client/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();

        RestErrorMessage restError = this.objectMapper.readValue(responseContent, RestErrorMessage.class);

        assertEquals("The Client ID field cannot be null.", restError.getMessage());
        assertEquals("VALIDATION_ERROR", restError.getErrorCode());
        assertEquals(HttpStatus.BAD_REQUEST, restError.getStatus());
        assertEquals("/barbershop/insert-client/1", restError.getPath());
        verify(this.barberShopService, times(0)).updateClientAtBarberShop(anyInt(),any());
    }

    @Test
    void whenRemoveClientThenReturnSuccess() throws Exception {
        doNothing().when(this.barberShopService).removeClient(anyInt(), anyInt());
        mockMvc.perform(delete("/barbershop/remove-client/1/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(this.barberShopService, times(1)).removeClient(anyInt(), anyInt());
    }

    private void startUser() {
        this.clientRequest = new ClientRequest(ID, NAME, CPF, PHONE, STATUS_ACTIVE, List.of(this.barberShopRequest), new ArrayList<>());
        this.clientSimple = new ClientSimple(ID, NAME, PHONE, STATUS_ACTIVE);

        this.employeeRequest = new EmployeeRequest(ID, NAME, CPF, MAIL, PHONE, STATUS_ACTIVE, this.barberShopRequest);
        this.employeeSimple = new EmployeeSimple(ID, NAME, PHONE, STATUS_ACTIVE);

        this.itemRequest = new ItemRequest(ID, NAME, PRICE, TIME, STATUS_ACTIVE, this.barberShopRequest);
        this.itemSimple = new ItemSimple(ID, NAME, PRICE, TIME, STATUS_ACTIVE);

        this.barberShopSimple = new BarberShopSimple(ID, NAME_BARBER, ADRESS, NUMBER, OPENING, CLOSING, STATUS_ACTIVE);
        this.barberShopResponse = new BarberShopResponse(ID, NAME_BARBER, ZIP_CODE, ADRESS, MAIL, NUMBER, OPENING, CLOSING, STATUS_ACTIVE, List.of(this.itemSimple), List.of(this.employeeSimple), List.of(this.clientSimple), List.of());
        this.barberShopRequest = new BarberShopRequest(null, NAME_BARBER, ZIP_CODE, ADRESS, MAIL, NUMBER, OPENING, CLOSING, STATUS_ACTIVE, null, null, null);
    }
}