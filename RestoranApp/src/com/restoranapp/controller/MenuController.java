package com.restoranapp.controller;

import com.restoranapp.model.Menu;
import com.restoranapp.model.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MenuController {

    public List<Menu> getItemsByCategory(String category) {
        List<Menu> items = new ArrayList<>(); // Her seferinde sıfırdan oluşturuluyor

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM menus WHERE category = ?")) {

            stmt.setString(1, category);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String cat = rs.getString("category");
                double price = rs.getDouble("price");

                items.add(new Menu(id, name, cat, price));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return items;
    }
}
