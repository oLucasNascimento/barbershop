package br.com.barbermanager.barbershopmanagement.domain.service.scheduling;


import br.com.barbermanager.barbershopmanagement.api.request.scheduling.SchedulingRequest;
import br.com.barbermanager.barbershopmanagement.api.response.scheduling.SchedulingResponse;
import br.com.barbermanager.barbershopmanagement.domain.model.StatusEnum;

import java.util.List;

public interface SchedulingService {

    SchedulingResponse newScheduling(SchedulingRequest newScheduling);

    List<SchedulingResponse> allSchedulings(StatusEnum status);

    SchedulingResponse schedulingById(Integer schedulingId);

    void cancelScheduling(Integer schedulingId);

    SchedulingResponse updateScheduling(Integer schedulingId, SchedulingRequest schedulingUpdated);

//    List<SchedulingResponse> allSchedulingsByStatus(StatusEnum status);

    void finishScheduling(Integer schedulingId);

    List<SchedulingResponse> schedulingsByBarberShop(Integer barberShopId, StatusEnum status);

    List<SchedulingResponse> schedulingsByClient(Integer clientId, StatusEnum status);

    List<SchedulingResponse> schedulingsByEmployee(Integer employeeId, StatusEnum status);

    List<SchedulingResponse> schedulingsByItem(Integer itemId, StatusEnum status);
}
