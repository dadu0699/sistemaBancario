package org.didierdominguez.view.admin;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.didierdominguez.bean.AutoBank;
import org.didierdominguez.controller.ControllerAutoBank;
import org.didierdominguez.util.ReportGenerator;
import org.didierdominguez.util.ScreenSize;
import org.didierdominguez.util.Verifications;
import org.didierdominguez.view.Alert;

import java.util.ArrayList;

public class ViewAutoBank extends Stage {
    private static ViewAutoBank instance;
    private HBox hBox;
    private GridPane gridPane;
    private TableView tableView;
    private ObservableList observableList;

    private ViewAutoBank() {}

    public static ViewAutoBank getInstance() {
        if (instance == null) {
            instance = new ViewAutoBank();
        }
        return instance;
    }

    public void restartHBox() {
        hBox.getChildren().clear();
        hBox.getChildren().add(gridPane);
    }

    private void updateObservableList() {
        AutoBank[] autoBank = ControllerAutoBank.getInstance().getAutoBanks();
        ArrayList<AutoBank> arrayListAutoBank = new ArrayList<>();
        for (AutoBank agency : autoBank) {
            if (agency != null) {
                arrayListAutoBank.add(agency);
            }
        }
        if (observableList != null){
            observableList.clear();
        }
        observableList = FXCollections.observableArrayList(arrayListAutoBank);
    }

    private void updateObservableList(String param) {
        AutoBank[] autoBank = ControllerAutoBank.getInstance().searchAutoBankAgencies(param);
        ArrayList<AutoBank> arrayListAutoBank = new ArrayList<>();
        for (AutoBank agency : autoBank) {
            if (agency != null) {
                arrayListAutoBank.add(agency);
            }
        }
        observableList = FXCollections.observableArrayList(arrayListAutoBank);
    }

    public void updateTableViewItems() {
        updateObservableList();
        tableView.setItems(observableList);
    }

    public void updateTableViewItems(String param) {
        updateObservableList(param);
        tableView.setItems(observableList);
    }

    public HBox getViewAutoBank() {
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

        Text textTitle = new Text("AGENCIAS CON AUTOBANCO");
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
            String[] titles = {"ID", "NOMBRE", "DIRECCIÓN", "NÚMERO TELEFÓNICO", "CAJAS", "ESCRITORIOS",
                    "EFECTIVO", "EMPLEADOS", "CAJAS DE AUTOBANCO"};
            AutoBank[] dataAutoBank = ControllerAutoBank.getInstance().getAutoBanks();
            try {
                ReportGenerator.getInstance().generatePDF("AgenciesAutoBank.pdf",
                        "AGENCIAS CON AUTOBANCO", titles, dataAutoBank);
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
            hBox.getChildren().addAll(gridPane, CreateAutoBank.getInstance().getGridPane());
        });

        JFXButton buttonUpdate = new JFXButton("Modificar");
        buttonUpdate.getStyleClass().addAll("customButton", "warningButton");
        buttonUpdate.setButtonType(JFXButton.ButtonType.FLAT);
        buttonUpdate.setPrefSize(x, y);
        buttonUpdate.setOnAction(event -> {
            hBox.getChildren().clear();
            if (tableView.getSelectionModel().getSelectedItem() != null) {
                hBox.getChildren().addAll(gridPane, UpdateAutoBank.getInstance()
                        .getGridPane((AutoBank) tableView.getSelectionModel().getSelectedItem()));
            } else {
                hBox.getChildren().add(gridPane);
            }
        });

        JFXButton buttonDelete = new JFXButton("Eliminar");
        buttonDelete.getStyleClass().addAll("customButton", "dangerButton");
        buttonDelete.setButtonType(JFXButton.ButtonType.FLAT);
        buttonDelete.setPrefSize(x, y);
        buttonDelete.setOnAction(event -> {
            AutoBank agency = (AutoBank) tableView.getSelectionModel().getSelectedItem();
            if (agency != null) {
                restartHBox();
                ControllerAutoBank.getInstance().deleteAutoBank(agency.getId());
                updateTableViewItems();
            }
        });

