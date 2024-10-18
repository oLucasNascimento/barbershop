package br.com.barbermanager.barbershopmanagement.api.request.barbershop;

import br.com.barbermanager.barbershopmanagement.api.request.client.ClientRequest;
import br.com.barbermanager.barbershopmanagement.api.request.employee.EmployeeRequest;
import br.com.barbermanager.barbershopmanagement.api.request.item.ItemRequest;
import br.com.barbermanager.barbershopmanagement.api.request.scheduling.SchedulingRequest;
import br.com.barbermanager.barbershopmanagement.domain.model.*;
import br.com.barbermanager.barbershopmanagement.domain.model.validations.OnCreate;
import br.com.barbermanager.barbershopmanagement.domain.model.validations.SchedulingCreate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BarberShopRequest {

    @Null(groups = OnCreate.class)
    @NotNull(groups = SchedulingCreate.class, message = "The BarberShop Id field cannot be null.")
    private Integer barberShopId;

    @NotBlank(groups = OnCreate.class, message = "The Name field cannot be null.")
    private String name;

    @NotBlank(groups = OnCreate.class, message = "The ZipCode field cannot be null.")
    private String zipCode;

    @NotBlank(groups = OnCreate.class, message = "The Adress field cannot be null.")
    private String adress;

    @NotBlank(groups = OnCreate.class, message = "The Email field cannot be null.")
    private String email;

    @NotBlank(groups = OnCreate.class, message = "The Phone field cannot be null.")
    private String phone;

    @NotNull(groups = OnCreate.class, message = "The Opening Time field cannot be null.")
    private LocalTime openingTime;

    @NotNull(groups = OnCreate.class, message = "The Closing Time field cannot be null.")
    private LocalTime closingTime;

    private StatusEnum status;

    private List<ItemRequest> items;
    private List<EmployeeRequest> employees;
    private List<ClientRequest> clients;
    private List<SchedulingRequest> schedulings;

}
