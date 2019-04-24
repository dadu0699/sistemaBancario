package org.didierdominguez.view.transaction;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.didierdominguez.bean.Account;
import org.didierdominguez.bean.MonetaryAccount;
import org.didierdominguez.controller.ControllerAccount;
import org.didierdominguez.controller.ControllerMonetaryAccount;
import org.didierdominguez.controller.ControllerMonetaryWithdrawal;
import org.didierdominguez.util.ScreenSize;
import org.didierdominguez.util.Verifications;
import org.didierdominguez.view.Alert;

public class ViewMonetaryWithdrawal extends Stage {
    private static ViewMonetaryWithdrawal instance;

    private ViewMonetaryWithdrawal() {}

    public static ViewMonetaryWithdrawal getInstance() {
        if (instance == null) {
            instance = new ViewMonetaryWithdrawal();
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

        Text textTitle = new Text("RETIRO");
        textTitle.getStyleClass().add("textTitle");
        textTitle.setFont(new Font(25));
        gridPane.add(textTitle, 0, 3, 2, 1);

        JFXTextField fieldAccount = new JFXTextField();
        fieldAccount.setPromptText("NO. DE CUENTA");
        fieldAccount.setLabelFloat(true);
        fieldAccount.setPrefWidth(x);
        fieldAccount.getValidators().add(validator);
        fieldAccount.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                fieldAccount.validate();
            }
        });
        gridPane.add(fieldAccount, 0, 4, 2, 1);

        Label labelAmount = new Label("MONTO:");
        gridPane.add(labelAmount, 0, 5);
        Spinner<Double> spinnerAmount = new Spinner<>(0.00, 100000.00, 0, 1);
        spinnerAmount.setEditable(true);
        spinnerAmount.setPrefWidth(x);
        gridPane.add(spinnerAmount, 1, 5);

        JFXButton buttonAdd = new JFXButton("Aceptar");
        buttonAdd.getStyleClass().addAll("customButton", "primaryButton");
        buttonAdd.setButtonType(JFXButton.ButtonType.FLAT);
        buttonAdd.setPrefSize(x, y / 20);
        buttonAdd.setOnAction(event -> {
            Account account = ControllerAccount.getInstance().searchAccount(fieldAccount.getText(),
                    TransactionPanel.getInstance().getCustomer());
            MonetaryAccount monetaryAccount = ControllerMonetaryAccount.getInstance().searchAccount(fieldAccount.getText(),
                    TransactionPanel.getInstance().getCustomer());

            if (fieldAccount.getText().length() == 0
                    || !Verifications.getInstance().isNumeric(spinnerAmount.getEditor().getText())
                    || Double.parseDouble(spinnerAmount.getEditor().getText()) <= 0) {
                Alert.getInstance().showAlert(gridPane, "ERROR", "UNO O MÁS DATOS SON INCORRECTOS");
            } else if (account == null
                    && monetaryAccount == null) {
                Alert.getInstance().showAlert(gridPane, "ERROR", "LA CUENTA DE RETIRO NO FUE ENCONTRADA");
            } else {
                if (account != null && account.getStartingAmount() > 0
                        && account.getStartingAmount() >= Double.parseDouble(spinnerAmount.getEditor().getText())) {
                    ControllerMonetaryWithdrawal.getInstance().createMonetaryWithdrawal(fieldAccount.getText(),
                            -(Double.parseDouble(spinnerAmount.getEditor().getText())),
                            TransactionPanel.getInstance().getCustomer());
                    fieldAccount.clear();
                    Alert.getInstance().showAlert(gridPane, "RETIRO", "RETIRO REALIZADO");
                } else if (monetaryAccount != null && monetaryAccount.getStartingAmount() > 0
                        && monetaryAccount.getStartingAmount() >= Double.parseDouble(spinnerAmount.getEditor().getText())) {
                    ControllerMonetaryWithdrawal.getInstance().createMonetaryWithdrawal(fieldAccount.getText(),
                            -(Double.parseDouble(spinnerAmount.getEditor().getText())),
                            TransactionPanel.getInstance().getCustomer());
                    fieldAccount.clear();
                    Alert.getInstance().showAlert(gridPane, "RETIRO", "RETIRO REALIZADO");
                } else {
                    Alert.getInstance().showAlert(gridPane, "ERROR", "LA CUENTA DE RETIRO NO POSEE LOS " +
                            "FONDOS SUFICIENTES");
                }
            }
        });
        gridPane.add(buttonAdd, 0, 6);

        JFXButton buttonCancel = new JFXButton("Cancelar");
        buttonCancel.getStyleClass().addAll("customButton", "dangerButton");
        buttonCancel.setButtonType(JFXButton.ButtonType.FLAT);
        buttonCancel.setPrefSize(x, y / 20);
        buttonCancel.setOnAction(event -> fieldAccount.clear());
        gridPane.add(buttonCancel, 1, 6);

        return gridPane;
    }
}
