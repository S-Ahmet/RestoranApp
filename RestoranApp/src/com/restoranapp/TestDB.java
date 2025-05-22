package com.restoranapp;

import com.restoranapp.model.DatabaseManager;

import java.sql.Connection;

public class TestDB {
    public static void main(String[] args) {
        Connection conn = DatabaseManager.getConnection();
        if (conn != null) {
            System.out.println("Bağlantı testinden geçtin paşam! 🔥");
        } else {
            System.out.println("Bağlantı başarısız. Ayarları kontrol et.");
        }
    }
}
