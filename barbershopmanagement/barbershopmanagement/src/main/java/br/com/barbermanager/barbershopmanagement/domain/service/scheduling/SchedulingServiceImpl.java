package br.com.barbermanager.barbershopmanagement.domain.service.scheduling;

import br.com.barbermanager.barbershopmanagement.api.mapper.SchedulingMapper;
import br.com.barbermanager.barbershopmanagement.api.request.scheduling.SchedulingRequest;
import br.com.barbermanager.barbershopmanagement.api.response.scheduling.SchedulingResponse;
import br.com.barbermanager.barbershopmanagement.domain.repository.SchedulingRepository;
import br.com.barbermanager.barbershopmanagement.exception.AlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchedulingServiceImpl implements SchedulingService {

    @Autowired
    private SchedulingRepository schedulingRepository;

    @Autowired
    private SchedulingMapper schedulingMapper;

    @Override
    public SchedulingResponse newScheduling(SchedulingRequest newScheduling) {
//        newScheduling.setSchedulingTime((this.dateConvert(newScheduling.getSchedulingTime())));
        if ((this.schedulingRepository.schedulingExists((newScheduling.getBarberShop().getBarberShopId()), (newScheduling.getEmployee().getEmployeeId()), (newScheduling.getSchedulingTime())).isEmpty())) {
            return this.schedulingMapper.toSchedulingResponse((schedulingRepository.save((this.schedulingMapper.toScheduling(newScheduling)))));
        }
        throw new AlreadyExistsException("Scheduling exists.");
    }

    @Override
    public List<SchedulingResponse> allSchedulings() {
        return this.schedulingMapper.toSchedulingResponseList((this.schedulingRepository.findAll()));
    }

//    private LocalDateTime dateConvert(LocalDateTime dateOld) {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
//        String dateFormatted = dateOld.format(formatter);
//        return LocalDateTime.parse(dateFormatted, formatter);
//    }
}
