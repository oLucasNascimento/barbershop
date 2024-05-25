package br.com.barbermanager.barbershopmanagement.domain.service.item;

import br.com.barbermanager.barbershopmanagement.domain.model.Item;

import java.util.List;

public interface ItemService {

    Boolean itemExists(Integer itemId);

    Item createItem(Item newItem);

    List<Item> allItems();

    Item itemById(Integer itemId);

    List<Item> itemByBarberShop(Integer barberShopId);

    Boolean deleteItem(Integer itemId);

    Item updateItem(Integer itemId, Item updatedItem);
}
