package br.com.barbermanager.barbershopmanagement.api.response.barbershop;

import br.com.barbermanager.barbershopmanagement.api.response.client.ClientSimple;
import br.com.barbermanager.barbershopmanagement.api.response.employee.EmployeeSimple;
import br.com.barbermanager.barbershopmanagement.api.response.item.ItemSimple;
import br.com.barbermanager.barbershopmanagement.api.response.scheduling.SchedulingResponse;
import br.com.barbermanager.barbershopmanagement.domain.model.Client;
import br.com.barbermanager.barbershopmanagement.domain.model.Scheduling;
import br.com.barbermanager.barbershopmanagement.domain.model.StatusEnum;

import java.util.List;
import java.util.Set;

public class BarberShopResponse {

    private Integer barberShopId;
    private String name;
    private String zipCode;
    private String adress;
    private String email;
    private String phone;
    private StatusEnum status;

    private List<ItemSimple> items;
    private List<EmployeeSimple> employees;
    private List<ClientSimple> clients;
    private List<SchedulingResponse> schedulings;

    public Integer getBarberShopId() {
        return barberShopId;
    }

    public void setBarberShopId(Integer barberShopId) {
        this.barberShopId = barberShopId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
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

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public List<ItemSimple> getItems() {
        return items;
    }

    public void setItems(List<ItemSimple> items) {
        this.items = items;
    }

    public List<EmployeeSimple> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeSimple> employees) {
        this.employees = employees;
    }

    public List<ClientSimple> getClients() {
        return clients;
    }

    public void setClients(List<ClientSimple> clients) {
        this.clients = clients;
    }

    public List<SchedulingResponse> getSchedulings() {
        return schedulings;
    }

    public void setSchedulings(List<SchedulingResponse> schedulings) {
        this.schedulings = schedulings;
    }
}
