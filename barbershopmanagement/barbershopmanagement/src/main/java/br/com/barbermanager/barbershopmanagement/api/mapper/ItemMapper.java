package br.com.barbermanager.barbershopmanagement.api.mapper;

import br.com.barbermanager.barbershopmanagement.api.request.item.ItemRequest;
import br.com.barbermanager.barbershopmanagement.api.response.item.ItemResponse;
import br.com.barbermanager.barbershopmanagement.domain.model.Item;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItemMapper {

    private final ModelMapper mapper;

    public ItemMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public Item toItem(Object itemRequest){
        return this.mapper.map(itemRequest, Item.class);
    }

    public ItemRequest toItemRequest(Object item){
        return this.mapper.map(item, ItemRequest.class);
    }

    public ItemResponse toItemResponse(Object item){
        return this.mapper.map(item, ItemResponse.class);
    }

    public List<ItemResponse> toItemResponseList(List<Item> itemList){
        return itemList.stream().map(this::toItemResponse).collect(Collectors.toList());
    }

}
