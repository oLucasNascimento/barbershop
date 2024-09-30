package br.com.barbermanager.barbershopmanagement.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
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
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer itemId;

    private String name;
    private Double price;
    private Integer time;

    private StatusEnum status;

    @ManyToOne
    @JoinColumn(name = "fk_barberShop")
    @JsonIgnoreProperties("items")
    private BarberShop barberShop;

    @ManyToMany(mappedBy = "items")
    @JsonIgnoreProperties("items")
    private List<Scheduling> schedulings;

}
