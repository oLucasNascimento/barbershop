package br.com.barbermanager.barbershopmanagement.api.request.barbershop;

import br.com.barbermanager.barbershopmanagement.api.request.client.ClientRequest;
import br.com.barbermanager.barbershopmanagement.api.request.employee.EmployeeRequest;
import br.com.barbermanager.barbershopmanagement.api.request.item.ItemRequest;
import br.com.barbermanager.barbershopmanagement.domain.model.Client;
import br.com.barbermanager.barbershopmanagement.domain.model.Employee;
import br.com.barbermanager.barbershopmanagement.domain.model.Item;
import java.util.List;
import java.util.Set;

public class BarberShopRequest {

    private Integer barberShopId;

    private String name;
    private String zipCode;
    private String adress;
    private String email;
    private String phone;

    private List<ItemRequest> items;
    private List<EmployeeRequest> employees;
    private Set<ClientRequest> clients;

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

    public List<ItemRequest> getItems() {
        return items;
    }

    public void setItems(List<ItemRequest> items) {
        this.items = items;
    }

    public List<EmployeeRequest> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeRequest> employees) {
        this.employees = employees;
    }

    public Set<ClientRequest> getClients() {
        return clients;
    }

    public void setClients(Set<ClientRequest> clients) {
        this.clients = clients;
    }
}
