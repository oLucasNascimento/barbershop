package br.com.barbermanager.barbershopmanagement.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

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

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public LocalDateTime getSchedulingTime() {
        return schedulingTime;
    }

    public void setSchedulingTime(LocalDateTime schedulingTime) {
        this.schedulingTime = schedulingTime;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }
}
