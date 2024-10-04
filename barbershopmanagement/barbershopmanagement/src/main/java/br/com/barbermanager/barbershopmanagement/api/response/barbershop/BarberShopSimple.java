package br.com.barbermanager.barbershopmanagement.api.response.barbershop;

import br.com.barbermanager.barbershopmanagement.domain.model.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BarberShopSimple {

    private Integer barberShopId;
    private String name;
    private String phone;
    private String adress;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private StatusEnum status;

}