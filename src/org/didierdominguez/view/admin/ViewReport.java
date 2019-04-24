package org.didierdominguez.view.admin;

import com.jfoenix.controls.JFXButton;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.didierdominguez.bean.Customer;
import org.didierdominguez.controller.ControllerBankingAgency;
import org.didierdominguez.controller.ControllerCustomer;
import org.didierdominguez.controller.ControllerEmployee;
import org.didierdominguez.util.ReportGenerator;
import org.didierdominguez.util.ScreenSize;

public class ViewReport extends Stage {
    private static ViewReport instance;

    private ViewReport() {
    }

    public static ViewReport getInstance() {
        if (instance == null) {
            instance = new ViewReport();
        }
        return instance;
    }

    HBox getViewReport() {
        HBox hBox = new HBox();
        VBox vBoxButtons = new VBox();
        GridPane gridPane = new GridPane();

        double x = ScreenSize.getInstance().getX();
        double y = ScreenSize.getInstance().getY();

        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20));
        // gridPane.setGridLinesVisible(true);
        gridPane.setMinWidth(x / 2);
        gridPane.setPrefSize(x, y);
        hBox.setPrefSize(x, y);

        Text textTitle = new Text("REPORTES");
        textTitle.getStyleClass().add("textTitle");
        textTitle.setFont(new Font(25));
        gridPane.add(textTitle, 0, 0);

        vBoxButtons.setPrefSize(x, y);

        JFXButton buttonClientMoreAccounts = new JFXButton("TOP 3, CLIENTES CON MÁS CUENTAS");
        buttonClientMoreAccounts.getStyleClass().addAll("panelButton", "primaryButton");
        buttonClientMoreAccounts.setPrefSize(x, y);
        buttonClientMoreAccounts.setButtonType(JFXButton.ButtonType.FLAT);
        buttonClientMoreAccounts.setOnAction(event -> {
            String[] titles = {"ID", "NOMBRE", "NÚMERO TELEFÓNICO", "DIRECCIÓN", "CUENTAS DE AHORRO",
                    "CUENTAS MONETARIAS", "PRESTAMOS", "TARJETAS DE CRÉDITO", "TRANSACCIONES", "SALDO A FAVOR", "DEUDA",
                    "COMPRAS"};
            Customer[] dataCustomers = ControllerCustomer.getInstance().getCustomersMoreAccounts();
            try {
                ReportGenerator.getInstance().generatePDF("CustomersTopAccounts.pdf",
                        "TOP 3, CLIENTES CON MÁS CUENTAS", titles, dataCustomers);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        JFXButton buttonCustomerMostMoney = new JFXButton("TOP 3, CLIENTES CON MAYOR SUMA DE DINERO");
        buttonCustomerMostMoney.getStyleClass().addAll("panelButton", "primaryButton");
        buttonCustomerMostMoney.setPrefSize(x, y);
        buttonCustomerMostMoney.setButtonType(JFXButton.ButtonType.FLAT);
        buttonCustomerMostMoney.setOnAction(event -> {
            String[] titles = {"ID", "NOMBRE", "NÚMERO TELEFÓNICO", "DIRECCIÓN", "CUENTAS DE AHORRO",
                    "CUENTAS MONETARIAS", "PRESTAMOS", "TARJETAS DE CRÉDITO", "TRANSACCIONES", "SALDO A FAVOR", "DEUDA",
                    "COMPRAS"};
            Customer[] dataCustomers = ControllerCustomer.getInstance().getCustomersMostMoney();
            try {
                ReportGenerator.getInstance().generatePDF("CustomersTopMoney.pdf",
                        "TOP 3, CLIENTES CON MAYOR SUMA DE DINERO", titles, dataCustomers);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        JFXButton buttonHeadquarters = new JFXButton("TOP 3, CLIENTES QUE DEBEN MÁS AL BANCO");
        buttonHeadquarters.getStyleClass().addAll("panelButton", "primaryButton");
        buttonHeadquarters.setPrefSize(x, y);
        buttonHeadquarters.setButtonType(JFXButton.ButtonType.FLAT);
        buttonHeadquarters.setOnAction(event -> {
            String[] titles = {"ID", "NOMBRE", "NÚMERO TELEFÓNICO", "DIRECCIÓN", "CUENTAS DE AHORRO",
                    "CUENTAS MONETARIAS", "PRESTAMOS", "TARJETAS DE CRÉDITO", "TRANSACCIONES", "SALDO A FAVOR", "DEUDA",
                    "COMPRAS"};
            Customer[] dataCustomers = ControllerCustomer.getInstance().getCustomersMostDebt();
            try {
                ReportGenerator.getInstance().generatePDF("CustomersTopDebt.pdf",
                        "TOP 3, CLIENTES QUE DEBEN MÁS AL BANCO", titles, dataCustomers);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        JFXButton buttonMostUsedAgencies = new JFXButton("TOP 3, AGENCIAS MÁS UTILIZADAS");
        buttonMostUsedAgencies.getStyleClass().addAll("panelButton", "primaryButton");
        buttonMostUsedAgencies.setPrefSize(x, y);
        buttonMostUsedAgencies.setButtonType(JFXButton.ButtonType.FLAT);

        JFXButton buttonCallCenter = new JFXButton("TOP 2, OPERACIONES MÁS FRECUENTES REALIZADAS EN CALL-CENTER");
        buttonCallCenter.getStyleClass().addAll("panelButton", "primaryButton");
        buttonCallCenter.setPrefSize(x, y);
        buttonCallCenter.setButtonType(JFXButton.ButtonType.FLAT);

        JFXButton buttonAllCash = new JFXButton("SUMA DE EFECTIVO DE TODAS LAS AGENCIAS");
        buttonAllCash.getStyleClass().addAll("panelButton", "primaryButton");
        buttonAllCash.setPrefSize(x, y);
        buttonAllCash.setButtonType(JFXButton.ButtonType.FLAT);
        buttonAllCash.setOnAction(event -> {
            String[] titles = {"AGENCIAS BANCARIAS", "AGENCIAS CON AUTO BANCO", "TOTAL"};
            String[] data = ControllerBankingAgency.getInstance().getTotalCash();
            try {
                ReportGenerator.getInstance().generatePDF("TotalCashInAgencies.pdf",
                        "SUMA DE EFECTIVO DE TODAS LAS AGENCIAS", titles, data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        JFXButton buttonMostEmployees = new JFXButton("AGENCIA CON MAYOR NÚMERO DE EMPLEADOS");
        buttonMostEmployees.getStyleClass().addAll("panelButton", "primaryButton");
        buttonMostEmployees.setPrefSize(x, y);
        buttonMostEmployees.setButtonType(JFXButton.ButtonType.FLAT);
        buttonMostEmployees.setOnAction(event -> {
            String[] titles = {"AGENCIA", "DIRECCIÓN", "NÚMERO TELEFÓNICO", "EMPLEADOS"};
            String[] data = ControllerEmployee.getInstance().getAgencyWithMoreEmployees();
            try {
                ReportGenerator.getInstance().generatePDF("AgencyWithMoreEmployees.pdf",
                        "AGENCIA CON MAYOR NÚMERO DE EMPLEADOS", titles, data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        JFXButton buttonMostPurchasesCustomer = new JFXButton("TOP 3, DE CLIENTES CON MAYOR NÚMERO DE COMPRAS CON " +
                "TARJETAS DE CRÉDITO");
        buttonMostPurchasesCustomer.getStyleClass().addAll("panelButton", "primaryButton");
        buttonMostPurchasesCustomer.setPrefSize(x, y);
        buttonMostPurchasesCustomer.setButtonType(JFXButton.ButtonType.FLAT);
        buttonMostPurchasesCustomer.setOnAction(event -> {
            String[] titles = {"ID", "NOMBRE", "NÚMERO TELEFÓNICO", "DIRECCIÓN", "CUENTAS DE AHORRO",
                    "CUENTAS MONETARIAS", "PRESTAMOS", "TARJETAS DE CRÉDITO", "TRANSACCIONES", "SALDO A FAVOR", "DEUDA",
                    "COMPRAS"};
            Customer[] dataCustomers = ControllerCustomer.getInstance().getCustomersMostPurchase();
            try {
                ReportGenerator.getInstance().generatePDF("CustomersTopPurchase.pdf",
                        "TOP 3, DE CLIENTES CON MAYOR NÚMERO DE COMPRAS", titles, dataCustomers);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        vBoxButtons.getChildren().addAll(buttonClientMoreAccounts, buttonCustomerMostMoney,
                buttonHeadquarters, buttonMostUsedAgencies, buttonCallCenter, buttonAllCash,
                buttonMostEmployees, buttonMostPurchasesCustomer);
        gridPane.add(vBoxButtons, 0, 1);

        hBox.getChildren().add(gridPane);

        return hBox;
    }
}
