package org.didierdominguez.view.admin;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.didierdominguez.bean.ATM;
import org.didierdominguez.controller.ControllerATM;
import org.didierdominguez.util.ReportGenerator;
import org.didierdominguez.util.ScreenSize;
import org.didierdominguez.util.Verifications;
import org.didierdominguez.view.Alert;

import java.util.ArrayList;

public class ViewATM extends Stage {
    private static ViewATM instance;
    private HBox hBox;
    private GridPane gridPane;
    private TableView tableView;
    private ObservableList observableList;

    private ViewATM() {
    }

    public static ViewATM getInstance() {
        if (instance == null) {
            instance = new ViewATM();
        }
        return instance;
    }

    public void restartHBox() {
        hBox.getChildren().clear();
        hBox.getChildren().add(gridPane);
    }

    private void updateObservableList() {
        ATM[] atm = ControllerATM.getInstance().getAtms();
        ArrayList<ATM> arrayListATM = new ArrayList<>();
        for (ATM atms : atm) {
            if (atms != null) {
                arrayListATM.add(atms);
            }
        }
        if (observableList != null) {
            observableList.clear();
        }
        observableList = FXCollections.observableArrayList(arrayListATM);
    }

    private void updateObservableList(String param) {
        ATM[] atm = ControllerATM.getInstance().searchATMs(param);
        ArrayList<ATM> arrayListATM = new ArrayList<>();
        for (ATM atms : atm) {
            if (atms != null) {
                arrayListATM.add(atms);
            }
        }
        observableList = FXCollections.observableArrayList(arrayListATM);
    }

    public void updateTableViewItems() {
        updateObservableList();
        tableView.setItems(observableList);
    }

    public void updateTableViewItems(String param) {
        updateObservableList(param);
        tableView.setItems(observableList);
    }

