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
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer clientId;

    private String name;
    private String cpf;
    private String phone;

    private StatusEnum status;

    @ManyToMany(mappedBy = "clients")
    @JsonIgnoreProperties("clients")
    private Set<BarberShop> barberShops;

    @OneToMany(mappedBy = "client")
    @JsonIgnoreProperties("client")
    private List<Scheduling> schedulings;

}
