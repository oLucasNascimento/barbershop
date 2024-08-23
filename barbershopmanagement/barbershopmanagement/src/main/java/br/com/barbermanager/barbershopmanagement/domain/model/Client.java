package br.com.barbermanager.barbershopmanagement.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

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

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public Set<BarberShop> getBarberShops() {
        return barberShops;
    }

    public void setBarberShops(Set<BarberShop> barberShops) {
        this.barberShops = barberShops;
    }

    public List<Scheduling> getSchedulings() {
        return schedulings;
    }

    public void setSchedulings(List<Scheduling> schedulings) {
        this.schedulings = schedulings;
    }
}