    public HBox getViewATM() {
        hBox = new HBox();
        gridPane = new GridPane();

        double x = ScreenSize.getInstance().getX();
        double y = ScreenSize.getInstance().getY();

        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20));
        // gridPane.setGridLinesVisible(true);
        gridPane.setMinWidth(x / 2);
        gridPane.setPrefSize(x, y);
        hBox.setPrefSize(x, y);

        Text textTitle = new Text("CAJEROS AUTOMATICOS");
        textTitle.getStyleClass().add("textTitle");
        textTitle.setFont(new Font(25));
        gridPane.add(textTitle, 0, 0);

        HBox hBoxButtons = new HBox();
        JFXTextField textFieldSearch = new JFXTextField();
        textFieldSearch.setPromptText("BUSCAR");
        textFieldSearch.setPrefSize(x, y);
        textFieldSearch.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue,
                                                    String newValue) ->
                updateTableViewItems(textFieldSearch.getText().trim().toUpperCase())
        );

        JFXButton buttonReport = new JFXButton("Reporte");
        buttonReport.getStyleClass().addAll("customButton", "reportButton");
        buttonReport.setButtonType(JFXButton.ButtonType.FLAT);
        buttonReport.setPrefSize(x, y);
        buttonReport.setOnAction(event -> {
            String[] titles = {"ID", "UBICACIÓN", "EFECTIVO", "ESTADO", "TRANSACCIONES"};
            ATM[] dataATMs = ControllerATM.getInstance().getAtms();
            try {
                ReportGenerator.getInstance().generatePDF("ATMs.pdf",
                        "CAJEROS AUTOMATICOS", titles, dataATMs);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        JFXButton buttonAdd = new JFXButton("Agregar");
        buttonAdd.getStyleClass().addAll("customButton", "primaryButton");
        buttonAdd.setButtonType(JFXButton.ButtonType.FLAT);
        buttonAdd.setPrefSize(x, y);
        buttonAdd.setOnAction(event -> {
            hBox.getChildren().clear();
            hBox.getChildren().addAll(gridPane, CreateATM.getInstance().getGridPane());
        });

        JFXButton buttonUpdate = new JFXButton("Modificar");
        buttonUpdate.getStyleClass().addAll("customButton", "warningButton");
        buttonUpdate.setButtonType(JFXButton.ButtonType.FLAT);
        buttonUpdate.setPrefSize(x, y);
        buttonUpdate.setOnAction(event -> {
            hBox.getChildren().clear();
            if (tableView.getSelectionModel().getSelectedItem() != null) {
                hBox.getChildren().addAll(gridPane, UpdateATM.getInstance()
                        .getGridPane((ATM) tableView.getSelectionModel().getSelectedItem()));
            } else {
                hBox.getChildren().add(gridPane);
            }
        });

        JFXButton buttonDelete = new JFXButton("Eliminar");
        buttonDelete.getStyleClass().addAll("customButton", "dangerButton");
        buttonDelete.setButtonType(JFXButton.ButtonType.FLAT);
        buttonDelete.setPrefSize(x, y);
        buttonDelete.setOnAction(event -> {
            ATM atm = (ATM) tableView.getSelectionModel().getSelectedItem();
            if (atm != null) {
                restartHBox();
                ControllerATM.getInstance().deleteATM(atm.getId());
                updateTableViewItems();
            }
        });

        hBoxButtons.getChildren().addAll(textFieldSearch, buttonReport, buttonAdd, buttonUpdate, buttonDelete);
        hBoxButtons.setPrefSize(x, y / 8);
        gridPane.add(hBoxButtons, 0, 1);

        TableColumn<ATM, Integer> columnID = new TableColumn<>("ID");
        columnID.setPrefWidth(50);
        columnID.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<ATM, String> columnLocation = new TableColumn<>("UBICACIÓN");
        columnLocation.setPrefWidth((3 * x / 4) / 5);
        columnLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        TableColumn<ATM, Double> columnCash = new TableColumn<>("EFECTIVO");
        columnCash.setPrefWidth((3 * x / 4) / 5);
        columnCash.setCellValueFactory(new PropertyValueFactory<>("cash"));

        TableColumn<ATM, Boolean> columnState = new TableColumn<>("ESTADO");
        columnState.setPrefWidth((3 * x / 4) / 5);
        columnState.setCellValueFactory(new PropertyValueFactory<>("state"));
        columnState.setCellFactory(col -> new TableCell<ATM, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty) ;
                setText(empty ? null : item ? "ACTIVO" : "EN MANTENIMIENTO" );
            }
        });

        TableColumn<ATM, Integer> columnTransactions = new TableColumn<>("TRANSACCIONES");
        columnTransactions.setPrefWidth((3 * x / 4) / 5);
        columnTransactions.setCellValueFactory(new PropertyValueFactory<>("transactions"));

        updateObservableList();
        tableView = new TableView<>(observableList);
        tableView.getColumns().addAll(columnID, columnLocation, columnCash, columnState, columnTransactions);
        tableView.setPrefSize(x, 7 * y / 8);
        tableView.setOnMouseClicked(event -> {
            hBox.getChildren().clear();
            if (tableView.getSelectionModel().getSelectedItem() != null) {
                hBox.getChildren().addAll(gridPane, ShowATM.getInstance()
                        .getGridPane((ATM) tableView.getSelectionModel().getSelectedItem()));
            } else {
                hBox.getChildren().add(gridPane);
            }
        });
        gridPane.add(tableView, 0, 2);
        hBox.getChildren().add(gridPane);

        return hBox;
    }
}

class CreateATM {
    private static CreateATM instance;

    private CreateATM() {}

