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
    @Null(groups = ItemCreate.class, message = "{item.id.null}")
    @NotNull(groups = {SchedulingCreate.class, SchedulingUpdate.class, BarberShopUpdate.class}, message = "{item.id.not.null}")
    private Integer itemId;

    @Schema(description = "Service Name", example = "Haircut")
    @NotBlank(groups = ItemCreate.class, message = "{item.name.not.null}")
    private String name;

    @Schema(description = "Service Price", example = "20.0")
    @NotNull(groups = ItemCreate.class, message = "{item.price.not.null}")
    private Double price;

    @Schema(description = "Service Time (in minutes)", example = "30")
    @NotNull(groups = ItemCreate.class, message = "{item.time.not.null}")
    private Integer time;

    @Schema(hidden = true)
    private StatusEnum status;

    @Schema(description = "Service BarberShop", example = "{\"barberShopId\":\"1\"}")
    @NotNull(groups = ItemCreate.class, message = "{item.barbershop.not.null}")
    @JsonIgnoreProperties({"items", "employees", "clients"})
    @Valid
    private BarberShopRequest barberShop;

}