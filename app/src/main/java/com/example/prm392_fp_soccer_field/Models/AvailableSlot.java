package com.example.prm392_fp_soccer_field.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class AvailableSlot implements Parcelable {
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

    protected AvailableSlot(Parcel in) {
        slotId = in.readInt();
        startTime = in.readString();
        endTime = in.readString();
        customerId = in.readInt();
        customerName = in.readString();
        status = in.readString();
        orderId = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(slotId);
        dest.writeString(startTime);
        dest.writeString(endTime);
        dest.writeInt(customerId);
        dest.writeString(customerName);
        dest.writeString(status);
        dest.writeInt(orderId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AvailableSlot> CREATOR = new Creator<AvailableSlot>() {
        @Override
        public AvailableSlot createFromParcel(Parcel in) {
            return new AvailableSlot(in);
        }

        @Override
        public AvailableSlot[] newArray(int size) {
            return new AvailableSlot[size];
        }
    };

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