    static CreateATM getInstance() {
        if (instance == null) {
            instance = new CreateATM();
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

        Text textTitle = new Text("AGREGAR");
        textTitle.getStyleClass().add("textTitle");
        textTitle.setFont(new Font(25));
        gridPane.add(textTitle, 0, 3, 2, 1);

        JFXTextField fieldLocation = new JFXTextField();
        fieldLocation.setPromptText("UBICACIÓN");
        fieldLocation.setLabelFloat(true);
        fieldLocation.setPrefWidth(x);
        fieldLocation.getValidators().add(validator);
        fieldLocation.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                fieldLocation.validate();
            }
        });
        gridPane.add(fieldLocation, 0, 4, 2, 1);

        Label labelCash = new Label("EFECTIVO:");
        gridPane.add(labelCash, 0, 5);
        Spinner<Double> spinnerCash = new Spinner<>(0.00, 100000.00, 0, 1);
        spinnerCash.setEditable(true);
        spinnerCash.setPrefWidth(x);
        gridPane.add(spinnerCash, 1, 5);

        JFXButton buttonAdd = new JFXButton("Agregar");
        buttonAdd.getStyleClass().addAll("customButton", "primaryButton");
        buttonAdd.setButtonType(JFXButton.ButtonType.FLAT);
        buttonAdd.setPrefSize(x, y / 20);
        buttonAdd.setOnAction(event -> {
            if (fieldLocation.getText().length() == 0 || fieldLocation.getText().length() == 0
                    || !Verifications.getInstance().isNumeric(spinnerCash.getEditor().getText())) {
                Alert.getInstance().showAlert(gridPane, "ERROR", "UNO O MÁS DATOS SON INCORRECTOS");
            } else {
                ControllerATM.getInstance().createATM(fieldLocation.getText().trim().toUpperCase(),
                        Double.parseDouble(spinnerCash.getEditor().getText()));
                ViewATM.getInstance().updateTableViewItems();
            }
        });
        gridPane.add(buttonAdd, 0, 6);

        JFXButton buttonCancel = new JFXButton("Cancelar");
        buttonCancel.getStyleClass().addAll("customButton", "dangerButton");
        buttonCancel.setButtonType(JFXButton.ButtonType.FLAT);
        buttonCancel.setPrefSize(x, y / 20);
        buttonCancel.setOnAction(event -> ViewATM.getInstance().restartHBox());
        gridPane.add(buttonCancel, 1, 6);

        return gridPane;
    }
}

class UpdateATM {
    private static UpdateATM instance;

    private UpdateATM(){}

    static UpdateATM getInstance() {
        if (instance == null) {
            instance = new UpdateATM();
        }
        return instance;
    }

    GridPane getGridPane(ATM atm) {
        GridPane gridPane = new GridPane();
        RequiredFieldValidator validator = new RequiredFieldValidator();

        double x = ScreenSize.getInstance().getX();
        double y = ScreenSize.getInstance().getY();

        gridPane.setVgap(25);
        gridPane.setPadding(new Insets(20));
        // gridPane.setGridLinesVisible(true);

        Text textTitle = new Text("MODIFICAR");
        textTitle.getStyleClass().add("textTitle");
        textTitle.setFont(new Font(25));
        gridPane.add(textTitle, 0, 3, 2, 1);

        JFXTextField fieldLocation = new JFXTextField(atm.getLocation());
        fieldLocation.setPromptText("UBICACIÓN");
        fieldLocation.setLabelFloat(true);
        fieldLocation.setPrefWidth(x);
        fieldLocation.getValidators().add(validator);
        fieldLocation.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                fieldLocation.validate();
            }
        });
        gridPane.add(fieldLocation, 0, 4, 2, 1);

        Label labelCash = new Label("EFECTIVO:");
        gridPane.add(labelCash, 0, 5);
        Spinner<Double> spinnerCash = new Spinner<>(0.00, 100000.00,
                atm.getCash(), 1);
        spinnerCash.setEditable(true);
        spinnerCash.setPrefWidth(x);
        gridPane.add(spinnerCash, 1, 5);

        JFXToggleButton toggleState = new JFXToggleButton();
        toggleState.setPrefWidth(x);
        toggleState.selectedProperty().setValue(atm.getState());
        toggleState.setText(toggleState.isSelected() ? "ACTIVO" : "EN MANTENIMIENTO");
        toggleState.selectedProperty().addListener((observable, oldValue, newValue) ->
                toggleState.setText(toggleState.isSelected() ? "ACTIVO" : "EN MANTENIMIENTO")
        );
        gridPane.setMargin(toggleState, new Insets(-25, 0, -25, 0));
        gridPane.add(toggleState, 0, 6, 2, 1);

        JFXButton buttonUpdate = new JFXButton("Modificar");
        buttonUpdate.getStyleClass().addAll("customButton", "primaryButton");
        buttonUpdate.setButtonType(JFXButton.ButtonType.FLAT);
        buttonUpdate.setPrefSize(x, y / 20);
        buttonUpdate.setOnAction(event -> {
            if (fieldLocation.getText().length() == 0
                    || !Verifications.getInstance().isNumeric(spinnerCash.getEditor().getText())) {
                Alert.getInstance().showAlert(gridPane, "ERROR", "UNO O MÁS DATOS SON INCORRECTOS");
            } else {
                ControllerATM.getInstance().updateATM(atm.getId(), fieldLocation.getText().trim().toUpperCase(),
                        Double.parseDouble(spinnerCash.getEditor().getText()), toggleState.isSelected());
                if (!ControllerATM.getInstance().updateATM()){
                    Alert.getInstance().showAlert(gridPane, "ERROR", "ERROR AL MODIFICAR EL CAJERO AUTOMATICO");
                }
                ViewATM.getInstance().updateTableViewItems();
            }
        });
        gridPane.add(buttonUpdate, 0, 7);

        JFXButton buttonCancel = new JFXButton("Cancelar");
        buttonCancel.getStyleClass().addAll("customButton", "dangerButton");
        buttonCancel.setButtonType(JFXButton.ButtonType.FLAT);
        buttonCancel.setPrefSize(x, y / 20);
        buttonCancel.setOnAction(event -> ViewATM.getInstance().restartHBox());
        gridPane.add(buttonCancel, 1, 7);

        return gridPane;
    }
}

