package com.gammatech.sneakers.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "order_header")
public class Order {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private String id;
    private String customerId;
   private double totalAmount;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
   private List<OrderLine> orderLines;

    public Order() {
    }

    public Order(String id, String customerId,  List<OrderLine> orderLines) {
        this.id = id;
        this.customerId = customerId;
        this.orderLines = orderLines;
        this.totalAmount = calculateTotalAmount(orderLines);
    }

    private double calculateTotalAmount(List<OrderLine> orderLines ) {
        return orderLines.stream()
                .mapToDouble(line -> line.getPrice() * line.getQuantity())
                .sum();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

}