        hBoxButtons.getChildren().addAll(textFieldSearch, buttonReport, buttonAdd, buttonUpdate, buttonDelete);
        hBoxButtons.setPrefSize(x, y / 8);
        gridPane.add(hBoxButtons, 0, 1);

        TableColumn<AutoBank, Integer> columnID = new TableColumn<>("ID");
        columnID.setPrefWidth(50);
        columnID.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<AutoBank, String> columnName = new TableColumn<>("NOMBRE");
        columnName.setPrefWidth((3 * x / 4) / 8);
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<AutoBank, String> columnAddress = new TableColumn<>("DIRECCIÓN");
        columnAddress.setPrefWidth((3 * x / 4) / 8);
        columnAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        TableColumn<AutoBank, String> columnPhoneNumber = new TableColumn<>("NÚMERO TELEFÓNICO");
        columnPhoneNumber.setPrefWidth((3 * x / 4) / 8);
        columnPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        TableColumn<AutoBank, Integer> columnNoPayOffice = new TableColumn<>("CAJAS");
        columnNoPayOffice.setPrefWidth((3 * x / 4) / 8);
        columnNoPayOffice.setCellValueFactory(new PropertyValueFactory<>("noPayOffice"));
        TableColumn<AutoBank, Integer> columnNoCustomerService = new TableColumn<>("ESCRITORIOS");
        columnNoCustomerService.setPrefWidth((3 * x / 4) / 8);
        columnNoCustomerService.setCellValueFactory(new PropertyValueFactory<>("noCustomerService"));
        TableColumn<AutoBank, Integer> columnAutoBank = new TableColumn<>("CAJAS DE AUTOBANCO");
        columnAutoBank.setPrefWidth((3 * x / 4) / 8);
        columnAutoBank.setCellValueFactory(new PropertyValueFactory<>("autobankingBoxes"));
        TableColumn<AutoBank, Double> columnCash = new TableColumn<>("EFECTIVO");
        columnCash.setPrefWidth((3 * x / 4) / 8);
        columnCash.setCellValueFactory(new PropertyValueFactory<>("cash"));

        updateObservableList();
        tableView = new TableView<>(observableList);
        tableView.getColumns().addAll(columnID, columnName, columnAddress, columnPhoneNumber, columnNoPayOffice,
                columnNoCustomerService, columnAutoBank, columnCash);
        tableView.setPrefSize(x, 7 * y / 8);
        tableView.setOnMouseClicked(event -> {
            hBox.getChildren().clear();
            if (tableView.getSelectionModel().getSelectedItem() != null) {
                hBox.getChildren().addAll(gridPane, ShowAutoBank.getInstance()
                        .getGridPane((AutoBank) tableView.getSelectionModel().getSelectedItem()));
            } else {
                hBox.getChildren().add(gridPane);
            }
        });
        gridPane.add(tableView, 0, 2);
        hBox.getChildren().add(gridPane);

        return hBox;
    }
}

class CreateAutoBank {
    private static CreateAutoBank instance;

    private CreateAutoBank() {}

