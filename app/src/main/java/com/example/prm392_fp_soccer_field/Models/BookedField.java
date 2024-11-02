package com.example.prm392_fp_soccer_field.Models;

public class  BookedField {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;
    private String fieldName;
    private String date;
    private String time;
    private String status;

    public BookedField(String fieldName, String date, String time, String status) {
        this.fieldName = fieldName;
        this.date = date;
        this.time = time;
        this.status = status;
    }

    public String getFieldName() { return fieldName; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public String getStatus() { return status; }
}