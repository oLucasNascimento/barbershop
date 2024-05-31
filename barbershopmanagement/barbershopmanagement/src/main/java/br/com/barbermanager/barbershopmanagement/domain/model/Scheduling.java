package br.com.barbermanager.barbershopmanagement.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
public class Scheduling {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer schedulingId;

    @ManyToOne
    @JoinColumn(name = "fk_client")
    @JsonIgnoreProperties("schedulings")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "fk_barbershop")
    @JsonIgnoreProperties("schedulings")
    private BarberShop barberShop;

    @ManyToOne
    @JoinColumn(name = "fk_employee")
    @JsonIgnoreProperties("schedulings")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "fk_item")
    @JsonIgnoreProperties("schedulings")
    private Item item;

    private LocalDateTime schedulingTime;

    public Integer getSchedulingId() {
        return schedulingId;
    }

    public void setSchedulingId(Integer schedulingId) {
        this.schedulingId = schedulingId;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public BarberShop getBarberShop() {
        return barberShop;
    }

    public void setBarberShop(BarberShop barberShop) {
        this.barberShop = barberShop;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public LocalDateTime getSchedulingTime() {
        return schedulingTime;
    }

    public void setSchedulingTime(LocalDateTime schedulingTime) {
        this.schedulingTime = schedulingTime;
    }
}
