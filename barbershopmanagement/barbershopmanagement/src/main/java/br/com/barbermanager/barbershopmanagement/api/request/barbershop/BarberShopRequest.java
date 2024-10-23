package br.com.barbermanager.barbershopmanagement.api.request.barbershop;

import br.com.barbermanager.barbershopmanagement.api.request.client.ClientRequest;
import br.com.barbermanager.barbershopmanagement.api.request.employee.EmployeeRequest;
import br.com.barbermanager.barbershopmanagement.api.request.item.ItemRequest;
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

import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BarberShopRequest {

    @Null(groups = {ClientCreate.class, BarberShopCreate.class}, message = "The BarberShop ID field must be null.")
    @NotNull(groups = {SchedulingCreate.class, SchedulingUpdate.class, ClientUpdate.class, EmployeeCreate.class, ItemUpdate.class, ItemCreate.class}, message = "The BarberShop ID field cannot be null.")
    private Integer barberShopId;

    @NotBlank(groups = BarberShopCreate.class, message = "The Name field cannot be null.")
    private String name;

    @NotBlank(groups = BarberShopCreate.class, message = "The ZipCode field cannot be null.")
    private String zipCode;

    @NotBlank(groups = BarberShopCreate.class, message = "The Adress field cannot be null.")
    private String adress;

    @NotBlank(groups = BarberShopCreate.class, message = "The Email field cannot be null.")
    private String email;

    @NotBlank(groups = BarberShopCreate.class, message = "The Phone field cannot be null.")
    private String phone;

    @NotNull(groups = BarberShopCreate.class, message = "The Opening Time field cannot be null.")
    private LocalTime openingTime;

    @NotNull(groups = BarberShopCreate.class, message = "The Closing Time field cannot be null.")
    private LocalTime closingTime;

    private StatusEnum status;

    @Null(groups = {BarberShopCreate.class, ClientInBarberShop.class}, message = "The Item field must be null.")
    @JsonIgnoreProperties({"barberShop", "schedulings"})
    @Valid
    private List<ItemRequest> items;

    @Null(groups = {BarberShopCreate.class, ClientInBarberShop.class}, message = "The Employee field must be null.")
    @JsonIgnoreProperties("barberShop")
    @Valid
    private List<EmployeeRequest> employees;

    @Null(groups = BarberShopCreate.class, message = "The Client field must be null.")
    @NotNull(groups = ClientInBarberShop.class, message = "The Client field cannot be null.")
    @JsonIgnoreProperties({"barberShops", "schedulings"})
    @Valid
    private List<ClientRequest> clients;

}