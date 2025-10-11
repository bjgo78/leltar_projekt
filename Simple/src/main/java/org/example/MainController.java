package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.*;


public class MainController {
    SQLQuery sqlQuery = new SQLQuery();

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