package com.example.prm392_fp_soccer_field.Models;

import com.google.type.DateTime;

import java.util.Date;

public class Order {
    private int id;
    private String createdDate;
    private String updatedDate;
    private int paymentId;
    private int yardId;
    private int customerId;
    private String startedTime;
    private String endedTime;
    private int duration;
    private  boolean status;
    private Yard yard;
    private String bookingOrder;

    public String getBookingOrder() {
        return bookingOrder;
    }

    public void setBookingOrder(String bookingOrder) {
        this.bookingOrder = bookingOrder;
    }

    public Yard getYard() {
        return yard;
    }

    public void setYard(Yard yard) {
        this.yard = yard;
    }

    public Order(int id, String createdDate, String updatedDate, int paymentId, int yardId, int customerId, String startedTime, String endedTime, int duration, boolean status, Yard yard, String bookingOrder) {
        this.id = id;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.paymentId = paymentId;
        this.yardId = yardId;
        this.customerId = customerId;
        this.startedTime = startedTime;
        this.endedTime = endedTime;
        this.duration = duration;
        this.status = status;
        this.yard = yard;
        this.bookingOrder = bookingOrder;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
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

    public String getStartedTime() {
        return startedTime;
    }

    public void setStartedTime(String startedTime) {
        this.startedTime = startedTime;
    }

    public String getEndedTime() {
        return endedTime;
    }

    public void setEndedTime(String endedTime) {
        this.endedTime = endedTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
