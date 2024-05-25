package br.com.barbermanager.barbershopmanagement.domain.repository;

import br.com.barbermanager.barbershopmanagement.domain.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer> {

    @Query("SELECT i FROM Item i WHERE i.name = :name AND i.price = :price")
    Item existingItem(String name, Double price);

    @Query("SELECT i FROM Item i WHERE i.barberShop.barberShopId = :barberShopId")
    List<Item> itemByBarberShop(Integer barberShopId);

}
