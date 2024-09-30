package br.com.barbermanager.barbershopmanagement.api.response.barbershop;

import br.com.barbermanager.barbershopmanagement.api.response.client.ClientSimple;
import br.com.barbermanager.barbershopmanagement.api.response.employee.EmployeeSimple;
import br.com.barbermanager.barbershopmanagement.api.response.item.ItemSimple;
import br.com.barbermanager.barbershopmanagement.api.response.scheduling.SchedulingResponse;
import br.com.barbermanager.barbershopmanagement.domain.model.Client;
import br.com.barbermanager.barbershopmanagement.domain.model.Scheduling;
import br.com.barbermanager.barbershopmanagement.domain.model.StatusEnum;
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
public class BarberShopResponse {

    private Integer barberShopId;
    private String name;
    private String zipCode;
    private String adress;
    private String email;
    private String phone;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private StatusEnum status;

    private List<ItemSimple> items;
    private List<EmployeeSimple> employees;
    private List<ClientSimple> clients;
    private List<SchedulingResponse> schedulings;

}