package com.restoranapp.controller;

import com.restoranapp.model.DatabaseManager;
import com.restoranapp.model.Reservation;
import com.restoranapp.model.Session;
import com.restoranapp.model.Table;
import com.restoranapp.model.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReservationController {

    // ✅ Rezervasyon işlemi – çakışma kontrolü + mesaj döner
    public String reserveTable(Table selectedTable, LocalDateTime dateTime) {
        User currentUser = Session.getInstance().getCurrentUser();
        if (currentUser == null) return "Giriş yapılmadı.";

        try (Connection conn = DatabaseManager.getConnection()) {
            PreparedStatement checkStmt = conn.prepareStatement(
                    "SELECT COUNT(*) FROM reservations WHERE table_no = ? AND date_time = ?");
            checkStmt.setInt(1, selectedTable.getId());
            checkStmt.setTimestamp(2, Timestamp.valueOf(dateTime));
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            if (rs.getInt(1) > 0) {
                return "⚠ Bu masa bu tarih ve saatte zaten rezerve edilmiş!";
            }

            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO reservations (user_id, table_no, date_time) VALUES (?, ?, ?)");
            stmt.setInt(1, currentUser.getId());
            stmt.setInt(2, selectedTable.getId());
            stmt.setTimestamp(3, Timestamp.valueOf(dateTime));
            stmt.executeUpdate();

            return "✅ Rezervasyon tamamlandı: " + selectedTable.getName();

        } catch (Exception e) {
            e.printStackTrace();
            return "❌ Hata oluştu!";
        }
    }

    public List<Table> getAvailableTables() {
        List<Table> tables = new ArrayList<>();
        try (Connection conn = DatabaseManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM tables WHERE is_available = true");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                tables.add(new Table(rs.getInt("id"), rs.getString("name"), rs.getBoolean("is_available")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tables;
    }

    // ✅ Admin: tüm rezervasyonlar
    public List<Reservation> getAllReservations() {
        List<Reservation> reservations = new ArrayList<>();
        try (Connection conn = DatabaseManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("""
                SELECT r.id, u.username, r.table_no, r.date_time
                FROM reservations r JOIN users u ON r.user_id = u.id
                ORDER BY r.date_time DESC
            """);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                reservations.add(new Reservation(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getInt("table_no"),
                        rs.getTimestamp("date_time").toLocalDateTime()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reservations;
    }

    // ✅ Kullanıcı: kendi geçmişi
    public List<Reservation> getUserReservations(int userId) {
        List<Reservation> list = new ArrayList<>();
        try (Connection conn = DatabaseManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("""
                SELECT r.id, u.username, r.table_no, r.date_time
                FROM reservations r JOIN users u ON r.user_id = u.id
                WHERE r.user_id = ? ORDER BY r.date_time DESC
            """);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new Reservation(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getInt("table_no"),
                        rs.getTimestamp("date_time").toLocalDateTime()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
