package br.com.barbermanager.barbershopmanagement.domain.service.scheduling;

import br.com.barbermanager.barbershopmanagement.api.mapper.SchedulingMapper;
import br.com.barbermanager.barbershopmanagement.api.request.scheduling.SchedulingRequest;
import br.com.barbermanager.barbershopmanagement.api.response.scheduling.SchedulingResponse;
import br.com.barbermanager.barbershopmanagement.domain.repository.SchedulingRepository;
import br.com.barbermanager.barbershopmanagement.exception.AlreadyExistsException;
import br.com.barbermanager.barbershopmanagement.exception.NotFoundException;
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
        if ((this.schedulingRepository.schedulingExists((newScheduling.getEmployee().getEmployeeId()), (newScheduling.getSchedulingTime())).isEmpty())) {
            return this.schedulingMapper.toSchedulingResponse((schedulingRepository.save((this.schedulingMapper.toScheduling(newScheduling)))));
        }
        throw new AlreadyExistsException("The employee with ID '"+newScheduling.getEmployee().getEmployeeId() + "' already has an scheduling at time '"+ newScheduling.getSchedulingTime() + "'.");
    }

    @Override
    public List<SchedulingResponse> allSchedulings() {
        List<SchedulingResponse> schedulingResponses = this.schedulingMapper.toSchedulingResponseList((this.schedulingRepository.findAll()));
        if (schedulingResponses.isEmpty()){
            throw new NotFoundException("There aren't schedulings to show.");
        }
        return schedulingResponses;
    }

//    private LocalDateTime dateConvert(LocalDateTime dateOld) {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
//        String dateFormatted = dateOld.format(formatter);
//        return LocalDateTime.parse(dateFormatted, formatter);
//    }
}
