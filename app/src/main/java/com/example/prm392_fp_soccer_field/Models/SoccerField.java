package com.example.prm392_fp_soccer_field.Models;

public class SoccerField {
    private String name;
    private String location;
    private String price;
    private int imageResourceId;
    private float rating;

    public SoccerField(String name, String location, String price, int imageResourceId, float rating) {
        this.name = name;
        this.location = location;
        this.price = price;
        this.imageResourceId = imageResourceId;
        this.rating = rating;
    }

    public String getName() { return name; }
    public String getLocation() { return location; }
    public String getPrice() { return price; }
    public int getImageResourceId() { return imageResourceId; }
    public float getRating() { return rating; }
}