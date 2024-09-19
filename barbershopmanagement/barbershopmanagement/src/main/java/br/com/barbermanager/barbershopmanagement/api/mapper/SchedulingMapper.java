package br.com.barbermanager.barbershopmanagement.api.mapper;

import br.com.barbermanager.barbershopmanagement.api.request.scheduling.SchedulingRequest;
import br.com.barbermanager.barbershopmanagement.api.response.scheduling.SchedulingResponse;
import br.com.barbermanager.barbershopmanagement.domain.model.Scheduling;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SchedulingMapper {

    Scheduling toScheduling(SchedulingResponse scheduling);

    Scheduling toScheduling(SchedulingRequest scheduling);

    SchedulingRequest toSchedulingRequest(Object scheduling);

    SchedulingResponse toSchedulingResponse(Scheduling scheduling);

    List<SchedulingResponse> toSchedulingResponseList(List<Scheduling> schedulingList);

}
