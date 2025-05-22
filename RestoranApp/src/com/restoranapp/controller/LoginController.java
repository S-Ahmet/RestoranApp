package com.restoranapp.controller;

import com.restoranapp.model.*;
import java.sql.*;

public class LoginController {

    public User login(String username, String password, String role) {
        try (Connection conn = DatabaseManager.getConnection()) {
            String sql = "SELECT * FROM users WHERE username=? AND password=? AND role=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, role);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String uname = rs.getString("username");

                // 🔥 role'e göre doğru sınıfı oluştur
                if (role.equals("Customer")) {
                    return new Customer(id, uname);
                } else {
                    return new Manager(id, uname);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
