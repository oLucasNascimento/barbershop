package br.com.barbermanager.barbershopmanagement.domain.service.item;

import br.com.barbermanager.barbershopmanagement.api.request.item.ItemRequest;
import br.com.barbermanager.barbershopmanagement.api.response.item.ItemResponse;
import br.com.barbermanager.barbershopmanagement.api.response.item.ItemSimple;
import br.com.barbermanager.barbershopmanagement.domain.model.Item;
import br.com.barbermanager.barbershopmanagement.domain.model.StatusEnum;

import java.util.List;

public interface ItemService {

    Boolean itemExists(Integer itemId);

    ItemResponse createItem(ItemRequest newItem);

    List<ItemSimple> allItems();

    ItemResponse itemById(Integer itemId);

    List<ItemSimple> itemByBarberShop(Integer barberShopId);

    void deleteItem(Integer itemId);

    ItemResponse updateItem(Integer itemId, ItemRequest updatedItem);

    void activeItem(Integer itemId);

    List<ItemSimple> allItemsByStatus(StatusEnum status);

    List<ItemSimple> itemsByBarberShopAndStatus(Integer barberShopId, StatusEnum status);
}
