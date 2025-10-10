package org.example;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.util.Assets;

public class MainController {
    SQLQuery sqlQuery = new SQLQuery();

    @FXML
    private void initialize() {
        showLoginWindow(); //login ablak
    }

    private void showLoginWindow() {
        Stage loginStage = new Stage();
        loginStage.initModality(Modality.APPLICATION_MODAL);

        // Logo & Title
        ImageView logoView = new ImageView(Assets.APP_LOGO);
        logoView.setFitWidth(80);
        logoView.setPreserveRatio(true);


        Label titleLabel = new Label("Lentory");
        titleLabel.getStyleClass().add("title");

        Label subtitleLabel = new Label("Inventory Management System");
        subtitleLabel.getStyleClass().add("subtitle");

        // Login Form
        Label userLabel = new Label("Username");
        TextField userField = new TextField();
        userField.setPromptText("Enter your username");

        Label passLabel = new Label("Password");
        PasswordField passField = new PasswordField();
        passField.setPromptText("Enter your password");

        Button loginButton = new Button("Sign in");
        Label statusLabel = new Label();
        Label versionLabel = new Label("v0.0.2");
        versionLabel.getStyleClass().add("version");

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

        // Display
        HBox userBox = new HBox(10, userLabel);
        userBox.setAlignment(Pos.CENTER_LEFT);

        HBox passBox = new HBox(10, passLabel);
        passBox.setAlignment(Pos.CENTER_LEFT);

        VBox layout = new VBox(10);
        layout.getChildren().addAll(logoView, titleLabel, subtitleLabel, userBox, userField, passBox, passField, loginButton, statusLabel, versionLabel);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Scene scene = new Scene(layout, 350, 500);

        scene.getStylesheets().add(getClass().getResource("styles/login.css").toExternalForm());

        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                loginButton.fire();
            }
        });

        loginStage.setTitle("Lentory login");
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