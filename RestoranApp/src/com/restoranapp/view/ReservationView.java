package com.restoranapp.view;

import com.restoranapp.controller.ReservationController;
import com.restoranapp.model.Table;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ReservationView {
    private final ReservationController controller = new ReservationController();

    public void showWithReturn(Stage stage) {
        ComboBox<Table> tableBox = new ComboBox<>();
        tableBox.getItems().addAll(controller.getAvailableTables());

        DatePicker datePicker = new DatePicker(LocalDate.now());
        Spinner<Integer> hourSpinner = new Spinner<>(0, 23, 19);
        Spinner<Integer> minuteSpinner = new Spinner<>(0, 59, 0);

        Label resultLabel = new Label();
        resultLabel.setStyle("-fx-font-weight: bold;");

        Button reserveBtn = createStyledButton("✅ Rezerve Et");
        Button backBtn = createStyledButton("⬅ Geri");
        Button logoutBtn = createStyledButton("🚪 Çıkış Yap");
        Button historyBtn = createStyledButton("📖 Geçmiş");

        reserveBtn.setOnAction(e -> {
            Table selectedTable = tableBox.getValue();
            LocalDate date = datePicker.getValue();
            int hour = hourSpinner.getValue();
            int minute = minuteSpinner.getValue();
            LocalDateTime dateTime = LocalDateTime.of(date, LocalTime.of(hour, minute));

            if (selectedTable == null) {
                resultLabel.setText("⚠ Lütfen masa seçiniz.");
                return;
            }

            String result = controller.reserveTable(selectedTable, dateTime);
            resultLabel.setText(result);

            if (result.startsWith("✅")) {
                tableBox.getItems().setAll(controller.getAvailableTables());
            }
        });

        backBtn.setOnAction(e -> new MainMenuView().show(stage));
        logoutBtn.setOnAction(e -> new LoginView().start(stage));
        historyBtn.setOnAction(e -> new UserReservationView().show(new Stage())); // 🔥 geçiş

        VBox form = new VBox(15,
                createLabeledRow("🍽 Masa Seç:", tableBox),
                createLabeledRow("📅 Tarih:", datePicker),
                createLabeledRow("🕒 Saat:", new HBox(5, hourSpinner, new Label(":"), minuteSpinner)),
                reserveBtn,
                resultLabel
        );

        form.setPadding(new Insets(20));
        form.setStyle("-fx-background-color: #f8f8f8; -fx-border-radius: 8; -fx-background-radius: 8;");
        form.setAlignment(Pos.CENTER_LEFT);

        HBox bottomBar = new HBox(10, backBtn, logoutBtn, historyBtn);
        bottomBar.setAlignment(Pos.CENTER_RIGHT);
        bottomBar.setPadding(new Insets(10));

        VBox root = new VBox(15, form, bottomBar);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-font-size: 14px;");

        stage.setScene(new Scene(root, 450, 390));
        stage.setTitle("🪑 Masa Rezervasyonu");
        stage.show();
    }

    private HBox createLabeledRow(String labelText, javafx.scene.Node node) {
        Label label = new Label(labelText);
        label.setPrefWidth(100);
        HBox row = new HBox(10, label, node);
        row.setAlignment(Pos.CENTER_LEFT);
        return row;
    }

    private Button createStyledButton(String text) {
        Button btn = new Button(text);
        btn.setStyle("-fx-background-color: #2196f3; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8;");
        btn.setPrefHeight(40);
        btn.setPrefWidth(130);
        return btn;
    }
}
