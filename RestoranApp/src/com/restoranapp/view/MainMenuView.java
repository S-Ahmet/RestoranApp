package com.restoranapp.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenuView {
    public void show(Stage stage) {
        Button menuBtn = new Button("Menü");
        Button reservationBtn = new Button("Rezervasyon");
        Button logoutBtn = new Button("Çıkış Yap");

        menuBtn.setPrefWidth(200);
        reservationBtn.setPrefWidth(200);
        logoutBtn.setPrefWidth(200);

        menuBtn.setOnAction(e -> {
            new MenuView().showWithReturn(stage);
        });

        reservationBtn.setOnAction(e -> {
            new ReservationView().showWithReturn(stage);
        });

        logoutBtn.setOnAction(e -> {
            new LoginView().start(stage); // Giriş ekranına dön
        });

        VBox root = new VBox(15, menuBtn, reservationBtn, logoutBtn);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));

        stage.setScene(new Scene(root, 300, 250));
        stage.setTitle("Ana Menü");
        stage.show();
    }
}
