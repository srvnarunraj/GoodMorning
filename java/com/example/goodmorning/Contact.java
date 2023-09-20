package com.example.goodmorning;

public class Contact {
    private String phoneNumber;
    private String message;
    private int id;

    public Contact(String phoneNumber, String message, int id) {
        this.phoneNumber = phoneNumber;
        this.message = message;
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getId() {
        return id;
    }
}