    static CreateAutoBank getInstance() {
        if (instance == null) {
            instance = new CreateAutoBank();
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

        JFXTextField fieldName = new JFXTextField();
        fieldName.setPromptText("NOMBRE");
        fieldName.setLabelFloat(true);
        fieldName.setPrefWidth(x);
        fieldName.getValidators().add(validator);
        fieldName.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                fieldName.validate();
            }
        });
        gridPane.add(fieldName, 0, 4, 2, 1);

        JFXTextField fieldAddress = new JFXTextField();
        fieldAddress.setPromptText("DIRECCIÓN");
        fieldAddress.setLabelFloat(true);
        fieldAddress.setPrefWidth(x);
        fieldAddress.getValidators().add(validator);
        fieldAddress.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                fieldAddress.validate();
            }
        });
        gridPane.add(fieldAddress, 0, 5, 2, 1);

        JFXTextField fieldPhoneNumber = new JFXTextField();
        fieldPhoneNumber.setPromptText("NÚMERO TELEFÓNICO");
        fieldPhoneNumber.setLabelFloat(true);
        fieldPhoneNumber.setPrefWidth(x);
        fieldPhoneNumber.getValidators().add(validator);
        fieldPhoneNumber.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                fieldPhoneNumber.validate();
            }
        });
        gridPane.add(fieldPhoneNumber, 0, 6, 2, 1);

        Label labelNoPayOffice = new Label("CAJAS:");
        gridPane.add(labelNoPayOffice, 0, 7);
        Spinner<Integer> spinnerNoPayOffice = new Spinner<>(0, 100, 0, 1);
        spinnerNoPayOffice.setEditable(true);
        spinnerNoPayOffice.setPrefWidth(x);
        gridPane.add(spinnerNoPayOffice, 1, 7);

        Label labelNoCustomerService = new Label("ESCRITORIOS:");
        gridPane.add(labelNoCustomerService, 0, 8);
        Spinner<Integer> spinnerNoCustomerService = new Spinner<>(0, 100, 0, 1);
        spinnerNoCustomerService.setEditable(true);
        spinnerNoCustomerService.setPrefWidth(x);
        gridPane.add(spinnerNoCustomerService, 1, 8);

        Label labelAutobankingBoxes = new Label("CAJAS DE AUTOBANCO:");
        gridPane.add(labelAutobankingBoxes, 0, 9);
        Spinner<Integer> spinnerAutobankingBoxes = new Spinner<>(0, 100, 0, 1);
        spinnerAutobankingBoxes.setEditable(true);
        spinnerAutobankingBoxes.setPrefWidth(x);
        gridPane.add(spinnerAutobankingBoxes, 1, 9);

        Label labelCash = new Label("EFECTIVO:");
        gridPane.add(labelCash, 0, 10);
        Spinner<Double> spinnerCash = new Spinner<>(0.00, 100000.00, 0, 1);
        spinnerCash.setEditable(true);
        spinnerCash.setPrefWidth(x);
        gridPane.add(spinnerCash, 1, 10);

        JFXButton buttonAdd = new JFXButton("Agregar");
        buttonAdd.getStyleClass().addAll("customButton", "primaryButton");
        buttonAdd.setButtonType(JFXButton.ButtonType.FLAT);
        buttonAdd.setPrefSize(x, y / 20);
        buttonAdd.setOnAction(event -> {
            if (fieldName.getText().length() == 0
                    || fieldAddress.getText().length() == 0
                    || fieldPhoneNumber.getText().length() == 0
                    || !fieldPhoneNumber.getText().matches("^(?=(?:[1-9]){1})\\d{4}-\\d{4}")
                    || !Verifications.getInstance().isNumeric(spinnerNoPayOffice.getEditor().getText())
                    || !Verifications.getInstance().isNumeric(spinnerNoCustomerService.getEditor().getText())
                    || !Verifications.getInstance().isNumeric(spinnerAutobankingBoxes.getEditor().getText())
                    || !Verifications.getInstance().isNumeric(spinnerCash.getEditor().getText())) {
                Alert.getInstance().showAlert(gridPane, "ERROR", "UNO O MÁS DATOS SON INCORRECTOS");
            } else {
                AutoBank searchNameAutoBank = ControllerAutoBank.getInstance()
                        .searchAutoBank(fieldName.getText().trim().toUpperCase());
                if (searchNameAutoBank != null) {
                    Alert.getInstance().showAlert(gridPane, "ERROR", "LA AGENCIA CON AUTOBANCO YA EXISTE");
                } else {
                    ControllerAutoBank.getInstance().createAutoBank(fieldName.getText().trim().toUpperCase(),
                            fieldAddress.getText().trim().toUpperCase(), fieldPhoneNumber.getText().trim(),
                            Integer.parseInt(spinnerNoPayOffice.getEditor().getText()),
                            Integer.parseInt(spinnerNoCustomerService.getEditor().getText()),
                            Double.parseDouble(spinnerCash.getEditor().getText()),
                            Integer.parseInt(spinnerAutobankingBoxes.getEditor().getText()));
                    ViewAutoBank.getInstance().updateTableViewItems();
                }
            }
        });
        gridPane.add(buttonAdd, 0, 11);

        JFXButton buttonCancel = new JFXButton("Cancelar");
        buttonCancel.getStyleClass().addAll("customButton", "dangerButton");
        buttonCancel.setButtonType(JFXButton.ButtonType.FLAT);
        buttonCancel.setPrefSize(x, y / 20);
        buttonCancel.setOnAction(event -> ViewAutoBank.getInstance().restartHBox());
        gridPane.add(buttonCancel, 1, 11);

        return gridPane;
    }
}

