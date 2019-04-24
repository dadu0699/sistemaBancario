package org.didierdominguez.view.admin;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.didierdominguez.bean.Employee;
import org.didierdominguez.controller.ControllerEmployee;
import org.didierdominguez.util.ScreenSize;
import org.didierdominguez.view.Alert;

public class CRUDEmployeeHeadquarters {
}

class CreateEmployeeHeadquarters {
    private static CreateEmployeeHeadquarters instance;

    private CreateEmployeeHeadquarters() {}

    static CreateEmployeeHeadquarters getInstance() {
        if (instance == null) {
            instance = new CreateEmployeeHeadquarters();
        }
        return instance;
    }

    public GridPane getGridPane() {
        GridPane gridPane = new GridPane();
        RequiredFieldValidator validator = new RequiredFieldValidator();

        double x = ScreenSize.getInstance().getX();
        double y = ScreenSize.getInstance().getY();

        gridPane.setVgap(25);
        gridPane.setPadding(new Insets(20));
        // gridPane.setGridLinesVisible(true);

        Text textTitle = new Text("AGREGAR");
        textTitle.getStyleClass().add("textTitle");
        textTitle.setFont(new Font(25));
        gridPane.add(textTitle, 0, 3, 2, 1);

        JFXTextField fieldName = new JFXTextField();
        fieldName.setPromptText("NOMBRE");
        fieldName.setLabelFloat(true);
        fieldName.setPrefWidth(x);
        fieldName.getValidators().add(validator);
        fieldName.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                fieldName.validate();
            }
        });
        gridPane.add(fieldName, 0, 4, 2, 1);

        JFXTextField fieldPhoneNumber = new JFXTextField();
        fieldPhoneNumber.setPromptText("NÚMERO TELEFÓNICO");
        fieldPhoneNumber.setLabelFloat(true);
        fieldPhoneNumber.setPrefWidth(x);
        fieldPhoneNumber.getValidators().add(validator);
        fieldPhoneNumber.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                fieldPhoneNumber.validate();
            }
        });
        gridPane.add(fieldPhoneNumber, 0, 5, 2, 1);

        String[] options = {"GERENCIA", "MARKETING", "INFORMÁTICA", "FINANCIERO", "RECLAMOS", "COBROS"};
        ObservableList observableList = FXCollections.observableArrayList(options);
        ObservableList<String> data = observableList;
        JFXComboBox<String> comboBoxDepartment = new JFXComboBox<>(data);
        comboBoxDepartment.setPromptText("DEPARTAMENTO");
        comboBoxDepartment.setLabelFloat(true);
        comboBoxDepartment.setPrefWidth(x);
        gridPane.add(comboBoxDepartment, 0, 6, 2, 1);

        JFXButton buttonAdd = new JFXButton("Agregar");
        buttonAdd.getStyleClass().addAll("customButton", "primaryButton");
        buttonAdd.setButtonType(JFXButton.ButtonType.FLAT);
        buttonAdd.setPrefSize(x, y / 20);
        buttonAdd.setOnAction(event -> {
            if (fieldName.getText().length() == 0
                    || fieldPhoneNumber.getText().length() == 0
                    || !fieldPhoneNumber.getText().matches("^(?=(?:[1-9]){1})\\d{4}-\\d{4}")
                    || comboBoxDepartment.getSelectionModel().getSelectedItem() == null) {
                Alert.getInstance().showAlert(gridPane, "ERROR", "UNO O MÁS DATOS SON INCORRECTOS");
            } else {
                Employee searchNameEmployee = ControllerEmployee.getInstance()
                        .searchEmployee(fieldName.getText().trim().toUpperCase());
                if (searchNameEmployee != null) {
                    Alert.getInstance().showAlert(gridPane, "ERROR", "EL EMPLEADO YA EXISTE");
                } else {
                    ControllerEmployee.getInstance().createEmployee(fieldName.getText().trim().toUpperCase(),
                            fieldPhoneNumber.getText().trim(),
                            comboBoxDepartment.getSelectionModel().getSelectedItem());
                    ViewHeadquarters.getInstance().restartHBox();
                }
            }
        });
        gridPane.add(buttonAdd, 0, 7);

        JFXButton buttonCancel = new JFXButton("Cancelar");
        buttonCancel.getStyleClass().addAll("customButton", "dangerButton");
        buttonCancel.setButtonType(JFXButton.ButtonType.FLAT);
        buttonCancel.setPrefSize(x, y / 20);
        buttonCancel.setOnAction(event -> ViewHeadquarters.getInstance().restartHBox());
        gridPane.add(buttonCancel, 1, 7);

        return gridPane;
    }
}

