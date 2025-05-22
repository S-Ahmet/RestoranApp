package com.restoranapp.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/restorandb"; // veritabanı adın buysa
    private static final String USER = "root";
    private static final String PASSWORD = ""; // kendi şifren neyse yaz

    public static Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ MySQL bağlantısı başarılı.");
            return conn;
        } catch (SQLException e) {
            System.out.println("❌ HATA: Bağlantı kurulamadı.");
            e.printStackTrace();
            return null;
        }
    }
}
