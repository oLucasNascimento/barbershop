package br.com.barbermanager.barbershopmanagement.api.response.employee;

import br.com.barbermanager.barbershopmanagement.api.response.barbershop.BarberShopSimple;
import br.com.barbermanager.barbershopmanagement.api.response.scheduling.SchedulingResponse;

import java.util.List;

public class EmployeeResponse {

    private Integer employeeId;
    private String name;
    private String cpf;
    private String email;
    private String phone;

    private BarberShopSimple barberShop;
    private List<SchedulingResponse> schedulings;

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
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

    public BarberShopSimple getBarberShop() {
        return barberShop;
    }

    public void setBarberShop(BarberShopSimple barberShop) {
        this.barberShop = barberShop;
    }

    public List<SchedulingResponse> getSchedulings() {
        return schedulings;
    }

    public void setSchedulings(List<SchedulingResponse> schedulings) {
        this.schedulings = schedulings;
    }
}