class UpdateEmployeeHeadquarters{
    private static UpdateEmployeeHeadquarters instance;

    private UpdateEmployeeHeadquarters(){}

    static UpdateEmployeeHeadquarters getInstance() {
        if (instance == null) {
            instance = new UpdateEmployeeHeadquarters();
        }
        return instance;
    }

    GridPane getGridPane(Employee employee) {
        GridPane gridPane = new GridPane();
        RequiredFieldValidator validator = new RequiredFieldValidator();

        double x = ScreenSize.getInstance().getX();
        double y = ScreenSize.getInstance().getY();

        gridPane.setVgap(25);
        gridPane.setPadding(new Insets(20));
        // gridPane.setGridLinesVisible(true);

        Text textTitle = new Text("MODIFICAR");
        textTitle.getStyleClass().add("textTitle");
        textTitle.setFont(new Font(25));
        gridPane.add(textTitle, 0, 3, 2, 1);

        JFXTextField fieldName = new JFXTextField(employee.getName());
        fieldName.setPromptText("NOMBRE");
        fieldName.setLabelFloat(true);
        fieldName.setPrefWidth(x);
        fieldName.getValidators().add(validator);
        fieldName.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                fieldName.validate();
            }
        });
        gridPane.add(fieldName, 0, 4, 2, 1);

        JFXTextField fieldPhoneNumber = new JFXTextField(employee.getPhoneNumber());
        fieldPhoneNumber.setPromptText("NÚMERO TELEFÓNICO");
        fieldPhoneNumber.setLabelFloat(true);
        fieldPhoneNumber.setPrefWidth(x);
        fieldPhoneNumber.getValidators().add(validator);
        fieldPhoneNumber.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                fieldPhoneNumber.validate();
            }
        });
        gridPane.add(fieldPhoneNumber, 0, 5, 2, 1);

        String[] options = {"GERENCIA", "MARKETING", "INFORMÁTICA", "FINANCIERO", "RECLAMOS", "COBROS"};
        ObservableList observableList = FXCollections.observableArrayList(options);
        ObservableList<String> data = observableList;
        JFXComboBox<String> comboBoxDepartment = new JFXComboBox<>(data);
        comboBoxDepartment.setPromptText("DEPARTAMENTO");
        comboBoxDepartment.setValue((String) employee.getJob());
        comboBoxDepartment.setLabelFloat(true);
        comboBoxDepartment.setPrefWidth(x);
        gridPane.add(comboBoxDepartment, 0, 6, 2, 1);

        JFXButton buttonUpdate = new JFXButton("Modificar");
        buttonUpdate.getStyleClass().addAll("customButton", "primaryButton");
        buttonUpdate.setButtonType(JFXButton.ButtonType.FLAT);
        buttonUpdate.setPrefSize(x, y / 20);
        buttonUpdate.setOnAction(event -> {
            if (fieldName.getText().length() == 0
                    || fieldPhoneNumber.getText().length() == 0
                    || !fieldPhoneNumber.getText().matches("^(?=(?:[1-9]){1})\\d{4}-\\d{4}")) {
                Alert.getInstance().showAlert(gridPane, "ERROR", "UNO O MÁS DATOS SON INCORRECTOS");
            } else {
                ControllerEmployee.getInstance().updateEmployee(employee.getId(),
                        fieldName.getText().trim().toUpperCase(),
                        fieldPhoneNumber.getText().trim(),
                        comboBoxDepartment.getSelectionModel().getSelectedItem());
                if (!ControllerEmployee.getInstance().updateEmployee()){
                    Alert.getInstance().showAlert(gridPane, "ERROR", "ERROR AL MODIFICAR EMPLEADO");
                } else {
                    ViewHeadquarters.getInstance().restartHBox();
                }
            }
        });
        gridPane.add(buttonUpdate, 0, 7);

        JFXButton buttonCancel = new JFXButton("Cancelar");
        buttonCancel.getStyleClass().addAll("customButton", "dangerButton");
        buttonCancel.setButtonType(JFXButton.ButtonType.FLAT);
        buttonCancel.setPrefSize(x, y / 20);
        buttonCancel.setOnAction(event -> ViewHeadquarters.getInstance().restartHBox());
        gridPane.add(buttonCancel, 1, 7);

        return gridPane;
    }
}
