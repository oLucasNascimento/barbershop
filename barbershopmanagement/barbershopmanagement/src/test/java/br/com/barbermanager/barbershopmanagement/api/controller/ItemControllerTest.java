package br.com.barbermanager.barbershopmanagement.api.controller;

import br.com.barbermanager.barbershopmanagement.api.request.barbershop.BarberShopRequest;
import br.com.barbermanager.barbershopmanagement.api.request.client.ClientRequest;
import br.com.barbermanager.barbershopmanagement.api.request.employee.EmployeeRequest;
import br.com.barbermanager.barbershopmanagement.api.request.item.ItemRequest;
import br.com.barbermanager.barbershopmanagement.api.response.barbershop.BarberShopResponse;
import br.com.barbermanager.barbershopmanagement.api.response.barbershop.BarberShopSimple;
import br.com.barbermanager.barbershopmanagement.api.response.client.ClientSimple;
import br.com.barbermanager.barbershopmanagement.api.response.employee.EmployeeSimple;
import br.com.barbermanager.barbershopmanagement.api.response.item.ItemResponse;
import br.com.barbermanager.barbershopmanagement.api.response.item.ItemSimple;
import br.com.barbermanager.barbershopmanagement.domain.model.StatusEnum;
import br.com.barbermanager.barbershopmanagement.domain.service.barbershop.BarberShopService;
import br.com.barbermanager.barbershopmanagement.domain.service.item.ItemService;
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
class ItemControllerTest {

    public static final Integer ID = 1;
    public static final String NAME_BARBER = "El Bigodon";
    public static final String ZIP_CODE = "55000000";
    public static final String ADRESS = "Rua Macaparana";
    public static final String MAIL = "el@bigodon.hair";
    public static final String NUMBER = "99887766";
    public static final String PASSWORD = "1234";
    public static final LocalTime OPENING = LocalTime.of(9, 0);
    public static final LocalTime CLOSING = LocalTime.of(18, 0);
    public static final String NAME = "Lucas";
    public static final double PRICE = 20.0;
    public static final int TIME = 70;
    public static final StatusEnum STATUS_ACTIVE = StatusEnum.ACTIVE;

    private BarberShopSimple barberShopSimple = new BarberShopSimple();
    private BarberShopRequest barberShopRequest = new BarberShopRequest();

    private ItemRequest itemRequest = new ItemRequest();
    private ItemResponse itemResponse = new ItemResponse();
    private ItemSimple itemSimple = new ItemSimple();

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private ItemController itemController;

