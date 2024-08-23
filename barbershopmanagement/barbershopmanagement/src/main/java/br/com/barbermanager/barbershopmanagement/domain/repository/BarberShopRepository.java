package br.com.barbermanager.barbershopmanagement.domain.repository;

import br.com.barbermanager.barbershopmanagement.domain.model.BarberShop;
import br.com.barbermanager.barbershopmanagement.domain.model.Item;
import br.com.barbermanager.barbershopmanagement.domain.model.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BarberShopRepository extends JpaRepository<BarberShop, Integer> {

    @Query("SELECT b FROM BarberShop b WHERE b.email = :email")
    BarberShop findByEmail(String email);

    @Query("SELECT b FROM BarberShop b WHERE b.status = :status")
    List<BarberShop> findBarberShopsByStatus(StatusEnum status);

    @Query("SELECT b FROM BarberShop b JOIN b.clients c WHERE c.clientId = :clientId")
    List<BarberShop> findBarberShopsByClients(Integer clientId);
}
