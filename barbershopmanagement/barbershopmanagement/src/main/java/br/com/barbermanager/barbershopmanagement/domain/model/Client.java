package br.com.barbermanager.barbershopmanagement.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer clientId;

    private String name;
    private String cpf;
    private String phone;

    @ManyToMany(mappedBy = "clients")
    @JsonIgnoreProperties("clients")
    private Set<BarberShop> barberShops;

//    @OneToMany(mappedBy = "cliente")
//    @JsonIgnoreProperties("cliente")
//    private Set<Agendamento> agendamentos;

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Set<BarberShop> getBarberShops() {
        return barberShops;
    }

    public void setBarberShops(Set<BarberShop> barberShops) {
        this.barberShops = barberShops;
    }
}
