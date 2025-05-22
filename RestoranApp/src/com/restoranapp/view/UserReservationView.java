package com.restoranapp.view;

import com.restoranapp.controller.ReservationController;
import com.restoranapp.model.Reservation;
import com.restoranapp.model.Session;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UserReservationView {
    private final ReservationController controller = new ReservationController();

    public void show(Stage stage) {
        ListView<Reservation> listView = new ListView<>();
        int userId = Session.getInstance().getCurrentUser().getId();
        listView.getItems().addAll(controller.getUserReservations(userId));

        VBox root = new VBox(10, new Label("Geçmiş Rezervasyonlarınız:"), listView);
        root.setPadding(new Insets(20));
        stage.setScene(new Scene(root, 400, 400));
        stage.setTitle("Rezervasyon Geçmişi");
        stage.show();
    }
}
