package org.didierdominguez.view.transaction;

import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.didierdominguez.bean.MonetaryAccount;
import org.didierdominguez.controller.ControllerMonetaryAccount;
import org.didierdominguez.util.ScreenSize;
import org.didierdominguez.util.Verifications;
import org.didierdominguez.view.Alert;

public class ViewCreateCheck {
    private static ViewCreateCheck instance;

    private ViewCreateCheck() {}

    public static ViewCreateCheck getInstance() {
        if (instance == null) {
            instance = new ViewCreateCheck();
        }
        return instance;
    }

    public void showAlertCheck(HBox hBox, String title){
        JFXAlert<String> alert = new JFXAlert<>((Stage) hBox.getScene().getWindow());
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setOverlayClose(false);

        RequiredFieldValidator validator = new RequiredFieldValidator();
        double x = ScreenSize.getInstance().getX();
        GridPane gridfields = new GridPane();
        gridfields.setVgap(25);
        gridfields.setPadding(new Insets(20));

        JFXTextField fieldMonetaryAccount = new JFXTextField();
        fieldMonetaryAccount.setPromptText("NO. DE CUENTA");
        fieldMonetaryAccount.setLabelFloat(true);
        fieldMonetaryAccount.setPrefWidth(x);
        fieldMonetaryAccount.getValidators().add(validator);
        fieldMonetaryAccount.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                fieldMonetaryAccount.validate();
            }
        });
        gridfields.add(fieldMonetaryAccount, 0, 0, 2, 1);

        Label labelAmount = new Label("MONTO:");
        labelAmount.setPrefWidth(x);
        gridfields.add(labelAmount, 0, 1);
        Spinner<Double> spinnerAmount = new Spinner<>(0.00, 100000.00, 0, 1);
        spinnerAmount.setEditable(true);
        spinnerAmount.setPrefWidth(x);
        gridfields.add(spinnerAmount, 1, 1);

        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(new Label(title));
        layout.setBody(new VBox(gridfields));

        JFXButton cancelButton = new JFXButton("Cerrar");
        cancelButton.setCancelButton(true);
        cancelButton.getStyleClass().addAll("customButton", "primaryButton");
        cancelButton.setButtonType(JFXButton.ButtonType.FLAT);
        cancelButton.setOnAction(closeEvent -> alert.hideWithAnimation());

        JFXButton buttonAdd = new JFXButton("Aceptar");
        buttonAdd.getStyleClass().addAll("customButton", "primaryButton");
        buttonAdd.setButtonType(JFXButton.ButtonType.FLAT);
        buttonAdd.setOnAction(event -> {
            MonetaryAccount monetaryAccount = ControllerMonetaryAccount.getInstance().searchAccount(fieldMonetaryAccount.getText(),
                    TransactionPanel.getInstance().getCustomer());

            if (fieldMonetaryAccount.getText().length() == 0
                    || !Verifications.getInstance().isNumeric(spinnerAmount.getEditor().getText())
                    || Double.parseDouble(spinnerAmount.getEditor().getText()) <= 0) {
                Alert.getInstance().showAlert(gridfields, "ERROR", "UNO O MÁS DATOS SON INCORRECTOS");
            } else if (monetaryAccount == null) {
                Alert.getInstance().showAlert(gridfields, "ERROR", "LA CUENTA MONETARIA NO FUE ENCONTRADA");
            }  else {
                if (monetaryAccount.getCheck() > 0 && monetaryAccount.getStartingAmount() > 0
                        && monetaryAccount.getStartingAmount()
                        > Double.parseDouble(spinnerAmount.getEditor().getText())) {
                    ControllerMonetaryAccount.getInstance().updateAccount(
                            monetaryAccount.getId(), 0.0, true
                    );
                    fieldMonetaryAccount.clear();
                    Alert.getInstance().showAlert(gridfields, "CHEQUE", "CHEQUE EMITIDO");
                } else {
                    Alert.getInstance().showAlert(gridfields, "ERROR", "LA CUENTA DE RETIRO NO " +
                            "POSEE CHEQUES O FONDOS SUFICIENTES");
                }
            }
        });

        layout.setActions(buttonAdd, cancelButton);
        alert.setContent(layout);
        alert.show();
    }
}
