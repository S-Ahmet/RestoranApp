package com.restoranapp.view;

import com.restoranapp.controller.LoginController;
import com.restoranapp.controller.ReservationController;
import com.restoranapp.model.Session;
import com.restoranapp.model.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class LoginView {
    private final LoginController loginController = new LoginController();
    private String selectedRole = null;

    public void start(Stage stage) {
        Label usernameLabel = new Label("Kullanıcı Adı");
        usernameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        TextField usernameField = new TextField();
        usernameField.setPromptText("Kullanıcı adınızı girin");

        Label passwordLabel = new Label("Şifre");
        passwordLabel.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Şifrenizi girin");

        Button customerBtn = new Button("Kullanıcı");
        Button managerBtn = new Button("Admin");

        customerBtn.setPrefSize(140, 40);
        managerBtn.setPrefSize(140, 40);
        customerBtn.setStyle("-fx-background-radius: 10; -fx-font-weight: bold; -fx-background-color: lightgray;");
        managerBtn.setStyle("-fx-background-radius: 10; -fx-font-weight: bold; -fx-background-color: lightgray;");

        customerBtn.setOnAction(e -> {
            selectedRole = "Customer";
            customerBtn.setStyle("-fx-background-color: #2196f3; -fx-text-fill: white; -fx-background-radius: 10;");
            managerBtn.setStyle("-fx-background-color: lightgray; -fx-text-fill: black; -fx-background-radius: 10;");
        });

        managerBtn.setOnAction(e -> {
            selectedRole = "Manager";
            managerBtn.setStyle("-fx-background-color: #2196f3; -fx-text-fill: white; -fx-background-radius: 10;");
            customerBtn.setStyle("-fx-background-color: lightgray; -fx-text-fill: black; -fx-background-radius: 10;");
        });

        HBox roleBox = new HBox(15, customerBtn, managerBtn);
        roleBox.setAlignment(Pos.CENTER);
        roleBox.setPadding(new Insets(10));

        Button loginBtn = new Button("Giriş Yap");
        Button registerBtn = new Button("Kayıt Ol");
        loginBtn.setPrefSize(295, 40);
        registerBtn.setPrefSize(295, 40);

        Label messageLabel = new Label();

        loginBtn.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (username.isEmpty() || password.isEmpty() || selectedRole == null) {
                messageLabel.setText("Tüm alanları doldurun ve rol seçin.");
                return;
            }

            User user = loginController.login(username, password, selectedRole);
            if (user != null) {
                Session.getInstance().setCurrentUser(user); // 🔥 kullanıcıyı kaydet
                messageLabel.setText("Hoş geldin " + user.getUsername() + " [" + user.getRole() + "]");

                if (user.getRole().equals("Customer")) {
                    new MainMenuView().show(stage);
                } else {
                    ReservationController rController = new ReservationController();
                    AdminPanelView adminPanel = new AdminPanelView(rController);
                    adminPanel.show(new Stage());
                }
            } else {
                messageLabel.setText("Giriş başarısız.");
            }
        });

        registerBtn.setOnAction(e -> {
            new RegisterView().show(new Stage());
        });

        VBox root = new VBox(12,
                usernameLabel, usernameField,
                passwordLabel, passwordField,
                roleBox,
                loginBtn, registerBtn,
                messageLabel
        );
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        stage.setScene(new Scene(root, 350, 400));
        stage.setTitle("Giriş Ekranı");
        stage.show();
    }
}
