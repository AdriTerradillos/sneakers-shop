package com.gammatech.sneakers.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "sneaker")
public class Sneaker {

    public Sneaker() {
    }

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    private String brand;

    private String color;

    private String size;

    private double price;

    public Sneaker(Long id, String name, String brand, String color, String size, double price) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.color = color;
        this.size = size;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
