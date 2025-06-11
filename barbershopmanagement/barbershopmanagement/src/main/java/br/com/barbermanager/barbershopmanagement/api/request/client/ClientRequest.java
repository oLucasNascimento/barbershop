package br.com.barbermanager.barbershopmanagement.api.request.client;

import br.com.barbermanager.barbershopmanagement.api.request.barbershop.BarberShopRequest;
import br.com.barbermanager.barbershopmanagement.api.request.scheduling.SchedulingRequest;
import br.com.barbermanager.barbershopmanagement.domain.model.StatusEnum;
import br.com.barbermanager.barbershopmanagement.domain.model.validations.*;
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

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientRequest {

    @Schema(hidden = true)
    @Null(groups = ClientCreate.class, message = "{client.id.null}")
    @NotNull(groups = {SchedulingCreate.class, SchedulingUpdate.class, BarberShopUpdate.class, ClientInBarberShop.class}, message = "{client.id.not.null}")
    private Integer clientId;

    @Schema(description = "Client's Name", example = "John Smith")
    @NotBlank(groups = ClientCreate.class, message = "{client.name.not.null}")
    private String name;

    @Schema(description = "Client's CPF", example = "98564753475")
    @NotBlank(groups = ClientCreate.class, message = "{client.cpf.not.null}")
    private String cpf;

    @Schema(description = "Client's Password", example = "customerPass123!")
    @NotBlank(groups = ClientCreate.class, message = "{client.password.not.null}")
    private String password;

    @Schema(description = "Client's Phone", example = "+55 (11) 98765-4321")
    @NotBlank(groups = ClientCreate.class, message = "{client.phone.not.null}")
    private String phone;

    @Schema(hidden = true)
    private StatusEnum status;

    @Schema(description = "Client's BarberShops")
    @Null(groups = ClientCreate.class, message = "{client.barbershop.null}")
    @JsonIgnoreProperties({"items", "employees", "clients"})
    @Valid
    private List<BarberShopRequest> barberShops;

    @Schema(description = "Client's Scheduglings")
    @Null(message = "{client.schedulings.null}")
    @JsonIgnoreProperties({"barberShop", "items", "employees", "clients"})
    @Valid
    private List<SchedulingRequest> schedulings;

}