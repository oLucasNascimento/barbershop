package br.com.barbermanager.barbershopmanagement.api.request.employee;

import br.com.barbermanager.barbershopmanagement.api.request.barbershop.BarberShopRequest;
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

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequest {

    @Schema(hidden = true)
    @Null(groups = EmployeeCreate.class, message = "{employee.id.null}")
    @NotNull(groups = {SchedulingCreate.class, SchedulingUpdate.class, BarberShopUpdate.class}, message = "{employee.id.not.null}")
    private Integer employeeId;

    @Schema(description = "Employee's Name", example = "Maria Johnson")
    @NotBlank(groups = {EmployeeCreate.class, EmployeeUpdate.class}, message = "{employee.name.not.null}")
    private String name;

    @Schema(description = "Employee's CPF", example = "987.654.321-00")
    @NotBlank(groups = {EmployeeCreate.class, EmployeeUpdate.class}, message = "{employee.cpf.not.null}")
    private String cpf;

    @Schema(description = "Employee's Password", example = "mariaSecure456")
    @NotBlank(groups = {EmployeeCreate.class, EmployeeUpdate.class}, message = "{employee.password.not.null}")
    private String password;

    @Schema(description = "Employee's Phone", example = "+55 (21) 91234-5678")
    @NotBlank(groups = {EmployeeCreate.class, EmployeeUpdate.class}, message = "{employee.phone.not.null}")
    private String phone;

    @Schema(hidden = true)
    private StatusEnum status;

    @Schema(description = "Employee's BarberShop", example = "{\"barberShopId\":\"1\"}")
    @NotNull(groups = EmployeeCreate.class, message = "{employee.barbershop.not.null}")
    @JsonIgnoreProperties({"items", "employees", "clients"})
    @Valid
    private BarberShopRequest barberShop;

}