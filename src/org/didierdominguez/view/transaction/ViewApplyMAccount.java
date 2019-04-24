package org.didierdominguez.view.transaction;

import com.jfoenix.controls.JFXButton;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.didierdominguez.controller.ControllerMonetaryAccount;
import org.didierdominguez.util.ScreenSize;
import org.didierdominguez.util.Verifications;
import org.didierdominguez.view.Alert;

public class ViewApplyMAccount extends Stage {
    private static ViewApplyMAccount instance;

    private ViewApplyMAccount() {
    }

    public static ViewApplyMAccount getInstance() {
        if (instance == null) {
            instance = new ViewApplyMAccount();
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

        Text textTitle = new Text("APERTURA DE CUENTA MONETARIA");
        textTitle.getStyleClass().add("textTitle");
        textTitle.setFont(new Font(25));
        gridPane.add(textTitle, 0, 5, 2, 1);

        Label labelAmount = new Label("MONTO DE APERTURA:");
        gridPane.add(labelAmount, 0, 6);
        Spinner<Double> spinnerAmount = new Spinner<>(0.00, 100000.00, 0, 1);
        spinnerAmount.setEditable(true);
        spinnerAmount.setPrefWidth(x);
        gridPane.add(spinnerAmount, 1, 6);

        Label labelCheck = new Label("CHEQUES:");
        gridPane.add(labelCheck, 0, 7);
        Spinner<Integer> spinnerCheck = new Spinner<>(0, 100, 25, 1);
        spinnerCheck.setEditable(true);
        spinnerCheck.setPrefWidth(x);
        gridPane.add(spinnerCheck, 1, 7);

        JFXButton buttonAdd = new JFXButton("Agregar");
        buttonAdd.getStyleClass().addAll("customButton", "primaryButton");
        buttonAdd.setButtonType(JFXButton.ButtonType.FLAT);
        buttonAdd.setPrefSize(x, y / 20);
        buttonAdd.setOnAction(event -> {
            if (!Verifications.getInstance().isNumeric(spinnerAmount.getEditor().getText())
                    || Double.parseDouble(spinnerAmount.getEditor().getText()) <= 0
                    || !Verifications.getInstance().isNumeric(spinnerCheck.getEditor().getText())
                    || Double.parseDouble(spinnerAmount.getEditor().getText()) <= 25) {
                Alert.getInstance().showAlert(gridPane, "ERROR", "UNO O MÁS DATOS SON INCORRECTOS");
            } else {
                ControllerMonetaryAccount.getInstance().createAccount(TransactionPanel.getInstance().getCustomer(),
                        Double.parseDouble(spinnerAmount.getEditor().getText()),
                        Integer.parseInt(spinnerCheck.getEditor().getText()));
                Alert.getInstance().showAlert(gridPane, "SOLICITUD", "CUENTA MONETARIA APERTURADA");
            }
        });
        gridPane.add(buttonAdd, 0, 8);

        JFXButton buttonCancel = new JFXButton("Cancelar");
        buttonCancel.getStyleClass().addAll("customButton", "dangerButton");
        buttonCancel.setButtonType(JFXButton.ButtonType.FLAT);
        buttonCancel.setPrefSize(x, y / 20);
        gridPane.add(buttonCancel, 1, 8);

        return gridPane;
    }
}