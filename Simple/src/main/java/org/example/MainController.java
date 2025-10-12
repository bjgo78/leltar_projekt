package org.example;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class MainController {
    SQLQuery sqlQuery = new SQLQuery();

    @FXML
    private void initialize() {
        showLoginWindow(); //login ablak
        Tooltip tooltip1 = new Tooltip("A megfelelő mezőbe ird be az adataid majd KERESÉS gomb.");
        Tooltip tooltip2= new Tooltip("A megfelelő mezőbe ird be a perifériáid tulajdonságait majd KERESÉS gomb.");

        Tooltip.install(infoLabel1, tooltip1);
        Tooltip.install(infoLabel2, tooltip2);
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

        Scene scene = new Scene(layout, 300, 250);

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
    private Label infoLabel1;

    @FXML
    private Label infoLabel2;



    @FXML
    void PeripherialSearchButton() {
        System.out.println(sqlQuery.searchPc(pcIDfield.getText(), brandfield.getText(), versionfield.getText(),ownerfield.getText()));
    }

    @FXML
    void employeeSearchButton() {
        System.out.println(sqlQuery.searchEmployee(idField.getText(), nameField.getText(), jobTitleField.getText()));
    }
    @FXML
    void employeeAddButton(ActionEvent event) {
        Stage popupStage = new Stage();
        popupStage.setTitle("Dolgozó hozzáadása");

        Label nameLabel = new Label("Név:");
        TextField nameField = new TextField();

        Label jobLabel = new Label("Beosztása:");
        TextField jobField = new TextField();

        Button addButton = new Button("Hozzáad");
        addButton.setOnAction(e -> {
            String name = nameField.getText();
            String job = jobField.getText();

            if (name.isEmpty() || job.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText(null);
                alert.setContentText("Töltse ki a mezőket");
                alert.showAndWait();
            } else {
                System.out.println(name +" "+ job);
                //sqlQuery.addEmployee("Teszt Pista", "HR");
                //sqlQuery.addPC("Dell", "Vostro 3070", "Teszt Pista");
                popupStage.close();
            }
        });


        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        layout.getChildren().addAll(nameLabel, nameField, jobLabel, jobField, addButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 300, 220);

        scene.getStylesheets().add(getClass().getResource("login.css").toExternalForm());

        popupStage.setScene(scene);
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.showAndWait();

    }


}