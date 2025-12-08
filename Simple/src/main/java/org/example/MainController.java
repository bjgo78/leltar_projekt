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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.example.util.Assets;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class MainController {
    SQLQuery sqlQuery = new SQLQuery();

    @FXML
    private Label employeeCount;
    @FXML
    private Label pcCount;
    @FXML
    private Label peripheralCount;
    @FXML
    private TableView<Employee> employeeTable;
    @FXML
    private TableColumn<Employee, Integer> colEmpId;
    @FXML
    private TableColumn<Employee, String> colEmpName;
    @FXML
    private TableColumn<Employee, String> colEmpJob;

    @FXML private TableColumn<Employee, Void> colEmpEdit;
    @FXML private TableColumn<Employee, Void> colEmpDelete;
    @FXML private TableColumn<PC, Void> colPcEdit;
    @FXML private TableColumn<PC, Void> colPcDelete;

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
    @FXML private ImageView tabpaneEmployeesIcon;
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

        addEmployeeEditButtonToTable();
        addEmployeeDeleteButtonToTable();
        addPcEditButtonToTable();
        addPcDeleteButtonToTable();

        logoView.setImage(Assets.APP_LOGO);
        dashboardIcon.setImage(Assets.DASHBOARD);
        tabpaneEmployeesIcon.setImage(Assets.EMPLOYEES);
        tabpaneComputerIcon.setImage(Assets.COMPUTER);

        employeeIcon.setImage(Assets.EMPLOYEE);
        computerIcon.setImage(Assets.COMPUTER);
        peripheralIcon.setImage(Assets.PERIPHERAL);
        actionAddComputerIcon.setImage(Assets.PLUS);
        actionAddEmployeeIcon.setImage(Assets.EMPLOYEE_PLUS);
        actionDownloadIcon.setImage(Assets.DOWNLOAD);
        actionAssignDeviceIcon.setImage(Assets.ASSIGNDEVICE);

        colEmpId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colEmpName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmpJob.setCellValueFactory(new PropertyValueFactory<>("jobTitle"));

        colPcId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPcBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        colPcVersion.setCellValueFactory(new PropertyValueFactory<>("version"));
        colPcOwner.setCellValueFactory(new PropertyValueFactory<>("ownerName"));
        setCounters();
    }

    private void addPcEditButtonToTable() {
        colPcEdit.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = new Button("Edit");

            {
                editButton.setOnAction(event -> {
                    PC pc = getTableView().getItems().get(getIndex());
                    showPcEditPopup(pc);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(editButton);
                }
            }
        });
    }

    private void addPcDeleteButtonToTable() {
        colPcDelete.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Delete");

            {
                deleteButton.setOnAction(event -> {
                    PC pc = getTableView().getItems().get(getIndex());
                    sqlQuery.deletePC(pc.getId()); // SQLQuery-ben írjuk meg a delete metódust
                    pcTable.getItems().remove(pc);
                    setCounters();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });
    }

    private void showPcEditPopup(PC pc) {
        Stage popupStage = new Stage();
        popupStage.setTitle("Edit PC");

        TextField brandField = new TextField(pc.getBrand());
        TextField versionField = new TextField(pc.getVersion());
        TextField ownerField = new TextField(pc.getOwnerName());

        Button saveButton = new Button("Save");

        saveButton.setOnAction(e -> {
            String newBrand = brandField.getText().trim();
            String newVersion = versionField.getText().trim();
            String newOwner = ownerField.getText().trim();
            if (!newBrand.isEmpty() && !newVersion.isEmpty() && !newOwner.isEmpty()) {
                sqlQuery.updatePC(pc.getId(), newBrand, newVersion, newOwner); // SQLQuery-ben írjuk meg az update metódust
                pc.setBrand(newBrand);
                pc.setVersion(newVersion);
                pc.setOwnerName(newOwner);
                pcTable.refresh();
                popupStage.close();
            }
        });

        VBox layout = new VBox(10,
                new Label("Brand:"), brandField,
                new Label("Version:"), versionField,
                new Label("Owner:"), ownerField,
                saveButton
        );
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(15));

        Scene scene = new Scene(layout, 320, 300);
        popupStage.setScene(scene);
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.showAndWait();
    }

    private void addEmployeeEditButtonToTable() {
        colEmpEdit.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = new Button("Edit");

            {
                editButton.setOnAction(event -> {
                    Employee emp = getTableView().getItems().get(getIndex());
                    showEmployeeEditPopup(emp);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(editButton);
                }
            }
        });
    }

    private void addEmployeeDeleteButtonToTable() {
        colEmpDelete.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Delete");

            {
                deleteButton.setOnAction(event -> {
                    Employee emp = getTableView().getItems().get(getIndex());
                    sqlQuery.deleteEmployee(emp.getId());
                    employeeTable.getItems().remove(emp);
                    setCounters();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });
    }

    private void showEmployeeEditPopup(Employee emp) {
        Stage popupStage = new Stage();
        popupStage.setTitle("Edit Employee");

        TextField nameField = new TextField(emp.getName());
        TextField jobField = new TextField(emp.getJobTitle());
        Button saveButton = new Button("Save");

        saveButton.setOnAction(e -> {
            String newName = nameField.getText().trim();
            String newJob = jobField.getText().trim();
            if (!newName.isEmpty() && !newJob.isEmpty()) {
                sqlQuery.updateEmployee(emp.getId(), newName, newJob);
                emp.setName(newName);
                emp.setJobTitle(newJob);
                employeeTable.refresh();
                popupStage.close();
            }
        });

        VBox layout = new VBox(10, new Label("Name:"), nameField, new Label("Job Title:"), jobField, saveButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(15));

        Scene scene = new Scene(layout, 300, 250);
        popupStage.setScene(scene);
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.showAndWait();
    }

    @FXML
    void showDashboardView(ActionEvent event) {
        setCounters();
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

    void setCounters(){
        employeeCount.setText(String.valueOf(sqlQuery.getEmployeeCount()));
        pcCount.setText(String.valueOf(sqlQuery.getPCCount()));
        peripheralCount.setText(String.valueOf(sqlQuery.getPeripheralCount()));
    }
    @FXML
    void PCSearchButton() {
        ObservableList<PC> data = sqlQuery.searchPc(pcIDfield.getText(), brandfield.getText(), versionfield.getText(), ownerfield.getText());
        pcTable.setItems(data);
    }

    @FXML
    void employeeSearchButton() {
        ObservableList<Employee> data = sqlQuery.searchEmployee(idField.getText(), nameField.getText(), jobTitleField.getText());
        employeeTable.setItems(data);
    }

    @FXML
    void employeeAddButton() {
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
    void pcAdd() {
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

        Runnable addPC = () -> {
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

        addButton.setOnAction(e -> addPC.run());
        brandField.setOnAction(e -> addPC.run());
        versionField.setOnAction(e -> addPC.run());
        ownerField.setOnAction(e -> addPC.run());

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

    void peripheralAdd() {
        Stage popupStage = new Stage();
        popupStage.setTitle("Add Peripheral");

        Label brandLabel = new Label("Brand:");
        TextField brandField = new TextField();

        Label versionLabel = new Label("Version:");
        TextField versionField = new TextField();

        Label pcIDLabel = new Label("PC ID:");
        TextField pcIDField = new TextField();

        Button addButton = new Button("Add");
        addButton.setMaxWidth(Double.MAX_VALUE);

        Runnable addPeripheral = () -> {
            String brand = brandField.getText().trim();
            String version = versionField.getText().trim();
            String pcid = pcIDField.getText().trim();

            if (brand.isEmpty() || version.isEmpty() || pcid.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText(null);
                alert.setContentText("Please fill in the fields");
                alert.showAndWait();
            } else {
                sqlQuery.addPeripheral(brand, version, pcid);
                popupStage.close();
            }
        };

        addButton.setOnAction(e -> addPeripheral.run());
        brandField.setOnAction(e -> addPeripheral.run());
        versionField.setOnAction(e -> addPeripheral.run());
        pcIDField.setOnAction(e -> addPeripheral.run());

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        layout.getStyleClass().add("pop-up-root");
        layout.getChildren().addAll(brandLabel, brandField, versionLabel, versionField, pcIDLabel, pcIDField, new Label(""), addButton);
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

    @FXML
    void quickAddEmployee(MouseEvent event) {
        employeeAddButton();
    }
    @FXML
    void quickAddComputer(MouseEvent event) {
        pcAdd();
    }

    @FXML
    void quickAddDevice(MouseEvent event) {
        peripheralAdd();
    }

    @FXML
    void quickExportCSV(MouseEvent event) {
        //System.out.println(sqlQuery.exportCSV());
        saveCSV(dashboardTab.getTabPane().getScene().getWindow());

    }
    public void saveCSV(Window ownerWindow) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save CSV File");

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showSaveDialog(ownerWindow);

        if (file != null) {
            if (!file.getName().toLowerCase().endsWith(".csv")) {
                file = new File(file.getAbsolutePath() + ".csv");
            }

            try (FileOutputStream fos = new FileOutputStream(file);
                 OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
                 BufferedWriter writer = new BufferedWriter(osw)) {

                // Write UTF-8 BOM so Excel on Windows will detect UTF-8
                fos.write(0xEF);
                fos.write(0xBB);
                fos.write(0xBF);
                
                writer.write(sqlQuery.exportCSV());
                writer.flush();


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}