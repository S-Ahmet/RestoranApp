package com.restoranapp.model;

import java.time.LocalDateTime;
import java.util.List;

public class OrderHistory {
    private int id;
    private double total;
    private LocalDateTime dateTime;
    private List<Menu> items;

    public OrderHistory(int id, double total, LocalDateTime dateTime, List<Menu> items) {
        this.id = id;
        this.total = total;
        this.dateTime = dateTime;
        this.items = items;
    }

    public int getId() { return id; }
    public double getTotal() { return total; }
    public LocalDateTime getDateTime() { return dateTime; }
    public List<Menu> getItems() { return items; }

    @Override
    public String toString() {
        return "Sipariş #" + id + " - " + total + "₺ - " + dateTime.toLocalDate();
    }
}
