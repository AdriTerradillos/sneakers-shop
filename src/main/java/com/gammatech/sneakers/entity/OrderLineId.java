package com.gammatech.sneakers.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class OrderLineId implements Serializable {

    private String orderId;
    private String sneakerId;

    public OrderLineId() {}

    public OrderLineId(String orderId, String sneakerId) {
        this.orderId = orderId;
        this.sneakerId = sneakerId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSneakerId() {
        return sneakerId;
    }

    public void setSneakerId(String sneakerId) {
        this.sneakerId = sneakerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderLineId that)) return false;
        return Objects.equals(orderId, that.orderId) &&
                Objects.equals(sneakerId, that.sneakerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, sneakerId);
    }
}
