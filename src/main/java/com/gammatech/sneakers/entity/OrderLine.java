package com.gammatech.sneakers.entity;

import jakarta.persistence.*;

@Entity
public class OrderLine {

    @EmbeddedId
    private OrderLineId id;

    private double price;

    private int quantity;

    @MapsId("orderId")
    @ManyToOne
    @JoinColumn(name="order_id", nullable=false)
    private Order order;

    public OrderLine() {
    }

    public OrderLine(Order order, String sneakerId, double price, int quantity) {
        this.id = new OrderLineId(order.getId(), sneakerId);
        this.price = price;
        this.quantity = quantity;
    }

    public OrderLineId getId() {
        return id;
    }

    public void setId(OrderLineId id) {
        this.id = id;
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
