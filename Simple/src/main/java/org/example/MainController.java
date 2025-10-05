package org.example;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController {

    @FXML
    private void initialize() {
        showLoginWindow(); //login ablak
    }

    private void showLoginWindow() {
        Stage loginStage = new Stage();
        loginStage.initModality(Modality.APPLICATION_MODAL);
        loginStage.setTitle("Bejelentkezés");

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
        layout.getChildren().addAll(userLabel, userField, passLabel, passField, loginButton, statusLabel);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Scene scene = new Scene(layout, 300, 200);
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                loginButton.fire();
            }
        });

        loginStage.setScene(scene);
        loginStage.showAndWait();
    }
    @FXML
    private TextField computerField;

    @FXML
    private TextField deviceField;

    @FXML
    private TextField nameField;

    @FXML
    private Button searchButton;



}