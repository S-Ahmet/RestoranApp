package com.restoranapp.model;

import java.time.LocalDateTime;

public class Order {
    private int id;
    private String username;
    private double total;
    private LocalDateTime dateTime;

    public Order(int id, String username, double total, LocalDateTime dateTime) {
        this.id = id;
        this.username = username;
        this.total = total;
        this.dateTime = dateTime;
    }

    public int getId() { return id; }
    public String getUsername() { return username; }
    public double getTotal() { return total; }
    public LocalDateTime getDateTime() { return dateTime; }

    @Override
    public String toString() {
        return username + " | ₺" + total + " | " + dateTime;
    }
}