class UpdateAutoBank {
    private static UpdateAutoBank instance;

    private UpdateAutoBank(){}

    static UpdateAutoBank getInstance() {
        if (instance == null) {
            instance = new UpdateAutoBank();
        }
        return instance;
    }

    GridPane getGridPane(AutoBank autoBank) {
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

        JFXTextField fieldName = new JFXTextField(autoBank.getName());
        fieldName.setPromptText("NOMBRE");
        fieldName.setLabelFloat(true);
        fieldName.setPrefWidth(x);
        fieldName.getValidators().add(validator);
        fieldName.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                fieldName.validate();
            }
        });
        gridPane.add(fieldName, 0, 4, 2, 1);

        JFXTextField fieldAddress = new JFXTextField(autoBank.getAddress());
        fieldAddress.setPromptText("DIRECCIÓN");
        fieldAddress.setLabelFloat(true);
        fieldAddress.setPrefWidth(x);
        fieldAddress.getValidators().add(validator);
        fieldAddress.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                fieldAddress.validate();
            }
        });
        gridPane.add(fieldAddress, 0, 5, 2, 1);

        JFXTextField fieldPhoneNumber = new JFXTextField(autoBank.getPhoneNumber());
        fieldPhoneNumber.setPromptText("NÚMERO TELEFÓNICO");
        fieldPhoneNumber.setLabelFloat(true);
        fieldPhoneNumber.setPrefWidth(x);
        fieldPhoneNumber.getValidators().add(validator);
        fieldPhoneNumber.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                fieldPhoneNumber.validate();
            }
        });
        gridPane.add(fieldPhoneNumber, 0, 6, 2, 1);

        Label labelNoPayOffice = new Label("CAJAS:");
        gridPane.add(labelNoPayOffice, 0, 7);
        Spinner<Integer> spinnerNoPayOffice = new Spinner<>(0, 1000,
                autoBank.getNoPayOffice(), 1);
        spinnerNoPayOffice.setEditable(true);
        spinnerNoPayOffice.setPrefWidth(x);
        gridPane.add(spinnerNoPayOffice, 1, 7);

        Label labelNoCustomerService = new Label("ESCRITORIOS:");
        gridPane.add(labelNoCustomerService, 0, 8);
        Spinner<Integer> spinnerNoCustomerService = new Spinner<>(0, 100,
                autoBank.getNoCustomerService(), 1);
        spinnerNoCustomerService.setEditable(true);
        spinnerNoCustomerService.setPrefWidth(x);
        gridPane.add(spinnerNoCustomerService, 1, 8);

        Label labelAutobankingBoxes = new Label("CAJAS DE AUTOBANCO:");
        gridPane.add(labelAutobankingBoxes, 0, 9);
        Spinner<Integer> spinnerAutobankingBoxes = new Spinner<>(0, 100,
                autoBank.getAutobankingBoxes(), 1);
        spinnerAutobankingBoxes.setEditable(true);
        spinnerAutobankingBoxes.setPrefWidth(x);
        gridPane.add(spinnerAutobankingBoxes, 1, 9);

        Label labelCash = new Label("EFECTIVO:");
        gridPane.add(labelCash, 0, 10);
        Spinner<Double> spinnerCash = new Spinner<>(0.00, 100000.00,
                autoBank.getCash(), 1);
        spinnerCash.setEditable(true);
        spinnerCash.setPrefWidth(x);
        gridPane.add(spinnerCash, 1, 10);

        JFXButton buttonUpdate = new JFXButton("Modificar");
        buttonUpdate.getStyleClass().addAll("customButton", "primaryButton");
        buttonUpdate.setButtonType(JFXButton.ButtonType.FLAT);
        buttonUpdate.setPrefSize(x, y / 20);
        buttonUpdate.setOnAction(event -> {
            if (fieldName.getText().length() == 0
                    || fieldAddress.getText().length() == 0
                    || fieldPhoneNumber.getText().length() == 0
                    || !fieldPhoneNumber.getText().matches("^(?=(?:[1-9]){1})\\d{4}-\\d{4}")
                    || !Verifications.getInstance().isNumeric(spinnerNoPayOffice.getEditor().getText())
                    || !Verifications.getInstance().isNumeric(spinnerNoCustomerService.getEditor().getText())
                    || !Verifications.getInstance().isNumeric(spinnerAutobankingBoxes.getEditor().getText())
                    || !Verifications.getInstance().isNumeric(spinnerCash.getEditor().getText())) {
                Alert.getInstance().showAlert(gridPane, "ERROR", "UNO O MÁS DATOS SON INCORRECTOS");
            } else {
                ControllerAutoBank.getInstance().updateAutoBank(autoBank.getId(),
                        fieldName.getText().trim().toUpperCase(),
                        fieldAddress.getText().trim().toUpperCase(), fieldPhoneNumber.getText().trim(),
                        Integer.parseInt(spinnerNoPayOffice.getEditor().getText()),
                        Integer.parseInt(spinnerNoCustomerService.getEditor().getText()),
                        Double.parseDouble(spinnerCash.getEditor().getText()),
                        Integer.parseInt(spinnerAutobankingBoxes.getEditor().getText()));
                if (!ControllerAutoBank.getInstance().updateAutoBank()){
                    Alert.getInstance().showAlert(gridPane, "ERROR", "ERROR AL MODIFICAR LA AGENCIA CON AUTOBANCO");
                }
                ViewAutoBank.getInstance().updateTableViewItems();
            }
        });
        gridPane.add(buttonUpdate, 0, 11);

        JFXButton buttonCancel = new JFXButton("Cancelar");
        buttonCancel.getStyleClass().addAll("customButton", "dangerButton");
        buttonCancel.setButtonType(JFXButton.ButtonType.FLAT);
        buttonCancel.setPrefSize(x, y / 20);
        buttonCancel.setOnAction(event -> ViewAutoBank.getInstance().restartHBox());
        gridPane.add(buttonCancel, 1, 11);

        return gridPane;
    }
}

