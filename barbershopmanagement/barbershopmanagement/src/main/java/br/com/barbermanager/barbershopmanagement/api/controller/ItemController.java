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
        return ResponseEntity.ok(this.itemService.createItem(newItem));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ItemResponse>> allItems() {
        return ResponseEntity.ok(this.itemService.allItems());
    }

    @GetMapping("/barbershop/{barberShopId}")
    public ResponseEntity<List<ItemResponse>> itemsByBarberShop(@PathVariable Integer barberShopId) {
        return ResponseEntity.ok(this.itemService.itemByBarberShop(barberShopId));
    }

    @DeleteMapping("/delete/{itemId}")
    public ResponseEntity deleteItem(@PathVariable Integer itemId) {
        this.itemService.deleteItem(itemId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/update/{itemId}")
    public ResponseEntity<ItemResponse> updateItem(@PathVariable Integer itemId, @RequestBody ItemRequest updatedItem) {
        this.itemService.updateItem(itemId, updatedItem);
        return ResponseEntity.ok(this.itemService.itemById(itemId));
    }

}
