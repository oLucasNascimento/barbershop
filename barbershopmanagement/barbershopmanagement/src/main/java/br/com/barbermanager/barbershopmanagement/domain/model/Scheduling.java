package br.com.barbermanager.barbershopmanagement.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Scheduling {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer schedulingId;

    @ManyToOne
    @JoinColumn(name = "fk_client")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "fk_barberShop")
    private BarberShop barberShop;

    @ManyToOne
    @JoinColumn(name = "fk_employee")
    private Employee employee;

    @ManyToMany
    @JoinTable(name = "items_schedulings",
            joinColumns = @JoinColumn(name = "fk_schedulings"),
            inverseJoinColumns = @JoinColumn(name = "fk_items"))
    @JsonIgnoreProperties("schedulings")
    private List<Item> items;

    private LocalDateTime schedulingTime;
    private StatusEnum status;

}
