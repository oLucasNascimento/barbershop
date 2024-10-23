package br.com.barbermanager.barbershopmanagement.api.request.scheduling;

import br.com.barbermanager.barbershopmanagement.api.request.barbershop.BarberShopRequest;
import br.com.barbermanager.barbershopmanagement.api.request.client.ClientRequest;
import br.com.barbermanager.barbershopmanagement.api.request.employee.EmployeeRequest;
import br.com.barbermanager.barbershopmanagement.api.request.item.ItemRequest;
import br.com.barbermanager.barbershopmanagement.domain.model.StatusEnum;
import br.com.barbermanager.barbershopmanagement.domain.model.validations.SchedulingCreate;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SchedulingRequest {

    @Null(groups = SchedulingCreate.class, message = "The Scheduling ID field must be null.")
    private Integer schedulingId;

    @Valid
    @JsonIgnoreProperties({"barberShops", "schedulings"})
    @NotNull(groups = SchedulingCreate.class, message = "The Client field cannot be null.")
    private ClientRequest client;

    @Valid
    @JsonIgnoreProperties({"items", "employees", "clients"})
    @NotNull(groups = SchedulingCreate.class, message = "The BarberShop field cannot be null.")
    private BarberShopRequest barberShop;

    @Valid
    @JsonIgnoreProperties("barberShop")
    @NotNull(groups = SchedulingCreate.class, message = "The Employee field cannot be null.")
    private EmployeeRequest employee;

    @Valid
    @JsonIgnoreProperties("barberShop")
    @NotNull(groups = SchedulingCreate.class, message = "The Items field cannot be null.")
    private List<ItemRequest> items;

    @Valid
    @NotNull(groups = SchedulingCreate.class, message = "The Scheduling Time field cannot be null.")
    private LocalDateTime schedulingTime;

    private StatusEnum status;

}