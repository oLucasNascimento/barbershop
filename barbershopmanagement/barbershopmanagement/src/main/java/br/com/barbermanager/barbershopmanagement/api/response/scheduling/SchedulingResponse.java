package br.com.barbermanager.barbershopmanagement.api.response.scheduling;

import br.com.barbermanager.barbershopmanagement.api.response.barbershop.BarberShopSimple;
import br.com.barbermanager.barbershopmanagement.api.response.client.ClientSimple;
import br.com.barbermanager.barbershopmanagement.api.response.employee.EmployeeSimple;
import br.com.barbermanager.barbershopmanagement.api.response.item.ItemSimple;
import br.com.barbermanager.barbershopmanagement.domain.model.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SchedulingResponse {

    private Integer schedulingId;

    private ClientSimple client;
    private BarberShopSimple barberShop;
    private EmployeeSimple employee;
    private List<ItemSimple> items;

    private LocalDateTime schedulingTime;
    private StatusEnum status;

}