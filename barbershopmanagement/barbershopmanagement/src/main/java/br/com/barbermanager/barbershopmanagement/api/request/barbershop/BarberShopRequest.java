package br.com.barbermanager.barbershopmanagement.api.request.barbershop;

import br.com.barbermanager.barbershopmanagement.api.request.client.ClientRequest;
import br.com.barbermanager.barbershopmanagement.api.request.employee.EmployeeRequest;
import br.com.barbermanager.barbershopmanagement.api.request.item.ItemRequest;
import br.com.barbermanager.barbershopmanagement.api.request.scheduling.SchedulingRequest;
import br.com.barbermanager.barbershopmanagement.domain.model.*;
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

    private Integer barberShopId;

    private String name;
    private String zipCode;
    private String adress;
    private String email;
    private String phone;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private StatusEnum status;

    private List<ItemRequest> items;
    private List<EmployeeRequest> employees;
    private List<ClientRequest> clients;
    private List<SchedulingRequest> schedulings;

}
