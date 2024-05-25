package br.com.barbermanager.barbershopmanagement.domain.service.item;

import br.com.barbermanager.barbershopmanagement.domain.model.Item;
import br.com.barbermanager.barbershopmanagement.domain.repository.ItemRepository;
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

    @Override
    public Boolean itemExists(Integer itemId) {
        return this.itemRepository.existsById(itemId);
    }

    @Transactional
    @Override
    public Item createItem(Item newItem) {

        if((itemRepository.existingItem(newItem.getName(), newItem.getPrice())) == null){
            return this.itemRepository.save(newItem);
        }
        return null;
    }

    @Override
    public List<Item> allItems() {
        return this.itemRepository.findAll();
    }

    @Override
    public Item itemById(Integer itemId) {
        if (this.itemRepository.existsById(itemId)) {
            return this.itemRepository.getById(itemId);
        }
        return null;
    }

    @Override
    public List<Item> itemByBarberShop(Integer barberShopId) {
        return this.itemRepository.itemByBarberShop(barberShopId);
    }

    @Override
    public Boolean deleteItem(Integer itemId) {
        if (this.itemRepository.existsById(itemId)) {
            this.itemRepository.deleteById(itemId);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public Item updateItem(Integer itemId, Item updatedItem) {
        if (this.itemExists(itemId)) {
            Item item = this.itemById(itemId);
            BeanUtils.copyProperties(updatedItem, item, searchEmptyFields(updatedItem));
            return this.itemRepository.save(item);
        }
        return null;
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
