package br.com.barbermanager.barbershopmanagement.api.request.barbershop;

import br.com.barbermanager.barbershopmanagement.api.request.client.ClientRequest;
import br.com.barbermanager.barbershopmanagement.api.request.employee.EmployeeRequest;
import br.com.barbermanager.barbershopmanagement.api.request.item.ItemRequest;
import br.com.barbermanager.barbershopmanagement.domain.model.StatusEnum;
import br.com.barbermanager.barbershopmanagement.domain.model.validations.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BarberShopRequest {

    @Schema(hidden = true)
    @Null(groups = BarberShopCreate.class, message = "The BarberShop ID field must be null.")
    @NotNull(groups = {SchedulingCreate.class, SchedulingUpdate.class, ClientUpdate.class, EmployeeCreate.class, ItemUpdate.class, ItemCreate.class}, message = "The BarberShop ID field cannot be null.")
    private Integer barberShopId;

    @Schema(description = "Nome da Barbearia", example = "Senta Que Lá Vem Corte")
    @NotBlank(groups = BarberShopCreate.class, message = "The Name field cannot be null.")
    private String name;

    @Schema(description = "CEP da Barbearia", example = "08577000")
    @NotBlank(groups = BarberShopCreate.class, message = "The ZipCode field cannot be null.")
    private String zipCode;

    @Schema(description = "Endereco da Barbearia", example = "Rua Navalha Afiada")
    @NotBlank(groups = BarberShopCreate.class, message = "The Adress field cannot be null.")
    private String adress;

    @Schema(description = "Email da Barbearia", example = "sqlvc@hair.com")
    @NotBlank(groups = BarberShopCreate.class, message = "The Email field cannot be null.")
    private String email;

    @Schema(description = "Senha da Barbearia", example = "barber1234")
    @NotBlank(groups = BarberShopCreate.class, message = "The Password field cannot be null.")
    private String password;

    @Schema(description = "Telefone da Barbearia", example = "99887766")
    @NotBlank(groups = BarberShopCreate.class, message = "The Phone field cannot be null.")
    private String phone;

    @Schema(description = "Horario de abertura da Barbearia", example = "09:00:00")
    @NotNull(groups = BarberShopCreate.class, message = "The Opening Time field cannot be null.")
    private LocalTime openingTime;

    @Schema(description = "Horario de fechamento da Barbearia", example = "20:00:00")
    @NotNull(groups = BarberShopCreate.class, message = "The Closing Time field cannot be null.")
    private LocalTime closingTime;

    @Schema(hidden = true)
    private StatusEnum status;

    @Schema(description = "Itens da Barbearia")
    @Null(groups = {BarberShopCreate.class, ClientInBarberShop.class}, message = "The Item field must be null.")
    @JsonIgnoreProperties({"barberShop", "schedulings"})
    @Valid
    private List<ItemRequest> items;

    @Schema(description = "Funcionários da Barbearia")
    @Null(groups = {BarberShopCreate.class, ClientInBarberShop.class}, message = "The Employee field must be null.")
    @JsonIgnoreProperties("barberShop")
    @Valid
    private List<EmployeeRequest> employees;

    @Schema(description = "Clientes da Barbearia")
    @Null(groups = BarberShopCreate.class, message = "The Client field must be null.")
    @NotNull(groups = ClientInBarberShop.class, message = "The Client field cannot be null.")
    @JsonIgnoreProperties({"barberShops", "schedulings"})
    @Valid
    private List<ClientRequest> clients;

}