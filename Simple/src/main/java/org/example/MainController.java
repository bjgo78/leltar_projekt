package org.example;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController {
    SQLQuery sqlQuery = new SQLQuery();

    @FXML
    private TableView<Employee> employeeTable;
    @FXML
    private TableColumn<Employee, Integer> colEmpId;
    @FXML
    private TableColumn<Employee, String> colEmpName;
    @FXML
    private TableColumn<Employee, String> colEmpJob;

    @FXML
    private TableView<PC> pcTable;
    @FXML
    private TableColumn<PC, Integer> colPcId;
    @FXML
    private TableColumn<PC, String> colPcBrand;
    @FXML
    private TableColumn<PC, String> colPcVersion;
    @FXML
    private TableColumn<PC, String> colPcOwner;

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
    private AnchorPane employeePane;
    @FXML
    private AnchorPane pcPane;

    @FXML
    private void initialize() {
        colEmpId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colEmpName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmpJob.setCellValueFactory(new PropertyValueFactory<>("jobTitle"));

        colPcId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPcBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        colPcVersion.setCellValueFactory(new PropertyValueFactory<>("version"));
        colPcOwner.setCellValueFactory(new PropertyValueFactory<>("ownerName"));
    }

    @FXML
    void showEmployeeView(ActionEvent event) {
        employeePane.setVisible(true);
        pcPane.setVisible(false);
    }

    @FXML
    void showPcView(ActionEvent event) {
        employeePane.setVisible(false);
        pcPane.setVisible(true);
    }

    @FXML
    void PeripherialSearchButton() {
        ObservableList<PC> data = sqlQuery.searchPc(pcIDfield.getText(), brandfield.getText(), versionfield.getText(), ownerfield.getText());
        pcTable.setItems(data);
    }

    @FXML
    void employeeSearchButton() {
        ObservableList<Employee> data = sqlQuery.searchEmployee(idField.getText(), nameField.getText(), jobTitleField.getText());
        employeeTable.setItems(data);
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
                sqlQuery.addEmployee(name, job);
                popupStage.close();
            }
        });

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        layout.getStyleClass().add("pop-up-root");
        layout.getChildren().addAll(nameLabel, nameField, jobLabel, jobField, addButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 300, 280);
        try {
            scene.getStylesheets().add(getClass().getResource("styles/style.css").toExternalForm());
        } catch (Exception e) {
            e.printStackTrace();
        }

        popupStage.setScene(scene);
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.showAndWait();
    }

    @FXML
    void pcAdd(ActionEvent event) {
        Stage popupStage = new Stage();
        popupStage.setTitle("PC hozzáadása");

        Label brandLabel = new Label("Márka:");
        TextField brandField = new TextField();

        Label versionLabel = new Label("Verzió:");
        TextField versionField = new TextField();

        Label ownerLabel = new Label("Tulajdonos:");
        TextField ownerField = new TextField();

        Button addButton = new Button("PC hozzáadása");
        addButton.setMaxWidth(Double.MAX_VALUE);

        Runnable addPeripheral = () -> {
            String brand = brandField.getText().trim();
            String version = versionField.getText().trim();
            String owner = ownerField.getText().trim();

            if (brand.isEmpty() || version.isEmpty() || owner.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText(null);
                alert.setContentText("Töltse ki a mezőket");
                alert.showAndWait();
            } else {
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
        layout.getStyleClass().add("pop-up-root");
        layout.getChildren().addAll(brandLabel, brandField, versionLabel, versionField, ownerLabel, ownerField, new Label(""), addButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 320, 400);
        try {
            scene.getStylesheets().add(getClass().getResource("styles/style.css").toExternalForm());
        } catch (Exception e) {
            e.printStackTrace();
        }

        popupStage.setScene(scene);
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.showAndWait();
    }
}