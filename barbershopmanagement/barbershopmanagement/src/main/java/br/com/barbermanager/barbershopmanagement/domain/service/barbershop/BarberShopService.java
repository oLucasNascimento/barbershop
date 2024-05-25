package br.com.barbermanager.barbershopmanagement.domain.service.barbershop;

import br.com.barbermanager.barbershopmanagement.domain.model.BarberShop;

import java.util.List;

public interface BarberShopService {

    Boolean barberShopExists(Integer barberShopId);

    BarberShop createBarberShop(BarberShop newBarberShop);

    List<BarberShop> allBarberShops();

    void deleteBarberShop(Integer barberShopId);

    BarberShop updateBarberShop(Integer barberShopId, BarberShop updatedBarberShop);

    BarberShop udpateClientAtBarberShop(Integer barberShopId, BarberShop updatedClients);

    void removeClient(Integer barberShopId, Integer clientId);

    BarberShop barberShopById(Integer barberShopId);

    Boolean dismissEmployee(Integer barberShopId, Integer employeeId);
}