class ShowATM {
    private static ShowATM instance;

    private ShowATM() {
    }

    static ShowATM getInstance() {
        if (instance == null) {
            instance = new ShowATM();
        }
        return instance;
    }

    GridPane getGridPane(ATM atm) {
        GridPane gridPane = new GridPane();

        double x = ScreenSize.getInstance().getX();
        double y = ScreenSize.getInstance().getY();

        gridPane.setVgap(25);
        gridPane.setPadding(new Insets(20));
        // gridPane.setGridLinesVisible(true);

        Text textTitle = new Text("MOSTRAR");
        textTitle.getStyleClass().add("textTitle");
        textTitle.setFont(new Font(25));
        gridPane.add(textTitle, 0, 3, 2, 1);

        JFXTextField fieldLocation = new JFXTextField(atm.getLocation());
        fieldLocation.setPromptText("UBICACIÓN");
        fieldLocation.setLabelFloat(true);
        fieldLocation.setPrefWidth(x);
        fieldLocation.setEditable(false);
        gridPane.add(fieldLocation, 0, 4, 2, 1);

        JFXTextField fieldCash = new JFXTextField(atm.getCash().toString());
        fieldCash.setPromptText("EFECTIVO");
        fieldCash.setLabelFloat(true);
        fieldCash.setPrefWidth(x);
        fieldCash.setEditable(false);
        gridPane.add(fieldCash, 0, 5, 2, 1);

        JFXTextField fieldState = new JFXTextField(atm.getState() ? "ACTIVO" : "EN MANTENIMIENTO");
        fieldState.setPromptText("ESTADO");
        fieldState.setLabelFloat(true);
        fieldState.setPrefWidth(x);
        fieldState.setEditable(false);
        gridPane.add(fieldState, 0, 6, 2, 1);

        JFXButton buttonCopy = new JFXButton("Copiar");
        buttonCopy.getStyleClass().addAll("customButton", "primaryButton");
        buttonCopy.setButtonType(JFXButton.ButtonType.FLAT);
        buttonCopy.setPrefSize(x, y / 20);
        buttonCopy.setOnAction(event -> {
            final Clipboard clipboard = Clipboard.getSystemClipboard();
            final ClipboardContent content = new ClipboardContent();
            content.putString(
                    "UBICACIÓN:      " + atm.getLocation()
                    + "\nEFECTIVO:       " + atm.getCash()
                    + "\nESTADO:         " + (atm.getState() ? "ACTIVO" : "EN MANTENIMIENTO"));
            clipboard.setContent(content);
        });
        gridPane.add(buttonCopy, 0, 7);

        JFXButton buttonCancel = new JFXButton("Cancelar");
        buttonCancel.getStyleClass().addAll("customButton", "dangerButton");
        buttonCancel.setButtonType(JFXButton.ButtonType.FLAT);
        buttonCancel.setPrefSize(x, y / 20);
        buttonCancel.setOnAction(event -> ViewAgency.getInstance().restartHBox());
        gridPane.add(buttonCancel, 1, 7);

        return gridPane;
    }
}
