package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.*;import vafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class MainController {
    SQLQuery sqlQuery = new    @FXL
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
            rintln(sqlQuery.searchPc(pcIDfi
            

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
                //System.out.println(name +" "+ job);
                sqlQuery.addEmployee(name, job);

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
    @FXML
    void peripheralAdd(ActionEvent event) {
        Stage popupStage = new Stage();
        popupStage.setTitle("Periféria hozzáadása");

        Label brandLabel = new Label("Márka:");
        TextField brandField = new TextField();



                
        Button addButton = new Button("Periféria hozzáadása");

                
        Runnable addPeripheral = () -> {
            String brand = brandField.getText().trim();
            String version = versionField.getText().trim();
     

    

            if (brand.isEmpty() || version.isEmpty() || owner.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText(null);
                alert.setContentText("Töltse ki a mezőket");
                alert.showAndWait();
            } else {
                //System.out.println(brand + " " + version + " " + owner);
                sqlQuery.addPC(brand, version, owner);
                popupStage.close();
            }
        };

        addButton.setOnAction(e -> addPeripheral.run());
        brandField.setOnAction(e -> addPeripheral.run());
        versionField.setOnAction(e -> addPeripheral.run());
        ownerField.setOnAction(e -> addPeripheral.run());

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        layout.getChildren().addAll(
                brandLabel, brandField,
                ve rsionLabel, versionField,
                ow nerLabel, ownerField,
                addButton
        );
        layout.setAlignment(Pos.CENTER);



        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.showAndWait();

    }


}

     



     

