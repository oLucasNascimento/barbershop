package br.com.barbermanager.barbershopmanagement.api.response.item;

import br.com.barbermanager.barbershopmanagement.domain.model.StatusEnum;

public class ItemSimple {

    private Integer itemId;
    private String name;
    private Double price;
    private Integer time;
    private StatusEnum status;

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

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }
}