    @MockBean
    private ItemService itemService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.startUser();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void whenCreateNewItemThenReturnSuccess() throws Exception {
        when(this.itemService.createItem(any())).thenReturn(this.itemResponse);
        String userJson = this.objectMapper.writeValueAsString(this.itemRequest);
        String responseContent = mockMvc.perform(post("/item/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();

        ItemResponse response = this.objectMapper.readValue(responseContent, ItemResponse.class);

        assertNotNull(response);
        assertEquals(ID, response.getItemId());
        verify(this.itemService, times(1)).createItem(any());
    }

    @Test
    void whenCreateNewItemWithoutBarberShopThenThrowAnBadRequestException() throws Exception {
        this.itemRequest.setBarberShop(null);
        String userJson = this.objectMapper.writeValueAsString(this.itemRequest);
        String responseContent = mockMvc.perform(post("/item/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();

        RestErrorMessage restError = this.objectMapper.readValue(responseContent, RestErrorMessage.class);

        assertEquals("The BarberShop field cannot be null.", restError.getMessage());
        assertEquals("VALIDATION_ERROR", restError.getErrorCode());
        assertEquals(HttpStatus.BAD_REQUEST, restError.getStatus());
        assertEquals("/item/new", restError.getPath());
        verify(this.itemService, times(0)).createItem(any());
    }

    @Test
    void whenCreateNewItemWithBarberShopIdNullThenThrowAnBadRequestException() throws Exception {
        this.barberShopRequest.setBarberShopId(null);
        String userJson = this.objectMapper.writeValueAsString(this.itemRequest);
        String responseContent = mockMvc.perform(post("/item/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();

        RestErrorMessage restError = this.objectMapper.readValue(responseContent, RestErrorMessage.class);

        assertEquals("The BarberShop ID field cannot be null.", restError.getMessage());
        assertEquals("VALIDATION_ERROR", restError.getErrorCode());
        assertEquals(HttpStatus.BAD_REQUEST, restError.getStatus());
        assertEquals("/item/new", restError.getPath());
        verify(this.itemService, times(0)).createItem(any());
    }

    @Test
    void whenCreateNewItemWithNameNullThenThrowAnBadRequestException() throws Exception {
        this.itemRequest.setName(null);
        String userJson = this.objectMapper.writeValueAsString(this.itemRequest);
        String responseContent = mockMvc.perform(post("/item/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();

        RestErrorMessage restError = this.objectMapper.readValue(responseContent, RestErrorMessage.class);

        assertEquals("The Name field cannot be null.", restError.getMessage());
        assertEquals("VALIDATION_ERROR", restError.getErrorCode());
        assertEquals(HttpStatus.BAD_REQUEST, restError.getStatus());
        assertEquals("/item/new", restError.getPath());
        verify(this.itemService, times(0)).createItem(any());
    }

    @Test
    void whenCreateNewItemWithPriceNullThenThrowAnBadRequestException() throws Exception {
        this.itemRequest.setPrice(null);
        String userJson = this.objectMapper.writeValueAsString(this.itemRequest);
        String responseContent = mockMvc.perform(post("/item/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();

        RestErrorMessage restError = this.objectMapper.readValue(responseContent, RestErrorMessage.class);

        assertEquals("The Price field cannot be null.", restError.getMessage());
        assertEquals("VALIDATION_ERROR", restError.getErrorCode());
        assertEquals(HttpStatus.BAD_REQUEST, restError.getStatus());
        assertEquals("/item/new", restError.getPath());
        verify(this.itemService, times(0)).createItem(any());
    }

    @Test
    void whenCreateNewItemWithTimeNullThenThrowAnBadRequestException() throws Exception {
        this.itemRequest.setTime(null);
        String userJson = this.objectMapper.writeValueAsString(this.itemRequest);
        String responseContent = mockMvc.perform(post("/item/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();

        RestErrorMessage restError = this.objectMapper.readValue(responseContent, RestErrorMessage.class);

        assertEquals("The Time field cannot be null.", restError.getMessage());
        assertEquals("VALIDATION_ERROR", restError.getErrorCode());
        assertEquals(HttpStatus.BAD_REQUEST, restError.getStatus());
        assertEquals("/item/new", restError.getPath());
        verify(this.itemService, times(0)).createItem(any());
    }

    @Test
    void whenFindAllItemsThenReturnAnListOfItems() throws Exception {
        when(this.itemService.allItems(any())).thenReturn(List.of(this.itemSimple));
        String userJson = this.objectMapper.writeValueAsString(this.itemRequest);
        String responseContent = mockMvc.perform(get("/item/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        List<ItemSimple> response = this.objectMapper.readValue(responseContent, new TypeReference<List<ItemSimple>>() {
        });

        assertEquals(1, response.size());
        assertEquals(ID, response.get(0).getItemId());
        verify(this.itemService, times(1)).allItems(any());
    }

    @Test
    void whenFindItemByIdThenReturnAnItem() throws Exception {
        when(this.itemService.itemById(any())).thenReturn(this.itemResponse);
        String userJson = this.objectMapper.writeValueAsString(this.itemRequest);
        String responseContent = mockMvc.perform(get("/item/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        ItemResponse response = this.objectMapper.readValue(responseContent, ItemResponse.class);

        assertNotNull(response);
        assertEquals(ID, response.getItemId());
        verify(this.itemService, times(1)).itemById(any());
    }

    @Test
    void whenFindItemsByBarberShopThenReturnAnListOfItems() throws Exception {
        when(this.itemService.itemByBarberShop(anyInt(), any())).thenReturn(List.of(this.itemSimple));
        String userJson = this.objectMapper.writeValueAsString(this.itemRequest);
        String responseContent = mockMvc.perform(get("/item/barbershop/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        List<ItemSimple> response = this.objectMapper.readValue(responseContent, new TypeReference<List<ItemSimple>>() {
        });

        assertEquals(1, response.size());
        assertEquals(ID, response.get(0).getItemId());
        verify(this.itemService, times(1)).itemByBarberShop(anyInt(), any());
    }

    @Test
    void whenDeleteItemThenReturnSuccess() throws Exception {
        doNothing().when(this.itemService).deleteItem(anyInt());
        mockMvc.perform(delete("/item/delete/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(this.itemService, times(1)).deleteItem(anyInt());
    }

    @Test
    void whenUpdateItemThenReturnSuccess() throws Exception {
        when(this.itemService.updateItem(anyInt(), any())).thenReturn(this.itemResponse);
        when(this.itemService.itemById(anyInt())).thenReturn(this.itemResponse);
        String userJson = this.objectMapper.writeValueAsString(this.itemRequest);
        String responseContent = mockMvc.perform(patch("/item/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        ItemResponse response = this.objectMapper.readValue(responseContent, ItemResponse.class);

        assertNotNull(response);
        assertEquals(ID, response.getItemId());
        verify(this.itemService, times(1)).updateItem(anyInt(),any());
    }

    @Test
    void whenUpdateItemWithBarberShopIdNullThenThrowAnBadRequestException() throws Exception {
        this.barberShopRequest.setBarberShopId(null);
        String userJson = this.objectMapper.writeValueAsString(this.itemRequest);
        String responseContent = mockMvc.perform(patch("/item/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();
        RestErrorMessage restError = this.objectMapper.readValue(responseContent, RestErrorMessage.class);

        assertEquals("The BarberShop ID field cannot be null.", restError.getMessage());
        assertEquals("VALIDATION_ERROR", restError.getErrorCode());
        assertEquals(HttpStatus.BAD_REQUEST, restError.getStatus());
        assertEquals("/item/update/1", restError.getPath());
        verify(this.itemService, times(0)).updateItem(anyInt(),any());
    }

    @Test
    void whenActiveEmployeeThenReturnSuccess() throws Exception {
        doNothing().when(this.itemService).activeItem(anyInt());
        mockMvc.perform(patch("/item/active-item/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(this.itemService, times(1)).activeItem(anyInt());

    }

    private void startUser() {
        this.barberShopSimple = new BarberShopSimple(ID, NAME_BARBER, ADRESS, NUMBER, OPENING, CLOSING, STATUS_ACTIVE);
        this.barberShopRequest = new BarberShopRequest(ID, NAME_BARBER, ZIP_CODE, ADRESS, MAIL, PASSWORD, NUMBER, OPENING, CLOSING, STATUS_ACTIVE, null, null, null);

        this.itemRequest = new ItemRequest(null, NAME, PRICE, TIME, STATUS_ACTIVE, this.barberShopRequest);
        this.itemResponse = new ItemResponse(ID, NAME, PRICE, TIME, STATUS_ACTIVE, this.barberShopSimple, List.of());
        this.itemSimple = new ItemSimple(ID, NAME, PRICE, TIME, STATUS_ACTIVE);

    }
}