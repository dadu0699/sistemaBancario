package org.didierdominguez.view.transaction;

import com.jfoenix.controls.JFXButton;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.didierdominguez.controller.ControllerCreditCard;
import org.didierdominguez.util.ScreenSize;
import org.didierdominguez.util.Verifications;
import org.didierdominguez.view.Alert;

public class ViewApplyCreditCard extends Stage {
    private static ViewApplyCreditCard instance;

    private ViewApplyCreditCard() {}

    public static ViewApplyCreditCard getInstance() {
        if (instance == null) {
            instance = new ViewApplyCreditCard();
        }
        return instance;
    }

    public GridPane getGridPane() {
        GridPane gridPane = new GridPane();

        double x = ScreenSize.getInstance().getX();
        double y = ScreenSize.getInstance().getY();

        gridPane.setVgap(25);
        gridPane.setPadding(new Insets(20));
        // gridPane.setGridLinesVisible(true);

        Text textTitle = new Text("SOLICITE UNA TARJETA DE CRÉDITO");
        textTitle.getStyleClass().add("textTitle");
        textTitle.setFont(new Font(25));
        gridPane.add(textTitle, 0, 5, 2, 1);

        Label labelAmount = new Label("LIMITE DE CREDITO:");
        gridPane.add(labelAmount, 0, 6);
        Spinner<Double> spinnerAmount = new Spinner<>(0.00, 100000.00, 0, 1);
        spinnerAmount.setEditable(true);
        spinnerAmount.setPrefWidth(x);
        gridPane.add(spinnerAmount, 1, 6);

        JFXButton buttonAdd = new JFXButton("Aceptar");
        buttonAdd.getStyleClass().addAll("customButton", "primaryButton");
        buttonAdd.setButtonType(JFXButton.ButtonType.FLAT);
        buttonAdd.setPrefSize(x, y / 20);
        buttonAdd.setOnAction(event -> {
            if (!Verifications.getInstance().isNumeric(spinnerAmount.getEditor().getText())
                    || Double.parseDouble(spinnerAmount.getEditor().getText()) <= 0) {
                Alert.getInstance().showAlert(gridPane, "ERROR", "NO PUEDE SOLICITAR UNA TARJETA " +
                        "CON LIMITE DE CREDITO MENOR O IGUAL A 0");
            } else {
                ControllerCreditCard.getInstance().createCreditCard(TransactionPanel.getInstance().getCustomer(),
                        Double.parseDouble(spinnerAmount.getEditor().getText()));
                Alert.getInstance().showAlert(gridPane, "SOLICITUD", "TARJETA SOLICITADA");
            }
        });
        gridPane.add(buttonAdd, 0, 7);

        JFXButton buttonCancel = new JFXButton("Cancelar");
        buttonCancel.getStyleClass().addAll("customButton", "dangerButton");
        buttonCancel.setButtonType(JFXButton.ButtonType.FLAT);
        buttonCancel.setPrefSize(x, y / 20);
        gridPane.add(buttonCancel, 1, 7);

        return gridPane;
    }
}