package com.restoranapp.view;

import com.restoranapp.controller.OrderController;
import com.restoranapp.model.Order;
import com.restoranapp.model.Session;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;

public class OrderView {
    private final OrderController orderController;

    public OrderView(OrderController controller) {
        this.orderController = controller;
    }

    public void show(Stage stage) {
        int userId = Session.getInstance().getCurrentUser().getId();
        List<Order> userOrders = orderController.getOrdersByUserId(userId);

        Label title = new Label("🧾 Sipariş Geçmişi");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        ListView<String> listView = new ListView<>();
        for (Order o : userOrders) {
            listView.getItems().add(o.toString());
        }

        VBox root = new VBox(15, title, listView);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #f8f8f8;");

        stage.setScene(new Scene(root, 500, 400));
        stage.setTitle("Sipariş Geçmişi");
        stage.show();
    }
}
