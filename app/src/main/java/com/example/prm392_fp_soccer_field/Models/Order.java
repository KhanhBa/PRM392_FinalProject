package com.example.prm392_fp_soccer_field.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.type.DateTime;

import java.util.Date;

public class Order implements Parcelable {
    private int id;
    private String createdDate;
    private String updatedDate;
    private int slotId;
    private int yardId;
    private int customerId;
    private double totalPrice;
    private String startTime;

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    private String endTime;
    private int duration;
    private String status;
    private String bookingDate;

    public Order(int id, String createdDate, String updatedDate, int slotId, int yardId, int customerId, String startTime, String endTime, int duration, String status, String bookingDate) {
        this.id = id;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.slotId = slotId;
        this.yardId = yardId;
        this.customerId = customerId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
        this.status = status;
        this.bookingDate = bookingDate;
    }

    protected Order(Parcel in) {
        id = in.readInt();
        createdDate = in.readString();
        updatedDate = in.readString();
        slotId = in.readInt();
        yardId = in.readInt();
        customerId = in.readInt();
        startTime = in.readString();
        endTime = in.readString();
        duration = in.readInt();
        status = in.readString();
        bookingDate = in.readString();
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(createdDate);
        dest.writeString(updatedDate);
        dest.writeInt(slotId);
        dest.writeInt(yardId);
        dest.writeInt(customerId);
        dest.writeString(startTime);
        dest.writeString(endTime);
        dest.writeInt(duration);
        dest.writeString(status);
        dest.writeString(bookingDate);
    }
}
