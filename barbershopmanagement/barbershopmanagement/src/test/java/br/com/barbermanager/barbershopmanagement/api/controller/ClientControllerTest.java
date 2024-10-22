package br.com.barbermanager.barbershopmanagement.api.controller;

import br.com.barbermanager.barbershopmanagement.api.request.barbershop.BarberShopRequest;
import br.com.barbermanager.barbershopmanagement.api.request.client.ClientRequest;
import br.com.barbermanager.barbershopmanagement.api.request.employee.EmployeeRequest;
import br.com.barbermanager.barbershopmanagement.api.request.item.ItemRequest;
import br.com.barbermanager.barbershopmanagement.api.response.barbershop.BarberShopResponse;
import br.com.barbermanager.barbershopmanagement.api.response.barbershop.BarberShopSimple;
import br.com.barbermanager.barbershopmanagement.api.response.client.ClientResponse;
import br.com.barbermanager.barbershopmanagement.api.response.client.ClientSimple;
import br.com.barbermanager.barbershopmanagement.api.response.employee.EmployeeSimple;
import br.com.barbermanager.barbershopmanagement.api.response.item.ItemResponse;
import br.com.barbermanager.barbershopmanagement.api.response.item.ItemSimple;
import br.com.barbermanager.barbershopmanagement.domain.model.StatusEnum;
import br.com.barbermanager.barbershopmanagement.domain.service.barbershop.BarberShopService;
import br.com.barbermanager.barbershopmanagement.domain.service.client.ClientService;
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
class ClientControllerTest {

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

    //    private EmployeeSimple employeeSimple = new EmployeeSimple();
//    private EmployeeRequest employeeRequest = new EmployeeRequest();
//
//    private ItemSimple itemSimple = new ItemSimple();
//    private ItemRequest itemRequest = new ItemRequest();
//
    private ClientSimple clientSimple = new ClientSimple();
    private ClientRequest clientRequest = new ClientRequest();
    private ClientResponse clientResponse = new ClientResponse();

    private BarberShopSimple barberShopSimple = new BarberShopSimple();
    private BarberShopRequest barberShopRequest = new BarberShopRequest();

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private ClientController clientController;

