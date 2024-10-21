package com.example.prm392_fp_soccer_field.Models;

import com.google.type.DateTime;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "Order")
public class Order {
    @DatabaseField(columnName = "id",generatedId = true)
    private int Id;
    @DatabaseField(columnName = "create_date")
    private Date CreatedDate;
    @DatabaseField(columnName = "update_date")
    private Date UpdatedDate;
    @DatabaseField(columnName = "payment_id")
    private int PaymentId;
    @DatabaseField(columnName = "yard_id")
    private int YardId;
    @DatabaseField(columnName = "customer_id")
    private int CustomerId;
    @DatabaseField(columnName = "start_time")
    private DateTime StartedTime;
    @DatabaseField(columnName = "end_time")
    private DateTime EndedTime;
    @DatabaseField(columnName = "duration")
    private int Duration;
    @DatabaseField(columnName = "status")
    private  boolean Status;

    public Order(int id, Date createdDate, Date updatedDate, int paymentId, int yardId, int customerId, DateTime startedTime, DateTime endedTime, int duration, boolean status) {
        Id = id;
        CreatedDate = createdDate;
        UpdatedDate = updatedDate;
        PaymentId = paymentId;
        YardId = yardId;
        CustomerId = customerId;
        StartedTime = startedTime;
        EndedTime = endedTime;
        Duration = duration;
        Status = status;
    }

    public Order() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public Date getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(Date createdDate) {
        CreatedDate = createdDate;
    }

    public Date getUpdatedDate() {
        return UpdatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        UpdatedDate = updatedDate;
    }

    public int getPaymentId() {
        return PaymentId;
    }

    public void setPaymentId(int paymentId) {
        PaymentId = paymentId;
    }

    public int getYardId() {
        return YardId;
    }

    public void setYardId(int yardId) {
        YardId = yardId;
    }

    public int getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(int customerId) {
        CustomerId = customerId;
    }

    public DateTime getStartedTime() {
        return StartedTime;
    }

    public void setStartedTime(DateTime startedTime) {
        StartedTime = startedTime;
    }

    public DateTime getEndedTime() {
        return EndedTime;
    }

    public void setEndedTime(DateTime endedTime) {
        EndedTime = endedTime;
    }

    public int getDuration() {
        return Duration;
    }

    public void setDuration(int duration) {
        Duration = duration;
    }

    public boolean isStatus() {
        return Status;
    }

    public void setStatus(boolean status) {
        Status = status;
    }
}
