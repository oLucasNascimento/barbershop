package br.com.barbermanager.barbershopmanagement.api.response.employee;

import br.com.barbermanager.barbershopmanagement.api.response.barbershop.BarberShopSimple;

public class EmployeeResponse {

    private Integer employeeId;
    private String name;
    private String phone;
    private BarberShopSimple barberShop;

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
}
