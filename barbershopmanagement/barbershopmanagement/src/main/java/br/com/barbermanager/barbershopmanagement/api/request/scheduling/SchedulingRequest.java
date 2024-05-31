package br.com.barbermanager.barbershopmanagement.api.request.scheduling;

import br.com.barbermanager.barbershopmanagement.api.request.barbershop.BarberShopRequest;
import br.com.barbermanager.barbershopmanagement.api.request.client.ClientRequest;
import br.com.barbermanager.barbershopmanagement.api.request.employee.EmployeeRequest;
import br.com.barbermanager.barbershopmanagement.api.request.item.ItemRequest;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SchedulingRequest {

    private Integer schedulingId;

    private ClientRequest client;
    private BarberShopRequest barberShop;
    private EmployeeRequest employee;
    private ItemRequest item;

    private LocalDateTime schedulingTime;

    public Integer getSchedulingId() {
        return schedulingId;
    }

    public void setSchedulingId(Integer schedulingId) {
        this.schedulingId = schedulingId;
    }

    public ClientRequest getClient() {
        return client;
    }

    public void setClient(ClientRequest client) {
        this.client = client;
    }

    public BarberShopRequest getBarberShop() {
        return barberShop;
    }

    public void setBarberShop(BarberShopRequest barberShop) {
        this.barberShop = barberShop;
    }

    public EmployeeRequest getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeRequest employee) {
        this.employee = employee;
    }

    public ItemRequest getItem() {
        return item;
    }

    public void setItem(ItemRequest item) {
        this.item = item;
    }

    public LocalDateTime getSchedulingTime() {
        return schedulingTime;
    }

    public void setSchedulingTime(LocalDateTime schedulingTime) {
        this.schedulingTime = schedulingTime;
    }
}
