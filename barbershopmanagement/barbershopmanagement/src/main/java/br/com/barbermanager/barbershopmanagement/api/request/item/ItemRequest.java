package br.com.barbermanager.barbershopmanagement.api.request.item;

import br.com.barbermanager.barbershopmanagement.api.request.barbershop.BarberShopRequest;
import br.com.barbermanager.barbershopmanagement.api.request.scheduling.SchedulingRequest;
import br.com.barbermanager.barbershopmanagement.domain.model.BarberShop;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.List;
import java.util.Set;

public class ItemRequest {

    private Integer itemId;

    private String name;
    private Double price;
    private Integer time;

    private BarberShopRequest barberShop;
    private List<SchedulingRequest> schedulings;

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

    public BarberShopRequest getBarberShop() {
        return barberShop;
    }

    public void setBarberShop(BarberShopRequest barberShop) {
        this.barberShop = barberShop;
    }

    public List<SchedulingRequest> getSchedulings() {
        return schedulings;
    }

    public void setSchedulings(List<SchedulingRequest> schedulings) {
        this.schedulings = schedulings;
    }
}
