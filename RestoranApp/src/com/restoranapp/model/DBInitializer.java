package com.restoranapp.model;

import java.sql.Connection;
import java.sql.Statement;

public class DBInitializer {

    public static void initialize() {
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement()) {

            // USERS TABLOSU
            stmt.execute("CREATE TABLE IF NOT EXISTS users (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "username VARCHAR(50) NOT NULL," +
                    "password VARCHAR(50) NOT NULL," +
                    "role ENUM('Customer', 'Manager') NOT NULL)");

            // MENUS TABLOSU
            stmt.execute("CREATE TABLE IF NOT EXISTS menus (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR(100) NOT NULL," +
                    "category VARCHAR(50)," +
                    "price DOUBLE NOT NULL)");

            // ORDERS TABLOSU
            stmt.execute("CREATE TABLE IF NOT EXISTS orders (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "user_id INT," +
                    "total DOUBLE," +
                    "date_time DATETIME DEFAULT CURRENT_TIMESTAMP," +
                    "FOREIGN KEY (user_id) REFERENCES users(id))");

            // ORDER_ITEMS TABLOSU
            stmt.execute("CREATE TABLE IF NOT EXISTS order_items (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "order_id INT," +
                    "menu_id INT," +
                    "FOREIGN KEY (order_id) REFERENCES orders(id)," +
                    "FOREIGN KEY (menu_id) REFERENCES menus(id))");

            // RESERVATIONS TABLOSU
            stmt.execute("CREATE TABLE IF NOT EXISTS reservations (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "user_id INT," +
                    "table_no INT," +
                    "date_time DATETIME," +
                    "FOREIGN KEY (user_id) REFERENCES users(id))");

            // TABLES TABLOSU
            stmt.execute("CREATE TABLE IF NOT EXISTS tables (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR(50) NOT NULL," +
                    "is_available BOOLEAN DEFAULT TRUE)");

            // ÖRNEK MASALAR
            stmt.executeUpdate("INSERT IGNORE INTO tables (name, is_available) VALUES " +
                    "('Masa 1', true)," +
                    "('Masa 2', true)," +
                    "('Masa 3', true)," +
                    "('Masa 4', true)," +
                    "('Masa 5', true)");

            // ÖRNEK KULLANICILAR
            stmt.executeUpdate("INSERT IGNORE INTO users (username, password, role) VALUES " +
                    "('admin', 'admin123', 'Manager')," +
                    "('user1', '1234', 'Customer')," +
                    "('user2', '1234', 'Customer')," +
                    "('user3', '1234', 'Customer')," +
                    "('user4', '1234', 'Customer')," +
                    "('user5', '1234', 'Customer')," +
                    "('user6', '1234', 'Customer')," +
                    "('user7', '1234', 'Customer')," +
                    "('user8', '1234', 'Customer')," +
                    "('user9', '1234', 'Customer')," +
                    "('user10', '1234', 'Customer')");

            // ÖRNEK MENÜLER
            stmt.executeUpdate("INSERT IGNORE INTO menus (name, category, price) VALUES " +
                    "('Mercimek Çorbası', 'Çorba', 20)," +
                    "('Ezogelin Çorbası', 'Çorba', 22)," +
                    "('Domates Çorbası', 'Çorba', 21)," +
                    "('Tavuk Suyu Çorbası', 'Çorba', 25)," +
                    "('İşkembe Çorbası', 'Çorba', 28)," +
                    "('Adana Kebap', 'Ana Yemek', 80)," +
                    "('Urfa Kebap', 'Ana Yemek', 78)," +
                    "('İskender', 'Ana Yemek', 85)," +
                    "('Lahmacun', 'Ana Yemek', 25)," +
                    "('Etli Ekmek', 'Ana Yemek', 30)," +
                    "('Tavuk Şiş', 'Ana Yemek', 65)," +
                    "('Köfte', 'Ana Yemek', 60)," +
                    "('Pideli Köfte', 'Ana Yemek', 70)," +
                    "('Fırın Tavuk', 'Ana Yemek', 75)," +
                    "('Et Sote', 'Ana Yemek', 88)," +
                    "('Karnıyarık', 'Ana Yemek', 55)," +
                    "('Sebzeli Güveç', 'Ana Yemek', 58)," +
                    "('Makarna', 'Ana Yemek', 45)," +
                    "('Pilav Üstü Tavuk', 'Ana Yemek', 50)," +
                    "('Mantı', 'Ana Yemek', 65)," +
                    "('Kuzu Tandır', 'Ana Yemek', 95)," +
                    "('Baklava', 'Tatlı', 40)," +
                    "('Sütlaç', 'Tatlı', 35)," +
                    "('Kazandibi', 'Tatlı', 38)," +
                    "('Profiterol', 'Tatlı', 42)," +
                    "('Revani', 'Tatlı', 34)," +
                    "('Aşure', 'Tatlı', 36)," +
                    "('Künefe', 'Tatlı', 48)," +
                    "('Tiramisu', 'Tatlı', 46)," +
                    "('Magnolia', 'Tatlı', 39)," +
                    "('Cheesecake', 'Tatlı', 44)," +
                    "('Fırın Sütlaç', 'Tatlı', 36)," +
                    "('Güllaç', 'Tatlı', 33)," +
                    "('Meyveli Kup', 'Tatlı', 37)," +
                    "('Supangle', 'Tatlı', 35)," +
                    "('Ayran', 'İçecek', 10)," +
                    "('Kola', 'İçecek', 15)," +
                    "('Fanta', 'İçecek', 15)," +
                    "('Soda', 'İçecek', 8)," +
                    "('Su', 'İçecek', 5)");

            System.out.println("✅ Tüm tablolar ve örnek veriler başarıyla oluşturuldu.");

        } catch (Exception e) {
            System.err.println("❌ DBInitializer Hatası: " + e.getMessage());
        }
    }
}
