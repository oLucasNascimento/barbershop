package br.com.barbermanager.barbershopmanagement.api.request.item;

import br.com.barbermanager.barbershopmanagement.api.request.barbershop.BarberShopRequest;
import br.com.barbermanager.barbershopmanagement.domain.model.StatusEnum;
import br.com.barbermanager.barbershopmanagement.domain.model.validations.BarberShopUpdate;
import br.com.barbermanager.barbershopmanagement.domain.model.validations.ItemCreate;
import br.com.barbermanager.barbershopmanagement.domain.model.validations.SchedulingCreate;
import br.com.barbermanager.barbershopmanagement.domain.model.validations.SchedulingUpdate;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(hidden = true)
    @Null(groups = ItemCreate.class, message = "The Item ID field must be null.")
    @NotNull(groups = {SchedulingCreate.class, SchedulingUpdate.class, BarberShopUpdate.class}, message = "The Item ID field cannot be null.")
    private Integer itemId;

    @Schema(description = "Nome do Serviço", example = "Corte de Cabelo + Barba")
    @NotBlank(groups = ItemCreate.class, message = "The Name field cannot be null.")
    private String name;

    @Schema(description = "Preço do Serviço", example = "40.0")
    @NotNull(groups = ItemCreate.class, message = "The Price field cannot be null.")
    private Double price;

    @Schema(description = "Tempo do Serviço (em minutos)", example = "50")
    @NotNull(groups = ItemCreate.class, message = "The Time field cannot be null.")
    private Integer time;

    @Schema(hidden = true)
    private StatusEnum status;

    @Schema(description = "Barbearia do Funcionário")
    @NotNull(groups = ItemCreate.class, message = "The BarberShop field cannot be null.")
    @JsonIgnoreProperties({"items", "employees", "clients"})
    @Valid
    private BarberShopRequest barberShop;

}