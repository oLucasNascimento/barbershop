package br.com.barbermanager.barbershopmanagement.api.request.scheduling;

import br.com.barbermanager.barbershopmanagement.api.request.barbershop.BarberShopRequest;
import br.com.barbermanager.barbershopmanagement.api.request.client.ClientRequest;
import br.com.barbermanager.barbershopmanagement.api.request.employee.EmployeeRequest;
import br.com.barbermanager.barbershopmanagement.api.request.item.ItemRequest;
import br.com.barbermanager.barbershopmanagement.domain.model.StatusEnum;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

public class SchedulingRequest {

    private Integer schedulingId;

    private ClientRequest client;
    private BarberShopRequest barberShop;
    private EmployeeRequest employee;
    private List<ItemRequest> items;

    private LocalDateTime schedulingTime;
    private StatusEnum status;

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

    public List<ItemRequest> getItems() {
        return items;
    }

    public void setItems(List<ItemRequest> items) {
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
