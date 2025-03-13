package br.com.barbermanager.barbershopmanagement.domain.service.barbershop;

import br.com.barbermanager.barbershopmanagement.api.mapper.BarberShopMapper;
import br.com.barbermanager.barbershopmanagement.api.mapper.EmployeeMapper;
import br.com.barbermanager.barbershopmanagement.api.mapper.ItemMapper;
import br.com.barbermanager.barbershopmanagement.api.request.barbershop.BarberShopRequest;
import br.com.barbermanager.barbershopmanagement.api.request.client.ClientRequest;
import br.com.barbermanager.barbershopmanagement.api.response.barbershop.BarberShopResponse;
import br.com.barbermanager.barbershopmanagement.api.response.barbershop.BarberShopSimple;
import br.com.barbermanager.barbershopmanagement.api.response.client.ClientSimple;
import br.com.barbermanager.barbershopmanagement.api.response.employee.EmployeeResponse;
import br.com.barbermanager.barbershopmanagement.api.response.item.ItemResponse;
import br.com.barbermanager.barbershopmanagement.domain.model.*;
import br.com.barbermanager.barbershopmanagement.domain.repository.BarberShopRepository;
import br.com.barbermanager.barbershopmanagement.domain.service.employee.EmployeeService;
import br.com.barbermanager.barbershopmanagement.domain.service.item.ItemService;
import br.com.barbermanager.barbershopmanagement.domain.service.user.UserService;
import br.com.barbermanager.barbershopmanagement.exception.AlreadyActiveException;
import br.com.barbermanager.barbershopmanagement.exception.AlreadyExistsException;
import br.com.barbermanager.barbershopmanagement.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class BarberShopServiceImplTest {

    public static final Integer ID = 1;
    public static final String NAME_BARBER = "El Bigodon";
    public static final String ZIP_CODE = "55000000";
    public static final String ADRESS = "Rua Macaparana";
    public static final String MAIL = "el@bigodon.hair";
    public static final String NUMBER = "99887766";
    public static final String PASSWORD = "1234";
    public static final LocalTime OPENING = LocalTime.of(9, 0);
    public static final LocalTime CLOSING = LocalTime.of(18, 0);
    public static final StatusEnum STATUS_ACTIVE = StatusEnum.ACTIVE;
    public static final StatusEnum STATUS_INACTIVE = StatusEnum.INACTIVE;
    public static final String NO_BARBER_SHOPS = "There aren't barber shops to show.";

    private BarberShop barberShop = new BarberShop();
    private BarberShopRequest barberShopRequest = new BarberShopRequest();
    private BarberShopResponse barberShopResponse = new BarberShopResponse();
    private BarberShopSimple barberShopSimple = new BarberShopSimple();

    private Client client = new Client();
    private ClientRequest clientRequest = new ClientRequest();
    private ClientSimple clientSimple = new ClientSimple();

    private Employee employee = new Employee();
    private EmployeeResponse employeeResponse = new EmployeeResponse();

    private Item item = new Item();

    @InjectMocks
    @Spy
    private BarberShopServiceImpl barberShopService;

    @Mock
    private BarberShopRepository barberShopRepository;

    @Mock
    private EmployeeService employeeService;

    @Mock
    private ItemService itemService;

    @Mock
    private BarberShopMapper barberShopMapper;

    @Mock
    private EmployeeMapper employeeMapper;

    @Mock
    private ItemMapper itemMapper;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.startDomains();
    }

    @Test
    void whenCheckingIfBarberShopExistsThenReturnTrue() {
        when(this.barberShopRepository.existsById(anyInt())).thenReturn(true);
        Boolean exists = this.barberShopService.barberShopExists(ID);
        assertNotNull(exists);
        assertEquals(true, exists);
    }

    @Test
    void whenCheckingIfBarberShopExistsThenReturnFalse() {
        when(this.barberShopRepository.existsById(anyInt())).thenReturn(false);
        Boolean exists = this.barberShopService.barberShopExists(ID);
        assertNotNull(exists);
    }

    @Test
    void whenCreateBarberShopThenReturnAnBarberShopResponse() {
        when(this.barberShopRepository.findByEmail(anyString())).thenReturn(null);
        when(this.barberShopMapper.toBarberShopResponse(any())).thenReturn(this.barberShopResponse);
        when(this.userService.register(any())).thenReturn(true);

        BarberShopResponse response = this.barberShopService.createBarberShop(this.barberShopRequest);

        assertNotNull(response);
        assertEquals(BarberShopResponse.class, response.getClass());
        assertEquals(this.barberShopResponse.getClass(), response.getClass());
    }

    @Test
    void whenCreateBarberShopThenThrowAnAlreadyExistsException() {
        when(this.barberShopRepository.findByEmail(anyString())).thenReturn(this.barberShop);

        try {
            this.barberShopService.createBarberShop(this.barberShopRequest);
        } catch (Exception ex) {
            assertEquals(AlreadyExistsException.class, ex.getClass());
            assertEquals("Barber Shop with email '" + MAIL + "' already exists.", ex.getMessage());
        }
    }

    @Test
    void whenFindAllBarberShopsThenReturnAnBarberShopSimpleList() {
        when(this.barberShopRepository.findAll()).thenReturn(List.of(this.barberShop));
        when(this.barberShopMapper.toBarberShopSimpleList(any())).thenReturn(List.of(this.barberShopSimple));

        List<BarberShopSimple> response = this.barberShopService.allBarberShops(null);

        assertNotNull(response);
        assertEquals(BarberShopSimple.class, response.get(0).getClass());
    }

    @Test
    void whenFindAllBarberShopsByActiveThenReturnOnlyBarberShopsActives() {
        when(this.barberShopRepository.findAll()).thenReturn(List.of(this.barberShop));
        when(this.barberShopMapper.toBarberShopSimpleList(any())).thenReturn(List.of(this.barberShopSimple));

        List<BarberShopSimple> response = this.barberShopService.allBarberShops(STATUS_ACTIVE);

        assertNotNull(response);
        assertEquals(BarberShopSimple.class, response.get(0).getClass());
        assertTrue(response.stream().allMatch(barberShop -> barberShop.getStatus() == STATUS_ACTIVE));
    }

    @Test
    void whenFindAllBarberShopsThenThrowAnNotFoundException() {
        when(this.barberShopRepository.findAll()).thenReturn(new ArrayList<>());

        try {
            this.barberShopService.allBarberShops(null);
        } catch (Exception ex) {
            assertEquals(NotFoundException.class, ex.getClass());
            assertEquals(NO_BARBER_SHOPS, ex.getMessage());
        }
    }

    @Test
    void whenFindAllBarberShopsByStatusThenThrowAnNotFoundException() {
        when(this.barberShopRepository.findAll()).thenReturn(List.of(this.barberShop));
        when(this.barberShopMapper.toBarberShopSimpleList(any())).thenReturn(List.of(this.barberShopSimple)).thenReturn(new ArrayList<>());

        try {
            this.barberShopService.allBarberShops(STATUS_INACTIVE);
        } catch (Exception ex) {
            assertEquals(NotFoundException.class, ex.getClass());
            assertEquals("There aren't barber shops with status '" + STATUS_INACTIVE + "'.", ex.getMessage());
        }
    }

    @Test
    void whenFindBarberShopsByClientThenReturnAnBarberShopSimpleList() {
        this.barberShop.setClients(List.of(this.client));

        when(this.barberShopRepository.findBarberShopsByClients(anyInt())).thenReturn(List.of(this.barberShop));
        when(this.barberShopMapper.toBarberShopSimpleList(any())).thenReturn(List.of(this.barberShopSimple));

        List<BarberShopSimple> response = this.barberShopService.barberShopsByClient(ID, null);

        assertNotNull(response);
        assertEquals(BarberShopSimple.class, response.get(0).getClass());
        assertEquals(ID, barberShop.getClients().get(0).getClientId());
    }

    @Test
    void whenFindBarberShopsByClientAndStatusEqualsActiveThenReturnOnlyBarberShopsActives() {
        Client client = new Client();
        client.setClientId(ID);
        this.barberShop.setClients(List.of(client));

        when(this.barberShopRepository.findBarberShopsByClients(anyInt())).thenReturn(List.of(this.barberShop));
        when(this.barberShopMapper.toBarberShopSimpleList(any())).thenReturn(List.of(this.barberShopSimple));

        List<BarberShopSimple> response = this.barberShopService.barberShopsByClient(ID, STATUS_ACTIVE);

        assertNotNull(response);
        assertEquals(ID, barberShop.getClients().get(0).getClientId());
        assertEquals(BarberShopSimple.class, response.get(0).getClass());
        assertTrue(response.stream().allMatch(barberShop -> barberShop.getStatus().equals(STATUS_ACTIVE)));
    }

    @Test
    void whenFindBarberShopsByClientAndClientNotBelongThenThrowNotFoundException() {
        when(this.barberShopRepository.findBarberShopsByClients(anyInt())).thenReturn(new ArrayList<>());
        when(this.barberShopMapper.toBarberShopSimpleList(any())).thenReturn(new ArrayList<>());

        try {
            this.barberShopService.barberShopsByClient(ID, null);
        } catch (Exception ex) {
            assertEquals(NotFoundException.class, ex.getClass());
            assertEquals(NO_BARBER_SHOPS, ex.getMessage());
        }
    }

    @Test
    void whenFindBarberShopsByClientAndThereNotWithStatusInformatedThenThrowNotFoundException() {
        Client client = new Client();
        client.setClientId(ID);
        this.barberShop.setClients(List.of(client));

        when(this.barberShopRepository.findBarberShopsByClients(anyInt())).thenReturn(List.of(this.barberShop));
        when(this.barberShopMapper.toBarberShopSimpleList(any())).thenReturn(List.of(this.barberShopSimple));

        try {
            this.barberShopService.barberShopsByClient(ID, STATUS_INACTIVE);
        } catch (Exception ex) {
            assertEquals(NotFoundException.class, ex.getClass());
            assertEquals("There aren't barber shops with status '" + STATUS_INACTIVE + "' for this client.", ex.getMessage());
        }
    }

    @Test
    void whenFindBarberShopByIdThenReturnAnBarberShop() {
        when(this.barberShopRepository.existsById(anyInt())).thenReturn(true);
        when(this.barberShopRepository.getById(ID)).thenReturn(this.barberShop);
        when(this.barberShopMapper.toBarberShopResponse(any())).thenReturn(this.barberShopResponse);

        BarberShopResponse response = this.barberShopService.barberShopById(ID);

        assertNotNull(response);
        assertEquals(BarberShopResponse.class, response.getClass());
        assertEquals(ID, response.getBarberShopId());
    }

    @Test
    void whenFindBarberShopByIdThenThrowNotFoundException() {
        when(this.barberShopRepository.existsById(anyInt())).thenReturn(false);
        when(this.barberShopRepository.getById(ID)).thenReturn(this.barberShop);
        when(this.barberShopMapper.toBarberShopResponse(any())).thenReturn(this.barberShopResponse);

        try {
            this.barberShopService.barberShopById(ID);
        } catch (Exception ex) {
            assertEquals(NotFoundException.class, ex.getClass());
            assertEquals("Barber Shop with ID '" + ID + "' not found.", ex.getMessage());
        }
    }

    @Test
    void whenDeleteBarberShopThenReturnSuccess() {
        when(this.barberShopService.barberShopExists(anyInt())).thenReturn(true);
        when(this.barberShopRepository.getById(anyInt())).thenReturn(this.barberShop);

        this.barberShopService.deleteBarberShop(ID);

        assertEquals(STATUS_INACTIVE, this.barberShop.getStatus());
    }

    @Test
    void whenDeleteBarberShopThenThrowNotFoundException() {
        when(this.barberShopService.barberShopExists(anyInt())).thenReturn(false);

        try {
            this.barberShopService.deleteBarberShop(ID);
        } catch (Exception ex) {
            assertEquals(NotFoundException.class, ex.getClass());
            assertEquals("Barber Shop with ID '" + ID + "' not found.", ex.getMessage());
        }
    }

    @Test
    void whenActiveBarberShopThenReturnSucces() {
        this.barberShop.setStatus(STATUS_INACTIVE);
        when(this.barberShopService.barberShopExists(anyInt())).thenReturn(true);
        when(this.barberShopRepository.getById(anyInt())).thenReturn(this.barberShop);

        this.barberShopService.activeBarberShop(ID);

        assertEquals(STATUS_ACTIVE, this.barberShop.getStatus());
    }

    @Test
    void whenActiveBarberShopThenThrowAlreadyActiveException() {
        when(this.barberShopService.barberShopExists(anyInt())).thenReturn(true);
        when(this.barberShopRepository.getById(anyInt())).thenReturn(this.barberShop);

        try {
            this.barberShopService.activeBarberShop(ID);
        } catch (Exception ex) {
            assertEquals(AlreadyActiveException.class, ex.getClass());
            assertEquals("BarberShop with ID '" + ID + "' is already active.", ex.getMessage());
        }
    }

    @Test
    void whenActiveBarberShopThenThrowNotFoundException() {
        when(this.barberShopService.barberShopExists(anyInt())).thenReturn(false);
        when(this.barberShopRepository.getById(anyInt())).thenReturn(this.barberShop);

        try {
            this.barberShopService.activeBarberShop(ID);
        } catch (Exception ex) {
            assertEquals(NotFoundException.class, ex.getClass());
            assertEquals("BarberShop with ID '" + ID + "' not found.", ex.getMessage());
        }
    }

    @Test
    void whenUpdateBarberShopThenReturnSuccess() {
        this.barberShopRequest.setAdress(null);
        when(this.barberShopService.barberShopExists(anyInt())).thenReturn(true);
        when(this.barberShopRepository.findByEmail(anyString())).thenReturn(null);
        when(this.barberShopRepository.getById(anyInt())).thenReturn(this.barberShop);
        when(this.barberShopMapper.toBarberShop((BarberShopRequest) any())).thenReturn(this.barberShop);
        when(this.barberShopMapper.toBarberShopResponse(any())).thenReturn(this.barberShopResponse);

        BarberShopResponse response = this.barberShopService.updateBarberShop(ID, this.barberShopRequest);

        assertNotNull(response);
        assertEquals(BarberShopResponse.class, response.getClass());
    }

    @Test
    void whenUpdateBarberShopWithEmployeeThenReturnSuccess() {
        this.barberShop.setEmployees(List.of(this.employee));

        when(this.barberShopService.barberShopExists(anyInt())).thenReturn(true);
        when(this.barberShopRepository.findByEmail(anyString())).thenReturn(null);
        when(this.barberShopRepository.getById(anyInt())).thenReturn(this.barberShop);
        when(this.barberShopMapper.toBarberShop((BarberShopRequest) any())).thenReturn(this.barberShop);
        when(this.barberShopMapper.toBarberShopResponse(any())).thenReturn(this.barberShopResponse);

        when(this.employeeService.employeeById(anyInt())).thenReturn(new EmployeeResponse());
        when(this.employeeMapper.toEmployee((EmployeeResponse) any())).thenReturn(this.employee);
        when(this.employeeService.updateEmployee(anyInt(), any())).thenReturn(new EmployeeResponse());

        BarberShopResponse response = this.barberShopService.updateBarberShop(ID, this.barberShopRequest);

        assertNotNull(response);
        assertEquals(1, this.barberShop.getEmployees().size());
        assertEquals(BarberShopResponse.class, response.getClass());
        verify(this.employeeService, times(1)).updateEmployee(anyInt(), any());
        verify(this.barberShopRepository, times(1)).save(any());
    }

    @Test
    void whenUpdateBarberShopWithItemThenReturnSuccess() {
        this.barberShop.setItems(List.of(this.item));

        when(this.barberShopService.barberShopExists(anyInt())).thenReturn(true);
        when(this.barberShopRepository.findByEmail(anyString())).thenReturn(null);
        when(this.barberShopRepository.getById(anyInt())).thenReturn(this.barberShop);
        when(this.barberShopMapper.toBarberShop((BarberShopRequest) any())).thenReturn(this.barberShop);
        when(this.barberShopMapper.toBarberShopResponse(any())).thenReturn(this.barberShopResponse);

        when(this.itemService.itemById(anyInt())).thenReturn(new ItemResponse());
        when(this.itemMapper.toItem((ItemResponse) any())).thenReturn(this.item);
        when(this.itemService.itemExists(anyInt())).thenReturn(true);
        when(this.itemMapper.toItemResponse(any())).thenReturn(new ItemResponse());
        when(this.itemService.updateItem(anyInt(), any())).thenReturn(new ItemResponse());

        BarberShopResponse response = this.barberShopService.updateBarberShop(ID, this.barberShopRequest);

        assertNotNull(response);
        assertEquals(BarberShopResponse.class, response.getClass());
        verify(this.itemService, times(1)).updateItem(anyInt(), any());
        verify(this.barberShopRepository, times(1)).save(any());
    }

    @Test
    void whenUpdateBarberShopWithEmployeeAndItemThenReturnSuccess() {
        this.barberShop.setItems(List.of(this.item));
        this.barberShop.setEmployees(List.of(this.employee));

        when(this.barberShopService.barberShopExists(anyInt())).thenReturn(true);
        when(this.barberShopRepository.findByEmail(anyString())).thenReturn(null);
        when(this.barberShopRepository.getById(anyInt())).thenReturn(this.barberShop);
        when(this.barberShopMapper.toBarberShop((BarberShopRequest) any())).thenReturn(this.barberShop);
        when(this.barberShopMapper.toBarberShopResponse(any())).thenReturn(this.barberShopResponse);

        when(this.itemService.itemById(anyInt())).thenReturn(new ItemResponse());
        when(this.itemMapper.toItem((ItemResponse) any())).thenReturn(this.item);
        when(this.itemService.itemExists(anyInt())).thenReturn(true);
        when(this.itemMapper.toItemResponse(any())).thenReturn(new ItemResponse());
        when(this.itemService.updateItem(anyInt(), any())).thenReturn(new ItemResponse());

        when(this.employeeService.employeeById(anyInt())).thenReturn(new EmployeeResponse());
        when(this.employeeMapper.toEmployee((EmployeeResponse) any())).thenReturn(this.employee);
        when(this.employeeService.updateEmployee(anyInt(), any())).thenReturn(new EmployeeResponse());

        BarberShopResponse response = this.barberShopService.updateBarberShop(ID, this.barberShopRequest);

        assertNotNull(response);
        assertEquals(BarberShopResponse.class, response.getClass());
        verify(this.itemService, times(1)).updateItem(anyInt(), any());
        verify(this.employeeService, times(1)).updateEmployee(anyInt(), any());
        verify(this.barberShopRepository, times(1)).save(any());
    }

    @Test
    void whenUpdateBarberShopWithEmployeeThenThrowAlreadyActiveException() {
        this.employee.setBarberShop(this.barberShop);
        this.barberShop.setEmployees(List.of(this.employee));

        when(this.barberShopService.barberShopExists(anyInt())).thenReturn(true);
        when(this.barberShopRepository.findByEmail(anyString())).thenReturn(null);
        when(this.barberShopRepository.getById(anyInt())).thenReturn(this.barberShop);
        when(this.barberShopMapper.toBarberShop((BarberShopRequest) any())).thenReturn(this.barberShop);
        when(this.barberShopMapper.toBarberShopResponse(any())).thenReturn(this.barberShopResponse);

        when(this.employeeService.employeeById(anyInt())).thenReturn(new EmployeeResponse());
        when(this.employeeMapper.toEmployee((EmployeeResponse) any())).thenReturn(this.employee);
        when(this.employeeService.updateEmployee(anyInt(), any())).thenReturn(new EmployeeResponse());

        try {
            this.barberShopService.updateBarberShop(ID, this.barberShopRequest);
        } catch (Exception ex) {
            assertEquals(AlreadyActiveException.class, ex.getClass());
            assertEquals("Employee with ID '" + ID + "' belongs to other barber shop.", ex.getMessage());
        }
    }

    @Test
    void whenUpdateBarberShopWithItemThenThrowAlreadyActiveException() {
        this.item.setBarberShop(this.barberShop);
        this.barberShop.setItems(List.of(this.item));

        when(this.barberShopService.barberShopExists(anyInt())).thenReturn(true);
        when(this.barberShopRepository.findByEmail(anyString())).thenReturn(null);
        when(this.barberShopRepository.getById(anyInt())).thenReturn(this.barberShop);
        when(this.barberShopMapper.toBarberShop((BarberShopRequest) any())).thenReturn(this.barberShop);
        when(this.barberShopMapper.toBarberShopResponse(any())).thenReturn(this.barberShopResponse);

        when(this.employeeService.employeeById(anyInt())).thenReturn(new EmployeeResponse());
        when(this.employeeMapper.toEmployee((EmployeeResponse) any())).thenReturn(this.employee);
        when(this.employeeService.updateEmployee(anyInt(), any())).thenReturn(new EmployeeResponse());

        when(this.itemMapper.toItem((ItemResponse) any())).thenReturn(this.item);

        try {
            this.barberShopService.updateBarberShop(ID, this.barberShopRequest);
        } catch (Exception ex) {
            assertEquals(AlreadyActiveException.class, ex.getClass());
            assertEquals("Item with ID '" + ID + "' belongs to other barber shop.", ex.getMessage());
        }
    }

    @Test
    void whenUpdateBarberShopWithItemThenThrowNullPointerException() {
        this.item.setBarberShop(this.barberShop);
        this.barberShopRequest.setItems(null);

        try {
            this.barberShopService.updateBarberShop(ID, this.barberShopRequest);
        } catch (Exception ex) {
            assertEquals(NullPointerException.class, ex.getClass());
            assertEquals("Null Fields aren't allowed.", ex.getMessage());
        }
    }

    @Test
    void whenUpdateBarberShopThenThrowAlreadyExistsException() {
        when(this.barberShopService.barberShopExists(anyInt())).thenReturn(true);
        when(this.barberShopRepository.findByEmail(anyString())).thenReturn(new BarberShop());

        try {
            this.barberShopService.updateBarberShop(ID, this.barberShopRequest);
        } catch (Exception ex) {
            assertEquals(AlreadyExistsException.class, ex.getClass());
            assertEquals("Barber Shop with email '" + MAIL + "' already exists.", ex.getMessage());
        }
    }

    @Test
    void whenUpdateBarberShopThenThrowNotFoundException() {
        when(this.barberShopService.barberShopExists(anyInt())).thenReturn(false);

        try {
            this.barberShopService.updateBarberShop(ID, this.barberShopRequest);
        } catch (Exception ex) {
            assertEquals(NotFoundException.class, ex.getClass());
            assertEquals("Barber Shop with ID '" + ID + "' not found.", ex.getMessage());
        }
    }

    @Test
    void whenUdpateClientAtBarberShopThenReturnSuccess() {
        BarberShop barberUpdt = new BarberShop(ID, NAME_BARBER, ZIP_CODE, ADRESS, MAIL, NUMBER, OPENING, CLOSING, STATUS_ACTIVE, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        this.barberShop.setClients(List.of(this.client));

        when(this.barberShopService.barberShopExists(anyInt())).thenReturn(true);
        when(this.barberShopRepository.getById(anyInt())).thenReturn(barberUpdt);
        when(this.barberShopMapper.toBarberShop((BarberShopRequest) any())).thenReturn(this.barberShop);
        when(this.barberShopMapper.toBarberShopResponse(any())).thenReturn(this.barberShopResponse);

        BarberShopResponse response = this.barberShopService.updateClientAtBarberShop(ID, this.barberShopRequest);

        assertNotNull(response);
        assertEquals(BarberShopResponse.class, response.getClass());
        assertEquals(1, barberUpdt.getClients().size());
        verify(this.barberShopRepository, times(1)).save(any());
    }

    @Test
    void whenUpdateClientAtBarberShopThenThrowNotFoundException() {
        when(this.barberShopService.barberShopExists(anyInt())).thenReturn(false);
        try {
            this.barberShopService.updateClientAtBarberShop(ID, this.barberShopRequest);
        } catch (Exception ex) {
            assertEquals(NotFoundException.class, ex.getClass());
            assertEquals("Barber Shop with ID '" + ID + "' not found.", ex.getMessage());
        }
    }

    @Test
    void whenRemoveClientThenReturnSuccess() {
        List<Client> clients = new ArrayList<>();
        clients.add(this.client);
        this.barberShop.setClients(clients);
        when(this.barberShopService.barberShopExists(anyInt())).thenReturn(true);
        when(this.barberShopRepository.getById(anyInt())).thenReturn(this.barberShop);
        when(this.barberShopMapper.toBarberShop((BarberShopRequest) any())).thenReturn(this.barberShop);

        this.barberShopService.removeClient(ID, ID);

        assertTrue(this.barberShop.getClients().isEmpty());
        verify(this.barberShopService, times(1)).updateClientAtBarberShop(anyInt(), any());
    }

    @Test
    void whenRemoveClientWithWrongIdThenThrowNotFoundException() {
        when(this.barberShopService.barberShopExists(anyInt())).thenReturn(false);

        try {
            this.barberShopService.removeClient(ID, ID);
        } catch (Exception ex) {
            assertEquals(NotFoundException.class, ex.getClass());
            assertEquals("Barber Shop with ID '" + ID + "' not found.", ex.getMessage());
        }
    }

    @Test
    void whenRemoveClientWithoutClientsExistingThenThrowNotFoundException() {
        when(this.barberShopService.barberShopExists(anyInt())).thenReturn(true);
        when(this.barberShopRepository.getById(anyInt())).thenReturn(this.barberShop);

        try {
            this.barberShopService.removeClient(ID, ID);
        } catch (Exception ex) {
            assertEquals(NotFoundException.class, ex.getClass());
            assertEquals("Barber Shop hasn't clients to be removed.", ex.getMessage());
        }
    }

    @Test
    void whenRemoveClientWithClientIdNotExistingThenThrowNotFoundException() {
        this.client.setClientId(2);
        this.barberShop.setClients(List.of(this.client));
        when(this.barberShopService.barberShopExists(anyInt())).thenReturn(true);
        when(this.barberShopRepository.getById(anyInt())).thenReturn(this.barberShop);

        try {
            this.barberShopService.removeClient(ID, ID);
        } catch (Exception ex) {
            assertEquals(NotFoundException.class, ex.getClass());
            assertEquals("Client with ID '" + ID + "' doesn't belong at the barbershop with ID '" + ID + "'.", ex.getMessage());
        }
    }

    @Test
    void whenDismissEmployeeThenReturnSuccess() {
        this.barberShop.setEmployees(List.of(this.employee));

        when(this.barberShopService.barberShopExists(anyInt())).thenReturn(true);
        when(this.barberShopRepository.getById(anyInt())).thenReturn(this.barberShop);
        when(this.employeeService.employeeById(anyInt())).thenReturn(this.employeeResponse);
        when(this.employeeMapper.toEmployee((EmployeeResponse) any())).thenReturn(this.employee);

        this.barberShopService.dismissEmployee(ID, ID);
        doNothing().when(this.employeeService).removeBarberShop(anyInt());

        verify(this.employeeService, times(1)).removeBarberShop(anyInt());
    }

    @Test
    void whenDismissEmployeeThenThrowNotFoundException() {
        Employee employeeRemoved = new Employee();
        employeeRemoved.setEmployeeId(2);
        this.barberShop.setEmployees(List.of(employeeRemoved));

        when(this.barberShopService.barberShopExists(anyInt())).thenReturn(true);
        when(this.barberShopRepository.getById(anyInt())).thenReturn(this.barberShop);
        when(this.employeeService.employeeById(anyInt())).thenReturn(this.employeeResponse);
        when(this.employeeMapper.toEmployee((EmployeeResponse) any())).thenReturn(this.employee);

        try {
            this.barberShopService.dismissEmployee(ID, ID);
        } catch (Exception ex) {
            assertEquals(NotFoundException.class, ex.getClass());
            assertEquals("Employee with ID '" + ID + "' was not found at the barbershop.", ex.getMessage());
        }
    }

    @Test
    void whenDismissEmployeeWithoutEmployeeExistingThenThrowNotFoundException() {
        when(this.barberShopService.barberShopExists(anyInt())).thenReturn(true);
        when(this.barberShopRepository.getById(anyInt())).thenReturn(this.barberShop);

        try {
            this.barberShopService.dismissEmployee(ID, ID);
        } catch (Exception ex) {
            assertEquals(NotFoundException.class, ex.getClass());
            assertEquals("Theren't employees at the barbershop.", ex.getMessage());
        }
    }

    @Test
    void whenDismissEmployeeInBarberShopNotExistingThenThrowNotFoundException() {
        when(this.barberShopService.barberShopExists(anyInt())).thenReturn(false);

        try {
            this.barberShopService.dismissEmployee(ID, ID);
        } catch (Exception ex) {
            assertEquals(NotFoundException.class, ex.getClass());
            assertEquals("Barber Shop with ID '" + ID + "' not found.", ex.getMessage());
        }
    }

    private void startDomains() {
        this.barberShop = new BarberShop(ID, NAME_BARBER, ZIP_CODE, ADRESS, MAIL, NUMBER, OPENING, CLOSING, STATUS_ACTIVE, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        this.barberShopRequest = new BarberShopRequest(ID, NAME_BARBER, ZIP_CODE, ADRESS, MAIL, PASSWORD, NUMBER, OPENING, CLOSING, STATUS_ACTIVE, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        this.barberShopResponse = new BarberShopResponse(ID, NAME_BARBER, ZIP_CODE, ADRESS, MAIL, NUMBER, OPENING, CLOSING, STATUS_ACTIVE, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        this.barberShopSimple = new BarberShopSimple(ID, NAME_BARBER, NUMBER, ADRESS, OPENING, CLOSING, STATUS_ACTIVE);

        this.client.setClientId(ID);
        this.clientRequest.setClientId(ID);
        this.clientSimple.setClientId(ID);
        this.employee.setEmployeeId(ID);
        this.employeeResponse.setEmployeeId(ID);
        this.item.setItemId(ID);
    }

}