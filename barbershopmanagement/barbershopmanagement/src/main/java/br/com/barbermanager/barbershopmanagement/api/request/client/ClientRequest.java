package br.com.barbermanager.barbershopmanagement.api.request.client;

import br.com.barbermanager.barbershopmanagement.api.request.barbershop.BarberShopRequest;
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
public class ClientRequest {

    private Integer clientId;

    private String name;
    private String cpf;
    private String phone;
    private StatusEnum status;

    private List<BarberShopRequest> barberShops;

}
