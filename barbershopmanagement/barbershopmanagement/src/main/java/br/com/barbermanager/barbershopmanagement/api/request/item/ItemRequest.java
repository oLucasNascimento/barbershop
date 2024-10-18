package br.com.barbermanager.barbershopmanagement.api.request.item;

import br.com.barbermanager.barbershopmanagement.api.request.barbershop.BarberShopRequest;
import br.com.barbermanager.barbershopmanagement.api.request.scheduling.SchedulingRequest;
import br.com.barbermanager.barbershopmanagement.domain.model.StatusEnum;
import br.com.barbermanager.barbershopmanagement.domain.model.validations.ItemCreate;
import br.com.barbermanager.barbershopmanagement.domain.model.validations.OnCreate;
import br.com.barbermanager.barbershopmanagement.domain.model.validations.SchedulingCreate;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemRequest {

    @Null(groups = OnCreate.class)
    @NotNull(groups = SchedulingCreate.class, message = "The Item Id field cannot be null.")
    private Integer itemId;

    @NotBlank(groups = OnCreate.class, message = "The Name field cannot be null.")
    private String name;

    @NotNull(groups = OnCreate.class, message = "The Price field cannot be null.")
    private Double price;

    @NotNull(groups = OnCreate.class, message = "The Name field cannot be null.")
    private Integer time;

    private StatusEnum status;

    @Valid
    @NotNull(groups = ItemCreate.class, message = "The Barber Shop field cannot be null.")
    private BarberShopRequest barberShop;

    private List<SchedulingRequest> schedulings;

}