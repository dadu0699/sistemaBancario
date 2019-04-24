package org.didierdominguez.view.admin;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDecorator;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.didierdominguez.util.ScreenSize;

public class AdminPanel extends Stage {
    private static AdminPanel instance;
    private Stage stage;

    private AdminPanel() {
    }

    public static AdminPanel getInstance() {
        if (instance == null) {
            instance = new AdminPanel();
        }
        return instance;
    }

    private HBox getPane() {
        HBox hBox = new HBox();
        VBox vBoxButtons = new VBox();
        VBox vBoxPanels = new VBox();

        double x = ScreenSize.getInstance().getX();
        double y = ScreenSize.getInstance().getY();

        hBox.setId("adminPanel");
        hBox.setPrefSize(x, y);
        vBoxButtons.setPrefSize((x / 4), y);
        vBoxPanels.setPrefSize((3 * x / 4), y);
        vBoxPanels.getChildren().add(ViewAgency.getInstance().getViewAgency());

        JFXButton buttonAgency = new JFXButton("AGENCIA BANCARIA");
        buttonAgency.setId("buttonAgency");
        buttonAgency.getStyleClass().addAll("panelButton", "primaryButton");
        buttonAgency.setPrefSize(x, y);
        buttonAgency.setButtonType(JFXButton.ButtonType.FLAT);
        buttonAgency.setOnAction(event -> {
            vBoxPanels.getChildren().clear();
            vBoxPanels.getChildren().add(ViewAgency.getInstance().getViewAgency());
        });

        JFXButton buttonAutobank = new JFXButton("AGENCIA CON AUTOBANCO");
        buttonAutobank.setId("buttonAutobank");
        buttonAutobank.getStyleClass().addAll("panelButton", "primaryButton");
        buttonAutobank.setPrefSize(x, y);
        buttonAutobank.setButtonType(JFXButton.ButtonType.FLAT);
        buttonAutobank.setOnAction(event -> {
            vBoxPanels.getChildren().clear();
            vBoxPanels.getChildren().add(ViewAutoBank.getInstance().getViewAutoBank());
        });

        JFXButton buttonHeadquarters = new JFXButton("OFICINAS CENTRALES");
        buttonHeadquarters.setId("buttonHeadquarters");
        buttonHeadquarters.getStyleClass().addAll("panelButton", "primaryButton");
        buttonHeadquarters.setPrefSize(x, y);
        buttonHeadquarters.setButtonType(JFXButton.ButtonType.FLAT);
        buttonHeadquarters.setOnAction(event -> {
            vBoxPanels.getChildren().clear();
            vBoxPanels.getChildren().add(ViewHeadquarters.getInstance().getViewHeadquarters());
        });

        JFXButton buttonATM = new JFXButton("CAJEROS AUTOMATICOS");
        buttonATM.setId("buttonATM");
        buttonATM.getStyleClass().addAll("panelButton", "primaryButton");
        buttonATM.setPrefSize(x, y);
        buttonATM.setButtonType(JFXButton.ButtonType.FLAT);
        buttonATM.setOnAction(event -> {
            vBoxPanels.getChildren().clear();
            vBoxPanels.getChildren().add(ViewATM.getInstance().getViewATM());
        });

        JFXButton buttonClient = new JFXButton("CLIENTES");
        buttonClient.setId("buttonClient");
        buttonClient.getStyleClass().addAll("panelButton", "primaryButton");
        buttonClient.setPrefSize(x, y);
        buttonClient.setButtonType(JFXButton.ButtonType.FLAT);
        buttonClient.setOnAction(event -> {
            vBoxPanels.getChildren().clear();
            vBoxPanels.getChildren().add(ViewCustomer.getInstance().getViewCustomer());
        });

        JFXButton buttonEmployee = new JFXButton("EMPLEADOS");
        buttonEmployee.setId("buttonEmployee");
        buttonEmployee.getStyleClass().addAll("panelButton", "primaryButton");
        buttonEmployee.setPrefSize(x, y);
        buttonEmployee.setButtonType(JFXButton.ButtonType.FLAT);
        buttonEmployee.setOnAction(event -> {
            vBoxPanels.getChildren().clear();
            vBoxPanels.getChildren().add(ViewEmployee.getInstance().getViewEmployee());
        });

        JFXButton buttonReport = new JFXButton("REPORTES");
        buttonReport.setId("buttonReport");
        buttonReport.getStyleClass().addAll("panelButton", "primaryButton");
        buttonReport.setPrefSize(x, y);
        buttonReport.setButtonType(JFXButton.ButtonType.FLAT);
        buttonReport.setOnAction(event -> {
            vBoxPanels.getChildren().clear();
            vBoxPanels.getChildren().add(ViewReport.getInstance().getViewReport());
        });

        JFXButton buttonExit = new JFXButton("SALIR");
        buttonExit.setId("buttonReport");
        buttonExit.getStyleClass().addAll("panelButton", "dangerButton");
        buttonExit.setPrefSize(x, y);
        buttonExit.setButtonType(JFXButton.ButtonType.FLAT);
        buttonExit.setOnAction(event -> {
            stage.hide();
        });

        vBoxButtons.getChildren().addAll(buttonAgency, buttonAutobank, buttonHeadquarters, buttonATM, buttonClient,
                buttonEmployee, buttonReport, buttonExit);
        hBox.getChildren().addAll(vBoxButtons, vBoxPanels);

        return hBox;
    }

    public void showWindow() {
        stage = new Stage();
        JFXDecorator decorator = new JFXDecorator(stage, getPane(), false, false, true);
        Scene scene = new Scene(decorator, 1024, 576);
        scene.getStylesheets().add("/org/didierdominguez/assets/stylesheets/style.css");
        stage.setTitle("Banco del Exterior");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setMaximized(true);
        stage.setResizable(false);
        stage.show();
    }
}
