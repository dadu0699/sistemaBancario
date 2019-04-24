package org.didierdominguez.view.transaction;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.didierdominguez.bean.MonetaryAccount;
import org.didierdominguez.controller.ControllerMonetaryAccount;
import org.didierdominguez.controller.ControllerServicePay;
import org.didierdominguez.util.ScreenSize;
import org.didierdominguez.util.Verifications;
import org.didierdominguez.view.Alert;

public class ViewServicePay extends Stage {
    private static ViewServicePay instance;

    private ViewServicePay() {}

    public static ViewServicePay getInstance() {
        if (instance == null) {
            instance = new ViewServicePay();
        }
        return instance;
    }

    GridPane getGridPane() {
        GridPane gridPane = new GridPane();
        RequiredFieldValidator validator = new RequiredFieldValidator();

        double x = ScreenSize.getInstance().getX();
        double y = ScreenSize.getInstance().getY();

        gridPane.setVgap(25);
        gridPane.setPadding(new Insets(20));
        // gridPane.setGridLinesVisible(true);

        Text textTitle = new Text("PAGO DE SERVICIOS");
        textTitle.getStyleClass().add("textTitle");
        textTitle.setFont(new Font(25));
        gridPane.add(textTitle, 0, 3, 2, 1);

        String[] optionsService = {"Agua", "Luz", "Teléfono"};
        ObservableList observableListService = FXCollections.observableArrayList(optionsService);
        ObservableList<String> dataService = observableListService;
        JFXComboBox<String> comboBoxService = new JFXComboBox<>(dataService);
        comboBoxService.setPromptText("SERVICIO");
        comboBoxService.setLabelFloat(true);
        comboBoxService.setPrefWidth(x);
        gridPane.add(comboBoxService, 0, 4, 2, 1);

        Label labelAmount = new Label("MONTO:");
        gridPane.add(labelAmount, 0, 5);
        Spinner<Double> spinnerAmount = new Spinner<>(0.00, 100000.00, 0, 1);
        spinnerAmount.setEditable(true);
        spinnerAmount.setPrefWidth(x);
        gridPane.add(spinnerAmount, 1, 5);

        String[] options = {"Efectivo", "Cheque"};
        ObservableList observableList = FXCollections.observableArrayList(options);
        ObservableList<String> data = observableList;
        JFXComboBox<String> comboBoxDepositType = new JFXComboBox<>(data);
        comboBoxDepositType.setPromptText("TIPO DE PAGO");
        comboBoxDepositType.setLabelFloat(true);
        comboBoxDepositType.setPrefWidth(x);
        JFXTextField fieldRetirementAccount = new JFXTextField();
        fieldRetirementAccount.setPromptText("NO. DE CUENTA DEL RETIRO");
        fieldRetirementAccount.setLabelFloat(true);
        fieldRetirementAccount.setPrefWidth(x);
        fieldRetirementAccount.getValidators().add(validator);
        fieldRetirementAccount.focusedProperty().addListener((observables, oldValue, newValue) -> {
            if (!newValue) {
                fieldRetirementAccount.validate();
            }
        });

        JFXButton buttonAdd = new JFXButton("Aceptar");
        buttonAdd.getStyleClass().addAll("customButton", "primaryButton");
        buttonAdd.setButtonType(JFXButton.ButtonType.FLAT);
        buttonAdd.setPrefSize(x, y / 20);
        buttonAdd.setOnAction(event -> {
            MonetaryAccount retirementMonetaryAccount = ControllerMonetaryAccount.getInstance().searchAccount(fieldRetirementAccount.getText());

            if (comboBoxService.getSelectionModel().getSelectedItem() == null
                    || comboBoxDepositType.getSelectionModel().getSelectedItem() == null
                    || !Verifications.getInstance().isNumeric(spinnerAmount.getEditor().getText())
                    || Double.parseDouble(spinnerAmount.getEditor().getText()) <= 0) {
                Alert.getInstance().showAlert(gridPane, "ERROR", "UNO O MÁS DATOS SON INCORRECTOS");
            } else if (retirementMonetaryAccount == null
                    && comboBoxDepositType.getSelectionModel().getSelectedItem().equals("Cheque")) {
                Alert.getInstance().showAlert(gridPane, "ERROR", "LA CUENTA DE RETIRO NO FUE ENCONTRADA");
            } else {
                if (comboBoxDepositType.getSelectionModel().getSelectedItem().equals("Efectivo")) {
                    ControllerServicePay.getInstance().createServicePay(
                            comboBoxDepositType.getSelectionModel().getSelectedItem(),
                            "Efectivo",
                            TransactionPanel.getInstance().getCustomer(),
                            Double.parseDouble(spinnerAmount.getEditor().getText()));
                    fieldRetirementAccount.clear();
                    Alert.getInstance().showAlert(gridPane, "DEPOSITO", "PAGO REALIZADO");
                } else {
                    if (retirementMonetaryAccount.getCheck() > 0 && retirementMonetaryAccount.getStartingAmount() > 0
                            && retirementMonetaryAccount.getStartingAmount()
                            > Double.parseDouble(spinnerAmount.getEditor().getText())) {
                        ControllerServicePay.getInstance().createServicePay(
                                comboBoxDepositType.getSelectionModel().getSelectedItem(),
                                "Cheque",
                                TransactionPanel.getInstance().getCustomer(),
                                Double.parseDouble(spinnerAmount.getEditor().getText()));
                        ControllerMonetaryAccount.getInstance().updateAccount(
                                retirementMonetaryAccount.getId(),
                                (Double.parseDouble(spinnerAmount.getEditor().getText()) * -1), true
                        );
                        fieldRetirementAccount.clear();
                        Alert.getInstance().showAlert(gridPane, "DEPOSITO", "PAGO REALIZADO");
                    } else {
                        Alert.getInstance().showAlert(gridPane, "ERROR", "LA CUENTA DE RETIRO NO " +
                                "POSEE CHEQUES O FONDOS SUFICIENTES");
                    }
                }
            }
        });
        gridPane.add(buttonAdd, 0, 7);

        JFXButton buttonCancel = new JFXButton("Cancelar");
        buttonCancel.getStyleClass().addAll("customButton", "dangerButton");
        buttonCancel.setButtonType(JFXButton.ButtonType.FLAT);
        buttonCancel.setPrefSize(x, y / 20);
        buttonCancel.setOnAction(event -> {
            fieldRetirementAccount.clear();
        });
        gridPane.add(buttonCancel, 1, 7);

        comboBoxDepositType.valueProperty().addListener(observable -> {
            if (gridPane.getChildren().contains(buttonAdd) && gridPane.getChildren().contains(buttonCancel)) {
                gridPane.getChildren().removeAll(buttonAdd, buttonCancel);
            }

            if (comboBoxDepositType.getSelectionModel().getSelectedItem().equals("Cheque")) {
                gridPane.add(fieldRetirementAccount, 0, 7, 2, 1);
                gridPane.add(buttonAdd, 0, 8);
                gridPane.add(buttonCancel, 1, 8);
            } else if (comboBoxDepositType.getSelectionModel().getSelectedItem().equals("Efectivo")) {
                if (gridPane.getChildren().contains(fieldRetirementAccount)) {
                    gridPane.getChildren().remove(fieldRetirementAccount);
                }
                gridPane.add(buttonAdd, 0, 7);
                gridPane.add(buttonCancel, 1, 7);
            }
        });
        gridPane.add(comboBoxDepositType, 0, 6, 2, 1);

        return gridPane;
    }
}
