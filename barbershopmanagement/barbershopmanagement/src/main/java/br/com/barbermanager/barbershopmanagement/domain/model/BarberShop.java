package br.com.barbermanager.barbershopmanagement.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class BarberShop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer barberShopId;

    private String name;
    private String zipCode;
    private String adress;
    private String email;
    private String phone;

    private LocalTime openingTime;
    private LocalTime closingTime;

    private StatusEnum status;

    @OneToMany(mappedBy = "barberShop")
    @JsonIgnoreProperties("barberShop")
    private List<Item> items;

    @OneToMany(mappedBy = "barberShop")
    @JsonIgnoreProperties("barberShop")
    private List<Employee> employees;

    @ManyToMany
    @JoinTable(name = "barberShops_clients",
            joinColumns = @JoinColumn(name = "fk_barberShops"),
            inverseJoinColumns = @JoinColumn(name = "fk_clients"))
    @JsonIgnoreProperties("barberShops")
    private List<Client> clients;

    @OneToMany(mappedBy = "barberShop")
    @JsonIgnoreProperties("barberShop")
    private List<Scheduling> schedulings;

}