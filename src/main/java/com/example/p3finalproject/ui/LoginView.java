package com.example.p3finalproject.ui;

import com.example.p3finalproject.exceptions.*;
import com.example.p3finalproject.model.Player;
import com.example.p3finalproject.server.ServerUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;


public class LoginView {

    public LoginView() {

    }

    public Parent getView() {
        GridPane layout = new GridPane();

        Label usernameText = new Label("Username");
        TextField usernameField = new TextField();
        Label passwordText = new Label("Password");
        PasswordField passwordField = new PasswordField();
        layout.setAlignment(Pos.CENTER);
        layout.setVgap(10);
        layout.setHgap(10);
        layout.setPadding(new Insets(5, 10, 5, 10));

        Button loginButton = new Button("Log in");
        Button registerButton = new Button("Register");

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);

        Label operationStatus = new Label("");

        HBox hBox = new HBox();
        hBox.getChildren().addAll(loginButton, registerButton);
        hBox.setPadding(new Insets(5, 10, 5, 10));
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(5);

        vBox.getChildren().addAll(hBox, operationStatus);
        vBox.setSpacing(15);

        layout.add(usernameText, 0, 0);
        layout.add(usernameField, 0, 1);
        layout.add(passwordText, 0, 2);
        layout.add(passwordField, 0, 3);
        layout.add(vBox, 0, 5);

        registerButton.setOnAction(e -> {
            Player player = null;

            try {
                player = new Player(usernameField.getText(), passwordField.getText(), false);
            } catch (InvalidPasswordException | InvalidUsernameException | EmptyFieldsException err) {
                operationStatus.setText(err.getMessage());
                operationStatus.setTextFill(Color.valueOf("red"));
            }

            if (player != null) {
                EntityManager entityManager = null;
                EntityTransaction entityTransaction = null;

                try {
                    entityManager = ServerUtils.getEntityManager();
                    entityTransaction = entityManager.getTransaction();
                    entityTransaction.begin();
                    entityManager.persist(player);
                    entityTransaction.commit();
                    operationStatus.setTextFill(Color.valueOf("green"));
                    operationStatus.setText("Registration successful! Log in.");
                } catch (Exception err) {
                    operationStatus.setTextFill(Color.valueOf("red"));
                    operationStatus.setText("Registration failed!");
                    err.printStackTrace();
                    entityTransaction.rollback();
                } finally {
                    entityManager.close();
                }

            }

        });

        loginButton.setOnAction(e -> {
            EntityManager entityManager = null;
            EntityTransaction entityTransaction = null;

            try {
                entityManager = ServerUtils.getEntityManager();
                List<Player> players = ServerUtils.retrievePlayers(entityManager);

                try {
                    int userID = ServerUtils.login(usernameField.getText(),
                            passwordField.getText(), players);
                    ServerUtils.updateCurrentlyLoggedInPlayerID(userID);
                    operationStatus.setText("");

                    Stage stage = (Stage) layout.getScene().getWindow();
                    Parent menuView = new MenuView().getView();
                    Scene menuScene = new Scene(menuView, 400, 300);
                    stage.setScene(menuScene);
                    stage.show();

                } catch (EmptyDatabaseException | IncorrectPasswordException | IncorrectUsernameException err) {
                    operationStatus.setTextFill(Color.valueOf("red"));
                    operationStatus.setText(err.getMessage());
                }
            } catch (Exception err) {
                err.printStackTrace();
                entityTransaction.rollback();
            } finally {
                entityManager.close();
            }

        });

        return layout;
    }


}
