package br.com.barbermanager.barbershopmanagement.domain.service.item;

import br.com.barbermanager.barbershopmanagement.api.request.item.ItemRequest;
import br.com.barbermanager.barbershopmanagement.api.response.item.ItemResponse;
import br.com.barbermanager.barbershopmanagement.domain.model.Item;

import java.util.List;

public interface ItemService {

    Boolean itemExists(Integer itemId);

    ItemResponse createItem(ItemRequest newItem);

    List<ItemResponse> allItems();

    ItemResponse itemById(Integer itemId);

    List<ItemResponse> itemByBarberShop(Integer barberShopId);

    void deleteItem(Integer itemId);

    ItemResponse updateItem(Integer itemId, ItemRequest updatedItem);
}
