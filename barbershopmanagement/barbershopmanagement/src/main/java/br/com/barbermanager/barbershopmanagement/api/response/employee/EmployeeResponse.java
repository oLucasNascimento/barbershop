package br.com.barbermanager.barbershopmanagement.api.response.employee;

import br.com.barbermanager.barbershopmanagement.api.response.barbershop.BarberShopSimple;
import br.com.barbermanager.barbershopmanagement.api.response.scheduling.SchedulingResponse;
import br.com.barbermanager.barbershopmanagement.domain.model.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponse {

    private Integer employeeId;
    private String name;
    private String cpf;
    private String email;
    private String phone;
    private StatusEnum status;

    private BarberShopSimple barberShop;
    private List<SchedulingResponse> schedulings;

}