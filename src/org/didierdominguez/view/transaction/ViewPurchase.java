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
import org.didierdominguez.bean.CreditCard;
import org.didierdominguez.controller.ControllerCreditCard;
import org.didierdominguez.controller.ControllerPurchase;
import org.didierdominguez.util.ScreenSize;
import org.didierdominguez.util.Verifications;
import org.didierdominguez.view.Alert;

public class ViewPurchase {
    private static ViewPurchase instance;

    private ViewPurchase() {
    }

    public static ViewPurchase getInstance() {
        if (instance == null) {
            instance = new ViewPurchase();
        }
        return instance;
    }

    public void showAlertProduct(HBox hBox, String title) {
        JFXAlert<String> alert = new JFXAlert<>((Stage) hBox.getScene().getWindow());
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setOverlayClose(false);

        RequiredFieldValidator validator = new RequiredFieldValidator();
        double x = ScreenSize.getInstance().getX();
        GridPane gridfields = new GridPane();
        gridfields.setVgap(25);
        gridfields.setPadding(new Insets(20));

        JFXTextField fieldCreditCard = new JFXTextField();
        fieldCreditCard.setPromptText("NO. DE TARJETA DE CREDITO");
        fieldCreditCard.setLabelFloat(true);
        fieldCreditCard.setPrefWidth(x);
        fieldCreditCard.getValidators().add(validator);
        fieldCreditCard.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                fieldCreditCard.validate();
            }
        });
        gridfields.add(fieldCreditCard, 0, 0, 2, 1);

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
            CreditCard creditCard = ControllerCreditCard.getInstance().searchCreditCard(fieldCreditCard.getText());

            if (fieldCreditCard.getText().length() == 0
                    || !Verifications.getInstance().isNumeric(spinnerAmount.getEditor().getText())
                    || Double.parseDouble(spinnerAmount.getEditor().getText()) <= 0) {
                Alert.getInstance().showAlert(gridfields, "ERROR", "UNO O MÁS DATOS SON INCORRECTOS");
            } else if (creditCard == null || creditCard.getAuthorization() == null
                    || creditCard.getCustomer() != TransactionPanel.getInstance().getCustomer()) {
                Alert.getInstance().showAlert(gridfields, "ERROR", "LA TARJETA NO FUE ENCONTRADA");
            } else {
                if ((Double.parseDouble(spinnerAmount.getEditor().getText()) + creditCard.getAmountOwed()) <= creditCard
                        .getCreditLimit()) {
                    ControllerCreditCard.getInstance().updateCreditCard(creditCard.getId(),
                            (Double.parseDouble(spinnerAmount.getEditor().getText())));
                    ControllerPurchase.getInstance().createPurchase(TransactionPanel.getInstance().getCustomer(),
                            creditCard, Double.parseDouble(spinnerAmount.getEditor().getText()));
                    Alert.getInstance().showAlert(gridfields, "ÉXITO", "COMPRA REALIZADA");
                } else {
                    Alert.getInstance().showAlert(gridfields, "ERROR", "LA TARJETA NO POSEE FONDOS");
                }
            }
        });

        layout.setActions(buttonAdd, cancelButton);
        alert.setContent(layout);
        alert.show();
    }
}
