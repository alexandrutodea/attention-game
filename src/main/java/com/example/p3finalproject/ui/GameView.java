package com.example.p3finalproject.ui;

import com.example.p3finalproject.model.Board;
import com.example.p3finalproject.model.Player;
import com.example.p3finalproject.model.Result;
import com.example.p3finalproject.server.ServerUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class GameView {

    private Board board;
    private long start;
    private long end;

    public GameView(Board board) {
        this.board = board;
    }

    public Parent getView() {

        VBox mainLayout = new VBox();

        mainLayout.setPadding(new Insets(10, 10, 10, 10));
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setSpacing(30);
        BackgroundFill backgroundFill = new BackgroundFill(Color.valueOf(board.getBackgroundColor()),
                CornerRadii.EMPTY,
                Insets.EMPTY);
        Background background = new Background(backgroundFill);
        mainLayout.setBackground(background);

        Label gameStatusText = new Label("Press Start");
        gameStatusText.setFont(new Font(20));
        BorderPane.setAlignment(gameStatusText, Pos.CENTER);

        gameStatusText.setPadding(new Insets(25, 10, 0, 10));

        Button startButton = new Button("Start");
        startButton.setPrefHeight(40);
        startButton.setPrefWidth(60);
        startButton.setAlignment(Pos.CENTER);

        board.drawNewRandomNumber();
        GridPane boardView = constructBoard(startButton, gameStatusText);

        HBox hBox = new HBox();

        hBox.setAlignment(Pos.CENTER);

        Button leaderboardButton = new Button("Leaderboard");
        leaderboardButton.setPrefHeight(40);
        leaderboardButton.setPrefWidth(100);

        Button backButton = new Button("Back");
        backButton.setPrefHeight(40);
        backButton.setPrefWidth(60);
        hBox.setSpacing(15);

        hBox.getChildren().addAll(startButton, leaderboardButton, backButton);

        backButton.setOnAction(e -> {
            Stage stage = (Stage) mainLayout.getScene().getWindow();
            MenuView menuView = new MenuView();
            Scene menuScene = new Scene(menuView.getView(), 400, 300);
            stage.setScene(menuScene);
            stage.centerOnScreen();
        });

        startButton.setOnAction(e -> {
            start = System.currentTimeMillis() / 1000;
            board.drawNewRandomNumber();
            startButton.setDisable(true);
            gameStatusText.setText("Find number " + board.getCurrentRandomNumber());
            mainLayout.getChildren().set(1, constructBoard(startButton, gameStatusText));
        });

        leaderboardButton.setOnAction(e -> {
            LeaderboardView leaderboardView = new LeaderboardView();
            Scene leaderboardScene = new Scene(leaderboardView.getView(), 400, 300);
            Stage stage = new Stage();
            stage.setTitle("Top 10 Players");
            stage.setScene(leaderboardScene);
            stage.show();
        });

        mainLayout.getChildren().addAll(gameStatusText, boardView, hBox);

        return mainLayout;

    }

    private GridPane constructBoard(Button startButton, Label gameStatusText) {
        GridPane gridPane = new GridPane();

        gridPane.setAlignment(Pos.CENTER);

        int col = 0;
        int row = 0;

        gridPane.setVgap(3);
        gridPane.setHgap(3);

        for (int i = 0; i < board.getNumberOfButtons(); i++) {
            if (col > 10) {
                col = 0;
                row++;
            }
            Button button = new Button(String.valueOf(board.getRandomNumbers().get(i)));
            button.setPrefHeight(35);
            button.setPrefWidth(35);

            button.setOnAction(e -> {
                int buttonNumber = Integer.parseInt(button.getText());
                if (buttonNumber == board.getCurrentRandomNumber()) {
                    System.out.println("Yes");
                    end = System.currentTimeMillis() / 1000;
                    long score = end - start;
                    gameStatusText.setText("Score: " + score);

                    EntityManager entityManager = null;
                    EntityTransaction transaction = null;

                    try {
                        entityManager = ServerUtils.getEntityManager();
                        transaction = entityManager.getTransaction();
                        transaction.begin();
                        Player playerToUpdate = entityManager.find(Player.class, ServerUtils.retrieveCurrentlyLoggedInPlayerID());
                        Result result = new Result(playerToUpdate, score);
                        entityManager.persist(result);
                        playerToUpdate.addResult(result);
                        transaction.commit();
                    } catch (Exception err) {
                        err.printStackTrace();
                        transaction.rollback();
                    } finally {
                        entityManager.close();
                    }

                    startButton.setDisable(false);
                    startButton.setText("Retry");
                }
            });

            gridPane.add(button, col, row);
            col++;
        }

        return gridPane;
    }

}
