package br.com.barbermanager.barbershopmanagement.domain.service.scheduling;

import br.com.barbermanager.barbershopmanagement.api.mapper.SchedulingMapper;
import br.com.barbermanager.barbershopmanagement.api.request.item.ItemRequest;
import br.com.barbermanager.barbershopmanagement.api.request.scheduling.SchedulingRequest;
import br.com.barbermanager.barbershopmanagement.api.response.item.ItemResponse;
import br.com.barbermanager.barbershopmanagement.api.response.scheduling.SchedulingResponse;
import br.com.barbermanager.barbershopmanagement.domain.model.Item;
import br.com.barbermanager.barbershopmanagement.domain.model.Scheduling;
import br.com.barbermanager.barbershopmanagement.domain.repository.SchedulingRepository;
import br.com.barbermanager.barbershopmanagement.domain.service.item.ItemService;
import br.com.barbermanager.barbershopmanagement.exception.AlreadyExistsException;
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

            if (((scheduling.getEmployee().getEmployeeId().equals(schedulingOld.getEmployee().getEmployeeId())) && (newStart.isBefore(existingEnd)) && (newEnd.isAfter(existingStart)) && !(newStart.equals(existingEnd)))) {
                return false;
            }
        }
        return true;
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
    public SchedulingResponse schedulingById(Integer schedulingId) {
        if (this.schedulingRepository.existsById(schedulingId)) {
            return this.schedulingMapper.toSchedulingResponse(this.schedulingRepository.getById(schedulingId));
        }
        throw new NotFoundException("Scheduling with ID '" + schedulingId + "' not found.");
    }

    @Override
    public void deleteScheduling(Integer schedulingId) {
        if (this.schedulingRepository.existsById(schedulingId)) {
            this.schedulingRepository.deleteById(schedulingId);
            return;
        }
        throw new NotFoundException("Scheduling with ID '" + schedulingId + "' not found.");
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
