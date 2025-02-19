package br.com.barbermanager.barbershopmanagement.domain.service.scheduling;

import br.com.barbermanager.barbershopmanagement.api.controller.BarberShopController;
import br.com.barbermanager.barbershopmanagement.api.mapper.EmployeeMapper;
import br.com.barbermanager.barbershopmanagement.api.mapper.SchedulingMapper;
import br.com.barbermanager.barbershopmanagement.api.request.barbershop.BarberShopRequest;
import br.com.barbermanager.barbershopmanagement.api.request.client.ClientRequest;
import br.com.barbermanager.barbershopmanagement.api.request.scheduling.SchedulingRequest;
import br.com.barbermanager.barbershopmanagement.api.response.barbershop.BarberShopResponse;
import br.com.barbermanager.barbershopmanagement.api.response.employee.EmployeeResponse;
import br.com.barbermanager.barbershopmanagement.api.response.employee.EmployeeSimple;
import br.com.barbermanager.barbershopmanagement.api.response.item.ItemResponse;
import br.com.barbermanager.barbershopmanagement.api.response.scheduling.SchedulingResponse;
import br.com.barbermanager.barbershopmanagement.domain.model.*;
import br.com.barbermanager.barbershopmanagement.domain.repository.SchedulingRepository;
import br.com.barbermanager.barbershopmanagement.domain.service.barbershop.BarberShopService;
import br.com.barbermanager.barbershopmanagement.domain.service.client.ClientService;
import br.com.barbermanager.barbershopmanagement.domain.service.employee.EmployeeService;
import br.com.barbermanager.barbershopmanagement.domain.service.item.ItemService;
import br.com.barbermanager.barbershopmanagement.exception.*;
import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToFile;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SchedulingServiceImpl implements SchedulingService {

    @Autowired
    private SchedulingRepository schedulingRepository;

    @Autowired
    private SchedulingMapper schedulingMapper;

    @Autowired
    private ItemService itemService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private BarberShopService barberShopService;

    @Autowired
    private ClientService clientService;

    @Override
    public SchedulingResponse newScheduling(SchedulingRequest newScheduling) {
        Scheduling scheduling = this.schedulingMapper.toScheduling(newScheduling);
        this.isSchedulingAvailable(scheduling);
        scheduling.setStatus(StatusEnum.SCHEDULED);
        return this.schedulingMapper.toSchedulingResponse((schedulingRepository.save(scheduling)));
    }

    private void isSchedulingAvailable(Scheduling scheduling) {
        if (scheduling.getBarberShop() != null) {
            BarberShopResponse barberShop = this.barberShopService.barberShopById(scheduling.getBarberShop().getBarberShopId());
            scheduling.getBarberShop().setOpeningTime(barberShop.getOpeningTime());
            scheduling.getBarberShop().setClosingTime(barberShop.getClosingTime());
            if (barberShop.getStatus().equals(StatusEnum.INACTIVE)) {
                throw new InactiveException("The barber shop informated is inactive.");
            }
            Optional.ofNullable(scheduling.getEmployee()).ifPresent(sch -> {
                this.checkEmployee(scheduling.getEmployee().getEmployeeId(), scheduling.getBarberShop().getBarberShopId());
            });
            Optional.ofNullable(scheduling.getItems()).ifPresent(sch -> {
                this.checkItem(scheduling.getItems(), scheduling.getBarberShop().getBarberShopId());
            });
        }
        Optional.ofNullable(scheduling.getSchedulingTime()).ifPresent(time -> {
            this.checkSchedulingTime(scheduling);
        });
    }

    @Override
    public SchedulingResponse schedulingById(Integer schedulingId) {
        if (this.schedulingRepository.existsById(schedulingId)) {
            return this.schedulingMapper.toSchedulingResponse(this.schedulingRepository.getById(schedulingId));
        }
        throw new NotFoundException("Scheduling with ID '" + schedulingId + "' not found.");
    }

    @Override
    public List<SchedulingResponse> allSchedulings(StatusEnum status) {
        List<SchedulingResponse> schedulings = this.schedulingMapper.toSchedulingResponseList((this.schedulingRepository.findAll()));
        if (schedulings.isEmpty()) {
            throw new NotFoundException("There aren't schedulings to show.");
        }
        if (status != null) {
            this.schedulingRepository.findSchedulingsByStatus(status);
            System.out.println("TESTE");
            List<SchedulingResponse> schedulingsByStatus = this.schedulingMapper.toSchedulingResponseList((this.schedulingRepository.findSchedulingsByStatus(status)));
            if (schedulingsByStatus.isEmpty()) {
                throw new NotFoundException("There aren't schedulings with status '" + status + "'.");
            }
        }
        return schedulings;
    }

    @Override
    public List<SchedulingResponse> schedulingsByBarberShop(Integer barberShopId, StatusEnum status) {
        List<SchedulingResponse> schedulings = this.schedulingMapper.toSchedulingResponseList(this.schedulingRepository.findSchedulingsByBarberShop(barberShopId));
        if (schedulings.isEmpty()) {
            throw new NotFoundException("There aren't schedulings for this barber shop.");
        }
        if (status != null) {
            List<SchedulingResponse> schedulingByStatus = schedulings.stream().filter(scheduling -> scheduling.getStatus().equals(status)).collect(Collectors.toList());
            if (schedulingByStatus.isEmpty()) {
                throw new NotFoundException("There aren't schedulings with status '" + status + "' for this barber shop.");
            }
            return schedulingByStatus;
        }
        return schedulings;
    }

    @Override
    public List<SchedulingResponse> schedulingsByClient(Integer clientId, StatusEnum status) {
        List<SchedulingResponse> schedulings = this.schedulingMapper.toSchedulingResponseList(this.schedulingRepository.findSchedulingsByClient(clientId));
        if (schedulings.isEmpty()) {
            throw new NotFoundException("There aren't schedulings for this client.");
        }
        if (status != null) {
            List<SchedulingResponse> schedulingByStatus = schedulings.stream().filter(scheduling -> scheduling.getStatus().equals(status)).collect(Collectors.toList());
            if (schedulingByStatus.isEmpty()) {
                throw new NotFoundException("There aren't schedulings with status '" + status + "' for this client.");
            }
            return schedulingByStatus;
        }
        return schedulings;
    }

    @Override
    public List<SchedulingResponse> schedulingsByEmployee(Integer employeeId, StatusEnum status) {
        List<SchedulingResponse> schedulings = this.schedulingMapper.toSchedulingResponseList(this.schedulingRepository.findSchedulingsByEmployee(employeeId));
        if (schedulings.isEmpty()) {
            throw new NotFoundException("There aren't schedulings for this employee.");
        }
        if (status != null) {
            List<SchedulingResponse> schedulingByStatus = schedulings.stream().filter(scheduling -> scheduling.getStatus().equals(status)).collect(Collectors.toList());
            if (schedulingByStatus.isEmpty()) {
                throw new NotFoundException("There aren't schedulings with status '" + status + "' for this employee.");
            }
            return schedulingByStatus;
        }
        return schedulings;
    }

    @Override
    public List<SchedulingResponse> schedulingsByItem(Integer itemId, StatusEnum status) {
        List<SchedulingResponse> schedulings = this.schedulingMapper.toSchedulingResponseList(this.schedulingRepository.findSchedulingsByItem(itemId));
        if (schedulings.isEmpty()) {
            throw new NotFoundException("There aren't schedulings for this item.");
        }
        if (status != null) {
            List<SchedulingResponse> schedulingByStatus = schedulings.stream().filter(scheduling -> scheduling.getStatus().equals(status)).collect(Collectors.toList());
            if (schedulingByStatus.isEmpty()) {
                throw new NotFoundException("There aren't schedulings with status '" + status + "' for this item.");
            }
            return schedulingByStatus;
        }
        return schedulings;
    }

    @Override
    public void cancelScheduling(Integer schedulingId) {
        if (this.schedulingRepository.existsById(schedulingId)) {
            Scheduling scheduling = this.schedulingRepository.getById(schedulingId);
            if (scheduling.getStatus().equals(StatusEnum.SCHEDULED)) {
                scheduling.setStatus(StatusEnum.CANCELLED);
                this.schedulingRepository.save(scheduling);
                return;
            }
            throw new BadRequestException("Unable to cancel scheduling with status '" + scheduling.getStatus() + "'.");
        }
        throw new NotFoundException("Scheduling with ID '" + schedulingId + "' not found.");
    }

    @Override
    public void finishScheduling(Integer schedulingId) {
        if (this.schedulingRepository.existsById(schedulingId)) {
            Scheduling scheduling = this.schedulingRepository.getById(schedulingId);
            if (scheduling.getStatus().equals(StatusEnum.SCHEDULED)) {
                scheduling.setStatus(StatusEnum.FINISHED);
                this.schedulingRepository.save(scheduling);
                return;
            }
            throw new BadRequestException("Unable to complete scheduling with status '" + scheduling.getStatus() + "'.");
        }
        throw new NotFoundException("Scheduling with ID '" + schedulingId + "' not found.");
    }

    @Override
    public SchedulingResponse updateScheduling(Integer schedulingId, SchedulingRequest schedulingUpdated) {
        if (this.schedulingRepository.existsById(schedulingId)) {
            Scheduling schedulingUpdt = this.schedulingMapper.toScheduling(schedulingUpdated);
            Scheduling scheduling = this.schedulingRepository.getById(schedulingId);
            if (scheduling.getStatus().equals(StatusEnum.SCHEDULED)) {
                if (schedulingUpdt.getBarberShop() != null && schedulingUpdt.getBarberShop().getBarberShopId() != scheduling.getBarberShop().getBarberShopId()) {
                    throw new BadRequestException("Isn't possible to change the barbershop for this scheduling.");
                }
                if (schedulingUpdt.getClient() != null && schedulingUpdt.getClient().getClientId() != scheduling.getClient().getClientId()) {
                    throw new BadRequestException("Isn't possible to change the client for this scheduling.");
                }

                Optional.ofNullable(schedulingUpdt.getEmployee()).ifPresent(sch -> {
                    this.checkEmployee(schedulingUpdt.getEmployee().getEmployeeId(), scheduling.getBarberShop().getBarberShopId());
                });

                Optional.ofNullable(schedulingUpdt.getItems()).ifPresent(items -> {
                    List<Item> itemsUpdt = scheduling.getItems();
                    for (Item item : items) {
                        if (!(itemsUpdt.stream().anyMatch(itemUpdt -> itemUpdt.getItemId().equals(item.getItemId())))) {
                            this.checkItem(item, scheduling.getBarberShop().getBarberShopId());
                            itemsUpdt.add(item);
                        }
                    }
                    scheduling.setItems(itemsUpdt);
                });
                schedulingUpdt.setItems(scheduling.getItems());
                schedulingUpdt.setEmployee(scheduling.getEmployee());

                Optional.ofNullable(schedulingUpdt.getSchedulingTime()).ifPresent(time -> {
                    schedulingUpdt.setBarberShop(scheduling.getBarberShop());
                    this.checkSchedulingTime(schedulingUpdt);
                });

//                this.schedulingRepository.save(scheduling);
                schedulingUpdt.setItems(null);
                BeanUtils.copyProperties(schedulingUpdt, scheduling, this.searchEmptyFields(schedulingUpdt));
                return this.schedulingMapper.toSchedulingResponse((this.schedulingRepository.save(scheduling)));
            }
            throw new BadRequestException("This schedule is not available for changes.");
        }
        throw new NotFoundException("Scheduling with ID '" + schedulingId + "' not found.");
    }

    private void checkItem(Item item, Integer barberShopId) {
        ItemResponse itemById = this.itemService.itemById(item.getItemId());
        if (itemById.getStatus() == StatusEnum.INACTIVE) {
            throw new InactiveException("This item isn't ACTIVE.");
        }
        if (itemById.getBarberShop().getBarberShopId() != barberShopId) {
            throw new BadRequestException("This item doesn't belong to the informed barbershop.");
        }
    }

    private void checkItem(List<Item> items, Integer barberShopId) {
        for (Item item : items) {
            this.checkItem(item, barberShopId);
        }
    }

    private void checkEmployee(Integer employeeId, Integer barberShopId) {
        for (EmployeeSimple employee : this.employeeService.employeesByBarberShop(barberShopId, null)) {
            if (employee.getEmployeeId().equals(employeeId)) {
                if (employee.getStatus().equals(StatusEnum.INACTIVE)) {
                    throw new InactiveException("This employee isn't ACTIVE.");
                }
                return;
            }
        }
        throw new BadRequestException("This employee doesn't belong to the informed barbershop.");
    }

    private void checkSchedulingTime(Scheduling scheduling) {
        LocalDate date = scheduling.getSchedulingTime().toLocalDate();

        List<ItemResponse> newItems = scheduling.getItems().stream().map(item -> this.itemService.itemById(item.getItemId())).collect(Collectors.toList());
        LocalDateTime newStart = scheduling.getSchedulingTime();
        LocalDateTime newEnd = scheduling.getSchedulingTime().plusMinutes(newItems.stream().mapToInt(ItemResponse::getTime).sum());

        LocalDateTime openingTime = LocalDateTime.of(newStart.toLocalDate(), scheduling.getBarberShop().getOpeningTime());
        LocalDateTime closingTime = LocalDateTime.of(newStart.toLocalDate(), scheduling.getBarberShop().getClosingTime());

        if (newStart.isBefore(openingTime) || newEnd.isAfter(closingTime) || newStart.isAfter(newEnd)) {
            throw new BusinessException("These hours are outside of the barber shop's working hours.");
        }

        for (Scheduling schedulingOld : this.schedulingRepository.findAllByDate(date)) {
            List<ItemResponse> existingItems = schedulingOld.getItems().stream().map(item -> this.itemService.itemById(item.getItemId())).collect(Collectors.toList());

            LocalDateTime existingStart = schedulingOld.getSchedulingTime();
            LocalDateTime existingEnd = schedulingOld.getSchedulingTime().plusMinutes(existingItems.stream().mapToInt(ItemResponse::getTime).sum());

            if (((schedulingOld.getSchedulingId() != scheduling.getSchedulingId())
                    && (schedulingOld.getStatus() == StatusEnum.SCHEDULED)
                    && (scheduling.getEmployee().getEmployeeId().equals(schedulingOld.getEmployee().getEmployeeId()))
                    && (newStart.isBefore(existingEnd))
                    && (newEnd.isAfter(existingStart))
                    && !(newStart.equals(existingEnd)))) {
                throw new BusinessException("This time isn't available for scheduling.");
            }
        }

    }

    private String[] searchEmptyFields(Object source) {
        BeanWrapper src = new BeanWrapperImpl(source);
        Set<String> emptyFields = new HashSet<>();
        for (PropertyDescriptor pd : src.getPropertyDescriptors()) {
            if (src.getPropertyValue(pd.getName()) == null) {
                emptyFields.add(pd.getName());
            }
        }
        String[] result = new String[emptyFields.size()];
        return emptyFields.toArray(result);
    }

}
