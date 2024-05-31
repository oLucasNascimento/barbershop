package br.com.barbermanager.barbershopmanagement.api.response.scheduling;

import br.com.barbermanager.barbershopmanagement.api.response.barbershop.BarberShopSimple;
import br.com.barbermanager.barbershopmanagement.api.response.client.ClientSimple;
import br.com.barbermanager.barbershopmanagement.api.response.employee.EmployeeSimple;
import br.com.barbermanager.barbershopmanagement.api.response.item.ItemSimple;
import br.com.barbermanager.barbershopmanagement.domain.service.employee.EmployeeService;

import java.time.LocalDateTime;

public class SchedulingResponse {

    private Integer schedulingId;

    private ClientSimple client;
    private BarberShopSimple barberShop;
    private EmployeeSimple employee;
    private ItemSimple item;

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

    public ItemSimple getItem() {
        return item;
    }

    public void setItem(ItemSimple item) {
        this.item = item;
    }

    public LocalDateTime getSchedulingTime() {
        return schedulingTime;
    }

    public void setSchedulingTime(LocalDateTime schedulingTime) {
        this.schedulingTime = schedulingTime;
    }
}
