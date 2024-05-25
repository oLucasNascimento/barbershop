package br.com.barbermanager.barbershopmanagement.api.controller;

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
    public ResponseEntity<Item> newItem(@RequestBody Item newItem) {
        if ((this.itemService.createItem(newItem)) != null) {
            return ResponseEntity.ok(newItem);
        }
        return ResponseEntity.status(409).build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<Item>> allItems() {
        List<Item> items = this.itemService.allItems();
        if (items.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(items);
    }

    @GetMapping("/barberShop/{barberShopId}")
    public ResponseEntity<List<Item>> itemsByBarberShop(@PathVariable Integer barberShopId){
        List<Item> items = this.itemService.itemByBarberShop(barberShopId);
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
    public ResponseEntity<Item> updateItem(@PathVariable Integer itemId, @RequestBody Item updatedItem) {
        if ((this.itemService.updateItem(itemId, updatedItem)) != null) {
            return ResponseEntity.ok(this.itemService.itemById(itemId));
        }
        return ResponseEntity.badRequest().build();
    }

}
