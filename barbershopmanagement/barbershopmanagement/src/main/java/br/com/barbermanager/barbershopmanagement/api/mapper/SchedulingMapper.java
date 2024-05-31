package br.com.barbermanager.barbershopmanagement.api.mapper;

import br.com.barbermanager.barbershopmanagement.api.request.scheduling.SchedulingRequest;
import br.com.barbermanager.barbershopmanagement.api.response.scheduling.SchedulingResponse;
import br.com.barbermanager.barbershopmanagement.domain.model.Scheduling;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SchedulingMapper {

    private final ModelMapper mapper;

    public SchedulingMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public Scheduling toScheduling(Object scheduling) {
        return this.mapper.map(scheduling, Scheduling.class);
    }

    public SchedulingRequest toSchedulingRequest(Object scheduling) {
        return this.mapper.map(scheduling, SchedulingRequest.class);
    }

    public SchedulingResponse toSchedulingResponse(Object scheduling) {
        return this.mapper.map(scheduling, SchedulingResponse.class);
    }

    public List<SchedulingResponse> toSchedulingResponseList(List<Scheduling> schedulingList) {
        return schedulingList.stream().map(this::toSchedulingResponse).collect(Collectors.toList());
    }

}
