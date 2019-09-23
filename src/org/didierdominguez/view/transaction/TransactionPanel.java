package org.didierdominguez.view.transaction;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXScrollPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.didierdominguez.bean.Customer;
import org.didierdominguez.controller.ControllerCustomer;
import org.didierdominguez.util.ScreenSize;

public class TransactionPanel extends Stage {
    private static TransactionPanel instance;
    private Stage stage;
    private HBox hBox;
    private Customer customer;

    private TransactionPanel() {
    }

    public static TransactionPanel getInstance() {
        if (instance == null) {
            instance = new TransactionPanel();
        }
        return instance;
    }

    public void restartHBox() {
        hBox.getChildren().clear();
        hBox.getChildren().add(getScrollPane());
    }

    public HBox getViewAgency() {
        hBox = new HBox();
        hBox.getChildren().add(getScrollPane());

        return hBox;
    }

    public Customer getCustomer() {
        return customer;
    }

    private JFXScrollPane getScrollPane() {
        JFXScrollPane scrollPane = new JFXScrollPane();
        GridPane gridPaneCards = new GridPane();
        Customer[] customers = ControllerCustomer.getInstance().getCustomers();

        double x = ScreenSize.getInstance().getX();
        double y = ScreenSize.getInstance().getY();

        scrollPane.setPrefSize(x, y);
        gridPaneCards.setVgap(10);
        gridPaneCards.setHgap(10);
        gridPaneCards.setPadding(new Insets(20));
        gridPaneCards.setPrefSize(x, y);
        gridPaneCards.getStyleClass().addAll("gridPaneCards");

        Text textTitle = new Text("CLIENTES");
        textTitle.getStyleClass().add("textTitle");
        textTitle.setFont(new Font(40));
        scrollPane.getBottomBar().getChildren().add(textTitle);
        scrollPane.getBottomBar().alignmentProperty().setValue(Pos.CENTER_LEFT);
        scrollPane.getBottomBar().setPadding(new Insets(0, 0, 10, 30));
        JFXScrollPane.smoothScrolling((ScrollPane) scrollPane.getChildren().get(0));

        int counter = 0;
        for (int i = 0; i < (customers.length / 2); i++) {
            for (int j = 0; j <= 2; j++) {
                if (customers[counter] != null) {
                    JFXDialogLayout layout = new JFXDialogLayout();
                    JFXButton buttonAdd = new JFXButton("Agregar");

                    buttonAdd.getStyleClass().addAll("customButton", "primaryButton");
                    buttonAdd.setButtonType(JFXButton.ButtonType.FLAT);
                    buttonAdd.setPrefSize(x, y);

                    layout.setHeading(new Label(customers[counter].getName()));
                    layout.setBody(getGridPane(customers[counter]));
                    layout.getStyleClass().addAll("customerCard");
                    layout.setPrefSize(x, (y / (customers.length / 2)));
                    gridPaneCards.add(layout, j, i);
                    counter++;
                }
            }
        }

        scrollPane.setContent(gridPaneCards);
        return scrollPane;
    }

    GridPane getGridPane(Customer customer) {
        GridPane gridPane = new GridPane();

        double x = ScreenSize.getInstance().getX();
        double y = ScreenSize.getInstance().getY();

        gridPane.setVgap(10);
        gridPane.setHgap(20);
        gridPane.setPadding(new Insets(10));

        Label labelAddress = new Label("DIRECCIÓN:");
        labelAddress.getStyleClass().add("labelC");
        gridPane.add(labelAddress, 0, 0);
        Label labelGetAddress = new Label(customer.getAddress());
        labelGetAddress.getStyleClass().add("label-brightC");
        gridPane.add(labelGetAddress, 1, 0);

        Label labelPhoneNumber = new Label("NÚMERO TELEFÓNICO:");
        labelPhoneNumber.getStyleClass().add("labelC");
        gridPane.add(labelPhoneNumber, 0, 1);
        Label labelGetPhoneNumber = new Label(customer.getPhoneNumber());
        labelGetPhoneNumber.getStyleClass().add("label-brightC");
        gridPane.add(labelGetPhoneNumber, 1, 1);

        JFXButton buttonSelect = new JFXButton("Seleccionar");
        buttonSelect.getStyleClass().addAll("customButton", "primaryButton");
        buttonSelect.setButtonType(JFXButton.ButtonType.FLAT);
        buttonSelect.setPrefSize(x, y);
        buttonSelect.setOnAction(event -> {
            this.customer = customer;
            getPane();
        });
        gridPane.add(buttonSelect, 0, 2, 2, 1);

        return gridPane;
    }

