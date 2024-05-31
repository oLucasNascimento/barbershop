package br.com.barbermanager.barbershopmanagement.domain.service.barbershop;

import br.com.barbermanager.barbershopmanagement.api.request.barbershop.BarberShopRequest;
import br.com.barbermanager.barbershopmanagement.api.response.barbershop.BarberShopResponse;

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
