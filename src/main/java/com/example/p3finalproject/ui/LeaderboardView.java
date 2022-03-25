package com.example.p3finalproject.ui;
import com.example.p3finalproject.model.Result;
import com.example.p3finalproject.server.ServerUtils;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.Collections;
import java.util.List;

public class LeaderboardView {

    public LeaderboardView() {

    }

    public Parent getView() {
        VBox vBox = new VBox();
        TableView<Result> tableView = new TableView();
        tableView.setEditable(true);

        TableColumn<Result, String> usernameColumn = new TableColumn("Username");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<Result, Long> scoreColumn = new TableColumn("Score");
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));

        TableColumn<Result, String> timeColumn = new TableColumn<>("Time");
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));

        tableView.getColumns().addAll(usernameColumn, scoreColumn, timeColumn);

        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        List<Result> results = null;

        try {
            entityManager = ServerUtils.getEntityManager();
            Query query = entityManager.createQuery("from Result");
            results = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            entityManager.close();
        }

        if (results != null) {
            Collections.sort(results);
            for (int i = 0; i < results.size(); i++) {
                if (i < 10) {
                    tableView.getItems().add(results.get(i));
                } else {
                    break;
                }
            }
        }

        vBox.getChildren().addAll(tableView);
        vBox.setSpacing(5);
        vBox.setPadding(new Insets(10, 10,10, 10));
        return vBox;
    }

}
