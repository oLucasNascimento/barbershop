package br.com.barbermanager.barbershopmanagement.api.mapper;

import br.com.barbermanager.barbershopmanagement.api.request.barbershop.BarberShopRequest;
import br.com.barbermanager.barbershopmanagement.api.request.client.ClientRequest;
import br.com.barbermanager.barbershopmanagement.api.request.employee.EmployeeRequest;
import br.com.barbermanager.barbershopmanagement.api.request.item.ItemRequest;
import br.com.barbermanager.barbershopmanagement.api.response.barbershop.BarberShopResponse;
import br.com.barbermanager.barbershopmanagement.api.response.barbershop.BarberShopSimple;
import br.com.barbermanager.barbershopmanagement.api.response.client.ClientSimple;
import br.com.barbermanager.barbershopmanagement.api.response.employee.EmployeeSimple;
import br.com.barbermanager.barbershopmanagement.api.response.item.ItemSimple;
import br.com.barbermanager.barbershopmanagement.domain.model.BarberShop;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BarberShopMapper {

    BarberShop toBarberShop(BarberShopRequest barberShop);

    BarberShop toBarberShop(BarberShopResponse barberShop);

    BarberShopResponse toBarberShopResponse(BarberShop barberShop);

    List<BarberShopSimple> toBarberShopSimpleList(List<BarberShop> barberShops);

    BarberShopRequest toBarberShopRequest(BarberShop barberShop);

}
