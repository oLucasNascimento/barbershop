package br.com.barbermanager.barbershopmanagement.domain.service.barbershop;

import br.com.barbermanager.barbershopmanagement.api.request.barbershop.BarberShopRequest;
import br.com.barbermanager.barbershopmanagement.api.response.barbershop.BarberShopResponse;
import br.com.barbermanager.barbershopmanagement.api.response.barbershop.BarberShopSimple;
import br.com.barbermanager.barbershopmanagement.api.response.client.ClientSimple;
import br.com.barbermanager.barbershopmanagement.api.response.scheduling.SchedulingResponse;
import br.com.barbermanager.barbershopmanagement.domain.model.Scheduling;
import br.com.barbermanager.barbershopmanagement.domain.model.StatusEnum;

import java.util.List;

public interface BarberShopService {

    Boolean barberShopExists(Integer barberShopId);

    BarberShopResponse createBarberShop(BarberShopRequest newBarberShop);

    List<BarberShopSimple> allBarberShops();

    void deleteBarberShop(Integer barberShopId);

    BarberShopResponse updateBarberShop(Integer barberShopId, BarberShopRequest updatedBarberShop);

    BarberShopResponse udpateClientAtBarberShop(Integer barberShopId, BarberShopRequest updatedClients);

    void removeClient(Integer barberShopId, Integer clientId);

    BarberShopResponse barberShopById(Integer barberShopId);

    void dismissEmployee(Integer barberShopId, Integer employeeId);

    List<BarberShopSimple> allBarberShopsByStatus(StatusEnum status);

    List<BarberShopSimple> barberShopsByClient(Integer clientId);

    List<BarberShopSimple> barberShopsByClientAndStatus(Integer barberShopId, StatusEnum status);

    void activeBarberShop(Integer barberShopId);
}
