package br.com.barbermanager.barbershopmanagement.api.request.employee;

import br.com.barbermanager.barbershopmanagement.api.request.barbershop.BarberShopRequest;
import br.com.barbermanager.barbershopmanagement.domain.model.BarberShop;
import br.com.barbermanager.barbershopmanagement.domain.model.StatusEnum;
import br.com.barbermanager.barbershopmanagement.domain.model.validations.AssociatedUpdate;
import br.com.barbermanager.barbershopmanagement.domain.model.validations.EmployeeCreate;
import br.com.barbermanager.barbershopmanagement.domain.model.validations.OnCreate;
import br.com.barbermanager.barbershopmanagement.domain.model.validations.SchedulingCreate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
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
public class EmployeeRequest {

    @Null(groups = OnCreate.class)
    @NotNull(groups = {SchedulingCreate.class, AssociatedUpdate.class}, message = "The Employee ID field cannot be null.")
    private Integer employeeId;

    @NotBlank(groups = OnCreate.class, message = "The Name field cannot be null.")
    private String name;

    @NotBlank(groups = OnCreate.class, message = "The CPF field cannot be null.")
    private String cpf;

    @Email
    @NotBlank(groups = OnCreate.class, message = "The Email field cannot be null.")
    private String email;

    @NotBlank(groups = OnCreate.class, message = "The Phone field cannot be null.")
    private String phone;

    private StatusEnum status;

    @Valid
    @JsonIgnore
    @NotNull(groups = EmployeeCreate.class, message = "The Barber Shop field cannot be null.")
    private BarberShopRequest barberShop;

}