package br.com.barbermanager.barbershopmanagement.api.response.item;

import br.com.barbermanager.barbershopmanagement.domain.model.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemSimple {

    private Integer itemId;
    private String name;
    private Double price;
    private Integer time;
    private StatusEnum status;

}