package com.restoranapp;

import com.restoranapp.model.DatabaseConnection;
import com.restoranapp.model.DBInitializer;
import com.restoranapp.view.LoginView;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // 🔌 Veritabanı bağlantısını başlat
        DatabaseConnection.getInstance().connect();

        // 🔨 Tabloları ve verileri oluştur
        DBInitializer.initialize();

        // 🖥️ Giriş ekranını göster
        LoginView loginView = new LoginView();
        loginView.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
