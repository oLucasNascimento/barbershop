package br.com.barbermanager.barbershopmanagement.api.response.client;

import br.com.barbermanager.barbershopmanagement.api.response.barbershop.BarberShopSimple;
import br.com.barbermanager.barbershopmanagement.api.response.scheduling.SchedulingResponse;
import br.com.barbermanager.barbershopmanagement.domain.model.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientResponse {

    private Integer clientId;
    private String name;
    private String cpf;
    private String phone;
    private StatusEnum status;

    private List<BarberShopSimple> barberShops;
    private List<SchedulingResponse> schedulings;

}