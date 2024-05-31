package br.com.barbermanager.barbershopmanagement.api.response.client;

import br.com.barbermanager.barbershopmanagement.api.response.barbershop.BarberShopSimple;

import java.util.Set;

public class ClientResponse {

    private Integer clientId;
    private String name;
    private String phone;

    private Set<BarberShopSimple> barberShops;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Set<BarberShopSimple> getBarberShops() {
        return barberShops;
    }

    public void setBarberShops(Set<BarberShopSimple> barberShops) {
        this.barberShops = barberShops;
    }
}