    private HBox getPane() {
        VBox vBoxButtons = new VBox();
        VBox vBoxPanels = new VBox();

        double x = ScreenSize.getInstance().getX();
        double y = ScreenSize.getInstance().getY();

        hBox.setPrefSize(x, y);
        vBoxButtons.setPrefSize((x / 4), y);
        vBoxPanels.setPrefSize((3 * x / 4), y);
        vBoxPanels.getChildren().add(AgencyOperations.getInstance().getViewAgency());

        JFXButton buttonAgency = new JFXButton("AGENCIA BANCARIA");
        buttonAgency.getStyleClass().addAll("panelButton", "primaryButton");
        buttonAgency.setPrefSize(x, y);
        buttonAgency.setButtonType(JFXButton.ButtonType.FLAT);
        buttonAgency.setOnAction(event -> {
            vBoxPanels.getChildren().clear();
            vBoxPanels.getChildren().add(AgencyOperations.getInstance().getViewAgency());
        });

        JFXButton buttonAutobank = new JFXButton("AGENCIA CON AUTOBANCO");
        buttonAutobank.getStyleClass().addAll("panelButton", "primaryButton");
        buttonAutobank.setPrefSize(x, y);
        buttonAutobank.setButtonType(JFXButton.ButtonType.FLAT);
        buttonAutobank.setOnAction(event -> {
            vBoxPanels.getChildren().clear();
            vBoxPanels.getChildren().add(AutoBankOperations.getInstance().getViewAutoBank());
        });

        JFXButton buttonATM = new JFXButton("CAJEROS AUTOMATICOS");
        buttonATM.getStyleClass().addAll("panelButton", "primaryButton");
        buttonATM.setPrefSize(x, y);
        buttonATM.setButtonType(JFXButton.ButtonType.FLAT);
        buttonATM.setOnAction(event -> {
            vBoxPanels.getChildren().clear();
            vBoxPanels.getChildren().add(ATMOperations.getInstance().getViewATM());
        });

        JFXButton buttonCallCenter = new JFXButton("CALL-CENTER");
        buttonCallCenter.getStyleClass().addAll("panelButton", "primaryButton");
        buttonCallCenter.setPrefSize(x, y);
        buttonCallCenter.setButtonType(JFXButton.ButtonType.FLAT);

        JFXButton buttonCreateCheck = new JFXButton("EMISIÓN DE CHEQUE");
        buttonCreateCheck.getStyleClass().addAll("panelButton", "primaryButton");
        buttonCreateCheck.setPrefSize(x, y);
        buttonCreateCheck.setButtonType(JFXButton.ButtonType.FLAT);
        buttonCreateCheck.setOnAction(event -> {
            ViewCreateCheck.getInstance().showAlertCheck(hBox, "EMITIR CHEQUE");
        });

        JFXButton buttonPurchase = new JFXButton("REALIZAR COMPRA");
        buttonPurchase.getStyleClass().addAll("panelButton", "primaryButton");
        buttonPurchase.setPrefSize(x, y);
        buttonPurchase.setButtonType(JFXButton.ButtonType.FLAT);
        buttonPurchase.setOnAction(event -> {
            ViewPurchase.getInstance().showAlertProduct(hBox, "COMPRA");
        });

        JFXButton buttonDetail = new JFXButton("DETALLES");
        buttonDetail.getStyleClass().addAll("panelButton", "primaryButton");
        buttonDetail.setPrefSize(x, y);
        buttonDetail.setButtonType(JFXButton.ButtonType.FLAT);
        buttonDetail.setOnAction(event -> {
            vBoxPanels.getChildren().clear();
            vBoxPanels.getChildren().add(ViewCustomerDetail.getInstance().getViewCustomerDetail());
        });

        JFXButton buttonExit = new JFXButton("SALIR");
        buttonExit.getStyleClass().addAll("panelButton", "dangerButton");
        buttonExit.setPrefSize(x, y);
        buttonExit.setButtonType(JFXButton.ButtonType.FLAT);
        buttonExit.setOnAction(event -> restartHBox());

        vBoxButtons.getChildren().addAll(buttonAgency, buttonAutobank, buttonATM, buttonCallCenter, buttonCreateCheck,
                buttonPurchase, buttonDetail, buttonExit);
        hBox.getChildren().clear();
        hBox.getChildren().addAll(vBoxButtons, vBoxPanels);

        return hBox;
    }

    public void showWindow() {
        stage = new Stage();
        JFXDecorator decorator = new JFXDecorator(stage, getViewAgency(), false, false, true);
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
