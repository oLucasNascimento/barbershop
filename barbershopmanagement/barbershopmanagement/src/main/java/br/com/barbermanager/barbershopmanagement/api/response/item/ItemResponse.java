package br.com.barbermanager.barbershopmanagement.api.response.item;

import br.com.barbermanager.barbershopmanagement.api.response.barbershop.BarberShopSimple;
import br.com.barbermanager.barbershopmanagement.api.response.scheduling.SchedulingResponse;
import br.com.barbermanager.barbershopmanagement.domain.model.Scheduling;
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
public class ItemResponse {

    private Integer itemId;
    private String name;
    private Double price;
    private Integer time;
    private StatusEnum status;

    private BarberShopSimple barberShop;
    private List<SchedulingResponse> schedulings;

}