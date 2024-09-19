package br.com.barbermanager.barbershopmanagement.api.mapper;

import br.com.barbermanager.barbershopmanagement.api.request.item.ItemRequest;
import br.com.barbermanager.barbershopmanagement.api.response.item.ItemResponse;
import br.com.barbermanager.barbershopmanagement.api.response.item.ItemSimple;
import br.com.barbermanager.barbershopmanagement.domain.model.Item;
import org.mapstruct.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ItemMapper {


    Item toItem(ItemRequest itemRequest);
    Item toItem(ItemResponse itemRequest);

    ItemRequest toItemRequest(Item item);

    ItemResponse toItemResponse(Item item);

    ItemSimple toItemSimple(Item item);

    List<ItemResponse> toItemResponseList(List<Item> itemList);

    List<ItemSimple> toItemSimpleList(List<Item> itemList);

}
