package br.com.barbermanager.barbershopmanagement.api.request.employee;

import br.com.barbermanager.barbershopmanagement.domain.model.BarberShop;

public class EmployeeRequest {

    private String name;
    private String cpf;
    private String email;
    private String phone;

    private BarberShop barberShop;

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

    public BarberShop getBarberShop() {
        return barberShop;
    }

    public void setBarberShop(BarberShop barberShop) {
        this.barberShop = barberShop;
    }
}
