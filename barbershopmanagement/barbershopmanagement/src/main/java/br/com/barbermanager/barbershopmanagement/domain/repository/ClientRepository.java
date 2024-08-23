package br.com.barbermanager.barbershopmanagement.domain.repository;

import br.com.barbermanager.barbershopmanagement.domain.model.Client;
import br.com.barbermanager.barbershopmanagement.domain.model.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

    @Query("SELECT c FROM Client c WHERE c.cpf = :cpf")
    Client findByCpf(String cpf);

    @Query("SELECT c FROM Client c WHERE c.status = :status")
    List<Client> findClientsByStatus(StatusEnum status);

    @Query("SELECT c FROM Client c JOIN c.barberShops b WHERE b.barberShopId = :barberShopId")
    List<Client> findClientsByBarberShop(Integer barberShopId);
}
