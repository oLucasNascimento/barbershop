package br.com.barbermanager.barbershopmanagement.domain.service.item;

import br.com.barbermanager.barbershopmanagement.api.mapper.ItemMapper;
import br.com.barbermanager.barbershopmanagement.api.request.barbershop.BarberShopRequest;
import br.com.barbermanager.barbershopmanagement.api.request.item.ItemRequest;
import br.com.barbermanager.barbershopmanagement.api.response.item.ItemResponse;
import br.com.barbermanager.barbershopmanagement.api.response.item.ItemSimple;
import br.com.barbermanager.barbershopmanagement.domain.model.Item;
import br.com.barbermanager.barbershopmanagement.domain.model.StatusEnum;
import br.com.barbermanager.barbershopmanagement.domain.repository.ItemRepository;
import br.com.barbermanager.barbershopmanagement.exception.AlreadyActiveException;
import br.com.barbermanager.barbershopmanagement.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ItemServiceImplTest {

    public static final int ID = 1;
    public static final String ID_NOT_FOUND = "Item with ID '" + ID + "' not found.";
    public static final String NAME = "Corte";
    public static final double PRICE = 20.0;
    public static final int TIME = 60;
    public static final StatusEnum STATUS_ACTIVE = StatusEnum.ACTIVE;
    public static final StatusEnum STATUS_INACTIVE = StatusEnum.INACTIVE;

    private Item item = new Item();
    private ItemSimple itemSimple = new ItemSimple();
    private ItemRequest itemRequest = new ItemRequest();
    private ItemResponse itemResponse = new ItemResponse();

    @InjectMocks
    private ItemServiceImpl itemService;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private ItemMapper itemMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.startUser();
    }

    @Test
    void whenItemExistsThenRetunTrue() {
        when(this.itemRepository.existsById(anyInt())).thenReturn(true);

        Boolean exists = this.itemService.itemExists(ID);

        assertTrue(exists);
    }

    @Test
    void whenItemExistsThenRetunFalse() {
        when(this.itemRepository.existsById(anyInt())).thenReturn(false);

        Boolean exists = this.itemService.itemExists(ID);

        assertFalse(exists);
    }

    @Test
    void whenCreateItemThenReturnSuccess() {
        this.itemRequest.setBarberShop(new BarberShopRequest());
        this.itemRequest.setStatus(STATUS_INACTIVE);
        when(this.itemRepository.existingItem(anyString(), anyDouble(), anyInt())).thenReturn(null);
        when(this.itemMapper.toItem((ItemRequest) any())).thenReturn(this.item);
        when(this.itemMapper.toItemResponse(any())).thenReturn(this.itemResponse);

        ItemResponse response = this.itemService.createItem(this.itemRequest);

        assertNotNull(response);
        assertEquals(ItemResponse.class, response.getClass());
        assertEquals(STATUS_ACTIVE, response.getStatus());
        verify(this.itemRepository, times(1)).save(any());
    }

    @Test
    void whenCreateItemThenThrowAnNotFoundException() {
        BarberShopRequest barberShop = new BarberShopRequest();
        barberShop.setBarberShopId(ID);
        this.itemRequest.setBarberShop(barberShop);
        when(this.itemRepository.existingItem(anyString(), anyDouble(), anyInt())).thenReturn(this.item);

        try {
            this.itemService.createItem(this.itemRequest);
        } catch (Exception ex) {
            assertEquals(NotFoundException.class, ex.getClass());
            assertEquals("Item with name '" + NAME + "' and price '" + PRICE + "' already exists.", ex.getMessage());
        }
    }

    @Test
    void whenCreateItemWithoutBarberShopThenThrowAnNotFoundException() {
        try {
            this.itemService.createItem(this.itemRequest);
        } catch (Exception ex) {
            assertEquals(NotFoundException.class, ex.getClass());
            assertEquals("A barber shop must be informed.", ex.getMessage());
        }
    }

    @Test
    void whenFindAllItemsThenReturnAnListOfItems() {
        when(this.itemRepository.findAll()).thenReturn(List.of(this.item));
        when(this.itemMapper.toItemSimpleList(anyList())).thenReturn(List.of(this.itemSimple));

        List<ItemSimple> response = this.itemService.allItems(null);

        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertEquals(ItemSimple.class, response.get(0).getClass());
        assertEquals(ID, response.get(0).getItemId());
    }

    @Test
    void whenFindAllItemsWithStatusThenReturnAnListOfItems() {
        when(this.itemRepository.findAll()).thenReturn(List.of(this.item));
        when(this.itemMapper.toItemSimpleList(anyList())).thenReturn(List.of(this.itemSimple));
        when(this.itemRepository.findItemsByStatus(any())).thenReturn(List.of(this.item));

        List<ItemSimple> response = this.itemService.allItems(STATUS_ACTIVE);

        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertEquals(ItemSimple.class, response.get(0).getClass());
        assertEquals(STATUS_ACTIVE, response.get(0).getStatus());
        assertEquals(ID, response.get(0).getItemId());
    }

    @Test
    void whenFindAllItemsWithoutStatusExistingThenThrowAnNotFoundException() {
        when(this.itemRepository.findAll()).thenReturn(List.of(this.item));
        when(this.itemMapper.toItemSimpleList(anyList())).thenReturn(List.of(this.itemSimple)).thenReturn(new ArrayList<>());
        when(this.itemRepository.findItemsByStatus(any())).thenReturn(new ArrayList<>());

        try {
            this.itemService.allItems(STATUS_INACTIVE);
        } catch (Exception ex) {
            assertEquals(NotFoundException.class, ex.getClass());
            assertEquals("There aren't items with status '" + STATUS_INACTIVE + "' to show.", ex.getMessage());
        }
    }

    @Test
    void whenFindAllItemsWithoutItemsExistingThenThrowAnNotFoundException() {
        when(this.itemRepository.findAll()).thenReturn(List.of(this.item));
        when(this.itemMapper.toItemSimpleList(anyList())).thenReturn(new ArrayList<>());

        try {
            this.itemService.allItems(null);
        } catch (Exception ex) {
            assertEquals(NotFoundException.class, ex.getClass());
            assertEquals("There aren't items to show.", ex.getMessage());
        }
    }

    @Test
    void whenFindItemByIdThenReturnAnItem() {
        when(this.itemRepository.existsById(anyInt())).thenReturn(true);
        when(this.itemRepository.getById(anyInt())).thenReturn(this.item);
        when(this.itemMapper.toItemResponse(any())).thenReturn(this.itemResponse);

        ItemResponse response = this.itemService.itemById(ID);

        assertNotNull(response);
        assertEquals(ItemResponse.class, response.getClass());
        assertEquals(ID, response.getItemId());
    }

    @Test
    void whenFindItemByIdThenThrowAnNotFoundException() {
        when(this.itemRepository.existsById(anyInt())).thenReturn(false);

        try {
            this.itemService.itemById(ID);
        } catch (Exception ex) {
            assertEquals(NotFoundException.class, ex.getClass());
            assertEquals(ID_NOT_FOUND, ex.getMessage());
        }
    }

    @Test
    void whenFindItemByBarberShopThenReturnAnListOfItems() {
        when(this.itemRepository.itemByBarberShop(anyInt())).thenReturn(List.of(this.item));
        when(this.itemMapper.toItemSimpleList(anyList())).thenReturn(List.of(this.itemSimple));

        List<ItemSimple> response = this.itemService.itemByBarberShop(ID, null);

        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertEquals(ItemSimple.class, response.get(0).getClass());
        assertEquals(ID, response.get(0).getItemId());
    }

    @Test
    void whenFindItemByBarberShopWithStatusThenReturnAnListOfItems() {
        when(this.itemRepository.itemByBarberShop(anyInt())).thenReturn(List.of(this.item));
        when(this.itemMapper.toItemSimpleList(anyList())).thenReturn(List.of(this.itemSimple));

        List<ItemSimple> response = this.itemService.itemByBarberShop(ID, STATUS_ACTIVE);

        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertEquals(ItemSimple.class, response.get(0).getClass());
        assertEquals(STATUS_ACTIVE, response.get(0).getStatus());
        assertEquals(ID, response.get(0).getItemId());
    }

    @Test
    void whenFindItemByBarberShopWithStatusNotExistingThenReturnAnListOfItems() {
        when(this.itemRepository.itemByBarberShop(anyInt())).thenReturn(List.of(this.item));
        when(this.itemMapper.toItemSimpleList(anyList())).thenReturn(List.of(this.itemSimple));

        try{
            this.itemService.itemByBarberShop(ID, STATUS_INACTIVE);
        } catch (Exception ex) {
            assertEquals(NotFoundException.class, ex.getClass());
            assertEquals("There aren't items with status '" + STATUS_INACTIVE + "' at this barbershop.", ex.getMessage());
        }
    }

    @Test
    void whenFindItemByBarberShopWithoutItemsExistingThenReturnAnListOfItems() {
        when(this.itemRepository.itemByBarberShop(anyInt())).thenReturn(List.of(this.item));
        when(this.itemMapper.toItemSimpleList(anyList())).thenReturn(new ArrayList<>());

        try{
            this.itemService.itemByBarberShop(ID, STATUS_INACTIVE);
        } catch (Exception ex) {
            assertEquals(NotFoundException.class, ex.getClass());
            assertEquals("There aren't items at this barbershop", ex.getMessage());
        }
    }

    @Test
    void whenDeleteItemThenReturnSuccess() {
        when(this.itemRepository.existsById(anyInt())).thenReturn(true);
        when(this.itemRepository.getById(anyInt())).thenReturn(this.item);

        this.itemService.deleteItem(ID);

        assertEquals(STATUS_INACTIVE, this.item.getStatus());
        verify(this.itemRepository, times(1)).save(any());
    }

    @Test
    void whenDeleteItemThenThrowAnNotFoundException() {
        when(this.itemRepository.existsById(anyInt())).thenReturn(false);

        try {
            this.itemService.deleteItem(ID);
        } catch (Exception ex) {
            assertEquals(NotFoundException.class, ex.getClass());
            assertEquals(ID_NOT_FOUND, ex.getMessage());
        }
    }

    @Test
    void whenUpdateItemThenReturnSuccess() {
        when(this.itemRepository.existsById(anyInt())).thenReturn(true);
        when(this.itemRepository.getById(anyInt())).thenReturn(this.item);
        when(this.itemMapper.toItem((ItemRequest) any())).thenReturn(this.item);
        when(this.itemMapper.toItemResponse(any())).thenReturn(this.itemResponse);

        ItemResponse response = this.itemService.updateItem(ID, this.itemRequest);

        assertNotNull(response);
        assertEquals(ItemResponse.class, response.getClass());
        assertEquals(ID, response.getItemId());
        verify(this.itemRepository, times(1)).save(any());
    }

    @Test
    void whenUpdateItemThenThrowAnNotFoundException() {
        when(this.itemRepository.existsById(anyInt())).thenReturn(false);

        try {
            this.itemService.updateItem(ID, this.itemRequest);
        } catch (Exception ex) {
            assertEquals(NotFoundException.class, ex.getClass());
            assertEquals(ID_NOT_FOUND, ex.getMessage());
        }
    }

    @Test
    void whenActiveItemThenReturnSuccess() {
        this.item.setStatus(STATUS_INACTIVE);
        when(this.itemRepository.existsById(anyInt())).thenReturn(true);
        when(this.itemRepository.getById(anyInt())).thenReturn(this.item);

        this.itemService.activeItem(ID);

        assertEquals(STATUS_ACTIVE, this.item.getStatus());
        verify(this.itemRepository,times(1)).save(any());
    }

    @Test
    void whenActiveItemThenThrowAnAlreadyActiveException() {
        when(this.itemRepository.existsById(anyInt())).thenReturn(true);
        when(this.itemRepository.getById(anyInt())).thenReturn(this.item);

        try {
            this.itemService.activeItem(ID);
        } catch (Exception ex) {
            assertEquals(AlreadyActiveException.class, ex.getClass());
            assertEquals("Item with ID '" + ID + "' is already active.", ex.getMessage());
        }
    }

    @Test
    void whenActiveItemThenThrowAnNotFoundException() {
        when(this.itemRepository.existsById(anyInt())).thenReturn(false);

        try {
            this.itemService.activeItem(ID);
        } catch (Exception ex) {
            assertEquals(NotFoundException.class, ex.getClass());
            assertEquals(ID_NOT_FOUND, ex.getMessage());
        }
    }

    private void startUser() {
        this.item = new Item(ID, NAME, PRICE, TIME, STATUS_ACTIVE, null, new ArrayList<>());
        this.itemSimple = new ItemSimple(ID, NAME, PRICE, TIME, STATUS_ACTIVE);
        this.itemRequest = new ItemRequest(ID, NAME, PRICE, TIME, STATUS_ACTIVE, null, new ArrayList<>());
        this.itemResponse = new ItemResponse(ID, NAME, PRICE, TIME, STATUS_ACTIVE, null, new ArrayList<>());
    }

}