package br.com.barbermanager.barbershopmanagement.api.response.item;

import br.com.barbermanager.barbershopmanagement.api.response.barbershop.BarberShopSimple;

public class ItemResponse {

    private Integer itemId;
    private String name;
    private Double price;
    private Integer time;

    private BarberShopSimple barberShop;

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public BarberShopSimple getBarberShop() {
        return barberShop;
    }

    public void setBarberShop(BarberShopSimple barberShop) {
        this.barberShop = barberShop;
    }
}
