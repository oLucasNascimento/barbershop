package br.com.barbermanager.barbershopmanagement.api.controller;

import br.com.barbermanager.barbershopmanagement.api.request.item.ItemRequest;
import br.com.barbermanager.barbershopmanagement.api.response.item.ItemResponse;
import br.com.barbermanager.barbershopmanagement.api.response.item.ItemSimple;
import br.com.barbermanager.barbershopmanagement.domain.model.StatusEnum;
import br.com.barbermanager.barbershopmanagement.domain.model.validations.ItemCreate;
import br.com.barbermanager.barbershopmanagement.domain.model.validations.ItemUpdate;
import br.com.barbermanager.barbershopmanagement.domain.service.item.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/item")
@Validated
public class ItemController {

    @Autowired
    private ItemService itemService;

    @PostMapping("/new")
    public ResponseEntity<ItemResponse> newItem(@RequestBody @Validated(ItemCreate.class) ItemRequest newItem) {
        ItemResponse response = this.itemService.createItem(newItem);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getItemId())
                .toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ItemSimple>> allItems(@RequestParam(required = false) StatusEnum status) {
        return ResponseEntity.ok(this.itemService.allItems(status));
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<ItemResponse> itemById(@PathVariable Integer itemId) {
        return ResponseEntity.ok(this.itemService.itemById(itemId));
    }

    @GetMapping("/barbershop/{barberShopId}")
    public ResponseEntity<List<ItemSimple>> itemsByBarberShop(@RequestParam(required = false) StatusEnum status, @PathVariable Integer barberShopId) {
        return ResponseEntity.ok(this.itemService.itemByBarberShop(barberShopId, status));
    }

    @DeleteMapping("/delete/{itemId}")
    public ResponseEntity deleteItem(@PathVariable Integer itemId) {
        this.itemService.deleteItem(itemId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/update/{itemId}")
    public ResponseEntity<ItemResponse> updateItem(@PathVariable Integer itemId, @RequestBody @Validated(ItemUpdate.class) ItemRequest updatedItem) {
        this.itemService.updateItem(itemId, updatedItem);
        return ResponseEntity.ok(this.itemService.itemById(itemId));
    }

    @PatchMapping("/active-item/{itemId}")
    public ResponseEntity activeEmployee(@PathVariable Integer itemId) {
        this.itemService.activeItem(itemId);
        return ResponseEntity.ok().build();
    }


}
