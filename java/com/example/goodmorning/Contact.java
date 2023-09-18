package com.example.goodmorning;

public class Contact {
    private String phoneNumber;
    private String message;
    private int id; // Add an ID or position

    public Contact(String phoneNumber, String message, int id) {
        this.phoneNumber = phoneNumber;
        this.message = message;
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getMessage() {
        return message;
    }

    public int getId() {
        return id;
    }
}
