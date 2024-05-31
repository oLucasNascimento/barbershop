package br.com.barbermanager.barbershopmanagement.api.controller;

import br.com.barbermanager.barbershopmanagement.api.request.item.ItemRequest;
import br.com.barbermanager.barbershopmanagement.api.response.item.ItemResponse;
import br.com.barbermanager.barbershopmanagement.domain.model.Item;
import br.com.barbermanager.barbershopmanagement.domain.service.item.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @PostMapping("/new")
    public ResponseEntity<ItemResponse> newItem(@RequestBody ItemRequest newItem) {
        ItemResponse itemResponse = this.itemService.createItem(newItem);
        if (itemResponse != null) {
            return ResponseEntity.ok(itemResponse);
        }
        return ResponseEntity.status(409).build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<ItemResponse>> allItems() {
        List<ItemResponse> items = this.itemService.allItems();
        if (items.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(items);
    }

    @GetMapping("/barbershop/{barberShopId}")
    public ResponseEntity<List<ItemResponse>> itemsByBarberShop(@PathVariable Integer barberShopId){
        List<ItemResponse> items = this.itemService.itemByBarberShop(barberShopId);
        if(items.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(items);
    }

    @DeleteMapping("/delete/{itemId}")
    public ResponseEntity deleteItem(@PathVariable Integer itemId) {
        if (this.itemService.deleteItem(itemId)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PatchMapping("/update/{itemId}")
    public ResponseEntity<ItemResponse> updateItem(@PathVariable Integer itemId, @RequestBody ItemRequest updatedItem) {
        if ((this.itemService.updateItem(itemId, updatedItem)) != null) {
            return ResponseEntity.ok(this.itemService.itemById(itemId));
        }
        return ResponseEntity.badRequest().build();
    }

}
