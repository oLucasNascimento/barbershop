package br.com.barbermanager.barbershopmanagement.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer employeeId;

    private String name;
    private String cpf;
    private String email;
    private String phone;

    private StatusEnum status;

    @ManyToOne
    @JoinColumn(name = "fk_barberShop")
    @JsonIgnoreProperties("employees")
    private BarberShop barberShop;

    @OneToMany(mappedBy = "employee")
    @JsonIgnoreProperties("employee")
    private List<Scheduling> schedulings;

}
