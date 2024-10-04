package br.com.barbermanager.barbershopmanagement.domain.service.client;

import br.com.barbermanager.barbershopmanagement.api.mapper.BarberShopMapper;
import br.com.barbermanager.barbershopmanagement.api.mapper.ClientMapper;
import br.com.barbermanager.barbershopmanagement.api.request.barbershop.BarberShopRequest;
import br.com.barbermanager.barbershopmanagement.api.request.client.ClientRequest;
import br.com.barbermanager.barbershopmanagement.api.response.barbershop.BarberShopResponse;
import br.com.barbermanager.barbershopmanagement.api.response.barbershop.BarberShopSimple;
import br.com.barbermanager.barbershopmanagement.api.response.client.ClientResponse;
import br.com.barbermanager.barbershopmanagement.api.response.client.ClientSimple;
import br.com.barbermanager.barbershopmanagement.api.response.employee.EmployeeResponse;
import br.com.barbermanager.barbershopmanagement.domain.model.*;
import br.com.barbermanager.barbershopmanagement.domain.repository.ClientRepository;
import br.com.barbermanager.barbershopmanagement.domain.service.barbershop.BarberShopService;
import br.com.barbermanager.barbershopmanagement.exception.AlreadyActiveException;
import br.com.barbermanager.barbershopmanagement.exception.AlreadyExistsException;
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

class ClientServiceImplTest {

    public static final int ID = 1;
    public static final String NAME = "Lucas";
    public static final String CPF = "123456789";
    public static final String PHONE = "99887766";
    public static final StatusEnum STATUS_ACTIVE = StatusEnum.ACTIVE;
    public static final StatusEnum STATUS_INACTIVE = StatusEnum.INACTIVE;

    private Client client = new Client();
    private ClientRequest clientRequest = new ClientRequest();
    private ClientResponse clientResponse = new ClientResponse();
    private ClientSimple clientSimple = new ClientSimple();

    private BarberShop barberShop = new BarberShop();
    private BarberShopResponse barberShopResponse = new BarberShopResponse();

    @InjectMocks
    private ClientServiceImpl clientService;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private BarberShopService barberShopService;

    @Mock
    private BarberShopMapper barberShopMapper;

