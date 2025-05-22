package com.restoranapp.model;

import java.time.LocalDateTime;

public class Reservation {
    private int id;
    private String username;
    private int tableNo;
    private LocalDateTime dateTime;

    public Reservation(int id, String username, int tableNo, LocalDateTime dateTime) {
        this.id = id;
        this.username = username;
        this.tableNo = tableNo;
        this.dateTime = dateTime;
    }

    public int getId() { return id; }
    public String getUsername() { return username; }
    public int getTableNo() { return tableNo; }
    public LocalDateTime getDateTime() { return dateTime; }

    @Override
    public String toString() {
        return username + " | Masa: " + tableNo + " | " + dateTime;
    }
}
