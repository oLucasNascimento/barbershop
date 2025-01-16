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
    @Null(groups = ClientCreate.class, message = "The Client ID field must be null.")
    @NotNull(groups = {SchedulingCreate.class, SchedulingUpdate.class, BarberShopUpdate.class, ClientInBarberShop.class}, message = "The Client ID field cannot be null.")
    private Integer clientId;

    @Schema(description = "Nome do Cliente", example = "Tiago Ferreira")
    @NotBlank(groups = ClientCreate.class, message = "The Name field cannot be null.")
    private String name;

    @Schema(description = "CPF do Cliente", example = "98564753475")
    @NotBlank(groups = ClientCreate.class, message = "The CPF field cannot be null.")
    private String cpf;

    @Schema(description = "Senha do Cliente", example = "1234567890")
    @NotBlank(groups = ClientCreate.class, message = "The Password field cannot be null.")
    private String password;

    @Schema(description = "Telefone do Cliente", example = "99887766")
    @NotBlank(groups = ClientCreate.class, message = "The Phone field cannot be null.")
    private String phone;

    @Schema(hidden = true)
    private StatusEnum status;

    @Null(groups = ClientCreate.class, message = "The BarberShop field must be null.")
    @JsonIgnoreProperties({"items", "employees", "clients"})
    @Valid
    private List<BarberShopRequest> barberShops;

    @Schema(description = "Agendamentos do Cliente")
    @Null(message = "The Scheduling field must be null.")
    @JsonIgnoreProperties({"barberShop", "items", "employees", "clients"})
    @Valid
    private List<SchedulingRequest> schedulings;

}