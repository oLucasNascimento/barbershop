package br.com.barbermanager.barbershopmanagement.api.request.scheduling;

import br.com.barbermanager.barbershopmanagement.api.request.barbershop.BarberShopRequest;
import br.com.barbermanager.barbershopmanagement.api.request.client.ClientRequest;
import br.com.barbermanager.barbershopmanagement.api.request.employee.EmployeeRequest;
import br.com.barbermanager.barbershopmanagement.api.request.item.ItemRequest;
import br.com.barbermanager.barbershopmanagement.domain.model.StatusEnum;
import br.com.barbermanager.barbershopmanagement.domain.model.validations.SchedulingCreate;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(hidden = true)
    @Null(groups = SchedulingCreate.class, message = "{scheduling.id.null}")
    private Integer schedulingId;

    @Schema(description = "Client", example = "{\"clientId\":\"1\"}")
    @Valid
    @JsonIgnoreProperties({"barberShops", "schedulings"})
    @NotNull(groups = SchedulingCreate.class, message = "{scheduling.client.not.null}")
    private ClientRequest client;

    @Schema(description = "BarberShop", example = "{\"barberShopId\":\"1\"}")
    @Valid
    @JsonIgnoreProperties({"items", "employees", "clients"})
    @NotNull(groups = SchedulingCreate.class, message = "{scheduling.barbershop.not.null}")
    private BarberShopRequest barberShop;

    @Schema(description = "Employee", example = "{\"employeeId\":\"1\"}")
    @Valid
    @JsonIgnoreProperties("barberShop")
    @NotNull(groups = SchedulingCreate.class, message = "{scheduling.employee.not.null}")
    private EmployeeRequest employee;

    @Schema(description = "Services", example = "[{\"itemId\":\"1\"}]")
    @Valid
    @JsonIgnoreProperties("barberShop")
    @NotNull(groups = SchedulingCreate.class, message = "{scheduling.item.not.null}")
    private List<ItemRequest> items;

    @Schema(description = "Scheduling Time", example = "2024-12-20T17:00:00Z")
    @Valid
    @NotNull(groups = SchedulingCreate.class, message = "{scheduling.scheduling.time.not.null}")
    private LocalDateTime schedulingTime;

    @Schema(hidden = true)
    private StatusEnum status;

}