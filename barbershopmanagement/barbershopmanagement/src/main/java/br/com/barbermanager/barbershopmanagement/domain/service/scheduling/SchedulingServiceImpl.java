package br.com.barbermanager.barbershopmanagement.domain.service.scheduling;

import br.com.barbermanager.barbershopmanagement.api.mapper.SchedulingMapper;
import br.com.barbermanager.barbershopmanagement.api.request.item.ItemRequest;
import br.com.barbermanager.barbershopmanagement.api.request.scheduling.SchedulingRequest;
import br.com.barbermanager.barbershopmanagement.api.response.barbershop.BarberShopSimple;
import br.com.barbermanager.barbershopmanagement.api.response.item.ItemResponse;
import br.com.barbermanager.barbershopmanagement.api.response.scheduling.SchedulingResponse;
import br.com.barbermanager.barbershopmanagement.domain.model.Item;
import br.com.barbermanager.barbershopmanagement.domain.model.Scheduling;
import br.com.barbermanager.barbershopmanagement.domain.model.StatusEnum;
import br.com.barbermanager.barbershopmanagement.domain.repository.SchedulingRepository;
import br.com.barbermanager.barbershopmanagement.domain.service.item.ItemService;
import br.com.barbermanager.barbershopmanagement.exception.AlreadyExistsException;
import br.com.barbermanager.barbershopmanagement.exception.BadRequestException;
import br.com.barbermanager.barbershopmanagement.exception.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.beans.PropertyDescriptor;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SchedulingServiceImpl implements SchedulingService {

    @Autowired
    private SchedulingRepository schedulingRepository;

    @Autowired
    private SchedulingMapper schedulingMapper;

    @Autowired
    private ItemService itemService;

//    private Boolean schedulingExists(Integer schedulingId) {
//        return this.schedulingRepository.existsById(schedulingId);
//    }

    @Override
    public SchedulingResponse newScheduling(SchedulingRequest newScheduling) {
        Scheduling scheduling = this.schedulingMapper.toScheduling(newScheduling);
        if (isSchedulingAvailable(scheduling)) {
            scheduling.setStatus(StatusEnum.SCHEDULED);
            return this.schedulingMapper.toSchedulingResponse((schedulingRepository.save(scheduling)));
        } else {
            throw new AlreadyExistsException("This time isn't available for scheduling");
        }
    }


    private Boolean isSchedulingAvailable(Scheduling scheduling) {

        LocalDate date = scheduling.getSchedulingTime().toLocalDate();

        List<ItemResponse> newItems = scheduling.getItems().stream().map(item -> this.itemService.itemById(item.getItemId())).collect(Collectors.toList());
        LocalDateTime newStart = scheduling.getSchedulingTime();
        LocalDateTime newEnd = scheduling.getSchedulingTime().plusMinutes(newItems.stream().mapToInt(ItemResponse::getTime).sum());

        for (Scheduling schedulingOld : this.schedulingRepository.findAllByDate(date)) {
            List<ItemResponse> existingItems = schedulingOld.getItems().stream().map(item -> this.itemService.itemById(item.getItemId())).collect(Collectors.toList());

            LocalDateTime existingStart = schedulingOld.getSchedulingTime();
            LocalDateTime existingEnd = schedulingOld.getSchedulingTime().plusMinutes(existingItems.stream().mapToInt(ItemResponse::getTime).sum());

            if (((schedulingOld.getStatus() == StatusEnum.SCHEDULED) && (scheduling.getEmployee().getEmployeeId().equals(schedulingOld.getEmployee().getEmployeeId())) && (newStart.isBefore(existingEnd)) && (newEnd.isAfter(existingStart)) && !(newStart.equals(existingEnd)))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public SchedulingResponse schedulingById(Integer schedulingId) {
        if (this.schedulingRepository.existsById(schedulingId)) {
            return this.schedulingMapper.toSchedulingResponse(this.schedulingRepository.getById(schedulingId));
        }
        throw new NotFoundException("Scheduling with ID '" + schedulingId + "' not found.");
    }

    @Override
    public List<SchedulingResponse> allSchedulings() {
        List<SchedulingResponse> schedulingResponses = this.schedulingMapper.toSchedulingResponseList((this.schedulingRepository.findAll()));
        if (schedulingResponses.isEmpty()) {
            throw new NotFoundException("There aren't schedulings to show.");
        }
        return schedulingResponses;
    }

    @Override
    public List<SchedulingResponse> allSchedulingsByStatus(StatusEnum status) {
        List<SchedulingResponse> schedulings = this.schedulingMapper.toSchedulingResponseList((this.schedulingRepository.findSchedulingsByStatus(status)));
        if (schedulings.isEmpty()) {
            throw new NotFoundException("There aren't schedulings with status '" + status + "' to show.");
        }
        return schedulings;
    }

    @Override
    public List<SchedulingResponse> schedulingsByBarberShop(Integer barberShopId, StatusEnum status) {
        List<SchedulingResponse> schedulings = this.schedulingMapper.toSchedulingResponseList(this.schedulingRepository.findSchedulingsByBarberShop(barberShopId));
        if (status == null) {
            if (schedulings.isEmpty()) {
                throw new NotFoundException("There aren't schedulings for this barber shop.");
            }
            return schedulings;
        } else {
            List<SchedulingResponse> schedulingByStatus = new ArrayList<>();
            for (SchedulingResponse scheduling : schedulings) {
                if (scheduling.getStatus().equals(status)) {
                    schedulingByStatus.add(scheduling);
                }
            }
            {
                if (schedulingByStatus.isEmpty()) {
                    throw new NotFoundException("There aren't schedulings with status '" + status + "' for this barber shop.");
                }
                return schedulingByStatus;
            }
        }
    }

    @Override
    public List<SchedulingResponse> schedulingsByClient(Integer clientId, StatusEnum status) {
        List<SchedulingResponse> schedulings = this.schedulingMapper.toSchedulingResponseList(this.schedulingRepository.findSchedulingsByClient(clientId));
        if (status == null) {
            if (schedulings.isEmpty()) {
                throw new NotFoundException("There aren't schedulings for this client.");
            }
            return schedulings;
        } else {
            List<SchedulingResponse> schedulingByStatus = new ArrayList<>();
            for (SchedulingResponse scheduling : schedulings) {
                if (scheduling.getStatus().equals(status)) {
                    schedulingByStatus.add(scheduling);
                }
            }
            if (schedulingByStatus.isEmpty()) {
                throw new NotFoundException("There aren't schedulings with status '" + status + "' for this client.");
            }
            return schedulingByStatus;
        }
    }
    @Override
    public List<SchedulingResponse> schedulingsByEmployee(Integer employeeId, StatusEnum status) {
        List<SchedulingResponse> schedulings = this.schedulingMapper.toSchedulingResponseList(this.schedulingRepository.findSchedulingsByEmployee(employeeId));
        if (status == null) {
            if (schedulings.isEmpty()) {
                throw new NotFoundException("There aren't schedulings for this employee.");
            }
            return schedulings;
        } else {
            List<SchedulingResponse> schedulingByStatus = new ArrayList<>();
            for (SchedulingResponse scheduling : schedulings) {
                if (scheduling.getStatus().equals(status)) {
                    schedulingByStatus.add(scheduling);
                }
            }
            {
                if (schedulingByStatus.isEmpty()) {
                    throw new NotFoundException("There aren't schedulings with status '" + status + "' for this employee.");
                }
                return schedulingByStatus;
            }
        }
    }

    @Override
    public List<SchedulingResponse> schedulingsByItem(Integer itemId, StatusEnum status) {
        List<SchedulingResponse> schedulings = this.schedulingMapper.toSchedulingResponseList(this.schedulingRepository.findSchedulingsByItem(itemId));
        if (status == null) {
            if (schedulings.isEmpty()) {
                throw new NotFoundException("There aren't schedulings for this item.");
            }
            return schedulings;
        } else {
            List<SchedulingResponse> schedulingByStatus = new ArrayList<>();
            for (SchedulingResponse scheduling : schedulings) {
                if (scheduling.getStatus().equals(status)) {
                    schedulingByStatus.add(scheduling);
                }
            }
            {
                if (schedulingByStatus.isEmpty()) {
                    throw new NotFoundException("There aren't schedulings with status '" + status + "' for this item.");
                }
                return schedulingByStatus;
            }
        }
    }

    @Override
    public void cancelScheduling(Integer schedulingId) {
        if (this.schedulingRepository.existsById(schedulingId)) {
            Scheduling scheduling = this.schedulingRepository.getById(schedulingId);
            scheduling.setStatus(StatusEnum.CANCELLED);
            this.schedulingRepository.save(scheduling);
            return;
        }
        throw new NotFoundException("Scheduling with ID '" + schedulingId + "' not found.");
    }

    @Override
    public void finishScheduling(Integer schedulingId) {
        Scheduling scheduling = this.schedulingMapper.toScheduling(schedulingById(schedulingId));
        if (scheduling.getStatus().equals(StatusEnum.SCHEDULED)) {
            scheduling.setStatus(StatusEnum.FINISHED);
            this.schedulingRepository.save(scheduling);
            return;
        }
        throw new BadRequestException("Unable to complete scheduling with status '" + scheduling.getStatus() + "'.");
    }

    @Override
    public SchedulingResponse updateScheduling(Integer schedulingId, SchedulingRequest schedulingUpdated) {
        if (this.schedulingRepository.existsById(schedulingId)) {
            Scheduling scheduling = this.schedulingRepository.getById(schedulingId);
            if ((schedulingUpdated.getItems() != null)) {
                Scheduling schedulingUpdt = this.schedulingMapper.toScheduling(schedulingUpdated);
                List<Item> itemsUpdt = scheduling.getItems();
                for (Item item : schedulingUpdt.getItems()) {
                    if (!(itemsUpdt.stream().anyMatch(itemUpdt -> itemUpdt.getItemId().equals(item.getItemId())))) {
                        itemsUpdt.add(item);
                    }
                }
                scheduling.setItems(itemsUpdt);
                return this.schedulingMapper.toSchedulingResponse((this.schedulingRepository.save(scheduling)));
            }
            BeanUtils.copyProperties((this.schedulingMapper.toScheduling(schedulingUpdated)), scheduling, this.searchEmptyFields(schedulingUpdated));
            return this.schedulingMapper.toSchedulingResponse((this.schedulingRepository.save(scheduling)));
        }
        throw new NotFoundException("Scheduling with ID '" + schedulingId + "' not found.");
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

    //    private LocalDateTime dateConvert(LocalDateTime dateOld) {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
//        String dateFormatted = dateOld.format(formatter);
//        return LocalDateTime.parse(dateFormatted, formatter);
//    }
}
