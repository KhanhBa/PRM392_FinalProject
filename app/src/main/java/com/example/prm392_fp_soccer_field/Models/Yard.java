package com.example.prm392_fp_soccer_field.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Yard implements Parcelable {
    public int id;
    public String name;
    public String description;
    public String img;
    public float price;
    public boolean status;

    public Yard(int id, String name, String description, String img, float price, boolean status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.img = img;
        this.price = price;
        this.status = status;
    }

    protected Yard(Parcel in) {
        id = in.readInt();
        name = in.readString();
        description = in.readString();
        img = in.readString();
        price = in.readFloat();
        status = in.readByte() != 0;
    }

    public static final Creator<Yard> CREATOR = new Creator<Yard>() {
        @Override
        public Yard createFromParcel(Parcel in) {
            return new Yard(in);
        }

        @Override
        public Yard[] newArray(int size) {
            return new Yard[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(img);
        dest.writeFloat(price);
        dest.writeByte((byte) (status ? 1 : 0));
    }
}
