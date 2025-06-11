package br.com.barbermanager.barbershopmanagement.api.controller;

import br.com.barbermanager.barbershopmanagement.api.request.item.ItemRequest;
import br.com.barbermanager.barbershopmanagement.api.response.item.ItemResponse;
import br.com.barbermanager.barbershopmanagement.api.response.item.ItemSimple;
import br.com.barbermanager.barbershopmanagement.config.security.SecurityConfiguration;
import br.com.barbermanager.barbershopmanagement.domain.model.StatusEnum;
import br.com.barbermanager.barbershopmanagement.domain.model.validations.ItemCreate;
import br.com.barbermanager.barbershopmanagement.domain.model.validations.ItemUpdate;
import br.com.barbermanager.barbershopmanagement.domain.service.item.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@SecurityRequirement(name = SecurityConfiguration.SECURITY)
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Operation(summary = "Criar Serviço", description = "Cria um novo Serviço", tags = "Serviço")
    @PostMapping("/new")
    public ResponseEntity<ItemResponse> newItem(@RequestBody @Validated(ItemCreate.class) ItemRequest newItem) {
        ItemResponse response = this.itemService.createItem(newItem);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getItemId())
                .toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @Operation(summary = "Buscar Serviços", description = "Busca todos os Serviços", tags = "Serviço")
    @GetMapping("/all")
    public ResponseEntity<List<ItemSimple>> allItems(
            @Parameter(description = "Status dos Serviços",example = "ACTIVE")
            @RequestParam(required = false) StatusEnum status) {
        return ResponseEntity.ok(this.itemService.allItems(status));
    }

    @Operation(summary = "Serviço por ID", description = "Busca o Serviço pelo ID", tags = "Serviço")
    @GetMapping("/{itemId}")
    public ResponseEntity<ItemResponse> itemById(
            @Parameter(description = "ID do Serviço",example = "1")
            @PathVariable Integer itemId) {
        return ResponseEntity.ok(this.itemService.itemById(itemId));
    }

    @Operation(summary = "Serviços por Barbearia", description = "Busca os Serviços de uma Barbearia", tags = "Serviço")
    @GetMapping("/barbershop/{barberShopId}")
    public ResponseEntity<List<ItemSimple>> itemsByBarberShop(
            @Parameter(description = "Status do Serviço",example = "ACTIVE")
            @RequestParam(required = false) StatusEnum status,
            @Parameter(description = "ID da Barbearia",example = "1")
            @PathVariable Integer barberShopId) {
        return ResponseEntity.ok(this.itemService.itemByBarberShop(barberShopId, status));
    }

    @Operation(summary = "Deletar Serviço", description = "Deleta um Serviço especificado", tags = "Serviço")
    @DeleteMapping("/delete/{itemId}")
    public ResponseEntity deleteItem(
            @Parameter(description = "ID do Serviço",example = "1")
            @PathVariable Integer itemId) {
        this.itemService.deleteItem(itemId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Atualizar Serviço", description = "Atualiza um Serviço especificado", tags = "Serviço")
    @PatchMapping("/update/{itemId}")
    public ResponseEntity<ItemResponse> updateItem(
            @Parameter(description = "ID do Serviço",example = "1")
            @PathVariable Integer itemId,
            @RequestBody @Validated(ItemUpdate.class) ItemRequest updatedItem) {
        this.itemService.updateItem(itemId, updatedItem);
        return ResponseEntity.ok(this.itemService.itemById(itemId));
    }

    @Operation(summary = "Ativar Serviço", description = "Ativa um Serviço especificado", tags = "Serviço")
    @PatchMapping("/active-item/{itemId}")
    public ResponseEntity activeEmployee(
            @Parameter(description = "ID do Serviço",example = "1")
            @PathVariable Integer itemId) {
        this.itemService.activeItem(itemId);
        return ResponseEntity.ok().build();
    }


}
