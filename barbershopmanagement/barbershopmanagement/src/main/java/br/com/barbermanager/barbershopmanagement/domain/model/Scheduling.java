package br.com.barbermanager.barbershopmanagement.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Scheduling {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer schedulingId;

    @ManyToOne()
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
