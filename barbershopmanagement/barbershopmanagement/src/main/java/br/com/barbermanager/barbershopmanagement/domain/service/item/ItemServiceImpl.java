package br.com.barbermanager.barbershopmanagement.domain.service.item;

import br.com.barbermanager.barbershopmanagement.api.mapper.ItemMapper;
import br.com.barbermanager.barbershopmanagement.api.request.item.ItemRequest;
import br.com.barbermanager.barbershopmanagement.api.response.item.ItemResponse;
import br.com.barbermanager.barbershopmanagement.api.response.item.ItemSimple;
import br.com.barbermanager.barbershopmanagement.domain.model.Item;
import br.com.barbermanager.barbershopmanagement.domain.repository.ItemRepository;
import br.com.barbermanager.barbershopmanagement.exception.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemMapper itemMapper;

    @Override
    public Boolean itemExists(Integer itemId) {
        return this.itemRepository.existsById(itemId);
    }

    @Transactional
    @Override
    public ItemResponse createItem(ItemRequest newItem) {
        if ((itemRepository.existingItem(newItem.getName(), newItem.getPrice())) == null) {
            return this.itemMapper.toItemResponse((this.itemRepository.save((this.itemMapper.toItem(newItem)))));
        }
        throw new NotFoundException("Item with name '" + newItem.getName() + "' and price '" + newItem.getPrice() + "' already exists.");
    }

    @Override
    public List<ItemSimple> allItems() {
        List<ItemSimple> itemResponses = this.itemMapper.toItemSimpleList((this.itemRepository.findAll()));
        if (itemResponses.isEmpty()) {
            throw new NotFoundException("There aren't items to show.");
        }
        return itemResponses;
    }

    @Override
    public ItemResponse itemById(Integer itemId) {
        if (this.itemRepository.existsById(itemId)) {
            return this.itemMapper.toItemResponse((this.itemRepository.getById(itemId)));
        }
        throw new NotFoundException("Item with ID '" + itemId + "' not found.");
    }

    @Override
    public List<ItemResponse> itemByBarberShop(Integer barberShopId) {
        List<ItemResponse> itemResponses = this.itemMapper.toItemResponseList((this.itemRepository.itemByBarberShop(barberShopId)));
        if (itemResponses.isEmpty()) {
            throw new NotFoundException("There aren't items at this barbershop");
        }
        return itemResponses;
    }

    @Override
    public void deleteItem(Integer itemId) {
        if (this.itemRepository.existsById(itemId)) {
            this.itemRepository.deleteById(itemId);
            return;
        }
        throw new NotFoundException("Item with ID '" + itemId + "' not found.");
    }

    @Override
    @Transactional
    public ItemResponse updateItem(Integer itemId, ItemRequest updatedItem) {
        if (this.itemExists(itemId)) {
            Item item = this.itemRepository.getById(itemId);
            BeanUtils.copyProperties((this.itemMapper.toItem(updatedItem)), item, this.searchEmptyFields(updatedItem));
            return this.itemMapper.toItemResponse((this.itemRepository.save(item)));
        }
        throw new NotFoundException("Item with ID '" + itemId + "' not found.");
    }

    private String[] searchEmptyFields(Object source) {
        BeanWrapper src = new BeanWrapperImpl(source);
        Set<String> emptyFields = new HashSet<>();
        for (PropertyDescriptor pd : src.getPropertyDescriptors()) {
            if (src.getPropertyValue(pd.getName()) == null) {
                emptyFields.add(pd.getName());
            }
        }
        String[] result = new String[emptyFields.size()];
        return emptyFields.toArray(result);
    }

}