    @Mock
    private ClientMapper clientMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.startDomains();
    }

    @Test
    void whenClientExistsThenReturnTrue() {
        when(this.clientRepository.existsById(anyInt())).thenReturn(true);

        Boolean exists = this.clientService.clientExists(ID);

        assertTrue(exists);
    }

    @Test
    void whenClientExistsThenReturnFalse() {
        when(this.clientRepository.existsById(anyInt())).thenReturn(false);

        Boolean exists = this.clientService.clientExists(ID);

        assertFalse(exists);
    }

    @Test
    void whenCreateClientThenReturnSuccess() {
        this.clientRequest.setStatus(STATUS_INACTIVE);
        when(this.clientRepository.findByCpf(anyString())).thenReturn(null);
        when(this.clientMapper.toClient((ClientRequest) any())).thenReturn(this.client);
        when(this.clientMapper.toClientResponse(any())).thenReturn(this.clientResponse);

        ClientResponse response = this.clientService.createClient(this.clientRequest);

        assertNotNull(response);
        assertEquals(ClientResponse.class, response.getClass());
        assertEquals(STATUS_ACTIVE, this.clientRequest.getStatus());
        verify(this.clientRepository, times(1)).save(any());
    }

    @Test
    void whenCreateClientThenThrowAlreadyExistsException() {
        when(this.clientRepository.findByCpf(anyString())).thenReturn(this.client);
        try {
            this.clientService.createClient(this.clientRequest);
        } catch (Exception ex) {
            assertEquals(AlreadyExistsException.class, ex.getClass());
            assertEquals("Client with CPF '" + CPF + "' already exists.", ex.getMessage());
        }
    }

    @Test
    void whenFindAllClientsWithouStatusThenReturnAnListOfClients() {
        when(this.clientRepository.findAll()).thenReturn(List.of(this.client));
        when(this.clientMapper.toClientSimpleList(anyList())).thenReturn(List.of(this.clientSimple));

        List<ClientSimple> response = this.clientService.allClients(null);

        assertNotNull(response);
        assertEquals(ClientSimple.class, response.get(0).getClass());
    }

    @Test
    void whenFindAllClientsWithStatusThenReturnAnListOfClients() {
        when(this.clientRepository.findAll()).thenReturn(List.of(this.client));
        when(this.clientMapper.toClientSimpleList(anyList())).thenReturn(List.of(this.clientSimple));

        List<ClientSimple> response = this.clientService.allClients(STATUS_ACTIVE);

        assertNotNull(response);
        assertEquals(ClientSimple.class, response.get(0).getClass());
    }

    @Test
    void whenFindAllClientsWithoutClientsThenThrowAnNotFoundException() {
        when(this.clientRepository.findAll()).thenReturn(List.of(this.client));
        when(this.clientMapper.toClientSimpleList(anyList())).thenReturn(new ArrayList<>());

        try {
            this.clientService.allClients(null);
        } catch (Exception ex) {
            assertEquals(NotFoundException.class, ex.getClass());
            assertEquals("There aren't clients to show.", ex.getMessage());
        }
    }

    @Test
    void whenFindAllClientsWithoutStatusThenThrowAnNotFoundException() {
        when(this.clientRepository.findAll()).thenReturn(List.of(this.client));
        when(this.clientMapper.toClientSimpleList(anyList())).thenReturn(List.of(this.clientSimple)).thenReturn(new ArrayList<>());

        try {
            this.clientService.allClients(STATUS_INACTIVE);
        } catch (Exception ex) {
            assertEquals(NotFoundException.class, ex.getClass());
            assertEquals("There aren't clients with status '" + STATUS_INACTIVE + "'.", ex.getMessage());
        }
    }

    @Test
    void whenFindClientByIdThenReturnAnClient() {
        when(this.clientRepository.existsById(anyInt())).thenReturn(true);
        when(this.clientRepository.getById(anyInt())).thenReturn(this.client);
        when(this.clientMapper.toClientResponse(any())).thenReturn(this.clientResponse);

        ClientResponse response = this.clientService.clientById(ID);

        assertNotNull(response);
        assertEquals(ClientResponse.class, response.getClass());
        assertEquals(ID, response.getClientId());
    }

    @Test
    void whenFindClientByIdNotExistingThenThrowAnNotFoundException() {
        when(this.clientRepository.existsById(anyInt())).thenReturn(false);

        try {
            this.clientService.clientById(ID);
        } catch (Exception ex) {
            assertEquals(NotFoundException.class, ex.getClass());
            assertEquals("Client with ID '" + ID + "' not found.", ex.getMessage());
        }
    }

    @Test
    void WhenFindClientsByBarberShopThenReturnAnListOfClients() {
        when(this.clientRepository.findClientsByBarberShop(anyInt())).thenReturn(List.of(this.client));
        when(this.clientMapper.toClientSimpleList(anyList())).thenReturn(List.of(this.clientSimple));

        List<ClientSimple> response = this.clientService.clientsByBarberShop(ID, null);

        assertNotNull(response);
        assertEquals(ClientSimple.class, response.get(0).getClass());
    }

    @Test
    void WhenFindClientsByBarberShopWithStatusThenReturnAnListOfClients() {
        when(this.clientRepository.findClientsByBarberShop(anyInt())).thenReturn(List.of(this.client));
        when(this.clientMapper.toClientSimpleList(anyList())).thenReturn(List.of(this.clientSimple));

        List<ClientSimple> response = this.clientService.clientsByBarberShop(ID, STATUS_ACTIVE);

        assertNotNull(response);
        assertEquals(ClientSimple.class, response.get(0).getClass());
        assertEquals(STATUS_ACTIVE, response.get(0).getStatus());
    }

    @Test
    void WhenFindClientsByBarberShopWithStatusThenThrowAnNotFoundException() {
        when(this.clientRepository.findClientsByBarberShop(anyInt())).thenReturn(List.of(this.client));
        when(this.clientMapper.toClientSimpleList(anyList())).thenReturn(List.of(this.clientSimple)).thenReturn(new ArrayList<>());

        try {
            this.clientService.clientsByBarberShop(ID, STATUS_INACTIVE);
        } catch (Exception ex) {
            assertEquals(NotFoundException.class, ex.getClass());
            assertEquals("There aren't clients with status '" + STATUS_INACTIVE + "' at this barbershop.", ex.getMessage());
        }
    }

    @Test
    void WhenFindClientsByBarberShopWithoutBarberShopThenThrowAnNotFoundException() {
        when(this.clientRepository.findClientsByBarberShop(anyInt())).thenReturn(List.of(this.client));
        when(this.clientMapper.toClientSimpleList(anyList())).thenReturn(new ArrayList<>());

        try {
            this.clientService.clientsByBarberShop(ID, STATUS_INACTIVE);
        } catch (Exception ex) {
            assertEquals(NotFoundException.class, ex.getClass());
            assertEquals("There aren't clients at this barbershop.", ex.getMessage());
        }
    }

    @Test
    void whenDeleteClientThenReturnSuccess() {
        when(this.clientRepository.existsById(anyInt())).thenReturn(true);
        when(this.clientRepository.getById(anyInt())).thenReturn(this.client);

        this.clientService.deleteClient(ID);

        assertEquals(ID, this.client.getClientId());
        assertEquals(STATUS_INACTIVE, this.client.getStatus());
        verify(this.clientRepository, times(1)).save(any());
    }

    @Test
    void whenDeleteClientThenThrowAnNotFoundException() {
        when(this.clientRepository.existsById(anyInt())).thenReturn(false);

        try {
            this.clientService.deleteClient(ID);
        } catch (Exception ex) {
            assertEquals(NotFoundException.class, ex.getClass());
            assertEquals("Client with ID '" + ID + "' not found.", ex.getMessage());
        }
    }

    @Test
    void whenActiveClientThenReturnSuccess() {
        this.client.setStatus(STATUS_INACTIVE);

        when(this.clientService.clientExists(anyInt())).thenReturn(true);
        when(this.clientRepository.getById(anyInt())).thenReturn(this.client);

        this.clientService.activeClient(ID);

        assertEquals(ID, this.client.getClientId());
        assertEquals(STATUS_ACTIVE, this.client.getStatus());
        verify(this.clientRepository, times(1)).save(any());
    }

    @Test
    void whenActiveClientThenThrowAnAlreadyActiveException() {
        when(this.clientService.clientExists(anyInt())).thenReturn(true);
        when(this.clientRepository.getById(anyInt())).thenReturn(this.client);

        try {
            this.clientService.activeClient(ID);
        } catch (Exception ex) {
            assertEquals(AlreadyActiveException.class, ex.getClass());
            assertEquals("Client with ID '" + ID + "' is already active.", ex.getMessage());
        }
    }

    @Test
    void whenActiveClientThenThrowAnNotFoundException() {
        when(this.clientService.clientExists(anyInt())).thenReturn(false);

        try {
            this.clientService.activeClient(ID);
        } catch (Exception ex) {
            assertEquals(NotFoundException.class, ex.getClass());
            assertEquals("Client with ID '" + ID + "' not found.", ex.getMessage());
        }
    }

    @Test
    void whenUpdateClientThenReturnSuccess() {
        this.clientRequest.setSchedulings(null);
        this.client.setBarberShops(List.of(this.barberShop));

        when(this.clientService.clientExists(anyInt())).thenReturn(true);
        when(this.clientRepository.findByCpf(anyString())).thenReturn(null);
        when(this.clientRepository.getById(anyInt())).thenReturn(this.client);
        when(this.clientMapper.toClient((ClientRequest) any())).thenReturn(this.client);
        when(this.clientMapper.toClientResponse(any())).thenReturn(this.clientResponse);

        when(this.barberShopService.barberShopById(anyInt())).thenReturn(this.barberShopResponse);
        when(this.barberShopMapper.toBarberShop((BarberShopResponse) any())).thenReturn(this.barberShop);
        when(this.barberShopMapper.toBarberShopResponse(any())).thenReturn(this.barberShopResponse);

        ClientResponse response = this.clientService.updateClient(ID, this.clientRequest);

        assertNotNull(response);
        assertEquals(ClientResponse.class, response.getClass());
        assertEquals(ID, this.barberShop.getClients().get(0).getClientId());
        verify(this.clientRepository, times(1)).save(any());
    }

    @Test
    void whenUpdateClientWithBarberShopNotExistingThenThrowAnNotFoundException() {
        this.client.setBarberShops(List.of(this.barberShop));

        when(this.clientService.clientExists(anyInt())).thenReturn(true);
        when(this.clientRepository.findByCpf(anyString())).thenReturn(null);
        when(this.clientRepository.getById(anyInt())).thenReturn(this.client);
        when(this.clientMapper.toClient((ClientRequest) any())).thenReturn(this.client);

        when(this.barberShopService.barberShopById(anyInt())).thenReturn(this.barberShopResponse);
        when(this.barberShopMapper.toBarberShop((BarberShopResponse) any())).thenReturn(this.barberShop);

        try {
            this.clientService.updateClient(ID, this.clientRequest);
        } catch (Exception ex) {
            assertEquals(NotFoundException.class, ex.getClass());
            assertEquals("Barber Shop with ID '" + ID + "' not found.", ex.getMessage());
        }
    }

    @Test
    void whenUpdateClientWithCPFExistingThenThrowAnAlreadyExistsException() {
        when(this.clientService.clientExists(anyInt())).thenReturn(true);
        when(this.clientRepository.findByCpf(anyString())).thenReturn(this.client);

        try {
            this.clientService.updateClient(ID, this.clientRequest);
        } catch (Exception ex) {
            assertEquals(AlreadyExistsException.class, ex.getClass());
            assertEquals("Client with CPF '" + CPF + "' exists.", ex.getMessage());
        }
    }

    @Test
    void whenUpdateClientThenReturnsSuccess() {
        when(this.clientService.clientExists(anyInt())).thenReturn(false);

        try {
            this.clientService.updateClient(ID, this.clientRequest);
        } catch (Exception ex) {
            assertEquals(NotFoundException.class, ex.getClass());
            assertEquals("Client with ID '" + ID + "' not found.", ex.getMessage());
        }
    }

    private void startDomains() {
        this.client = new Client(ID, NAME, CPF, PHONE, STATUS_ACTIVE, new ArrayList<>(), new ArrayList<>());
        this.clientRequest = new ClientRequest(ID, NAME, CPF, PHONE, STATUS_ACTIVE, new ArrayList<>(), new ArrayList<>());
        this.clientResponse = new ClientResponse(ID, NAME, CPF, PHONE, STATUS_ACTIVE, new ArrayList<>(), new ArrayList<>());
        this.clientSimple = new ClientSimple(ID, NAME, PHONE, STATUS_ACTIVE);

        this.barberShop.setBarberShopId(ID);
        this.barberShopResponse.setBarberShopId(ID);
    }
}