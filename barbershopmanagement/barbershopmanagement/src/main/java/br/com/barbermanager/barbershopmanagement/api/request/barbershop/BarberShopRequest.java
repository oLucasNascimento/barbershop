package br.com.barbermanager.barbershopmanagement.api.request.barbershop;

import br.com.barbermanager.barbershopmanagement.api.request.client.ClientRequest;
import br.com.barbermanager.barbershopmanagement.api.request.employee.EmployeeRequest;
import br.com.barbermanager.barbershopmanagement.api.request.item.ItemRequest;
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

import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BarberShopRequest {

    @Schema(hidden = true)
    @Null(groups = BarberShopCreate.class, message = "{barber.id.null}")
    @NotNull(groups = {SchedulingCreate.class, SchedulingUpdate.class, ClientUpdate.class, EmployeeCreate.class, ItemUpdate.class, ItemCreate.class}, message = "{barber.id.not.null}")
    private Integer barberShopId;

    @Schema(description = "BarberShop's Name", example = "Elite Cuts Barbershop")
    @NotBlank(groups = BarberShopCreate.class, message = "{barber.name.not.blank}")
    private String name;

    @Schema(description = "BarberShop's Zip Code", example = "10001")
    @NotBlank(groups = BarberShopCreate.class, message = "{barber.zip.code.not.blank}")
    private String zipCode;

    @Schema(description = "BarberShop's Address", example = "123 Main Street, Brooklyn, NY")
    @NotBlank(groups = BarberShopCreate.class, message = "{barber.address.not.blank}")
    private String address;

    @Schema(description = "BarberShop's Email", example = "contact@elitecuts.com")
    @Email
    @NotBlank(groups = BarberShopCreate.class, message = "{barber.email.not.blank}")
    private String email;

    @Schema(description = "BarberShop's Password", example = "securePass123!")
    @NotBlank(groups = BarberShopCreate.class, message = "{barber.password.not.blank}")
    private String password;

    @Schema(description = "BarberShop's Phone", example = "+1 (555) 123-4567")
    @NotBlank(groups = BarberShopCreate.class, message = "{barber.phone.not.blank}")
    private String phone;

    @Schema(description = "BarberShop's Opening Time", example = "09:00:00")
    @NotNull(groups = BarberShopCreate.class, message = "{barber.opening.time.not.null}")
    private LocalTime openingTime;

    @Schema(description = "BarberShop's Closing Time", example = "20:00:00")
    @NotNull(groups = BarberShopCreate.class, message = "{barber.closing.time.not.null}")
    private LocalTime closingTime;

    @Schema(hidden = true)
    private StatusEnum status;

    @Schema(description = "BarberShop's Items")
    @Null(groups = {BarberShopCreate.class, ClientInBarberShop.class}, message = "{barber.item.null}")
    @JsonIgnoreProperties({"barberShop", "schedulings"})
    @Valid
    private List<ItemRequest> items;

    @Schema(description = "BarberShop's Employees")
    @Null(groups = {BarberShopCreate.class, ClientInBarberShop.class}, message = "{barber.employee.null}")
    @JsonIgnoreProperties("barberShop")
    @Valid
    private List<EmployeeRequest> employees;

    @Schema(description = "BarberShop's Clients")
    @Null(groups = BarberShopCreate.class, message = "{barber.client.null}")
    @NotNull(groups = ClientInBarberShop.class, message = "{barber.client.not.null}")
    @JsonIgnoreProperties({"barberShops", "schedulings"})
    @Valid
    private List<ClientRequest> clients;

}