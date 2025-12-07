package org.example;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.util.Assets;

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

    @FXML private TabPane tabPane;
    @FXML private Tab dashboardTab;
    @FXML private Tab employeesTab;
    @FXML private Tab computersTab;

    @FXML private Label infoLabel1;
    @FXML private Label infoLabel2;

    @FXML private ImageView logoView;
    @FXML private ImageView dashboardIcon;
    @FXML private ImageView tabpaneEmployeeIcon;
    @FXML private ImageView tabpaneComputerIcon;
    @FXML private ImageView employeeIcon;
    @FXML private ImageView computerIcon;
    @FXML private ImageView peripheralIcon;
    @FXML private ImageView actionAddComputerIcon;
    @FXML private ImageView actionAddEmployeeIcon;
    @FXML private ImageView actionDownloadIcon;
    @FXML private ImageView actionAssignDeviceIcon;

    @FXML
    private void initialize() {
        Tooltip tooltip1 = new Tooltip("Enter your information in the appropriate field, then click the SEARCH button.");
        Tooltip tooltip2= new Tooltip("Enter your PC's specifications in the appropriate field, then click the SEARCH button.");

        Tooltip.install(infoLabel1, tooltip1);
        Tooltip.install(infoLabel2, tooltip2);

        logoView.setImage(Assets.APP_LOGO);
        dashboardIcon.setImage(Assets.DASHBOARD);
        tabpaneEmployeeIcon.setImage(Assets.USER);
        tabpaneComputerIcon.setImage(Assets.COMPUTER);

        employeeIcon.setImage(Assets.USER);
        computerIcon.setImage(Assets.COMPUTER);
        peripheralIcon.setImage(Assets.PERIPHERAL);
        actionAddComputerIcon.setImage(Assets.PLUS);
        actionAddEmployeeIcon.setImage(Assets.USER_PLUS);
        actionDownloadIcon.setImage(Assets.DOWNLOAD);
        actionAssignDeviceIcon.setImage(Assets.ASSIGNDEVICE);

        colEmpId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colEmpName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmpJob.setCellValueFactory(new PropertyValueFactory<>("jobTitle"));

        colPcId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPcBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        colPcVersion.setCellValueFactory(new PropertyValueFactory<>("version"));
        colPcOwner.setCellValueFactory(new PropertyValueFactory<>("ownerName"));
    }

    @FXML
    void showDashboardView(ActionEvent event) {
        tabPane.getSelectionModel().select(dashboardTab);
    }

    @FXML
    void showEmployeeView(ActionEvent event) {
        tabPane.getSelectionModel().select(employeesTab);
    }

    @FXML
    void showPcView(ActionEvent event) {
        tabPane.getSelectionModel().select(computersTab);
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
        popupStage.setTitle("Add Employee");

        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField();

        Label jobLabel = new Label("Job Title:");
        TextField jobField = new TextField();

        Button addButton = new Button("Add");
        addButton.setOnAction(e -> {
            String name = nameField.getText();
            String job = jobField.getText();

            if (name.isEmpty() || job.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText(null);
                alert.setContentText("Please fill in the fields");
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
        popupStage.setTitle("Add PC");

        Label brandLabel = new Label("Brand:");
        TextField brandField = new TextField();

        Label versionLabel = new Label("Version:");
        TextField versionField = new TextField();

        Label ownerLabel = new Label("Owner:");
        TextField ownerField = new TextField();

        Button addButton = new Button("Add");
        addButton.setMaxWidth(Double.MAX_VALUE);

        Runnable addPeripheral = () -> {
            String brand = brandField.getText().trim();
            String version = versionField.getText().trim();
            String owner = ownerField.getText().trim();

            if (brand.isEmpty() || version.isEmpty() || owner.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText(null);
                alert.setContentText("Please fill in the fields");
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