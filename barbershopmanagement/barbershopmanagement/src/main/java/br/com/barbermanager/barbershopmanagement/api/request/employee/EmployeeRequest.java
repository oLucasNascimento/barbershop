package br.com.barbermanager.barbershopmanagement.api.request.employee;

import br.com.barbermanager.barbershopmanagement.api.request.barbershop.BarberShopRequest;
import br.com.barbermanager.barbershopmanagement.domain.model.StatusEnum;
import br.com.barbermanager.barbershopmanagement.domain.model.validations.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    @Null(groups = EmployeeCreate.class, message = "The Employee ID field must be null.")
    @NotNull(groups = {SchedulingCreate.class, SchedulingUpdate.class, BarberShopUpdate.class}, message = "The Employee ID field cannot be null.")
    private Integer employeeId;

    @NotBlank(groups = {EmployeeCreate.class, EmployeeUpdate.class}, message = "The Name field cannot be null.")
    private String name;

    @NotBlank(groups = {EmployeeCreate.class, EmployeeUpdate.class}, message = "The CPF field cannot be null.")
    private String cpf;

    @Email
    @NotBlank(groups = {EmployeeCreate.class, EmployeeUpdate.class}, message = "The Email field cannot be null.")
    private String email;

    @NotBlank(groups = {EmployeeCreate.class, EmployeeUpdate.class}, message = "The Phone field cannot be null.")
    private String phone;

    private StatusEnum status;

    @NotNull(groups = EmployeeCreate.class, message = "The BarberShop field cannot be null.")
    @JsonIgnoreProperties({"items", "employees", "clients"})
    @Valid
    private BarberShopRequest barberShop;

}