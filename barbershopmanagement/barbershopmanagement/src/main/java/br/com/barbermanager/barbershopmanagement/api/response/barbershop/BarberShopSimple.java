package br.com.barbermanager.barbershopmanagement.api.response.barbershop;

import br.com.barbermanager.barbershopmanagement.domain.model.StatusEnum;

public class BarberShopSimple {

    private Integer barberShopId;
    private String name;
    private String phone;
    private String adress;
    private StatusEnum status;

    public Integer getBarberShopId() {
        return barberShopId;
    }

    public void setBarberShopId(Integer barberShopId) {
        this.barberShopId = barberShopId;
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

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }
}
