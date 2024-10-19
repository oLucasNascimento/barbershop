package br.com.barbermanager.barbershopmanagement.api.request.item;

import br.com.barbermanager.barbershopmanagement.api.request.barbershop.BarberShopRequest;
import br.com.barbermanager.barbershopmanagement.api.request.scheduling.SchedulingRequest;
import br.com.barbermanager.barbershopmanagement.domain.model.StatusEnum;
import br.com.barbermanager.barbershopmanagement.domain.model.validations.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @NotNull(groups = {SchedulingCreate.class, AssociatedUpdate.class}, message = "The Item ID field cannot be null.")
    private Integer itemId;

    @NotBlank(groups = OnCreate.class, message = "The Name field cannot be null.")
    private String name;

    @NotNull(groups = OnCreate.class, message = "The Price field cannot be null.")
    private Double price;

    @NotNull(groups = OnCreate.class, message = "The Name field cannot be null.")
    private Integer time;

    private StatusEnum status;

    @Valid
    @JsonIgnore
    @NotNull(groups = ItemCreate.class, message = "The Barber Shop field cannot be null.")
    private BarberShopRequest barberShop;

    @JsonIgnore
    @Null(groups = OnCreate.class, message = "The Scheduling field must be null.")
    private List<SchedulingRequest> schedulings;

}