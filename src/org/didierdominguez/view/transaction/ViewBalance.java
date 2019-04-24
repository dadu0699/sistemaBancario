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

public class ViewBalance extends Stage {
    private static ViewBalance instance;

    private ViewBalance() {}

    public static ViewBalance getInstance() {
        if (instance == null) {
            instance = new ViewBalance();
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

        Text textTitle = new Text("SALDO");
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

        JFXButton buttonAdd = new JFXButton("Aceptar");
        buttonAdd.getStyleClass().addAll("customButton", "primaryButton");
        buttonAdd.setButtonType(JFXButton.ButtonType.FLAT);
        buttonAdd.setPrefSize(x, y / 20);
        buttonAdd.setOnAction(event -> {
            Account account = ControllerAccount.getInstance().searchAccount(fieldAccount.getText(),
                    TransactionPanel.getInstance().getCustomer());
            MonetaryAccount monetaryAccount = ControllerMonetaryAccount.getInstance().searchAccount(fieldAccount.getText(),
                    TransactionPanel.getInstance().getCustomer());

            if (fieldAccount.getText().length() == 0) {
                Alert.getInstance().showAlert(gridPane, "ERROR", "UNO O MÁS DATOS SON INCORRECTOS");
            } else if (account == null
                    && monetaryAccount == null) {
                Alert.getInstance().showAlert(gridPane, "ERROR", "LA CUENTA NO FUE ENCONTRADA");
            } else {
                if (account != null) {
                    Alert.getInstance().showAlert(gridPane, "SALDO", "POSEE UN SALDO DE: " + account.getStartingAmount());
                } else {
                    Alert.getInstance().showAlert(gridPane, "SALDO", "POSEE UN SALDO DE: " + monetaryAccount.getStartingAmount());
                }
            }
        });
        gridPane.add(buttonAdd, 0, 5);

        JFXButton buttonCancel = new JFXButton("Cancelar");
        buttonCancel.getStyleClass().addAll("customButton", "dangerButton");
        buttonCancel.setButtonType(JFXButton.ButtonType.FLAT);
        buttonCancel.setPrefSize(x, y / 20);
        buttonCancel.setOnAction(event -> fieldAccount.clear());
        gridPane.add(buttonCancel, 1, 5);

        return gridPane;
    }
}
