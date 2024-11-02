package com.example.prm392_fp_soccer_field.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class OrderDetail implements Parcelable {
    private int id;
    private int orderId;
    private int quantityService;
    private double finalPrice;
    private double totalPrice;
    private int serviceId;

    public OrderDetail(int id, int orderId, int quantityService, double finalPrice, double totalPrice, int serviceId) {
        this.id = id;
        this.orderId = orderId;
        this.quantityService = quantityService;
        this.finalPrice = finalPrice;
        this.totalPrice = totalPrice;
        this.serviceId = serviceId;
    }

    protected OrderDetail(Parcel in) {
        id = in.readInt();
        orderId = in.readInt();
        quantityService = in.readInt();
        finalPrice = in.readDouble();
        totalPrice = in.readDouble();
        serviceId = in.readInt();
    }

    public static final Creator<OrderDetail> CREATOR = new Creator<OrderDetail>() {
        @Override
        public OrderDetail createFromParcel(Parcel in) {
            return new OrderDetail(in);
        }

        @Override
        public OrderDetail[] newArray(int size) {
            return new OrderDetail[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(orderId);
        dest.writeInt(quantityService);
        dest.writeDouble(finalPrice);
        dest.writeDouble(totalPrice);
        dest.writeInt(serviceId);
    }
}
