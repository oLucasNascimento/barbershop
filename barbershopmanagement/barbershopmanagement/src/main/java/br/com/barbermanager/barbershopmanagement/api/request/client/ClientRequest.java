package br.com.barbermanager.barbershopmanagement.api.request.client;

import br.com.barbermanager.barbershopmanagement.api.request.barbershop.BarberShopRequest;

import java.util.Set;

public class ClientRequest {

    private Integer clientId;

    private String name;
    private String cpf;
    private String phone;

    private Set<BarberShopRequest> barberShops;

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Set<BarberShopRequest> getBarberShops() {
        return barberShops;
    }

    public void setBarberShops(Set<BarberShopRequest> barberShops) {
        this.barberShops = barberShops;
    }
}
