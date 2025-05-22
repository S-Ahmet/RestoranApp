package com.restoranapp.controller;

import com.restoranapp.model.DatabaseManager;
import com.restoranapp.model.Menu;
import com.restoranapp.model.Order;
import com.restoranapp.model.Session;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderController {

    // 🔥 Sipariş oluştur (giriş yapan kullanıcıya)
    public void placeOrder(List<Menu> items) {
        if (items == null || items.isEmpty()) return;

        double total = items.stream().mapToDouble(Menu::getPrice).sum();
        int userId = Session.getInstance().getCurrentUser().getId();

        try (Connection conn = DatabaseManager.getConnection()) {
            // Siparişi ekle
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO orders (user_id, total) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, userId);
            stmt.setDouble(2, total);
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            int orderId = -1;
            if (generatedKeys.next()) {
                orderId = generatedKeys.getInt(1);
            }

            // Sipariş kalemlerini ekle
            for (Menu item : items) {
                PreparedStatement itemStmt = conn.prepareStatement(
                        "INSERT INTO order_items (order_id, menu_id) VALUES (?, ?)");
                itemStmt.setInt(1, orderId);
                itemStmt.setInt(2, item.getId());
                itemStmt.executeUpdate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔁 Giriş yapan kullanıcının sipariş geçmişi
    public List<Order> getOrdersByUserId(int userId) {
        List<Order> orders = new ArrayList<>();
        try (Connection conn = DatabaseManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("""
                SELECT o.id, u.username, o.total, o.date_time
                FROM orders o JOIN users u ON o.user_id = u.id
                WHERE u.id = ?
                ORDER BY o.date_time DESC
            """);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                orders.add(new Order(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getDouble("total"),
                        rs.getTimestamp("date_time").toLocalDateTime()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }

    // 🔁 Admin → tüm siparişler
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        try (Connection conn = DatabaseManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("""
                SELECT o.id, u.username, o.total, o.date_time
                FROM orders o JOIN users u ON o.user_id = u.id
                ORDER BY o.date_time DESC
            """);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                orders.add(new Order(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getDouble("total"),
                        rs.getTimestamp("date_time").toLocalDateTime()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }
}
