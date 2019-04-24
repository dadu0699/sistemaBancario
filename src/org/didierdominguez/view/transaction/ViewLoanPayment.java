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
import org.didierdominguez.bean.Loan;
import org.didierdominguez.bean.MonetaryAccount;
import org.didierdominguez.controller.ControllerCustomer;
import org.didierdominguez.controller.ControllerLoan;
import org.didierdominguez.controller.ControllerLoanPayment;
import org.didierdominguez.controller.ControllerMonetaryAccount;
import org.didierdominguez.util.ScreenSize;
import org.didierdominguez.util.Verifications;
import org.didierdominguez.view.Alert;

public class ViewLoanPayment extends Stage {
    private static ViewLoanPayment instance;

    private ViewLoanPayment() {}

    public static ViewLoanPayment getInstance() {
        if (instance == null) {
            instance = new ViewLoanPayment();
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

        Text textTitle = new Text("PAGO DE TARJETA DE PRÉSTAMO");
        textTitle.getStyleClass().add("textTitle");
        textTitle.setFont(new Font(25));
        gridPane.add(textTitle, 0, 3, 2, 1);

        JFXTextField fieldLoan = new JFXTextField();
        fieldLoan.setPromptText("NO. DE PRÉSTAMO");
        fieldLoan.setLabelFloat(true);
        fieldLoan.setPrefWidth(x);
        fieldLoan.getValidators().add(validator);
        fieldLoan.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                fieldLoan.validate();
            }
        });
        gridPane.add(fieldLoan, 0, 4, 2, 1);

        Label labelAmount = new Label("MONTO:");
        gridPane.add(labelAmount, 0, 5);
        Spinner<Double> spinnerAmount = new Spinner<>(0.00, 100000.00, 0, 1);
        spinnerAmount.setEditable(true);
        spinnerAmount.setPrefWidth(x);
        gridPane.add(spinnerAmount, 1, 5);

        String[] options = {"Efectivo", "Cheque"};
        ObservableList<String> data = FXCollections.observableArrayList(options);
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
            Loan loan = ControllerLoan.getInstance().searchLoan(fieldLoan.getText());
            MonetaryAccount retirementMonetaryAccount = ControllerMonetaryAccount.getInstance().searchAccount(fieldRetirementAccount.getText());

            if (fieldLoan.getText().length() == 0
                    || comboBoxDepositType.getSelectionModel().getSelectedItem() == null
                    || !Verifications.getInstance().isNumeric(spinnerAmount.getEditor().getText())
                    || Double.parseDouble(spinnerAmount.getEditor().getText()) <= 0) {
                Alert.getInstance().showAlert(gridPane, "ERROR", "UNO O MÁS DATOS SON INCORRECTOS");
            } else if (loan == null || loan.getAuthorization() == null || !loan.getAuthorization()) {
                Alert.getInstance().showAlert(gridPane, "ERROR", "EL PRÉSTAMO NO FUE ENCONTRADO");
            }  else if (retirementMonetaryAccount == null
                    && comboBoxDepositType.getSelectionModel().getSelectedItem().equals("Cheque")) {
                Alert.getInstance().showAlert(gridPane, "ERROR", "LA CUENTA DE RETIRO NO FUE ENCONTRADA");
            } else {
                if (comboBoxDepositType.getSelectionModel().getSelectedItem().equals("Efectivo")) {
                    ControllerLoanPayment.getInstance().createLoanPayment(loan, "Efectivo",
                            Double.parseDouble(spinnerAmount.getEditor().getText()));
                    if (loan.getAmount() >= Double.parseDouble(spinnerAmount.getEditor().getText())) {
                        ControllerLoan.getInstance().updateLoan(loan.getId(),
                                (Double.parseDouble(spinnerAmount.getEditor().getText())));
                    } else {
                        double change = (Double.parseDouble(spinnerAmount.getEditor().getText())) - loan.getAmount();
                        ControllerLoan.getInstance().updateLoan(loan.getId(),
                                loan.getAmount());
                        Alert.getInstance().showAlert(gridPane, "VUELTO", "EL VUELTO ES DE: " + change);
                    }
                    ControllerCustomer.getInstance().updateTransactionsCarriedOut(TransactionPanel.getInstance()
                            .getCustomer().getId());
                    fieldLoan.clear();
                    fieldRetirementAccount.clear();
                    Alert.getInstance().showAlert(gridPane, "PAGO", "PAGO REALIZADO");
                } else {
                    if (retirementMonetaryAccount.getCheck() > 0 && retirementMonetaryAccount.getStartingAmount() > 0
                            && retirementMonetaryAccount.getStartingAmount()
                            > Double.parseDouble(spinnerAmount.getEditor().getText())) {
                        ControllerLoanPayment.getInstance().createLoanPayment(loan, "Cheque",
                                Double.parseDouble(spinnerAmount.getEditor().getText()));
                        ControllerMonetaryAccount.getInstance().updateAccount(
                                retirementMonetaryAccount.getId(),
                                (Double.parseDouble(spinnerAmount.getEditor().getText()) * -1), true
                        );
                        if (loan.getAmount() >= Double.parseDouble(spinnerAmount.getEditor().getText())) {
                            ControllerLoan.getInstance().updateLoan(loan.getId(),
                                    Double.parseDouble(spinnerAmount.getEditor().getText()));
                        } else {
                            double change = (Double.parseDouble(spinnerAmount.getEditor().getText())) - loan.getAmount();
                            ControllerLoan.getInstance().updateLoan(loan.getId(),
                                    loan.getAmount());
                            Alert.getInstance().showAlert(gridPane, "VUELTO", "EL VUELTO ES DE: " + change);
                        }
                        fieldLoan.clear();
                        fieldRetirementAccount.clear();
                        Alert.getInstance().showAlert(gridPane, "PAGO", "PAGO REALIZADO");
                        ControllerCustomer.getInstance().updateTransactionsCarriedOut(TransactionPanel.getInstance()
                                .getCustomer().getId());
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
