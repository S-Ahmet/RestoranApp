package com.restoranapp.view;

import com.restoranapp.controller.MenuController;
import com.restoranapp.controller.OrderController;
import com.restoranapp.model.Menu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;

public class MenuView {
    private final MenuController menuController = new MenuController();
    private final OrderController orderController = new OrderController();
    private final ObservableList<Menu> selectedItems = FXCollections.observableArrayList();
    private Label totalLabel = new Label("Toplam: 0₺");

    public void showWithReturn(Stage stage) {
        ComboBox<String> categoryBox = new ComboBox<>();
        categoryBox.getItems().addAll("Çorba", "Ana Yemek", "Tatlı", "İçecek");

        ListView<Menu> menuList = new ListView<>();
        ListView<Menu> orderList = new ListView<>(selectedItems);

        Button addButton = new Button("➕ Ekle");
        Button confirmButton = new Button("✅ Onayla Sipariş");
        Button viewOrdersButton = new Button("🧾 Geçmiş Siparişler");

        styleButton(addButton);
        styleButton(confirmButton);
        styleButton(viewOrdersButton);

        categoryBox.setOnAction(e -> {
            String selected = categoryBox.getValue();
            if (selected != null) {
                List<Menu> list = menuController.getItemsByCategory(selected);
                menuList.getItems().setAll(list); // 🔥 TEKRARSIZ
            }
        });

        addButton.setOnAction(e -> {
            Menu selectedItem = menuList.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                selectedItems.add(selectedItem);
                updateTotal();
            }
        });

        confirmButton.setOnAction(e -> {
            if (selectedItems.isEmpty()) {
                showAlert("Uyarı", "Lütfen önce yemek seçiniz!");
                return;
            }

            orderController.placeOrder(selectedItems);
            showAlert("Sipariş Alındı", "Toplam: " + calculateTotal() + "₺");
            selectedItems.clear();
            updateTotal();
        });

        viewOrdersButton.setOnAction(e -> new OrderView(orderController).show(new Stage()));

        VBox left = new VBox(10, categoryBox, menuList, addButton);
        VBox right = new VBox(10, new Label("Sipariş Listesi"), orderList, totalLabel, confirmButton, viewOrdersButton);
        HBox content = new HBox(20, left, right);
        content.setPadding(new Insets(10));

        Button backBtn = new Button("Geri");
        Button logoutBtn = new Button("Çıkış Yap");
        styleButton(backBtn);
        styleButton(logoutBtn);

        backBtn.setOnAction(e -> new MainMenuView().show(stage));
        logoutBtn.setOnAction(e -> new LoginView().start(stage));

        HBox bottomBar = new HBox(10, backBtn, logoutBtn);
        bottomBar.setAlignment(Pos.BOTTOM_RIGHT);

        VBox root = new VBox(15, content, bottomBar);
        root.setPadding(new Insets(20));

        stage.setScene(new Scene(root, 650, 420));
        stage.setTitle("Yemek Menüsü");
        stage.show();
    }

    private void updateTotal() {
        totalLabel.setText("Toplam: " + calculateTotal() + "₺");
    }

    private double calculateTotal() {
        return selectedItems.stream().mapToDouble(Menu::getPrice).sum();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void styleButton(Button btn) {
        btn.setStyle("-fx-background-radius: 10; -fx-font-weight: bold; -fx-background-color: lightgray;");
        btn.setPrefSize(140, 40);
    }
}
