package br.com.barbermanager.barbershopmanagement.domain.service.scheduling;


import br.com.barbermanager.barbershopmanagement.api.request.scheduling.SchedulingRequest;
import br.com.barbermanager.barbershopmanagement.api.response.scheduling.SchedulingResponse;

import java.util.List;

public interface SchedulingService {

    SchedulingResponse newScheduling(SchedulingRequest newScheduling);
    List<SchedulingResponse> allSchedulings();

    SchedulingResponse schedulingById(Integer schedulingId);

    void deleteScheduling(Integer schedulingId);

    SchedulingResponse updateScheduling(Integer schedulingId, SchedulingRequest schedulingUpdated);
}
