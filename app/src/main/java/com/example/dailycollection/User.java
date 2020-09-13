package com.example.dailycollection;

public class User {
    String message,date,mobile;
    int value;

    public User(String message, String date, int value, String mobile) {
        this.message = message;
        this.date = date;
        this.value = value;
        this.mobile = mobile;
    }

    public String getMessage() {
        return message;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
