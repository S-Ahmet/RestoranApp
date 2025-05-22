package com.restoranapp.model;

// Müşteri sınıfı
public class Customer extends User {
    public Customer(int id, String username) {
        super(id, username, "Customer");
    }
}
