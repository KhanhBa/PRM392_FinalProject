package com.example.prm392_fp_soccer_field.Models;

import java.util.List;

public class CreateOrder {

    private int slotId;
    private int yardId;
    private int customerId;
    private int duration;
    private String status;
    private String bookingDate;
    private double totalPrice;
    private CreatePayment payment;
    private List<CreateOrderDetail> orderDetails;

    public CreateOrder(int slotId, int yardId, int customerId, int duration, String status, String bookingDate, double totalPrice, CreatePayment payment, List<CreateOrderDetail> orderDetails) {
        this.slotId = slotId;
        this.yardId = yardId;
        this.customerId = customerId;
        this.duration = duration;
        this.status = status;
        this.bookingDate = bookingDate;
        this.totalPrice = totalPrice;
        this.payment = payment;
        this.orderDetails = orderDetails;
    }

    public CreateOrder() {
    }

    public int getSlotId() {
        return slotId;
    }

    public void setSlotId(int slotId) {
        this.slotId = slotId;
    }

    public int getYardId() {
        return yardId;
    }

    public void setYardId(int yardId) {
        this.yardId = yardId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public CreatePayment getPayment() {
        return payment;
    }

    public void setPayment(CreatePayment payment) {
        this.payment = payment;
    }

    public List<CreateOrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<CreateOrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }
}
