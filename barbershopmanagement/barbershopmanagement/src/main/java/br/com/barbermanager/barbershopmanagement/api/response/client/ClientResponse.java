package br.com.barbermanager.barbershopmanagement.api.response.client;

import br.com.barbermanager.barbershopmanagement.api.response.barbershop.BarberShopSimple;
import br.com.barbermanager.barbershopmanagement.api.response.scheduling.SchedulingResponse;

import java.util.List;
import java.util.Set;

public class ClientResponse {

    private Integer clientId;
    private String name;
    private String cpf;
    private String phone;

    private List<BarberShopSimple> barberShops;
    private List<SchedulingResponse> schedulings;

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

    public List<BarberShopSimple> getBarberShops() {
        return barberShops;
    }

    public void setBarberShops(List<BarberShopSimple> barberShops) {
        this.barberShops = barberShops;
    }

    public List<SchedulingResponse> getSchedulings() {
        return schedulings;
    }

    public void setSchedulings(List<SchedulingResponse> schedulings) {
        this.schedulings = schedulings;
    }
}
