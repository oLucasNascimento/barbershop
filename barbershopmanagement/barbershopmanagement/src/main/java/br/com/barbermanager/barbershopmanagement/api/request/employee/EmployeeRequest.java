package br.com.barbermanager.barbershopmanagement.api.request.employee;

import br.com.barbermanager.barbershopmanagement.api.request.barbershop.BarberShopRequest;
import br.com.barbermanager.barbershopmanagement.domain.model.StatusEnum;
import br.com.barbermanager.barbershopmanagement.domain.model.validations.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(hidden = true)
    @Null(groups = EmployeeCreate.class, message = "The Employee ID field must be null.")
    @NotNull(groups = {SchedulingCreate.class, SchedulingUpdate.class, BarberShopUpdate.class}, message = "The Employee ID field cannot be null.")
    private Integer employeeId;

    @Schema(description = "Nome do Funcionário", example = "Igor Silva")
    @NotBlank(groups = {EmployeeCreate.class, EmployeeUpdate.class}, message = "The Name field cannot be null.")
    private String name;

    @Schema(description = "CPF do Funcionário", example = "8978568795")
    @NotBlank(groups = {EmployeeCreate.class, EmployeeUpdate.class}, message = "The CPF field cannot be null.")
    private String cpf;

    @Schema(description = "Senha do Funcionário", example = "1234")
    @NotBlank(groups = {EmployeeCreate.class, EmployeeUpdate.class}, message = "The Password field cannot be null.")
    private String password;

    @Schema(description = "Telefone do Funcionário", example = "11223344")
    @NotBlank(groups = {EmployeeCreate.class, EmployeeUpdate.class}, message = "The Phone field cannot be null.")
    private String phone;

    @Schema(hidden = true)
    private StatusEnum status;

    @Schema(description = "Barbearia do Funcionário", example = "{\"barberShopId\":\"1\"}")
    @NotNull(groups = EmployeeCreate.class, message = "The BarberShop field cannot be null.")
    @JsonIgnoreProperties({"items", "employees", "clients"})
    @Valid
    private BarberShopRequest barberShop;

}