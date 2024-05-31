package br.com.barbermanager.barbershopmanagement.api.response.barbershop;

import br.com.barbermanager.barbershopmanagement.api.response.client.ClientSimple;
import br.com.barbermanager.barbershopmanagement.api.response.employee.EmployeeSimple;
import br.com.barbermanager.barbershopmanagement.api.response.item.ItemSimple;
import br.com.barbermanager.barbershopmanagement.domain.model.Client;

import java.util.List;
import java.util.Set;

public class BarberShopResponse {

    private Integer barberShopId;
    private String name;
    private String phone;

    private List<ItemSimple> items;
    private List<EmployeeSimple> employees;
    private Set<ClientSimple> clients;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public Set<ClientSimple> getClients() {
        return clients;
    }

    public void setClients(Set<ClientSimple> clients) {
        this.clients = clients;
    }
}
