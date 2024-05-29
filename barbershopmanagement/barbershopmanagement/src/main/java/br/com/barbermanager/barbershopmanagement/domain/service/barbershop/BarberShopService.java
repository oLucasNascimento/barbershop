package br.com.barbermanager.barbershopmanagement.domain.service.barbershop;

import br.com.barbermanager.barbershopmanagement.api.request.barbershop.BarberShopRequest;
import br.com.barbermanager.barbershopmanagement.api.response.barber.BarberShopResponse;
import br.com.barbermanager.barbershopmanagement.domain.model.BarberShop;

import java.util.List;

public interface BarberShopService {

    Boolean barberShopExists(Integer barberShopId);

    BarberShopResponse createBarberShop(BarberShopRequest newBarberShop);

    List<BarberShopResponse> allBarberShops();

    void deleteBarberShop(Integer barberShopId);

    BarberShopResponse updateBarberShop(Integer barberShopId, BarberShopRequest updatedBarberShop);

    BarberShopResponse udpateClientAtBarberShop(Integer barberShopId, BarberShopRequest updatedClients);

    void removeClient(Integer barberShopId, Integer clientId);

    BarberShopResponse barberShopById(Integer barberShopId);

    Boolean dismissEmployee(Integer barberShopId, Integer employeeId);
}