class ShowAutoBank {
    private static ShowAutoBank instance;

    private ShowAutoBank() {
    }

    static ShowAutoBank getInstance() {
        if (instance == null) {
            instance = new ShowAutoBank();
        }
        return instance;
    }

    GridPane getGridPane(AutoBank autoBank) {
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

        JFXTextField fieldName = new JFXTextField(autoBank.getName());
        fieldName.setPromptText("NOMBRE");
        fieldName.setLabelFloat(true);
        fieldName.setPrefWidth(x);
        fieldName.setEditable(false);
        gridPane.add(fieldName, 0, 4, 2, 1);

        JFXTextField fieldAddress = new JFXTextField(autoBank.getAddress());
        fieldAddress.setPromptText("DIRECCIÓN");
        fieldAddress.setLabelFloat(true);
        fieldAddress.setPrefWidth(x);
        fieldAddress.setEditable(false);
        gridPane.add(fieldAddress, 0, 5, 2, 1);

        JFXTextField fieldPhoneNumber = new JFXTextField(autoBank.getPhoneNumber());
        fieldPhoneNumber.setPromptText("NÚMERO TELEFÓNICO");
        fieldPhoneNumber.setLabelFloat(true);
        fieldPhoneNumber.setPrefWidth(x);
        fieldPhoneNumber.setEditable(false);
        gridPane.add(fieldPhoneNumber, 0, 6, 2, 1);

        JFXTextField fieldNoPayOffice = new JFXTextField(autoBank.getNoPayOffice().toString());
        fieldNoPayOffice.setPromptText("CAJAS");
        fieldNoPayOffice.setLabelFloat(true);
        fieldNoPayOffice.setPrefWidth(x);
        fieldNoPayOffice.setEditable(false);
        gridPane.add(fieldNoPayOffice, 0, 7, 2, 1);

        JFXTextField fieldNoCustomerService = new JFXTextField(autoBank.getNoCustomerService().toString());
        fieldNoCustomerService.setPromptText("ESCRITORIOS");
        fieldNoCustomerService.setLabelFloat(true);
        fieldNoCustomerService.setPrefWidth(x);
        fieldNoCustomerService.setEditable(false);
        gridPane.add(fieldNoCustomerService, 0, 8, 2, 1);

        JFXTextField fieldAutobankingBoxes = new JFXTextField(autoBank.getAutobankingBoxes().toString());
        fieldAutobankingBoxes.setPromptText("CAJAS DE AUTOBANCO");
        fieldAutobankingBoxes.setLabelFloat(true);
        fieldAutobankingBoxes.setPrefWidth(x);
        fieldAutobankingBoxes.setEditable(false);
        gridPane.add(fieldAutobankingBoxes, 0, 9, 2, 1);

        JFXTextField fieldCash = new JFXTextField(autoBank.getCash().toString());
        fieldCash.setPromptText("EFECTIVO");
        fieldCash.setLabelFloat(true);
        fieldCash.setPrefWidth(x);
        fieldCash.setEditable(false);
        gridPane.add(fieldCash, 0, 10, 2, 1);

        JFXButton buttonCopy = new JFXButton("Copiar");
        buttonCopy.getStyleClass().addAll("customButton", "primaryButton");
        buttonCopy.setButtonType(JFXButton.ButtonType.FLAT);
        buttonCopy.setPrefSize(x, y / 20);
        buttonCopy.setOnAction(event -> {
            final Clipboard clipboard = Clipboard.getSystemClipboard();
            final ClipboardContent content = new ClipboardContent();
            content.putString(
                    "NOMBRE:                " + autoBank.getName()
                    + "\nDIRECCIÓN:             " + autoBank.getAddress()
                    + "\nNÚMERO TELEFÓNICO:     " + autoBank.getPhoneNumber()
                    + "\nCAJAS:                 " + autoBank.getNoPayOffice().toString()
                    + "\nESCRITORIOS:           " + autoBank.getNoCustomerService().toString()
                    + "\nCAJAS DE AUTOBANCO:    " + autoBank.getAutobankingBoxes().toString()
                    + "\nEFECTIVO:              " + autoBank.getCash().toString());
            clipboard.setContent(content);
        });
        gridPane.add(buttonCopy, 0, 11);

        JFXButton buttonCancel = new JFXButton("Cancelar");
        buttonCancel.getStyleClass().addAll("customButton", "dangerButton");
        buttonCancel.setButtonType(JFXButton.ButtonType.FLAT);
        buttonCancel.setPrefSize(x, y / 20);
        buttonCancel.setOnAction(event -> ViewAutoBank.getInstance().restartHBox());
        gridPane.add(buttonCancel, 1, 11);

        return gridPane;
    }
}