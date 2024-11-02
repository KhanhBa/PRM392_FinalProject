package com.example.prm392_fp_soccer_field.Models;

public class CreatePayment{
    public double price;
    public String status;
    public String method;

    public CreatePayment(double price, String status, String method) {
        this.price = price;
        this.status = status;
        this.method = method;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
