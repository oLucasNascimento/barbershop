package br.com.barbermanager.barbershopmanagement.api.request.employee;

import br.com.barbermanager.barbershopmanagement.api.request.barbershop.BarberShopRequest;
import br.com.barbermanager.barbershopmanagement.domain.model.BarberShop;
import br.com.barbermanager.barbershopmanagement.domain.model.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequest {

    private Integer employeeId;

    private String name;
    private String cpf;
    private String email;
    private String phone;
    private StatusEnum status;

    private BarberShopRequest barberShop;

}
