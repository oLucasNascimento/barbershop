package br.com.barbermanager.barbershopmanagement.api.request.item;

import br.com.barbermanager.barbershopmanagement.api.request.barbershop.BarberShopRequest;
import br.com.barbermanager.barbershopmanagement.domain.model.StatusEnum;
import br.com.barbermanager.barbershopmanagement.domain.model.validations.BarberShopUpdate;
import br.com.barbermanager.barbershopmanagement.domain.model.validations.ItemCreate;
import br.com.barbermanager.barbershopmanagement.domain.model.validations.SchedulingCreate;
import br.com.barbermanager.barbershopmanagement.domain.model.validations.SchedulingUpdate;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemRequest {

    @Null(groups = ItemCreate.class, message = "The Item ID field must be null.")
    @NotNull(groups = {SchedulingCreate.class, SchedulingUpdate.class, BarberShopUpdate.class}, message = "The Item ID field cannot be null.")
    private Integer itemId;

    @NotBlank(groups = ItemCreate.class, message = "The Name field cannot be null.")
    private String name;

    @NotNull(groups = ItemCreate.class, message = "The Price field cannot be null.")
    private Double price;

    @NotNull(groups = ItemCreate.class, message = "The Time field cannot be null.")
    private Integer time;

    private StatusEnum status;

    @NotNull(groups = ItemCreate.class, message = "The BarberShop field cannot be null.")
    @JsonIgnoreProperties({"items", "employees", "clients"})
    @Valid
    private BarberShopRequest barberShop;

}