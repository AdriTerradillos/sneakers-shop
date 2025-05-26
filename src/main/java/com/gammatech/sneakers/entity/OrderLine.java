package com.gammatech.sneakers.entity;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class OrderLine {

    private String sneakerId;

    private double price;

    private int quantity;

    @ManyToOne
    @JoinColumn(name="order_id", nullable=false)
    private Order order;

    public OrderLine() {
    }

    public OrderLine(String sneakerId, double price, int quantity) {
        this.sneakerId = sneakerId;
        this.price = price;
        this.quantity = quantity;
    }

    public String getSneakerId() {
        return sneakerId;
    }

    public void setSneakerId(String sneakerId) {
        this.sneakerId = sneakerId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
