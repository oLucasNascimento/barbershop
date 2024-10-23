package br.com.barbermanager.barbershopmanagement.domain.service.scheduling;

import br.com.barbermanager.barbershopmanagement.api.mapper.SchedulingMapper;
import br.com.barbermanager.barbershopmanagement.api.request.barbershop.BarberShopRequest;
import br.com.barbermanager.barbershopmanagement.api.request.client.ClientRequest;
import br.com.barbermanager.barbershopmanagement.api.request.employee.EmployeeRequest;
import br.com.barbermanager.barbershopmanagement.api.request.item.ItemRequest;
import br.com.barbermanager.barbershopmanagement.api.request.scheduling.SchedulingRequest;
import br.com.barbermanager.barbershopmanagement.api.response.barbershop.BarberShopResponse;
import br.com.barbermanager.barbershopmanagement.api.response.barbershop.BarberShopSimple;
import br.com.barbermanager.barbershopmanagement.api.response.client.ClientResponse;
import br.com.barbermanager.barbershopmanagement.api.response.client.ClientSimple;
import br.com.barbermanager.barbershopmanagement.api.response.employee.EmployeeResponse;
import br.com.barbermanager.barbershopmanagement.api.response.employee.EmployeeSimple;
import br.com.barbermanager.barbershopmanagement.api.response.item.ItemResponse;
import br.com.barbermanager.barbershopmanagement.api.response.item.ItemSimple;
import br.com.barbermanager.barbershopmanagement.api.response.scheduling.SchedulingResponse;
import br.com.barbermanager.barbershopmanagement.domain.model.*;
import br.com.barbermanager.barbershopmanagement.domain.repository.SchedulingRepository;
import br.com.barbermanager.barbershopmanagement.domain.service.barbershop.BarberShopService;
import br.com.barbermanager.barbershopmanagement.domain.service.client.ClientService;
import br.com.barbermanager.barbershopmanagement.domain.service.employee.EmployeeService;
import br.com.barbermanager.barbershopmanagement.domain.service.item.ItemService;
import br.com.barbermanager.barbershopmanagement.exception.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class SchedulingServiceImplTest {

    public static final Integer ID = 1;
    public static final Integer ID2 = 2;
    public static final String NAME_BARBER = "El Bigodon";
    public static final String ZIP_CODE = "55000000";
    public static final String ADRESS = "Rua Macaparana";
    public static final String MAIL = "el@bigodon.hair";
    public static final String NUMBER = "99887766";
    public static final LocalTime OPENING = LocalTime.of(9, 0);
    public static final LocalTime CLOSING = LocalTime.of(18, 0);
    public static final LocalDateTime DATE_07 = LocalDateTime.of(2024, 10, 5, 7, 0);
    public static final LocalDateTime DATE_10 = LocalDateTime.of(2024, 10, 5, 10, 0);
    public static final LocalDateTime DATE_11 = LocalDateTime.of(2024, 10, 5, 11, 10);
    public static final LocalDateTime DATE_12 = LocalDateTime.of(2024, 10, 5, 12, 0);
    public static final LocalDateTime DATE_18 = LocalDateTime.of(2024, 10, 5, 18, 0);
    public static final LocalDateTime DATE_19 = LocalDateTime.of(2024, 10, 5, 19, 0);
    public static final String NAME = "Lucas";
    public static final String CPF = "123456789";
    public static final String PHONE = "99887766";
    public static final double PRICE = 20.0;
    public static final int TIME = 70;
    public static final StatusEnum STATUS_ACTIVE = StatusEnum.ACTIVE;
    public static final StatusEnum STATUS_INACTIVE = StatusEnum.INACTIVE;
    public static final StatusEnum STATUS_SCHEDULED = StatusEnum.SCHEDULED;
    public static final StatusEnum STATUS_FINISHED = StatusEnum.FINISHED;
    public static final StatusEnum STATUS_CANCELLED = StatusEnum.CANCELLED;

    private Scheduling scheduling = new Scheduling();
    private Scheduling schedulingOld = new Scheduling();
    private SchedulingRequest schedulingRequest = new SchedulingRequest();
    private SchedulingResponse schedulingResponse = new SchedulingResponse();

    private BarberShop barberShop = new BarberShop();
    private BarberShopSimple barberShopSimple = new BarberShopSimple();
    private BarberShopRequest barberShopRequest = new BarberShopRequest();
    private BarberShopResponse barberShopResponse = new BarberShopResponse();

    private Client client = new Client();
    private ClientSimple clientSimple = new ClientSimple();
    private ClientRequest clientRequest = new ClientRequest();
    private ClientResponse clientResponse = new ClientResponse();

    private Employee employee = new Employee();
    private EmployeeSimple employeeSimple = new EmployeeSimple();
    private EmployeeRequest employeeRequest = new EmployeeRequest();
    private EmployeeResponse employeeResponse = new EmployeeResponse();

    private Item item = new Item();
    private ItemSimple itemSimple = new ItemSimple();
    private ItemRequest itemRequest = new ItemRequest();
    private ItemResponse itemResponse = new ItemResponse();

    @InjectMocks
    private SchedulingServiceImpl schedulingService;

    @Mock
    private SchedulingRepository schedulingRepository;

    @Mock
    private SchedulingMapper schedulingMapper;

    @Mock
    private ItemService itemService;

    @Mock
    private EmployeeService employeeService;

    @Mock
    private BarberShopService barberShopService;

    @Mock
    private ClientService clientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.startUser();
    }

    @Test
    void whenCreateNewSchedulingThenReturnSuccess() {
        schedulingOld.setStatus(STATUS_CANCELLED);
        when(this.schedulingMapper.toScheduling((SchedulingRequest) any())).thenReturn(this.scheduling);
        when(this.schedulingMapper.toSchedulingResponse(any())).thenReturn(this.schedulingResponse);
        when(this.barberShopService.barberShopById(anyInt())).thenReturn(this.barberShopResponse);
        when(this.employeeService.employeesByBarberShop(anyInt(), any())).thenReturn(List.of(this.employeeSimple));
        when(this.itemService.itemById(anyInt())).thenReturn(this.itemResponse);
        when(this.schedulingRepository.findAllByDate(any())).thenReturn(List.of(this.schedulingOld));

        SchedulingResponse response = this.schedulingService.newScheduling(this.schedulingRequest);

        assertNotNull(response);
        assertEquals(SchedulingResponse.class, response.getClass());
        assertEquals(STATUS_SCHEDULED, scheduling.getStatus());

        assertEquals(ID, response.getClient().getClientId());
        assertEquals(ID, response.getBarberShop().getBarberShopId());
        assertEquals(ID, response.getEmployee().getEmployeeId());
        assertEquals(ID, response.getItems().get(0).getItemId());

        verify(this.schedulingRepository, times(1)).save(any());

    }

    @Test
    void whenCreateNewSchedulingWithSchedulingAtSameTimeButWithAnotherStatusThenReturnSuccess() {
        this.schedulingOld.setStatus(STATUS_FINISHED);
        when(this.schedulingMapper.toScheduling((SchedulingRequest) any())).thenReturn(this.scheduling);
        when(this.schedulingMapper.toSchedulingResponse(any())).thenReturn(this.schedulingResponse);
        when(this.barberShopService.barberShopById(anyInt())).thenReturn(this.barberShopResponse);
        when(this.employeeService.employeesByBarberShop(anyInt(), any())).thenReturn(List.of(this.employeeSimple));
        when(this.itemService.itemById(anyInt())).thenReturn(this.itemResponse);
        when(this.schedulingRepository.findAllByDate(any())).thenReturn(List.of(this.schedulingOld));

        SchedulingResponse response = this.schedulingService.newScheduling(this.schedulingRequest);

        assertNotNull(response);
        assertEquals(SchedulingResponse.class, response.getClass());
        assertEquals(STATUS_SCHEDULED, scheduling.getStatus());

        assertEquals(ID, response.getClient().getClientId());
        assertEquals(ID, response.getBarberShop().getBarberShopId());
        assertEquals(ID, response.getEmployee().getEmployeeId());
        assertEquals(ID, response.getItems().get(0).getItemId());

        verify(this.schedulingRepository, times(1)).save(any());
    }

    @Test
    void whenCreateNewSchedulingWithSchedulingAtSameTimeButWithAnotherEmployeeThenReturnSuccess() {
        Employee employeeOld = new Employee();
        employeeOld.setEmployeeId(ID2);
        EmployeeSimple employeeSimple = new EmployeeSimple();
        employeeSimple.setEmployeeId(ID2);

        this.schedulingOld.setStatus(STATUS_SCHEDULED);
        this.schedulingOld.setEmployee(employeeOld);
        this.schedulingResponse.setEmployee(employeeSimple);

        when(this.schedulingMapper.toSchedulingResponse(any())).thenReturn(this.schedulingResponse);
        when(this.barberShopService.barberShopById(anyInt())).thenReturn(this.barberShopResponse);
        when(this.itemService.itemById(anyInt())).thenReturn(this.itemResponse);
        when(this.employeeService.employeesByBarberShop(anyInt(), any())).thenReturn(List.of(this.employeeSimple));
        when(this.schedulingMapper.toScheduling((SchedulingRequest) any())).thenReturn(this.scheduling);
        when(this.schedulingRepository.findAllByDate(any())).thenReturn(List.of(schedulingOld));

        SchedulingResponse response = this.schedulingService.newScheduling(this.schedulingRequest);

        assertNotNull(response);
        assertEquals(SchedulingResponse.class, response.getClass());
        assertEquals(STATUS_SCHEDULED, scheduling.getStatus());

        assertEquals(ID, response.getClient().getClientId());
        assertEquals(ID, response.getBarberShop().getBarberShopId());
        assertEquals(ID2, response.getEmployee().getEmployeeId());
        assertEquals(ID, response.getItems().get(0).getItemId());

        verify(this.schedulingRepository, times(1)).save(any());
    }

    @Test
    void whenCreateNewSchedulingWithTimeBeforeExistingOneWithSameEmployeeThenReturnSuccess() {
        this.schedulingOld.setStatus(STATUS_SCHEDULED);
        this.schedulingOld.setSchedulingTime(DATE_12);

        when(this.schedulingMapper.toSchedulingResponse(any())).thenReturn(this.schedulingResponse);
        when(this.barberShopService.barberShopById(anyInt())).thenReturn(this.barberShopResponse);
        when(this.itemService.itemById(anyInt())).thenReturn(this.itemResponse);
        when(this.employeeService.employeesByBarberShop(anyInt(), any())).thenReturn(List.of(this.employeeSimple));
        when(this.schedulingMapper.toScheduling((SchedulingRequest) any())).thenReturn(this.scheduling);
        when(this.schedulingRepository.findAllByDate(any())).thenReturn(List.of(schedulingOld));

        SchedulingResponse response = this.schedulingService.newScheduling(this.schedulingRequest);

        assertNotNull(response);
        assertEquals(SchedulingResponse.class, response.getClass());
        assertEquals(STATUS_SCHEDULED, scheduling.getStatus());

        assertEquals(ID, response.getClient().getClientId());
        assertEquals(ID, response.getBarberShop().getBarberShopId());
        assertEquals(ID, response.getEmployee().getEmployeeId());
        assertEquals(ID, response.getItems().get(0).getItemId());
        assertEquals(DATE_10, response.getSchedulingTime());
        assertEquals(DATE_12, this.schedulingOld.getSchedulingTime());

        verify(this.schedulingRepository, times(1)).save(any());
    }

    @Test
    void whenCreateNewSchedulingWithStartTimeEqualToEndTimeOfAnotherThenReturnSuccess() {
        this.schedulingOld.setStatus(STATUS_SCHEDULED);
        this.scheduling.setSchedulingTime(DATE_11);
        this.schedulingResponse.setSchedulingTime(DATE_11);

        when(this.schedulingMapper.toSchedulingResponse(any())).thenReturn(this.schedulingResponse);
        when(this.barberShopService.barberShopById(anyInt())).thenReturn(this.barberShopResponse);
        when(this.itemService.itemById(anyInt())).thenReturn(this.itemResponse);
        when(this.employeeService.employeesByBarberShop(anyInt(), any())).thenReturn(List.of(this.employeeSimple));
        when(this.schedulingMapper.toScheduling((SchedulingRequest) any())).thenReturn(this.scheduling);
        when(this.schedulingRepository.findAllByDate(any())).thenReturn(List.of(schedulingOld));

        SchedulingResponse response = this.schedulingService.newScheduling(this.schedulingRequest);

        assertNotNull(response);
        assertEquals(SchedulingResponse.class, response.getClass());
        assertEquals(STATUS_SCHEDULED, scheduling.getStatus());

        assertEquals(ID, response.getClient().getClientId());
        assertEquals(ID, response.getBarberShop().getBarberShopId());
        assertEquals(ID, response.getEmployee().getEmployeeId());
        assertEquals(ID, response.getItems().get(0).getItemId());
        assertEquals(DATE_11, response.getSchedulingTime());

        verify(this.schedulingRepository, times(1)).save(any());
    }

    @Test
    void whenCreateNewSchedulingWithEndTimeAfterClosingTimeThenThrowAnBusinessException() {
        this.schedulingOld.setStatus(STATUS_SCHEDULED);
        this.scheduling.setSchedulingTime(DATE_18);


        when(this.schedulingMapper.toSchedulingResponse(any())).thenReturn(this.schedulingResponse);
        when(this.barberShopService.barberShopById(anyInt())).thenReturn(this.barberShopResponse);
        when(this.itemService.itemById(anyInt())).thenReturn(this.itemResponse);
        when(this.employeeService.employeesByBarberShop(anyInt(), any())).thenReturn(List.of(this.employeeSimple));
        when(this.schedulingMapper.toScheduling((SchedulingRequest) any())).thenReturn(this.scheduling);
        when(this.schedulingRepository.findAllByDate(any())).thenReturn(List.of(schedulingOld));

        try {
            this.schedulingService.newScheduling(this.schedulingRequest);
        } catch (Exception ex) {
            assertEquals(BusinessException.class, ex.getClass());
            assertEquals("These hours are outside of the barber shop's working hours.", ex.getMessage());
        }
    }

    @Test
    void whenCreateNewSchedulingWithStartTimeAfterOpeningTimeThenThrowAnBusinessException() {
        this.schedulingOld.setStatus(STATUS_SCHEDULED);
        this.scheduling.setSchedulingTime(DATE_07);


        when(this.schedulingMapper.toSchedulingResponse(any())).thenReturn(this.schedulingResponse);
        when(this.barberShopService.barberShopById(anyInt())).thenReturn(this.barberShopResponse);
        when(this.itemService.itemById(anyInt())).thenReturn(this.itemResponse);
        when(this.employeeService.employeesByBarberShop(anyInt(), any())).thenReturn(List.of(this.employeeSimple));
        when(this.schedulingMapper.toScheduling((SchedulingRequest) any())).thenReturn(this.scheduling);
        when(this.schedulingRepository.findAllByDate(any())).thenReturn(List.of(schedulingOld));

        try {
            this.schedulingService.newScheduling(this.schedulingRequest);
        } catch (Exception ex) {
            assertEquals(BusinessException.class, ex.getClass());
            assertEquals("These hours are outside of the barber shop's working hours.", ex.getMessage());
        }
    }

    @Test
    void whenCreateNewSchedulingWithEndTimeInAnotherDayThenThrowAnBusinessException() {
        this.schedulingOld.setStatus(STATUS_SCHEDULED);
        this.scheduling.setSchedulingTime(DATE_18);
        this.item.setTime(7000);

        when(this.schedulingMapper.toSchedulingResponse(any())).thenReturn(this.schedulingResponse);
        when(this.barberShopService.barberShopById(anyInt())).thenReturn(this.barberShopResponse);
        when(this.itemService.itemById(anyInt())).thenReturn(this.itemResponse);
        when(this.employeeService.employeesByBarberShop(anyInt(), any())).thenReturn(List.of(this.employeeSimple));
        when(this.schedulingMapper.toScheduling((SchedulingRequest) any())).thenReturn(this.scheduling);
        when(this.schedulingRepository.findAllByDate(any())).thenReturn(List.of(schedulingOld));

        try {
            this.schedulingService.newScheduling(this.schedulingRequest);
        } catch (Exception ex) {
            assertEquals(BusinessException.class, ex.getClass());
            assertEquals("These hours are outside of the barber shop's working hours.", ex.getMessage());
        }
    }

    @Test
    void whenCreateNewSchedulingWithBarberShopInactiveThenThrowAnInactiveException() {
        this.barberShopResponse.setStatus(STATUS_INACTIVE);
        when(this.schedulingMapper.toScheduling((SchedulingRequest) any())).thenReturn(this.scheduling);
        when(this.barberShopService.barberShopById(anyInt())).thenReturn(this.barberShopResponse);

        try {
            this.schedulingService.newScheduling(this.schedulingRequest);
        } catch (Exception ex) {
            assertEquals(InactiveException.class, ex.getClass());
            assertEquals("The barber shop informated is inactive.", ex.getMessage());
        }
    }

    @Test
    void whenCreateNewSchedulingWithEmployeeInactiveThenThrowAnInactiveException() {
        this.employeeSimple.setStatus(STATUS_INACTIVE);
        when(this.schedulingMapper.toScheduling((SchedulingRequest) any())).thenReturn(this.scheduling);
        when(this.barberShopService.barberShopById(anyInt())).thenReturn(this.barberShopResponse);
        when(this.employeeService.employeesByBarberShop(anyInt(), any())).thenReturn(List.of(this.employeeSimple));

        try {
            this.schedulingService.newScheduling(this.schedulingRequest);
        } catch (Exception ex) {
            assertEquals(InactiveException.class, ex.getClass());
            assertEquals("This employee isn't ACTIVE.", ex.getMessage());
        }
    }

    @Test
    void whenCreateNewSchedulingWithEmployeeThatNotBelongToBarberShopThenThrowAnBadRequestException() {
        this.employeeSimple.setEmployeeId(ID2);
        when(this.schedulingMapper.toScheduling((SchedulingRequest) any())).thenReturn(this.scheduling);
        when(this.barberShopService.barberShopById(anyInt())).thenReturn(this.barberShopResponse);
        when(this.employeeService.employeesByBarberShop(anyInt(), any())).thenReturn(List.of(this.employeeSimple));
        when(this.itemService.itemById(anyInt())).thenReturn(this.itemResponse);

        try {
            this.schedulingService.newScheduling(this.schedulingRequest);
        } catch (Exception ex) {
            assertEquals(BadRequestException.class, ex.getClass());
            assertEquals("This employee doesn't belong to the informed barbershop.", ex.getMessage());
        }
    }

    @Test
    void whenCreateNewSchedulingWithItemInactiveThenThrowAnInactiveException() {
        this.itemResponse.setStatus(STATUS_INACTIVE);
        when(this.schedulingMapper.toScheduling((SchedulingRequest) any())).thenReturn(this.scheduling);
        when(this.barberShopService.barberShopById(anyInt())).thenReturn(this.barberShopResponse);
        when(this.employeeService.employeesByBarberShop(anyInt(), any())).thenReturn(List.of(this.employeeSimple));
        when(this.itemService.itemById(anyInt())).thenReturn(this.itemResponse);

        try {
            this.schedulingService.newScheduling(this.schedulingRequest);
        } catch (Exception ex) {
            assertEquals(InactiveException.class, ex.getClass());
            assertEquals("This item isn't ACTIVE.", ex.getMessage());
        }
    }

    @Test
    void whenCreateNewSchedulingWithItemThatNotBelongToBarberShopThenThrowAnBadRequestException() {
        this.itemResponse.getBarberShop().setBarberShopId(ID2);
        when(this.schedulingMapper.toScheduling((SchedulingRequest) any())).thenReturn(this.scheduling);
        when(this.barberShopService.barberShopById(anyInt())).thenReturn(this.barberShopResponse);
        when(this.employeeService.employeesByBarberShop(anyInt(), any())).thenReturn(List.of(this.employeeSimple));
        when(this.itemService.itemById(anyInt())).thenReturn(this.itemResponse);

        try {
            this.schedulingService.newScheduling(this.schedulingRequest);
        } catch (Exception ex) {
            assertEquals(BadRequestException.class, ex.getClass());
            assertEquals("This item doesn't belong to the informed barbershop.", ex.getMessage());
        }
    }

    @Test
    void whenCreateNewSchedulingWithTimeAfterBarberShopsWorkingHoursThenThrowAnBusinessException() {
        this.scheduling.setSchedulingTime(DATE_19);
        when(this.schedulingMapper.toScheduling((SchedulingRequest) any())).thenReturn(this.scheduling);
        when(this.barberShopService.barberShopById(anyInt())).thenReturn(this.barberShopResponse);
        when(this.employeeService.employeesByBarberShop(anyInt(), any())).thenReturn(List.of(this.employeeSimple));
        when(this.itemService.itemById(anyInt())).thenReturn(this.itemResponse);

        try {
            this.schedulingService.newScheduling(this.schedulingRequest);
        } catch (Exception ex) {
            assertEquals(BusinessException.class, ex.getClass());
            assertEquals("These hours are outside of the barber shop's working hours.", ex.getMessage());
        }
    }

    @Test
    void whenCreateNewSchedulingWithTimeBeforeBarberShopsWorkingHoursThenThrowAnBusinessException() {
        this.scheduling.setSchedulingTime(DATE_07);
        when(this.schedulingMapper.toScheduling((SchedulingRequest) any())).thenReturn(this.scheduling);
        when(this.barberShopService.barberShopById(anyInt())).thenReturn(this.barberShopResponse);
        when(this.employeeService.employeesByBarberShop(anyInt(), any())).thenReturn(List.of(this.employeeSimple));
        when(this.itemService.itemById(anyInt())).thenReturn(this.itemResponse);

        try {
            this.schedulingService.newScheduling(this.schedulingRequest);
        } catch (Exception ex) {
            assertEquals(BusinessException.class, ex.getClass());
            assertEquals("These hours are outside of the barber shop's working hours.", ex.getMessage());
        }
    }

    @Test
    void whenCreateNewSchedulingWithSchedulingAtSameTimeAndStatusScheduledThenThrowAnBusinessException() {
        this.schedulingOld.setStatus(STATUS_SCHEDULED);
        this.schedulingOld.setSchedulingId(ID2);

        when(this.schedulingMapper.toScheduling((SchedulingRequest) any())).thenReturn(this.scheduling);
        when(this.barberShopService.barberShopById(anyInt())).thenReturn(this.barberShopResponse);
        when(this.employeeService.employeesByBarberShop(anyInt(), any())).thenReturn(List.of(this.employeeSimple));
        when(this.itemService.itemById(anyInt())).thenReturn(this.itemResponse);
        when(this.schedulingRepository.findAllByDate(any())).thenReturn(List.of(this.schedulingOld));

        try {
            this.schedulingService.newScheduling(this.schedulingRequest);
        } catch (Exception ex) {
            assertEquals(BusinessException.class, ex.getClass());
            assertEquals("This time isn't available for scheduling.", ex.getMessage());
        }
    }

    @Test
    void whenFindSchedulingByIdThenReturnAnScheduling() {
        when(this.schedulingRepository.existsById(anyInt())).thenReturn(true);
        when(this.schedulingRepository.getById(anyInt())).thenReturn(this.scheduling);
        when(this.schedulingMapper.toSchedulingResponse(any())).thenReturn(this.schedulingResponse);

        SchedulingResponse response = this.schedulingService.schedulingById(ID);

        assertNotNull(response);
        assertEquals(SchedulingResponse.class, response.getClass());
        assertEquals(ID, response.getSchedulingId());
    }

    @Test
    void whenFindSchedulingByIdWithIdNotExistingThenThrowAnNotFoundException() {
        when(this.schedulingRepository.existsById(anyInt())).thenReturn(false);

        try {
            this.schedulingService.schedulingById(ID2);
        } catch (Exception ex) {
            assertEquals(NotFoundException.class, ex.getClass());
            assertEquals("Scheduling with ID '" + ID2 + "' not found.", ex.getMessage());
        }
    }

    @Test
    void whenFindAllSchedulingsThenReturnAnListOfSchedulingsWithAnyStatus() {
        when(this.schedulingRepository.findAll()).thenReturn(List.of(this.scheduling));
        when(this.schedulingMapper.toSchedulingResponseList(anyList())).thenReturn(List.of(this.schedulingResponse));

        List<SchedulingResponse> response = this.schedulingService.allSchedulings(null);

        assertNotNull(response);
        assertEquals(SchedulingResponse.class, response.get(0).getClass());
        assertEquals(1, response.size());
    }

    @Test
    void whenFindAllSchedulingsThenReturnAnListOfSchedulingsWithScheduledStatus() {
        this.schedulingResponse.setStatus(STATUS_SCHEDULED);
        when(this.schedulingRepository.findAll()).thenReturn(List.of(this.scheduling));
        when(this.schedulingMapper.toSchedulingResponseList(anyList())).thenReturn(List.of(this.schedulingResponse));

        List<SchedulingResponse> response = this.schedulingService.allSchedulings(STATUS_SCHEDULED);

        assertNotNull(response);
        assertEquals(SchedulingResponse.class, response.get(0).getClass());
        assertEquals(1, response.size());
        assertEquals(STATUS_SCHEDULED, response.get(0).getStatus());
    }

    @Test
    void whenFindAllSchedulingsThenReturnAnListOfSchedulingsWithFinishedStatus() {
        this.schedulingResponse.setStatus(STATUS_FINISHED);
        when(this.schedulingRepository.findAll()).thenReturn(List.of(this.scheduling));
        when(this.schedulingMapper.toSchedulingResponseList(anyList())).thenReturn(List.of(this.schedulingResponse));

        List<SchedulingResponse> response = this.schedulingService.allSchedulings(STATUS_FINISHED);

        assertNotNull(response);
        assertEquals(SchedulingResponse.class, response.get(0).getClass());
        assertEquals(1, response.size());
        assertEquals(STATUS_FINISHED, response.get(0).getStatus());
    }

    @Test
    void whenFindAllSchedulingsThenReturnAnListOfSchedulingsWithCanceledStatus() {
        this.schedulingResponse.setStatus(STATUS_CANCELLED);
        when(this.schedulingRepository.findAll()).thenReturn(List.of(this.scheduling));
        when(this.schedulingMapper.toSchedulingResponseList(anyList())).thenReturn(List.of(this.schedulingResponse));

        List<SchedulingResponse> response = this.schedulingService.allSchedulings(STATUS_CANCELLED);

        assertNotNull(response);
        assertEquals(SchedulingResponse.class, response.get(0).getClass());
        assertEquals(1, response.size());
        assertEquals(STATUS_CANCELLED, response.get(0).getStatus());
    }

    @Test
    void whenFindAllSchedulingsWithoutSchedulingsThenThrowAnNotFoundException() {
        when(this.schedulingRepository.findAll()).thenReturn(List.of(this.scheduling));
        when(this.schedulingMapper.toSchedulingResponseList(anyList())).thenReturn(List.of());

        try {
            this.schedulingService.allSchedulings(STATUS_CANCELLED);
        } catch (Exception ex) {
            assertEquals(NotFoundException.class, ex.getClass());
            assertEquals("There aren't schedulings to show.", ex.getMessage());
        }
    }

    @Test
    void whenFindAllSchedulingsWithStatusNotExistingThenThrowAnNotFoundException() {
        when(this.schedulingRepository.findAll()).thenReturn(List.of(this.scheduling));
        when(this.schedulingRepository.findSchedulingsByStatus(any())).thenReturn(List.of());
        when(this.schedulingMapper.toSchedulingResponseList(anyList())).thenReturn(List.of(this.schedulingResponse)).thenReturn(List.of());

        try {
            this.schedulingService.allSchedulings(STATUS_SCHEDULED);
        } catch (Exception ex) {
            assertEquals(NotFoundException.class, ex.getClass());
            assertEquals("There aren't schedulings with status '" + STATUS_SCHEDULED + "'.", ex.getMessage());
        }
    }

    @Test
    void whenFindSchedulingsByBarberShopThenReturnAnListOfSchedulings() {
        when(this.schedulingRepository.findSchedulingsByBarberShop(anyInt())).thenReturn(List.of(this.scheduling));
        when(this.schedulingMapper.toSchedulingResponseList(any())).thenReturn(List.of(this.schedulingResponse));

        List<SchedulingResponse> response = this.schedulingService.schedulingsByBarberShop(ID, null);

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(SchedulingResponse.class, response.get(0).getClass());
        assertEquals(ID, response.get(0).getSchedulingId());
    }

    @Test
    void whenFindSchedulingsByBarberShopWithScheduledStatusThenReturnAnListOfSchedulings() {
        this.schedulingResponse.setStatus(STATUS_SCHEDULED);
        when(this.schedulingRepository.findSchedulingsByBarberShop(anyInt())).thenReturn(List.of(this.scheduling));
        when(this.schedulingMapper.toSchedulingResponseList(any())).thenReturn(List.of(this.schedulingResponse));

        List<SchedulingResponse> response = this.schedulingService.schedulingsByBarberShop(ID, STATUS_SCHEDULED);

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(SchedulingResponse.class, response.get(0).getClass());
        assertEquals(ID, response.get(0).getSchedulingId());
        assertEquals(STATUS_SCHEDULED, response.get(0).getStatus());
    }

    @Test
    void whenFindSchedulingsByBarberShopWithFinishedStatusThenReturnAnListOfSchedulings() {
        this.schedulingResponse.setStatus(STATUS_FINISHED);
        when(this.schedulingRepository.findSchedulingsByBarberShop(anyInt())).thenReturn(List.of(this.scheduling));
        when(this.schedulingMapper.toSchedulingResponseList(any())).thenReturn(List.of(this.schedulingResponse));

        List<SchedulingResponse> response = this.schedulingService.schedulingsByBarberShop(ID, STATUS_FINISHED);

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(SchedulingResponse.class, response.get(0).getClass());
        assertEquals(ID, response.get(0).getSchedulingId());
        assertEquals(STATUS_FINISHED, response.get(0).getStatus());
    }

    @Test
    void whenFindSchedulingsByBarberShopWithCancelledStatusThenReturnAnListOfSchedulings() {
        this.schedulingResponse.setStatus(STATUS_CANCELLED);
        when(this.schedulingRepository.findSchedulingsByBarberShop(anyInt())).thenReturn(List.of(this.scheduling));
        when(this.schedulingMapper.toSchedulingResponseList(any())).thenReturn(List.of(this.schedulingResponse));

        List<SchedulingResponse> response = this.schedulingService.schedulingsByBarberShop(ID, STATUS_CANCELLED);

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(SchedulingResponse.class, response.get(0).getClass());
        assertEquals(ID, response.get(0).getSchedulingId());
        assertEquals(STATUS_CANCELLED, response.get(0).getStatus());
    }

    @Test
    void whenFindSchedulingsByBarberShopWithStatusNotExistingThenThrowAnNotFoundException() {
        this.schedulingResponse.setStatus(STATUS_CANCELLED);
        when(this.schedulingRepository.findSchedulingsByBarberShop(anyInt())).thenReturn(List.of(this.scheduling));
        when(this.schedulingMapper.toSchedulingResponseList(any())).thenReturn(List.of(this.schedulingResponse));

        try {
            this.schedulingService.schedulingsByBarberShop(ID, STATUS_SCHEDULED);
        } catch (Exception ex) {
            assertEquals(NotFoundException.class, ex.getClass());
            assertEquals("There aren't schedulings with status '" + STATUS_SCHEDULED + "' for this barber shop.", ex.getMessage());
        }
    }

    @Test
    void whenFindSchedulingsByBarberShopWithoutSchedulingForThisBarberShopThenThrowAnNotFoundException() {
        when(this.schedulingRepository.findSchedulingsByBarberShop(anyInt())).thenReturn(List.of());
        when(this.schedulingMapper.toSchedulingResponseList(any())).thenReturn(List.of());

        try {
            this.schedulingService.schedulingsByBarberShop(ID, STATUS_SCHEDULED);
        } catch (Exception ex) {
            assertEquals(NotFoundException.class, ex.getClass());
            assertEquals("There aren't schedulings for this barber shop.", ex.getMessage());
        }
    }

    @Test
    void whenFindSchedulingsByClientThenReturnAnListOfSchedulings() {
        when(this.schedulingRepository.findSchedulingsByClient(anyInt())).thenReturn(List.of(this.scheduling));
        when(this.schedulingMapper.toSchedulingResponseList(any())).thenReturn(List.of(this.schedulingResponse));

        List<SchedulingResponse> response = this.schedulingService.schedulingsByClient(ID, null);

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(SchedulingResponse.class, response.get(0).getClass());
        assertEquals(ID, response.get(0).getSchedulingId());
    }

    @Test
    void whenFindSchedulingsByClientWithScheduledStatusThenReturnAnListOfSchedulings() {
        this.schedulingResponse.setStatus(STATUS_SCHEDULED);
        when(this.schedulingRepository.findSchedulingsByClient(anyInt())).thenReturn(List.of(this.scheduling));
        when(this.schedulingMapper.toSchedulingResponseList(any())).thenReturn(List.of(this.schedulingResponse));

        List<SchedulingResponse> response = this.schedulingService.schedulingsByClient(ID, STATUS_SCHEDULED);

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(SchedulingResponse.class, response.get(0).getClass());
        assertEquals(ID, response.get(0).getSchedulingId());
        assertEquals(STATUS_SCHEDULED, response.get(0).getStatus());
    }

    @Test
    void whenFindSchedulingsByClientWithFinishedStatusThenReturnAnListOfSchedulings() {
        this.schedulingResponse.setStatus(STATUS_FINISHED);
        when(this.schedulingRepository.findSchedulingsByClient(anyInt())).thenReturn(List.of(this.scheduling));
        when(this.schedulingMapper.toSchedulingResponseList(any())).thenReturn(List.of(this.schedulingResponse));

        List<SchedulingResponse> response = this.schedulingService.schedulingsByClient(ID, STATUS_FINISHED);

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(SchedulingResponse.class, response.get(0).getClass());
        assertEquals(ID, response.get(0).getSchedulingId());
        assertEquals(STATUS_FINISHED, response.get(0).getStatus());
    }

    @Test
    void whenFindSchedulingsByClientWithCancelledStatusThenReturnAnListOfSchedulings() {
        this.schedulingResponse.setStatus(STATUS_CANCELLED);
        when(this.schedulingRepository.findSchedulingsByClient(anyInt())).thenReturn(List.of(this.scheduling));
        when(this.schedulingMapper.toSchedulingResponseList(any())).thenReturn(List.of(this.schedulingResponse));

        List<SchedulingResponse> response = this.schedulingService.schedulingsByClient(ID, STATUS_CANCELLED);

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(SchedulingResponse.class, response.get(0).getClass());
        assertEquals(ID, response.get(0).getSchedulingId());
        assertEquals(STATUS_CANCELLED, response.get(0).getStatus());
    }

    @Test
    void whenFindSchedulingsByClientWithStatusNotExistingThenThrowAnNotFoundException() {
        this.schedulingResponse.setStatus(STATUS_CANCELLED);
        when(this.schedulingRepository.findSchedulingsByClient(anyInt())).thenReturn(List.of(this.scheduling));
        when(this.schedulingMapper.toSchedulingResponseList(any())).thenReturn(List.of(this.schedulingResponse));

        try {
            this.schedulingService.schedulingsByClient(ID, STATUS_SCHEDULED);
        } catch (Exception ex) {
            assertEquals(NotFoundException.class, ex.getClass());
            assertEquals("There aren't schedulings with status '" + STATUS_SCHEDULED + "' for this client.", ex.getMessage());
        }
    }

    @Test
    void whenFindSchedulingsByClientWithoutSchedulingForThisBarberShopThenThrowAnNotFoundException() {
        when(this.schedulingRepository.findSchedulingsByBarberShop(anyInt())).thenReturn(List.of());
        when(this.schedulingMapper.toSchedulingResponseList(any())).thenReturn(List.of());

        try {
            this.schedulingService.schedulingsByClient(ID, STATUS_SCHEDULED);
        } catch (Exception ex) {
            assertEquals(NotFoundException.class, ex.getClass());
            assertEquals("There aren't schedulings for this client.", ex.getMessage());
        }
    }

    @Test
    void whenFindSchedulingsByEmployeeThenReturnAnListOfSchedulings() {
        when(this.schedulingRepository.findSchedulingsByEmployee(anyInt())).thenReturn(List.of(this.scheduling));
        when(this.schedulingMapper.toSchedulingResponseList(any())).thenReturn(List.of(this.schedulingResponse));

        List<SchedulingResponse> response = this.schedulingService.schedulingsByEmployee(ID, null);

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(SchedulingResponse.class, response.get(0).getClass());
        assertEquals(ID, response.get(0).getSchedulingId());
    }

    @Test
    void whenFindSchedulingsByEmployeeWithScheduledStatusThenReturnAnListOfSchedulings() {
        this.schedulingResponse.setStatus(STATUS_SCHEDULED);
        when(this.schedulingRepository.findSchedulingsByEmployee(anyInt())).thenReturn(List.of(this.scheduling));
        when(this.schedulingMapper.toSchedulingResponseList(any())).thenReturn(List.of(this.schedulingResponse));

        List<SchedulingResponse> response = this.schedulingService.schedulingsByEmployee(ID, STATUS_SCHEDULED);

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(SchedulingResponse.class, response.get(0).getClass());
        assertEquals(ID, response.get(0).getSchedulingId());
        assertEquals(STATUS_SCHEDULED, response.get(0).getStatus());
    }

    @Test
    void whenFindSchedulingsByEmployeeWithFinishedStatusThenReturnAnListOfSchedulings() {
        this.schedulingResponse.setStatus(STATUS_FINISHED);
        when(this.schedulingRepository.findSchedulingsByEmployee(anyInt())).thenReturn(List.of(this.scheduling));
        when(this.schedulingMapper.toSchedulingResponseList(any())).thenReturn(List.of(this.schedulingResponse));

        List<SchedulingResponse> response = this.schedulingService.schedulingsByEmployee(ID, STATUS_FINISHED);

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(SchedulingResponse.class, response.get(0).getClass());
        assertEquals(ID, response.get(0).getSchedulingId());
        assertEquals(STATUS_FINISHED, response.get(0).getStatus());
    }

    @Test
    void whenFindSchedulingsByEmployeeWithCancelledStatusThenReturnAnListOfSchedulings() {
        this.schedulingResponse.setStatus(STATUS_CANCELLED);
        when(this.schedulingRepository.findSchedulingsByEmployee(anyInt())).thenReturn(List.of(this.scheduling));
        when(this.schedulingMapper.toSchedulingResponseList(any())).thenReturn(List.of(this.schedulingResponse));

        List<SchedulingResponse> response = this.schedulingService.schedulingsByEmployee(ID, STATUS_CANCELLED);

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(SchedulingResponse.class, response.get(0).getClass());
        assertEquals(ID, response.get(0).getSchedulingId());
        assertEquals(STATUS_CANCELLED, response.get(0).getStatus());
    }

    @Test
    void whenFindSchedulingsByEmployeeWithStatusNotExistingThenThrowAnNotFoundException() {
        this.schedulingResponse.setStatus(STATUS_CANCELLED);
        when(this.schedulingRepository.findSchedulingsByEmployee(anyInt())).thenReturn(List.of(this.scheduling));
        when(this.schedulingMapper.toSchedulingResponseList(any())).thenReturn(List.of(this.schedulingResponse));

        try {
            this.schedulingService.schedulingsByEmployee(ID, STATUS_SCHEDULED);
        } catch (Exception ex) {
            assertEquals(NotFoundException.class, ex.getClass());
            assertEquals("There aren't schedulings with status '" + STATUS_SCHEDULED + "' for this employee.", ex.getMessage());
        }
    }

    @Test
    void whenFindSchedulingsByEmployeeWithoutSchedulingForThisBarberShopThenThrowAnNotFoundException() {
        when(this.schedulingRepository.findSchedulingsByEmployee(anyInt())).thenReturn(List.of());
        when(this.schedulingMapper.toSchedulingResponseList(any())).thenReturn(List.of());

        try {
            this.schedulingService.schedulingsByEmployee(ID, STATUS_SCHEDULED);
        } catch (Exception ex) {
            assertEquals(NotFoundException.class, ex.getClass());
            assertEquals("There aren't schedulings for this employee.", ex.getMessage());
        }
    }

    @Test
    void whenFindSchedulingsByItemThenReturnAnListOfSchedulings() {
        when(this.schedulingRepository.findSchedulingsByItem(anyInt())).thenReturn(List.of(this.scheduling));
        when(this.schedulingMapper.toSchedulingResponseList(any())).thenReturn(List.of(this.schedulingResponse));

        List<SchedulingResponse> response = this.schedulingService.schedulingsByItem(ID, null);

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(SchedulingResponse.class, response.get(0).getClass());
        assertEquals(ID, response.get(0).getSchedulingId());
    }

    @Test
    void whenFindSchedulingsByItemWithScheduledStatusThenReturnAnListOfSchedulings() {
        this.schedulingResponse.setStatus(STATUS_SCHEDULED);
        when(this.schedulingRepository.findSchedulingsByItem(anyInt())).thenReturn(List.of(this.scheduling));
        when(this.schedulingMapper.toSchedulingResponseList(any())).thenReturn(List.of(this.schedulingResponse));

        List<SchedulingResponse> response = this.schedulingService.schedulingsByItem(ID, STATUS_SCHEDULED);

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(SchedulingResponse.class, response.get(0).getClass());
        assertEquals(ID, response.get(0).getSchedulingId());
        assertEquals(STATUS_SCHEDULED, response.get(0).getStatus());
    }

    @Test
    void whenFindSchedulingsByItemWithFinishedStatusThenReturnAnListOfSchedulings() {
        this.schedulingResponse.setStatus(STATUS_FINISHED);
        when(this.schedulingRepository.findSchedulingsByItem(anyInt())).thenReturn(List.of(this.scheduling));
        when(this.schedulingMapper.toSchedulingResponseList(any())).thenReturn(List.of(this.schedulingResponse));

        List<SchedulingResponse> response = this.schedulingService.schedulingsByItem(ID, STATUS_FINISHED);

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(SchedulingResponse.class, response.get(0).getClass());
        assertEquals(ID, response.get(0).getSchedulingId());
        assertEquals(STATUS_FINISHED, response.get(0).getStatus());
    }

    @Test
    void whenFindSchedulingsByItemWithCancelledStatusThenReturnAnListOfSchedulings() {
        this.schedulingResponse.setStatus(STATUS_CANCELLED);
        when(this.schedulingRepository.findSchedulingsByItem(anyInt())).thenReturn(List.of(this.scheduling));
        when(this.schedulingMapper.toSchedulingResponseList(any())).thenReturn(List.of(this.schedulingResponse));

        List<SchedulingResponse> response = this.schedulingService.schedulingsByItem(ID, STATUS_CANCELLED);

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(SchedulingResponse.class, response.get(0).getClass());
        assertEquals(ID, response.get(0).getSchedulingId());
        assertEquals(STATUS_CANCELLED, response.get(0).getStatus());
    }

    @Test
    void whenFindSchedulingsByItemWithStatusNotExistingThenThrowAnNotFoundException() {
        this.schedulingResponse.setStatus(STATUS_CANCELLED);
        when(this.schedulingRepository.findSchedulingsByItem(anyInt())).thenReturn(List.of(this.scheduling));
        when(this.schedulingMapper.toSchedulingResponseList(any())).thenReturn(List.of(this.schedulingResponse));

        try {
            this.schedulingService.schedulingsByItem(ID, STATUS_SCHEDULED);
        } catch (Exception ex) {
            assertEquals(NotFoundException.class, ex.getClass());
            assertEquals("There aren't schedulings with status '" + STATUS_SCHEDULED + "' for this item.", ex.getMessage());
        }
    }

    @Test
    void whenFindSchedulingsByItemWithoutSchedulingForThisBarberShopThenThrowAnNotFoundException() {
        when(this.schedulingRepository.findSchedulingsByItem(anyInt())).thenReturn(List.of());
        when(this.schedulingMapper.toSchedulingResponseList(any())).thenReturn(List.of());

        try {
            this.schedulingService.schedulingsByItem(ID, STATUS_SCHEDULED);
        } catch (Exception ex) {
            assertEquals(NotFoundException.class, ex.getClass());
            assertEquals("There aren't schedulings for this item.", ex.getMessage());
        }
    }

    @Test
    void whenToCancelSchedulingThenReturnSucess() {
        this.scheduling.setStatus(STATUS_SCHEDULED);
        when(this.schedulingRepository.existsById(anyInt())).thenReturn(true);
        when(this.schedulingRepository.getById(anyInt())).thenReturn(this.scheduling);

        this.schedulingService.cancelScheduling(ID);

        assertEquals(STATUS_CANCELLED, this.scheduling.getStatus());
        verify(this.schedulingRepository, times(1)).save(any());
    }

    @Test
    void whenToCancelSchedulingWithIdNotExistingThenThrowAnNotFoundException() {
        when(this.schedulingRepository.existsById(anyInt())).thenReturn(false);

        try {
            this.schedulingService.cancelScheduling(ID2);
        } catch (Exception ex) {
            assertEquals(NotFoundException.class, ex.getClass());
            assertEquals("Scheduling with ID '" + ID2 + "' not found.", ex.getMessage());
        }
    }

    @Test
    void whenToCancelSchedulingWithStatusDifferentThanScheduledThenThrowAnBadRequestException() {
        this.scheduling.setStatus(STATUS_CANCELLED);
        when(this.schedulingRepository.existsById(anyInt())).thenReturn(true);
        when(this.schedulingRepository.getById(anyInt())).thenReturn(this.scheduling);

        try {
            this.schedulingService.cancelScheduling(ID2);
        } catch (Exception ex) {
            assertEquals(BadRequestException.class, ex.getClass());
            assertEquals("Unable to cancel scheduling with status '" + scheduling.getStatus() + "'.", ex.getMessage());
        }
    }

    @Test
    void whenToFinishSchedulingThenReturnSucess() {
        this.scheduling.setStatus(STATUS_SCHEDULED);
        when(this.schedulingRepository.existsById(anyInt())).thenReturn(true);
        when(this.schedulingRepository.getById(anyInt())).thenReturn(this.scheduling);

        this.schedulingService.finishScheduling(ID);

        assertEquals(STATUS_FINISHED, this.scheduling.getStatus());
        verify(this.schedulingRepository, times(1)).save(any());
    }

    @Test
    void whenToFinishSchedulingWithIdNotExistingThenThrowAnNotFoundException() {
        when(this.schedulingRepository.existsById(anyInt())).thenReturn(false);

        try {
            this.schedulingService.finishScheduling(ID2);
        } catch (Exception ex) {
            assertEquals(NotFoundException.class, ex.getClass());
            assertEquals("Scheduling with ID '" + ID2 + "' not found.", ex.getMessage());
        }
    }

    @Test
    void whenToFinishSchedulingWithStatusDifferentThanScheduledThenThrowAnBadRequestException() {
        this.scheduling.setStatus(STATUS_CANCELLED);
        when(this.schedulingRepository.existsById(anyInt())).thenReturn(true);
        when(this.schedulingRepository.getById(anyInt())).thenReturn(this.scheduling);

        try {
            this.schedulingService.finishScheduling(ID2);
        } catch (Exception ex) {
            assertEquals(BadRequestException.class, ex.getClass());
            assertEquals("Unable to complete scheduling with status '" + scheduling.getStatus() + "'.", ex.getMessage());
        }
    }

    @Test
    void whenUpdateSchedulingWithFieldsFilledThenReturnSuccess() {
        List<Item> items = new ArrayList<>();
        items.add(this.item);

        Scheduling schedulingUpdt = new Scheduling();
        Item itemUpdt = new Item();

        this.scheduling.setStatus(STATUS_SCHEDULED);
        this.scheduling.setItems(items);
        this.schedulingOld.setStatus(STATUS_SCHEDULED);

        itemUpdt.setItemId(ID2);
        this.itemResponse.setItemId(ID2);
        this.employee.setEmployeeId(ID2);
        this.employeeSimple.setEmployeeId(ID2);

        schedulingUpdt.setSchedulingTime(DATE_12);
        schedulingUpdt.setItems(List.of(itemUpdt));
        schedulingUpdt.setEmployee(this.employee);

        when(this.schedulingRepository.existsById(anyInt())).thenReturn(true);
        when(this.schedulingMapper.toScheduling((SchedulingRequest) any())).thenReturn(schedulingUpdt);
        when(this.schedulingRepository.getById(anyInt())).thenReturn(this.scheduling);
        when(this.employeeService.employeesByBarberShop(anyInt(), any())).thenReturn(List.of(this.employeeSimple));
        when(this.itemService.itemById(anyInt())).thenReturn(this.itemResponse);
        when(this.schedulingRepository.findAllByDate(any())).thenReturn(List.of(this.schedulingOld));
        when(this.schedulingMapper.toSchedulingResponse(any())).thenReturn(this.schedulingResponse);

        this.schedulingService.updateScheduling(ID, this.schedulingRequest);

        assertEquals(2, this.scheduling.getItems().size());
        assertEquals(DATE_12, this.scheduling.getSchedulingTime());
        assertEquals(ID2, this.scheduling.getEmployee().getEmployeeId());

        verify(this.schedulingRepository, times(1)).save(any());
    }

    @Test
    void whenUpdateSchedulingWithOnlyItemFieldFilledThenReturnSuccess() {
        List<Item> items = new ArrayList<>();
        items.add(this.item);

        Scheduling schedulingUpdt = new Scheduling();
        Item itemUpdt = new Item();

        this.scheduling.setStatus(STATUS_SCHEDULED);
        this.scheduling.setItems(items);
        this.schedulingOld.setStatus(STATUS_SCHEDULED);

        itemUpdt.setItemId(ID2);
        this.itemResponse.setItemId(ID2);

        schedulingUpdt.setItems(List.of(itemUpdt));

        when(this.schedulingRepository.existsById(anyInt())).thenReturn(true);
        when(this.schedulingMapper.toScheduling((SchedulingRequest) any())).thenReturn(schedulingUpdt);
        when(this.schedulingRepository.getById(anyInt())).thenReturn(this.scheduling);
        when(this.employeeService.employeesByBarberShop(anyInt(), any())).thenReturn(List.of(this.employeeSimple));
        when(this.itemService.itemById(anyInt())).thenReturn(this.itemResponse);
        when(this.schedulingRepository.findAllByDate(any())).thenReturn(List.of(this.schedulingOld));
        when(this.schedulingMapper.toSchedulingResponse(any())).thenReturn(this.schedulingResponse);

        this.schedulingService.updateScheduling(ID, this.schedulingRequest);

        assertEquals(2, this.scheduling.getItems().size());

        verify(this.schedulingRepository, times(1)).save(any());
    }


    @Test
    void whenUpdateSchedulingWithOnlyEmployeeFieldFilledThenReturnSuccess() {
        Scheduling schedulingUpdt = new Scheduling();

        this.scheduling.setStatus(STATUS_SCHEDULED);
        this.schedulingOld.setStatus(STATUS_SCHEDULED);
        this.employee.setEmployeeId(ID2);
        this.employeeSimple.setEmployeeId(ID2);

        schedulingUpdt.setEmployee(this.employee);

        when(this.schedulingRepository.existsById(anyInt())).thenReturn(true);
        when(this.schedulingMapper.toScheduling((SchedulingRequest) any())).thenReturn(schedulingUpdt);
        when(this.schedulingRepository.getById(anyInt())).thenReturn(this.scheduling);
        when(this.employeeService.employeesByBarberShop(anyInt(), any())).thenReturn(List.of(this.employeeSimple));
        when(this.itemService.itemById(anyInt())).thenReturn(this.itemResponse);
        when(this.schedulingRepository.findAllByDate(any())).thenReturn(List.of(this.schedulingOld));
        when(this.schedulingMapper.toSchedulingResponse(any())).thenReturn(this.schedulingResponse);

        this.schedulingService.updateScheduling(ID, this.schedulingRequest);

        assertEquals(ID2, this.scheduling.getEmployee().getEmployeeId());

        verify(this.schedulingRepository, times(1)).save(any());
    }

    @Test
    void whenUpdateSchedulingWithOnlySchedulingTimeFieldFilledThenReturnSuccess() {
        Scheduling schedulingUpdt = new Scheduling();

        this.scheduling.setStatus(STATUS_SCHEDULED);
        this.schedulingOld.setStatus(STATUS_SCHEDULED);

        schedulingUpdt.setSchedulingTime(DATE_12);

        when(this.schedulingRepository.existsById(anyInt())).thenReturn(true);
        when(this.schedulingMapper.toScheduling((SchedulingRequest) any())).thenReturn(schedulingUpdt);
        when(this.schedulingRepository.getById(anyInt())).thenReturn(this.scheduling);
        when(this.employeeService.employeesByBarberShop(anyInt(), any())).thenReturn(List.of(this.employeeSimple));
        when(this.itemService.itemById(anyInt())).thenReturn(this.itemResponse);
        when(this.schedulingRepository.findAllByDate(any())).thenReturn(List.of(this.schedulingOld));
        when(this.schedulingMapper.toSchedulingResponse(any())).thenReturn(this.schedulingResponse);

        this.schedulingService.updateScheduling(ID, this.schedulingRequest);

        assertEquals(DATE_12, this.scheduling.getSchedulingTime());

        verify(this.schedulingRepository, times(1)).save(any());
    }

    @Test
    void whenUpdateSchedulingInformingABarberShopThenThrowAnBadRequestException() {
        Scheduling schedulingUpdt = new Scheduling();
        BarberShop barberShopUpdt = new BarberShop();
        barberShopUpdt.setBarberShopId(ID2);
        schedulingUpdt.setBarberShop(barberShopUpdt);

        this.scheduling.setStatus(STATUS_SCHEDULED);

        when(this.schedulingRepository.existsById(anyInt())).thenReturn(true);
        when(this.schedulingMapper.toScheduling((SchedulingRequest) any())).thenReturn(schedulingUpdt);
        when(this.schedulingRepository.getById(anyInt())).thenReturn(this.scheduling);

        try {
            this.schedulingService.updateScheduling(ID, this.schedulingRequest);
        } catch (Exception ex) {
            assertEquals(BadRequestException.class, ex.getClass());
            assertEquals("Isn't possible to change the barbershop for this scheduling.", ex.getMessage());
        }
    }

    @Test
    void whenUpdateSchedulingInformingAClientThenThrowAnBadRequestException() {
        Scheduling schedulingUpdt = new Scheduling();
        Client clientUpdt = new Client();
        clientUpdt.setClientId(ID2);
        schedulingUpdt.setClient(clientUpdt);

        this.scheduling.setStatus(STATUS_SCHEDULED);

        when(this.schedulingRepository.existsById(anyInt())).thenReturn(true);
        when(this.schedulingMapper.toScheduling((SchedulingRequest) any())).thenReturn(schedulingUpdt);
        when(this.schedulingRepository.getById(anyInt())).thenReturn(this.scheduling);

        try{
            this.schedulingService.updateScheduling(ID, this.schedulingRequest);
        } catch (Exception ex) {
            assertEquals(BadRequestException.class, ex.getClass());
            assertEquals("Isn't possible to change the client for this scheduling.", ex.getMessage());
        }
    }

    @Test
    void whenUpdateSchedulingInformingASchedulingWithStatusDifferentThanScheduledThenThrowAnBadRequestException() {
        this.scheduling.setStatus(STATUS_CANCELLED);

        when(this.schedulingRepository.existsById(anyInt())).thenReturn(true);
        when(this.schedulingMapper.toScheduling((SchedulingRequest) any())).thenReturn(this.scheduling);
        when(this.schedulingRepository.getById(anyInt())).thenReturn(this.scheduling);

        try{
            this.schedulingService.updateScheduling(ID, this.schedulingRequest);
        } catch (Exception ex) {
            assertEquals(BadRequestException.class, ex.getClass());
            assertEquals("This schedule is not available for changes.", ex.getMessage());
        }
    }

    @Test
    void whenUpdateSchedulingInformingASchedulingNotExistingThenThrowAnNotFoundException() {
        when(this.schedulingRepository.existsById(anyInt())).thenReturn(false);

        try{
            this.schedulingService.updateScheduling(ID2, this.schedulingRequest);
        } catch (Exception ex) {
            assertEquals(NotFoundException.class, ex.getClass());
            assertEquals("Scheduling with ID '" + ID2 + "' not found.", ex.getMessage());
        }
    }

    private void startUser() {

        this.client = new Client(ID, NAME, CPF, PHONE, STATUS_ACTIVE, List.of(this.barberShop), new ArrayList<>());
        this.clientSimple = new ClientSimple(ID, NAME, PHONE, STATUS_ACTIVE);
        this.clientRequest = new ClientRequest(ID, NAME, CPF, PHONE, STATUS_ACTIVE, List.of(this.barberShopRequest), new ArrayList<>());

        this.barberShop = new BarberShop(ID, NAME_BARBER, ZIP_CODE, ADRESS, MAIL, NUMBER, OPENING, CLOSING, STATUS_ACTIVE, List.of(this.item), List.of(this.employee), List.of(this.client), new ArrayList<>());
        this.barberShopSimple = new BarberShopSimple(ID, NAME_BARBER, ADRESS, NUMBER, OPENING, CLOSING, STATUS_ACTIVE);
        this.barberShopRequest = new BarberShopRequest(ID, NAME_BARBER, ZIP_CODE, ADRESS, MAIL, NUMBER, OPENING, CLOSING, STATUS_ACTIVE, List.of(this.itemRequest), List.of(this.employeeRequest), List.of(this.clientRequest));
        this.barberShopResponse = new BarberShopResponse(ID, NAME_BARBER, ZIP_CODE, ADRESS, MAIL, NUMBER, OPENING, CLOSING, STATUS_ACTIVE, List.of(this.itemSimple), List.of(this.employeeSimple), List.of(this.clientSimple), new ArrayList<>());

        this.employee = new Employee(ID, NAME, CPF, MAIL, PHONE, STATUS_ACTIVE, this.barberShop, new ArrayList<>());
        this.employeeSimple = new EmployeeSimple(ID, NAME, PHONE, STATUS_ACTIVE);
        this.employeeRequest = new EmployeeRequest(ID, NAME, CPF, MAIL, PHONE, STATUS_ACTIVE, this.barberShopRequest);
        this.employeeResponse = new EmployeeResponse(ID, NAME, CPF, MAIL, PHONE, STATUS_ACTIVE, this.barberShopSimple, new ArrayList<>());

        this.item = new Item(ID, NAME, PRICE, TIME, STATUS_ACTIVE, this.barberShop, new ArrayList<>());
        this.itemSimple = new ItemSimple(ID, NAME, PRICE, TIME, STATUS_ACTIVE);
        this.itemRequest = new ItemRequest(ID, NAME, PRICE, TIME, STATUS_ACTIVE, this.barberShopRequest);
        this.itemResponse = new ItemResponse(ID, NAME, PRICE, TIME, STATUS_ACTIVE, this.barberShopSimple, new ArrayList<>());

        this.scheduling = new Scheduling(ID, this.client, this.barberShop, this.employee, List.of(this.item), DATE_10, null);
        this.schedulingRequest = new SchedulingRequest(ID, this.clientRequest, this.barberShopRequest, this.employeeRequest, List.of(this.itemRequest), DATE_10, null);
        this.schedulingResponse = new SchedulingResponse(ID, this.clientSimple, this.barberShopSimple, this.employeeSimple, List.of(this.itemSimple), DATE_10, null);
        this.schedulingOld = new Scheduling(ID, this.client, this.barberShop, this.employee, List.of(this.item), DATE_10, null);
        ;
    }

}