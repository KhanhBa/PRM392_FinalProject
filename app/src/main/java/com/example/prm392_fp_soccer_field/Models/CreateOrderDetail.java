package com.example.prm392_fp_soccer_field.Models;

public class CreateOrderDetail {
    public int quantityService;
    public double finalPrice;
    public double totalPrice;
    public int serviceId;

    public CreateOrderDetail(int quantityService, double finalPrice, double totalPrice, int serviceId) {
        this.quantityService = quantityService;
        this.finalPrice = finalPrice;
        this.totalPrice = totalPrice;
        this.serviceId = serviceId;
    }

    public int getQuantityService() {
        return quantityService;
    }

    public void setQuantityService(int quantityService) {
        this.quantityService = quantityService;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }
}
