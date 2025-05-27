package com.gammatech.sneakers.controllers;

import com.gammatech.sneakers.entity.Sneaker;

import java.util.List;

public class SneakerPageResponse {
    private List<Sneaker> sneakers;
    private int totalElements;
    private int totalPages;
    private int currentPage;

    public SneakerPageResponse(List<Sneaker> content, int totalElements, int totalPages, int currentPage) {
        this.sneakers = content;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
    }

    public List<Sneaker> getSneakers() {
        return sneakers;
    }

    public void setSneakers(List<Sneaker> sneakers) {
        this.sneakers = sneakers;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
