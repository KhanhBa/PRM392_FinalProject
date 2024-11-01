package com.example.prm392_fp_soccer_field.Models;

public class AvailableSlot {
    private int slotId;
    private String startTime;
    private String endTime;
    private int customerId;
    private String customerName;
    private String status;
    private int orderId;

    public AvailableSlot(int slotId, String startTime, String endTime, int customerId, String customerName, String status, int orderId) {
        this.slotId = slotId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.customerId = customerId;
        this.customerName = customerName;
        this.status = status;
        this.orderId = orderId;
    }

    public int getSlotId() {
        return slotId;
    }

    public void setSlotId(int slotId) {
        this.slotId = slotId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}
