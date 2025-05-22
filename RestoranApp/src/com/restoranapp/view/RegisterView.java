package com.restoranapp.view;

import com.restoranapp.model.DatabaseManager;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class RegisterView {
    public void show(Stage stage) {
        TextField usernameField = new TextField();
        usernameField.setPromptText("Kullanıcı Adı");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Şifre");

        Button registerBtn = new Button("Kayıt Ol");
        Label messageLabel = new Label();

        registerBtn.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (username.isEmpty() || password.isEmpty()) {
                messageLabel.setText("Alanlar boş bırakılamaz.");
                return;
            }

            try (Connection conn = DatabaseManager.getConnection()) {
                String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, 'Customer')";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, username);
                stmt.setString(2, password);
                stmt.executeUpdate();

                messageLabel.setText("Kayıt başarılı. Giriş yapabilirsiniz.");
            } catch (Exception ex) {
                messageLabel.setText("HATA: " + ex.getMessage());
            }
        });

        VBox root = new VBox(10, usernameField, passwordField, registerBtn, messageLabel);
        root.setStyle("-fx-padding: 20;");
        stage.setScene(new Scene(root, 300, 200));
        stage.setTitle("Yeni Kayıt");
        stage.show();
    }
}
