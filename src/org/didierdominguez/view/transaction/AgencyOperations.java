package org.didierdominguez.view.transaction;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.didierdominguez.bean.BankingAgency;
import org.didierdominguez.controller.ControllerBankingAgency;
import org.didierdominguez.util.ScreenSize;

import java.util.ArrayList;

public class AgencyOperations extends Stage {
    private static AgencyOperations instance;
    private HBox hBoxPanels;
    private VBox vBoxPanels;
    private ObservableList observableList;

    private AgencyOperations() {
    }

    public static AgencyOperations getInstance() {
        if (instance == null) {
            instance = new AgencyOperations();
        }
        return instance;
    }

    public void restartHBox() {
        hBoxPanels.getChildren().clear();
        hBoxPanels.getChildren().add(vBoxPanels);
    }

    private void updateObservableList() {
        BankingAgency[] bankingAgency = ControllerBankingAgency.getInstance().getBankingAgencies();
        ArrayList<BankingAgency> arrayListBankingAgency = new ArrayList<>();
        for (BankingAgency agency : bankingAgency) {
            if (agency != null) {
                arrayListBankingAgency.add(agency);
            }
        }
        if (observableList != null){
            observableList.clear();
        }
        observableList = FXCollections.observableArrayList(arrayListBankingAgency);
    }

    VBox getViewAgency() {
        VBox vBox = new VBox();
        GridPane gridPaneTitle = new GridPane();

        double x = ScreenSize.getInstance().getX();
        double y = ScreenSize.getInstance().getY();

        gridPaneTitle.setVgap(10);
        gridPaneTitle.setPadding(new Insets(20));
        // gridPaneTitle.setGridLinesVisible(true);
        gridPaneTitle.setMinWidth(x / 2);
        gridPaneTitle.setPrefSize(x, y / 8);
        vBox.setPrefSize(x, y);

        Text textTitle = new Text("TRANSACCIONES");
        textTitle.getStyleClass().add("textTitle");
        textTitle.setFont(new Font(35));
        gridPaneTitle.add(textTitle, 0, 0);

        updateObservableList();
        JFXComboBox<BankingAgency> comboBox = new JFXComboBox<>(observableList);
        comboBox.setPromptText("Agencias");
        comboBox.setPrefSize(x, y);
        gridPaneTitle.add(comboBox, 0, 1);

        hBoxPanels = new HBox();
        hBoxPanels.setPrefSize(x, 7 * y / 8);
        vBoxPanels = new VBox();
        vBoxPanels.setPrefSize(x / 3, 7 * y / 8);
        vBoxPanels.getChildren().addAll(getGridPanePayOffice(), getGridPaneCustomerSupport());
        vBoxPanels.setMinWidth(x / 3);
        hBoxPanels.getChildren().addAll(vBoxPanels);

        vBox.getChildren().addAll(gridPaneTitle, hBoxPanels);
        return vBox;
    }

    private GridPane getGridPanePayOffice() {
        GridPane gridPane = new GridPane();

        double x = ScreenSize.getInstance().getX();
        double y = ScreenSize.getInstance().getY();

        gridPane.setPadding(new Insets(0, 20, 10 ,20));
        // gridPane.setGridLinesVisible(true);

        Text textTitle = new Text("CAJAS");
        textTitle.getStyleClass().add("textTitle");
        textTitle.setFont(new Font(25));
        gridPane.setMargin(textTitle, new Insets(0, 0, 15, 0));
        gridPane.add(textTitle, 0, 0);

        JFXButton buttonDeposit = new JFXButton("DEPÓSITO");
        buttonDeposit.getStyleClass().addAll("panelButton", "primaryButton");
        buttonDeposit.setButtonType(JFXButton.ButtonType.FLAT);
        buttonDeposit.setPrefSize(x, y / 20);
        buttonDeposit.setOnAction(event -> {
            restartHBox();
            hBoxPanels.getChildren().add(ViewDeposit.getInstance().getGridPane());
        });
        gridPane.add(buttonDeposit, 0, 1);

        JFXButton buttonMonetaryWithdrawal = new JFXButton("RETIRO");
        buttonMonetaryWithdrawal.getStyleClass().addAll("panelButton", "primaryButton");
        buttonMonetaryWithdrawal.setButtonType(JFXButton.ButtonType.FLAT);
        buttonMonetaryWithdrawal.setPrefSize(x, y / 20);
        buttonMonetaryWithdrawal.setOnAction(event -> {
            restartHBox();
            hBoxPanels.getChildren().add(ViewMonetaryWithdrawal.getInstance().getGridPane());
        });
        gridPane.add(buttonMonetaryWithdrawal, 0, 2);

        JFXButton buttonExchangeCheck = new JFXButton("CAMBIO DE CHEQUES");
        buttonExchangeCheck.getStyleClass().addAll("panelButton", "primaryButton");
        buttonExchangeCheck.setButtonType(JFXButton.ButtonType.FLAT);
        buttonExchangeCheck.setPrefSize(x, y / 20);
        buttonExchangeCheck.setOnAction(event -> {
            restartHBox();
            hBoxPanels.getChildren().add(ViewExchangeCheck.getInstance().getGridPane());
        });
        gridPane.add(buttonExchangeCheck, 0, 3);

        JFXButton buttonServices = new JFXButton("PAGO DE SERVICIOS");
        buttonServices.getStyleClass().addAll("panelButton", "primaryButton");
        buttonServices.setButtonType(JFXButton.ButtonType.FLAT);
        buttonServices.setPrefSize(x, y / 20);
        buttonServices.setOnAction(event -> {
            restartHBox();
            hBoxPanels.getChildren().add(ViewServicePay.getInstance().getGridPane());
        });
        gridPane.add(buttonServices, 0, 4);

        JFXButton buttonCreditCardPayment = new JFXButton("PAGO DE TARJETA DE CRÉDITO");
        buttonCreditCardPayment.getStyleClass().addAll("panelButton", "primaryButton");
        buttonCreditCardPayment.setButtonType(JFXButton.ButtonType.FLAT);
        buttonCreditCardPayment.setPrefSize(x, y / 20);
        buttonCreditCardPayment.setOnAction(event -> {
            restartHBox();
            hBoxPanels.getChildren().add(ViewCreditCardPayment.getInstance().getGridPane());
        });
        gridPane.add(buttonCreditCardPayment, 0, 5);

        JFXButton buttonLoans = new JFXButton("PAGO DE PRÉSTAMO");
        buttonLoans.getStyleClass().addAll("panelButton", "primaryButton");
        buttonLoans.setButtonType(JFXButton.ButtonType.FLAT);
        buttonLoans.setPrefSize(x, y / 20);
        buttonLoans.setOnAction(event -> {
            restartHBox();
            hBoxPanels.getChildren().add(ViewLoanPayment.getInstance().getGridPane());
        });
        gridPane.add(buttonLoans, 0, 6);

        return gridPane;
    }

