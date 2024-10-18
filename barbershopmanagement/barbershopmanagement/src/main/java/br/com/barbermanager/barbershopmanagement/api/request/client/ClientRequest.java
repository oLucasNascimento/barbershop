package br.com.barbermanager.barbershopmanagement.api.request.client;

import br.com.barbermanager.barbershopmanagement.api.request.barbershop.BarberShopRequest;
import br.com.barbermanager.barbershopmanagement.api.request.scheduling.SchedulingRequest;
import br.com.barbermanager.barbershopmanagement.domain.model.StatusEnum;
import br.com.barbermanager.barbershopmanagement.domain.model.validations.OnCreate;
import br.com.barbermanager.barbershopmanagement.domain.model.validations.SchedulingCreate;
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

    @Null(groups = OnCreate.class)
    @NotNull(groups = SchedulingCreate.class, message = "The Client ID field cannot be null.")
    private Integer clientId;

    @NotBlank(groups = OnCreate.class, message = "The Name field cannot be null.")
    private String name;

    @NotBlank(groups = OnCreate.class, message = "The CPF field cannot be null.")
    private String cpf;

    @NotBlank(groups = OnCreate.class, message = "The Phone field cannot be null.")
    private String phone;

    private StatusEnum status;

    private List<BarberShopRequest> barberShops;
    private List<SchedulingRequest> schedulings;

}