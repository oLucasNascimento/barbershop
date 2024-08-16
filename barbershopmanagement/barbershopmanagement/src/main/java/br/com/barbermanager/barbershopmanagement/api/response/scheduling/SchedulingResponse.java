package br.com.barbermanager.barbershopmanagement.api.response.scheduling;

import br.com.barbermanager.barbershopmanagement.api.response.barbershop.BarberShopSimple;
import br.com.barbermanager.barbershopmanagement.api.response.client.ClientSimple;
import br.com.barbermanager.barbershopmanagement.api.response.employee.EmployeeSimple;
import br.com.barbermanager.barbershopmanagement.api.response.item.ItemSimple;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class SchedulingResponse {

    private Integer schedulingId;

    private ClientSimple client;
    private BarberShopSimple barberShop;
    private EmployeeSimple employee;
    private List<ItemSimple> items;

    private LocalDateTime schedulingTime;

    public Integer getSchedulingId() {
        return schedulingId;
    }

    public void setSchedulingId(Integer schedulingId) {
        this.schedulingId = schedulingId;
    }

    public ClientSimple getClient() {
        return client;
    }

    public void setClient(ClientSimple client) {
        this.client = client;
    }

    public BarberShopSimple getBarberShop() {
        return barberShop;
    }

    public void setBarberShop(BarberShopSimple barberShop) {
        this.barberShop = barberShop;
    }

    public EmployeeSimple getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeSimple employee) {
        this.employee = employee;
    }

    public List<ItemSimple> getItems() {
        return items;
    }

    public void setItems(List<ItemSimple> items) {
        this.items = items;
    }

    public LocalDateTime getSchedulingTime() {
        return schedulingTime;
    }

    public void setSchedulingTime(LocalDateTime schedulingTime) {
        this.schedulingTime = schedulingTime;
    }
}
