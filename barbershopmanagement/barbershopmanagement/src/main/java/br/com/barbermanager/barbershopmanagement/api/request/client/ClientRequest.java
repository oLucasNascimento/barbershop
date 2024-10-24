package br.com.barbermanager.barbershopmanagement.api.request.client;

import br.com.barbermanager.barbershopmanagement.api.request.barbershop.BarberShopRequest;
import br.com.barbermanager.barbershopmanagement.api.request.scheduling.SchedulingRequest;
import br.com.barbermanager.barbershopmanagement.domain.model.StatusEnum;
import br.com.barbermanager.barbershopmanagement.domain.model.validations.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    @Null(groups = ClientCreate.class, message = "The Client ID field must be null.")
    @NotNull(groups = {SchedulingCreate.class, SchedulingUpdate.class, BarberShopUpdate.class, ClientInBarberShop.class}, message = "The Client ID field cannot be null.")
    private Integer clientId;

    @NotBlank(groups = ClientCreate.class, message = "The Name field cannot be null.")
    private String name;

    @NotBlank(groups = ClientCreate.class, message = "The CPF field cannot be null.")
    private String cpf;

    @NotBlank(groups = ClientCreate.class, message = "The Phone field cannot be null.")
    private String phone;

    private StatusEnum status;

    @Null(groups = ClientCreate.class, message = "The BarberShop field must be null.")
    @JsonIgnoreProperties({"items", "employees", "clients"})
    @Valid
    private List<BarberShopRequest> barberShops;

    @Null(message = "The Scheduling field must be null.")
    @JsonIgnoreProperties({"barberShop", "items", "employees", "clients"})
    @Valid
    private List<SchedulingRequest> schedulings;

}