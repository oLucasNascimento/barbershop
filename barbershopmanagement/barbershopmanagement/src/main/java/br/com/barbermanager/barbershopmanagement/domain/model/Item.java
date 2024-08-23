package br.com.barbermanager.barbershopmanagement.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

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

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public BarberShop getBarberShop() {
        return barberShop;
    }

    public void setBarberShop(BarberShop barberShop) {
        this.barberShop = barberShop;
    }

    public List<Scheduling> getSchedulings() {
        return schedulings;
    }

    public void setSchedulings(List<Scheduling> schedulings) {
        this.schedulings = schedulings;
    }
}
