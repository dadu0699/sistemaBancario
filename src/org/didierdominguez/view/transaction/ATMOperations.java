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
import org.didierdominguez.bean.ATM;
import org.didierdominguez.controller.ControllerATM;
import org.didierdominguez.util.ScreenSize;

import java.util.ArrayList;

public class ATMOperations extends Stage {
    private static ATMOperations instance;
    private HBox hBoxPanels;
    private VBox vBoxPanels;
    private ObservableList observableList;

    private ATMOperations() {
    }

    public static ATMOperations getInstance() {
        if (instance == null) {
            instance = new ATMOperations();
        }
        return instance;
    }

    public void restartHBox() {
        hBoxPanels.getChildren().clear();
        hBoxPanels.getChildren().add(vBoxPanels);
    }

    private void updateObservableList() {
        ATM[] atms = ControllerATM.getInstance().getAtms();
        ArrayList<ATM> arrayListBankingAgency = new ArrayList<>();
        for (ATM atm : atms) {
            if (atm != null && atm.getState()) {
                arrayListBankingAgency.add(atm);
            }
        }
        if (observableList != null){
            observableList.clear();
        }
        observableList = FXCollections.observableArrayList(arrayListBankingAgency);
    }

    VBox getViewATM() {
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
        JFXComboBox<ATM> comboBox = new JFXComboBox<>(observableList);
        comboBox.setPromptText("Cajeros Automaticos");
        comboBox.setPrefSize(x, y);
        gridPaneTitle.add(comboBox, 0, 1);

        hBoxPanels = new HBox();
        hBoxPanels.setPrefSize(x, 7 * y / 8);
        vBoxPanels = new VBox();
        vBoxPanels.setPrefSize(x / 3, 7 * y / 8);
        vBoxPanels.getChildren().addAll(getGridPanePayOffice());
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

        JFXButton buttonMonetaryWithdrawal = new JFXButton("RETIRO");
        buttonMonetaryWithdrawal.getStyleClass().addAll("panelButton", "primaryButton");
        buttonMonetaryWithdrawal.setButtonType(JFXButton.ButtonType.FLAT);
        buttonMonetaryWithdrawal.setPrefSize(x, y / 20);
        buttonMonetaryWithdrawal.setOnAction(event -> {
            restartHBox();
            hBoxPanels.getChildren().add(ViewMonetaryWithdrawal.getInstance().getGridPane());
        });
        gridPane.add(buttonMonetaryWithdrawal, 0, 1);

        JFXButton buttonBalance = new JFXButton("SALDO");
        buttonBalance.getStyleClass().addAll("panelButton", "primaryButton");
        buttonBalance.setButtonType(JFXButton.ButtonType.FLAT);
        buttonBalance.setPrefSize(x, y / 20);
        buttonBalance.setOnAction(event -> {
            restartHBox();
            hBoxPanels.getChildren().add(ViewBalance.getInstance().getGridPane());
        });
        gridPane.add(buttonBalance, 0, 2);

        return gridPane;
    }
}
