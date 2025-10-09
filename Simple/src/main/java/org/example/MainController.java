package org.example;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController {
    SQLQuery sqlQuery = new SQLQuery();

    @FXML
    private void initialize() {
        showLoginWindow(); //login ablak
    }

    private void showLoginWindow() {
        Stage loginStage = new Stage();
        loginStage.initModality(Modality.APPLICATION_MODAL);
        loginStage.setTitle("Bejelentkezés");

        // Logo & Title
        Label titleLabel = new Label("Lentory");
        titleLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #2F89FC;");

        Label subtitleLabel = new Label("Inventory Management System");
        subtitleLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #888;");

        // Login Form
        Label userLabel = new Label("Felhasználónév:");
        TextField userField = new TextField();

        Label passLabel = new Label("Jelszó:");
        PasswordField passField = new PasswordField();

        Button loginButton = new Button("Bejelentkezés");
        Label statusLabel = new Label();

        loginStage.setOnCloseRequest(e -> {
                    System.exit(0);
        });

        loginButton.setOnAction(e -> {
            String user = userField.getText();
            String pass = passField.getText();

            if (user.equals("admin") && pass.equals("1234")) {
                loginStage.close();
            } else {
                statusLabel.setText("Hibás felhasználónév vagy jelszó!");
            }
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(titleLabel, subtitleLabel, userLabel, userField, passLabel, passField, loginButton, statusLabel);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Scene scene = new Scene(layout, 300, 400);

        scene.getStylesheets().add(getClass().getResource("login.css").toExternalForm());

        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                loginButton.fire();
            }
        });

        loginStage.setScene(scene);
        loginStage.showAndWait();
    }

    @FXML
    private TextField jobTitleField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField idField;

    @FXML
    private TextField brandfield;

    @FXML
    private TextField ownerfield;

    @FXML
    private TextField pcIDfield;

    @FXML
    private TextField versionfield;

    @FXML
    void PeripherialSearchButton() {
        System.out.println(sqlQuery.searchPc(pcIDfield.getText(), brandfield.getText(), versionfield.getText(),ownerfield.getText()));
    }

    @FXML
    void employeeSearchButton() {
        System.out.println(sqlQuery.searchEmployee(idField.getText(), nameField.getText(), jobTitleField.getText()));
    }
    @FXML
    void employeeAddButton() {
        sqlQuery.addEmployee("Teszt Pista", "HR");
        sqlQuery.addPC("21", "Dell", "Vostro 3070", "Teszt Pista");
    }


}