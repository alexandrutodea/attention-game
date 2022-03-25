package com.example.p3finalproject.ui;

import com.example.p3finalproject.model.Board;
import com.example.p3finalproject.server.ServerUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class MenuView {

    public MenuView() {

    }

    public Parent getView() {
        HBox layout = new HBox();
        layout.setAlignment(Pos.CENTER);
        layout.setSpacing(10);
        layout.setPadding(new Insets(10, 10, 10, 10));
        Button playGame = new Button("Play Game");
        Button manageUsers = new Button("Manage users");
        Button back = new Button("Back");

        back.setOnAction(e -> {
            Stage stage = (Stage) layout.getScene().getWindow();
            LoginView loginView = new LoginView();
            Scene loginScene = new Scene(loginView.getView(), 400, 300);
            stage.setScene(loginScene);
        });

        manageUsers.setOnAction(e -> {
            Stage stage = (Stage) layout.getScene().getWindow();
            PlayerListView playerListView = new PlayerListView();
            Scene userListScene = new Scene(playerListView.getView(), 400, 300);
            stage.setScene(userListScene);
        });

        playGame.setOnAction(e -> {

            ObjectMapper objectMapper = new ObjectMapper();
            Board board = null;

            try {
                File file = new File("src/main/resources/config.json");
                board = objectMapper.readValue(file, Board.class);
            } catch (IOException err) {
                err.printStackTrace();
            }

            if (board != null) {
                GameView gameView = new GameView(board);
                Scene gameScene = new Scene(gameView.getView(), 700, 600);
                Stage stage = (Stage) layout.getScene().getWindow();
                stage.setScene(gameScene);
                stage.centerOnScreen();
            }
        });

        if (ServerUtils.isCurrentUserAdmin()) {
            layout.getChildren().addAll(playGame, manageUsers, back);
        } else {
            layout.getChildren().addAll(playGame, back);
        }

        return layout;
    }

}
