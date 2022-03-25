package com.example.p3finalproject;

import com.example.p3finalproject.ui.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage window) {

        window.setTitle("Attention Game");

        LoginView loginView = new LoginView();

        Scene initialScene = new Scene(loginView.getView(), 400, 300);
        window.setScene(initialScene);
        window.show();

    }

    public static void main(String[] args) {

        Application.launch(Main.class);

    }
}