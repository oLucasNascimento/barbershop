package br.com.barbermanager.barbershopmanagement.api.mapper;

import br.com.barbermanager.barbershopmanagement.api.request.barbershop.BarberShopRequest;
import br.com.barbermanager.barbershopmanagement.api.response.barbershop.BarberShopResponse;
import br.com.barbermanager.barbershopmanagement.api.response.barbershop.BarberShopSimple;
import br.com.barbermanager.barbershopmanagement.domain.model.BarberShop;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BarberShopMapper {

    private final ModelMapper mapper;

    public BarberShopMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public BarberShop toBarberShop(Object barberShop) {
        return this.mapper.map(barberShop, BarberShop.class);
    }

    public BarberShopRequest toBarberShopRequest(Object barberShop) {
        return this.mapper.map(barberShop, BarberShopRequest.class);
    }

    public BarberShopResponse toBarberShopResponse(BarberShop barberShop) {
        return this.mapper.map(barberShop, BarberShopResponse.class);
    }

    public BarberShopSimple toBarberShopSimple(Object barberShop) {
        return this.mapper.map(barberShop, BarberShopSimple.class);
    }

    public List<BarberShopResponse> toBarberShopResponseList(List<BarberShop> barberShopList) {
        return barberShopList.stream().map(this::toBarberShopResponse).collect(Collectors.toList());
    }

    public List<BarberShopSimple> toBarberShopSimpleList(List<BarberShop> barberShopList) {
        return barberShopList.stream().map(this::toBarberShopSimple).collect(Collectors.toList());
    }

}
