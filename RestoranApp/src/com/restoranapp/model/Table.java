package com.restoranapp.model;

public class Table {
    private int id;
    private String name;
    private boolean isAvailable;

    public Table(int id, String name, boolean isAvailable) {
        this.id = id;
        this.name = name;
        this.isAvailable = isAvailable;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public boolean isAvailable() { return isAvailable; }

    @Override
    public String toString() {
        return name;
    }
}
