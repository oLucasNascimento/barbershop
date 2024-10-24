package br.com.barbermanager.barbershopmanagement.domain.repository;

import br.com.barbermanager.barbershopmanagement.domain.model.Item;
import br.com.barbermanager.barbershopmanagement.domain.model.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

    @Query("SELECT i FROM Item i WHERE i.name = :name AND i.price = :price AND i.barberShop.barberShopId = :barberShopId")
    Item existingItem(String name, Double price, Integer barberShopId);

    @Query("SELECT i FROM Item i WHERE i.barberShop.barberShopId = :barberShopId")
    List<Item> itemByBarberShop(Integer barberShopId);

    @Query("SELECT i FROM Item i WHERE i.status = :status")
    List<Item> findItemsByStatus(StatusEnum status);
}
