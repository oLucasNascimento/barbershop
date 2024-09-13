package br.com.barbermanager.barbershopmanagement.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;

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

    public Integer getBarberShopId() {
        return barberShopId;
    }

    public void setBarberShopId(Integer barberShopId) {
        this.barberShopId = barberShopId;
    }

    public String getName() {
        return name;
    }

    public void setName(String nome) {
        this.name = nome;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String cep) {
        this.zipCode = cep;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String endereco) {
        this.adress = endereco;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalTime getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(LocalTime openingTime) {
        this.openingTime = openingTime;
    }

    public LocalTime getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(LocalTime closingTime) {
        this.closingTime = closingTime;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public List<Scheduling> getSchedulings() {
        return schedulings;
    }

    public void setSchedulings(List<Scheduling> schedulings) {
        this.schedulings = schedulings;
    }
}