    private GridPane getGridPaneCustomerSupport() {
        GridPane gridPane = new GridPane();

        double x = ScreenSize.getInstance().getX();
        double y = ScreenSize.getInstance().getY();

        gridPane.setPadding(new Insets(10, 20, 20 ,20));
        // gridPane.setGridLinesVisible(true);

        Text textTitle = new Text("ATENCIÓN AL CLIENTE");
        textTitle.getStyleClass().add("textTitle");
        textTitle.setFont(new Font(25));
        gridPane.setMargin(textTitle, new Insets(0, 0, 10, 0));
        gridPane.add(textTitle, 0, 0);

        JFXButton buttonSavingsAccounts = new JFXButton("CUENTAS DE AHORRO");
        buttonSavingsAccounts.getStyleClass().addAll("panelButton", "primaryButton");
        buttonSavingsAccounts.setButtonType(JFXButton.ButtonType.FLAT);
        buttonSavingsAccounts.setPrefSize(x, y / 20);
        buttonSavingsAccounts.setOnAction(event -> {
            restartHBox();
            hBoxPanels.getChildren().add(ViewApplySAcount.getInstance().getGridPane());
        });
        gridPane.add(buttonSavingsAccounts, 0, 1);

        JFXButton buttonMonetaryAccounts = new JFXButton("CUENTAS MONETARIAS");
        buttonMonetaryAccounts.getStyleClass().addAll("panelButton", "primaryButton");
        buttonMonetaryAccounts.setButtonType(JFXButton.ButtonType.FLAT);
        buttonMonetaryAccounts.setPrefSize(x, y / 20);
        buttonMonetaryAccounts.setOnAction(event -> {
            restartHBox();
            hBoxPanels.getChildren().add(ViewApplyMAccount.getInstance().getGridPane());
        });
        gridPane.add(buttonMonetaryAccounts, 0, 2);

        JFXButton buttonLoan = new JFXButton("PRÉSTAMOS");
        buttonLoan.getStyleClass().addAll("panelButton", "primaryButton");
        buttonLoan.setButtonType(JFXButton.ButtonType.FLAT);
        buttonLoan.setPrefSize(x, y / 20);
        buttonLoan.setOnAction(event -> {
            restartHBox();
            hBoxPanels.getChildren().add(ViewApplyLoan.getInstance().getGridPane());
        });
        gridPane.add(buttonLoan, 0, 3);

        JFXButton buttonCreditCards = new JFXButton("TARJETAS DE CRÉDITO");
        buttonCreditCards.getStyleClass().addAll("panelButton", "primaryButton");
        buttonCreditCards.setButtonType(JFXButton.ButtonType.FLAT);
        buttonCreditCards.setPrefSize(x, y / 20);
        buttonCreditCards.setOnAction(event -> {
            restartHBox();
            hBoxPanels.getChildren().add(ViewApplyCreditCard.getInstance().getGridPane());
        });
        gridPane.add(buttonCreditCards, 0, 4);

        return gridPane;
    }
}
