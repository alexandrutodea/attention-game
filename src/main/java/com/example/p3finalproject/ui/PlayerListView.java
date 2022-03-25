package com.example.p3finalproject.ui;

import com.example.p3finalproject.model.Player;
import com.example.p3finalproject.server.ServerUtils;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.hibernate.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class PlayerListView {

    public PlayerListView() {

    }

    public Parent getView() {
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10, 10, 10, 10));

        TableView<Player> tableView = new TableView<Player>();
        tableView.setEditable(true);

        TableColumn<Player, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Player, String> usernameColumn = new TableColumn<Player, String>("Username");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<Player, String> passwordColumn = new TableColumn<Player, String>("Password");
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("maskedPassword"));

        TableColumn<Player, String> adminColumn = new TableColumn<Player, String>("Admin");
        adminColumn.setCellValueFactory(new PropertyValueFactory<>("admin"));

        tableView.getColumns().addAll(usernameColumn, passwordColumn, adminColumn);

        refreshTable(tableView);

        HBox hBox = new HBox();
        Button removeUserButton = new Button("Remove user");
        Button promoteUserButton = new Button("Promote user");
        Button cancelButton = new Button("Cancel");
        hBox.setSpacing(5);

        removeUserButton.setDisable(true);
        promoteUserButton.setDisable(true);

        cancelButton.setOnAction(e -> {
            Stage stage = (Stage) vBox.getScene().getWindow();
            MenuView menuView = new MenuView();
            Scene menuScene = new Scene(menuView.getView(), 400, 300);
            stage.setScene(menuScene);
        });

        tableView.getSelectionModel().selectedItemProperty().addListener((observableValue, player, t1) -> {
            if (tableView.getSelectionModel().getSelectedItem() != null) {
                Player selectedPlayer = tableView.getSelectionModel().getSelectedItem();
                if (selectedPlayer.isAdmin()) {
                    promoteUserButton.setDisable(true);
                    removeUserButton.setDisable(true);
                } else {
                    promoteUserButton.setDisable(false);
                    removeUserButton.setDisable(false);
                }
            }
        });

        removeUserButton.setOnAction(e -> {
            Player player = (Player) tableView.getSelectionModel().getSelectedItem();
            EntityManager entityManager = null;
            EntityTransaction transaction = null;

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Are you sure?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                try {
                    entityManager = ServerUtils.getEntityManager();
                    transaction = entityManager.getTransaction();
                    transaction.begin();
                    Player playerToRemove = entityManager.find(Player.class, player.getId());
                    entityManager.remove(playerToRemove);
                    transaction.commit();
                    refreshTable(tableView);
                } catch (Exception err) {
                    err.printStackTrace();
                    transaction.rollback();
                } finally {
                    entityManager.close();
                }
            }

        });

        promoteUserButton.setOnAction(e -> {
            Player player = (Player) tableView.getSelectionModel().getSelectedItem();
            EntityManager entityManager = null;
            EntityTransaction transaction = null;

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Are you sure?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                try {
                    entityManager = ServerUtils.getEntityManager();
                    transaction = entityManager.getTransaction();
                    transaction.begin();
                    Player playerToUpdate = entityManager.find(Player.class, player.getId());
                    playerToUpdate.setAdmin(true);
                    transaction.commit();
                    refreshTable(tableView);
                } catch (Exception err) {
                    err.printStackTrace();
                    transaction.rollback();
                } finally {
                    entityManager.close();
                }
            }

        });


        hBox.getChildren().addAll(promoteUserButton, removeUserButton, cancelButton);
        vBox.getChildren().addAll(tableView, hBox);
        return vBox;
    }

    private static void refreshTable(TableView tableView) {
        EntityManager entityManager = null;
        Transaction entityTransaction = null;
        tableView.getItems().clear();
        try {
            entityManager = ServerUtils.getEntityManager();
            List<Player> players = ServerUtils.retrievePlayers(entityManager);
            for (Player player : players) {
                tableView.getItems().add(player);
            }
        } catch (Exception e) {
            e.printStackTrace();
            entityTransaction.rollback();
        } finally {
            entityManager.close();
        }

    }

}