    @MockBean
    private ClientService clientService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.startUser();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void whenCreateNewClientThenReturnSuccess() throws Exception {
        when(this.clientService.createClient(any())).thenReturn(this.clientResponse);
        String userJson = this.objectMapper.writeValueAsString(this.clientRequest);
        String responseContent = mockMvc.perform(post("/client/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();

        ClientResponse response = this.objectMapper.readValue(responseContent, ClientResponse.class);

        assertNotNull(response);
        assertEquals(ID, response.getClientId());
        verify(this.clientService, times(1)).createClient(any());
    }

    @Test
    void whenCreateNewClientWithAnBarberShopThenThrowAnBadRequestException() throws Exception {
        this.clientRequest.setBarberShops(List.of(this.barberShopRequest));
        String userJson = this.objectMapper.writeValueAsString(this.clientRequest);
        String responseContent = mockMvc.perform(post("/client/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();

        RestErrorMessage restError = this.objectMapper.readValue(responseContent, RestErrorMessage.class);

        assertEquals("The Barber Shop field must be null.", restError.getMessage());
        assertEquals("VALIDATION_ERROR", restError.getErrorCode());
        assertEquals(HttpStatus.BAD_REQUEST, restError.getStatus());
        assertEquals("/client/new", restError.getPath());
        verify(this.clientService, times(0)).createClient(any());
    }

    @Test
    void whenCreateNewClientWithNameNullThenThrowAnBadRequestException() throws Exception {
        this.clientRequest.setName(null);
        String userJson = this.objectMapper.writeValueAsString(this.clientRequest);
        String responseContent = mockMvc.perform(post("/client/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();

        RestErrorMessage restError = this.objectMapper.readValue(responseContent, RestErrorMessage.class);

        assertEquals("The Name field cannot be null.", restError.getMessage());
        assertEquals("VALIDATION_ERROR", restError.getErrorCode());
        assertEquals(HttpStatus.BAD_REQUEST, restError.getStatus());
        assertEquals("/client/new", restError.getPath());
        verify(this.clientService, times(0)).createClient(any());
    }

    @Test
    void whenCreateNewClientWithCpfNullThenThrowAnBadRequestException() throws Exception {
        this.clientRequest.setCpf(null);
        String userJson = this.objectMapper.writeValueAsString(this.clientRequest);
        String responseContent = mockMvc.perform(post("/client/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();

        RestErrorMessage restError = this.objectMapper.readValue(responseContent, RestErrorMessage.class);

        assertEquals("The CPF field cannot be null.", restError.getMessage());
        assertEquals("VALIDATION_ERROR", restError.getErrorCode());
        assertEquals(HttpStatus.BAD_REQUEST, restError.getStatus());
        assertEquals("/client/new", restError.getPath());
        verify(this.clientService, times(0)).createClient(any());
    }

    @Test
    void whenCreateNewClientWithPhoneNullThenThrowAnBadRequestException() throws Exception {
        this.clientRequest.setPhone(null);
        String userJson = this.objectMapper.writeValueAsString(this.clientRequest);
        String responseContent = mockMvc.perform(post("/client/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();

        RestErrorMessage restError = this.objectMapper.readValue(responseContent, RestErrorMessage.class);

        assertEquals("The Phone field cannot be null.", restError.getMessage());
        assertEquals("VALIDATION_ERROR", restError.getErrorCode());
        assertEquals(HttpStatus.BAD_REQUEST, restError.getStatus());
        assertEquals("/client/new", restError.getPath());
        verify(this.clientService, times(0)).createClient(any());
    }

    @Test
    void whenFindAllClientsThenReturnAnListOfClients() throws Exception {
        when(this.clientService.allClients(any())).thenReturn(List.of(this.clientSimple));

        String responseContent = mockMvc.perform(get("/client/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        List<ClientSimple> response = this.objectMapper.readValue(responseContent, new TypeReference<List<ClientSimple>>() {
        });

        verify(this.clientService, times(1)).allClients(any());
        assertNotNull(response);
        assertEquals(ID, response.get(0).getClientId());
    }

    @Test
    void whenFindClientByIdThenReturnAnClient() throws Exception {
        when(this.clientService.clientById(anyInt())).thenReturn(this.clientResponse);

        String responseContent = mockMvc.perform(get("/client/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        ClientResponse response = this.objectMapper.readValue(responseContent, ClientResponse.class);

        verify(this.clientService, times(1)).clientById(any());
        assertNotNull(response);
        assertEquals(ID, response.getClientId());
    }

    @Test
    void whenFindClientsByBarberShopThenReturnAnListOfClients() throws Exception {
        when(this.clientService.clientsByBarberShop(anyInt(), any())).thenReturn(List.of(this.clientSimple));

        String responseContent = mockMvc.perform(get("/client/barber-shop/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        List<ClientSimple> response = this.objectMapper.readValue(responseContent, new TypeReference<List<ClientSimple>>() {
        });

        verify(this.clientService, times(1)).clientsByBarberShop(anyInt(), any());
        assertNotNull(response);
        assertEquals(ID, response.get(0).getClientId());
    }

    @Test
    void whenDeleteClientThenReturnSuccess() throws Exception {
        doNothing().when(this.clientService).deleteClient(anyInt());
        mockMvc.perform(delete("/client/delete/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(this.clientService, times(1)).deleteClient(anyInt());
    }

    @Test
    void whenActiveClientThenReturnSuccess() throws Exception {
        doNothing().when(this.clientService).deleteClient(anyInt());
        mockMvc.perform(patch("/client/active-client/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(this.clientService, times(1)).activeClient(anyInt());
    }

    @Test
    void whenUpdateClientThenReturnSuccess() throws Exception {
        when(this.clientService.updateClient(anyInt(), any())).thenReturn(this.clientResponse);
        when(this.clientService.clientById(anyInt())).thenReturn(this.clientResponse);
        String userJson = this.objectMapper.writeValueAsString(this.clientRequest);
        String responseContent = mockMvc.perform(patch("/client/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        ClientResponse response = this.objectMapper.readValue(responseContent, ClientResponse.class);

        assertNotNull(response);
        assertEquals(ID, response.getClientId());
        verify(this.clientService, times(1)).updateClient(anyInt(),any());
    }

    @Test
    void whenUpdateClientWithBarberShopIdNullThenThrowAnBadRequestException() throws Exception {
        this.barberShopRequest.setBarberShopId(null);
        this.clientRequest.setBarberShops(List.of(this.barberShopRequest));
        String userJson = this.objectMapper.writeValueAsString(this.clientRequest);
        String responseContent = mockMvc.perform(patch("/client/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();

        RestErrorMessage restError = this.objectMapper.readValue(responseContent, RestErrorMessage.class);

        assertEquals("The Barber Shop ID field cannot be null.", restError.getMessage());
        assertEquals("VALIDATION_ERROR", restError.getErrorCode());
        assertEquals(HttpStatus.BAD_REQUEST, restError.getStatus());
        assertEquals("/client/update/1", restError.getPath());
        verify(this.clientService, times(0)).updateClient(anyInt(), any());
    }


    private void startUser() {
        this.barberShopSimple = new BarberShopSimple(ID, NAME_BARBER, ADRESS, NUMBER, OPENING, CLOSING, STATUS_ACTIVE);
        this.barberShopRequest = new BarberShopRequest(ID, NAME_BARBER, ZIP_CODE, ADRESS, MAIL, NUMBER, OPENING, CLOSING, STATUS_ACTIVE, null, null, null);

        this.clientSimple = new ClientSimple(ID, NAME, PHONE, STATUS_ACTIVE);
        this.clientRequest = new ClientRequest(null, NAME, CPF, PHONE, STATUS_ACTIVE, null, new ArrayList<>());
        this.clientResponse = new ClientResponse(ID, NAME, CPF, PHONE, STATUS_ACTIVE, List.of(this.barberShopSimple), new ArrayList<>());
    }
}