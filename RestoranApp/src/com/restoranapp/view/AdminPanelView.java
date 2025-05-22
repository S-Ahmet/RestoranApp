package com.restoranapp.view;

import com.restoranapp.controller.OrderController;
import com.restoranapp.controller.ReservationController;
import com.restoranapp.model.Order;
import com.restoranapp.model.Reservation;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class AdminPanelView {
    private final ReservationController reservationController;
    private final OrderController orderController;

    public AdminPanelView(ReservationController reservationController) {
        this.reservationController = reservationController;
        this.orderController = new OrderController();
    }

    public void show(Stage stage) {
        Label title = new Label("🔐 Yönetici Paneli");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Button reservationsBtn = createStyledButton("📅 Rezervasyonlar");
        Button ordersBtn = createStyledButton("🧾 Siparişler");
        Button logoutBtn = createStyledButton("🚪 Çıkış Yap");

        ListView<String> listView = new ListView<>();

        reservationsBtn.setOnAction(e -> {
            listView.getItems().clear();
            for (Reservation r : reservationController.getAllReservations()) {
                listView.getItems().add(r.toString());
            }
        });

        ordersBtn.setOnAction(e -> {
            listView.getItems().clear();
            for (Order o : orderController.getAllOrders()) {
                listView.getItems().add(o.toString());
            }
        });

        // 🔁 Aynı pencerede giriş ekranına dön
        logoutBtn.setOnAction(e -> {
            System.out.println("Admin çıkış yaptı.");
            new LoginView().start(stage); // yeni pencere yok, aynı stage
        });

        HBox buttons = new HBox(10, reservationsBtn, ordersBtn, logoutBtn);
        buttons.setAlignment(Pos.CENTER);

        VBox root = new VBox(15, title, buttons, listView);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #f4f4f4;");

        stage.setScene(new Scene(root, 500, 450));
        stage.setTitle("Admin Panel");
        stage.show();
    }

    private Button createStyledButton(String text) {
        Button btn = new Button(text);
        btn.setStyle("-fx-background-color: #3f51b5; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8;");
        btn.setPrefHeight(40);
        btn.setPrefWidth(150);
        return btn;
    }
}
