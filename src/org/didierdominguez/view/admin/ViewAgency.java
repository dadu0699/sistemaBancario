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
import org.didierdominguez.bean.BankingAgency;
import org.didierdominguez.controller.ControllerBankingAgency;
import org.didierdominguez.util.ReportGenerator;
import org.didierdominguez.util.ScreenSize;
import org.didierdominguez.util.Verifications;
import org.didierdominguez.view.Alert;

import java.util.ArrayList;

public class ViewAgency extends Stage {
    private static ViewAgency instance;
    private HBox hBox;
    private GridPane gridPane;
    private TableView tableView;
    private ObservableList observableList;

    private ViewAgency() {
    }

    public static ViewAgency getInstance() {
        if (instance == null) {
            instance = new ViewAgency();
        }
        return instance;
    }

    public void restartHBox() {
        hBox.getChildren().clear();
        hBox.getChildren().add(gridPane);
    }

    private void updateObservableList() {
        BankingAgency[] bankingAgency = ControllerBankingAgency.getInstance().getBankingAgencies();
        ArrayList<BankingAgency> arrayListBankingAgency = new ArrayList<>();
        for (BankingAgency agency : bankingAgency) {
            if (agency != null) {
                arrayListBankingAgency.add(agency);
            }
        }
        if (observableList != null) {
            observableList.clear();
        }
        observableList = FXCollections.observableArrayList(arrayListBankingAgency);
        // observableList =
        // FXCollections.observableArrayList(ControllerBankingAgency.getInstance().getBankingAgencies());
    }

    private void updateObservableList(String param) {
        BankingAgency[] bankingAgency = ControllerBankingAgency.getInstance().searchBankingAgencies(param);
        ArrayList<BankingAgency> arrayListBankingAgency = new ArrayList<>();
        for (BankingAgency agency : bankingAgency) {
            if (agency != null) {
                arrayListBankingAgency.add(agency);
            }
        }
        observableList = FXCollections.observableArrayList(arrayListBankingAgency);
    }

    public void updateTableViewItems() {
        updateObservableList();
        tableView.setItems(observableList);
    }

    public void updateTableViewItems(String param) {
        updateObservableList(param);
        tableView.setItems(observableList);
    }

    public HBox getViewAgency() {
        hBox = new HBox();
        gridPane = new GridPane();

        double x = ScreenSize.getInstance().getX();
        double y = ScreenSize.getInstance().getY();

        gridPane.setId("gridViewAgency");
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20));
        // gridPane.setGridLinesVisible(true);
        gridPane.setMinWidth(x / 2);
        gridPane.setPrefSize(x, y);
        hBox.setPrefSize(x, y);

        Text textTitle = new Text("AGENCIAS BANCARIAS");
        textTitle.getStyleClass().add("textTitle");
        textTitle.setFont(new Font(25));
        gridPane.add(textTitle, 0, 0);

        HBox hBoxButtons = new HBox();
        JFXTextField textFieldSearch = new JFXTextField();
        textFieldSearch.setPromptText("BUSCAR");
        textFieldSearch.setPrefSize(x, y);
        textFieldSearch.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue,
                String newValue) -> updateTableViewItems(textFieldSearch.getText().trim().toUpperCase()));

        JFXButton buttonReport = new JFXButton("Reporte");
        buttonReport.getStyleClass().addAll("customButton", "reportButton");
        buttonReport.setButtonType(JFXButton.ButtonType.FLAT);
        buttonReport.setPrefSize(x, y);
        buttonReport.setOnAction(event -> {
            String[] titles = { "ID", "NOMBRE", "DIRECCIÓN", "NÚMERO TELEFÓNICO", "CAJAS", "ESCRITORIOS", "EFECTIVO",
                    "EMPLEADOS" };
            BankingAgency[] dataBankingAgency = ControllerBankingAgency.getInstance().getBankingAgencies();
            try {
                ReportGenerator.getInstance().generatePDF("Agencies.pdf", "AGENCIAS BANCARIAS", titles,
                        dataBankingAgency);
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
            hBox.getChildren().addAll(gridPane, CreateBankingAgency.getInstance().getGridPane());
        });

        JFXButton buttonUpdate = new JFXButton("Modificar");
        buttonUpdate.getStyleClass().addAll("customButton", "warningButton");
        buttonUpdate.setButtonType(JFXButton.ButtonType.FLAT);
        buttonUpdate.setPrefSize(x, y);
        buttonUpdate.setOnAction(event -> {
            hBox.getChildren().clear();
            if (tableView.getSelectionModel().getSelectedItem() != null) {
                hBox.getChildren().addAll(gridPane, UpdateBankingAgency.getInstance()
                        .getGridPane((BankingAgency) tableView.getSelectionModel().getSelectedItem()));
            } else {
                hBox.getChildren().add(gridPane);
            }
        });

        JFXButton buttonDelete = new JFXButton("Eliminar");
        buttonDelete.getStyleClass().addAll("customButton", "dangerButton");
        buttonDelete.setButtonType(JFXButton.ButtonType.FLAT);
        buttonDelete.setPrefSize(x, y);
        buttonDelete.setOnAction(event -> {
            BankingAgency agency = (BankingAgency) tableView.getSelectionModel().getSelectedItem();
            if (agency != null) {
                restartHBox();
                ControllerBankingAgency.getInstance().deleteBankingAgency(agency.getId());
                updateTableViewItems();
            }
        });

        hBoxButtons.getChildren().addAll(textFieldSearch, buttonReport, buttonAdd, buttonUpdate, buttonDelete);
        hBoxButtons.setPrefSize(x, y / 8);
        gridPane.add(hBoxButtons, 0, 1);

        TableColumn<BankingAgency, Integer> columnID = new TableColumn<>("ID");
        columnID.setPrefWidth(50);
        columnID.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<BankingAgency, String> columnName = new TableColumn<>("NOMBRE");
        columnName.setPrefWidth((3 * x / 4) / 7);
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<BankingAgency, String> columnAddress = new TableColumn<>("DIRECCIÓN");
        columnAddress.setPrefWidth((3 * x / 4) / 7);
        columnAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        TableColumn<BankingAgency, String> columnPhoneNumber = new TableColumn<>("NÚMERO TELEFÓNICO");
        columnPhoneNumber.setPrefWidth((3 * x / 4) / 7);
        columnPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        TableColumn<BankingAgency, Integer> columnNoPayOffice = new TableColumn<>("CAJAS");
        columnNoPayOffice.setPrefWidth((3 * x / 4) / 7);
        columnNoPayOffice.setCellValueFactory(new PropertyValueFactory<>("noPayOffice"));
        TableColumn<BankingAgency, Integer> columnNoCustomerService = new TableColumn<>("ESCRITORIOS");
        columnNoCustomerService.setPrefWidth((3 * x / 4) / 7);
        columnNoCustomerService.setCellValueFactory(new PropertyValueFactory<>("noCustomerService"));
        TableColumn<BankingAgency, Double> columnCash = new TableColumn<>("EFECTIVO");
        columnCash.setPrefWidth((3 * x / 4) / 7);
        columnCash.setCellValueFactory(new PropertyValueFactory<>("cash"));

        updateObservableList();
        tableView = new TableView<>(observableList);
        tableView.getColumns().addAll(columnID, columnName, columnAddress, columnPhoneNumber, columnNoPayOffice,
                columnNoCustomerService, columnCash);
        tableView.setPrefSize(x, 7 * y / 8);
        tableView.setOnMouseClicked(event -> {
            hBox.getChildren().clear();
            if (tableView.getSelectionModel().getSelectedItem() != null) {
                hBox.getChildren().addAll(gridPane, ShowBankingAgency.getInstance()
                        .getGridPane((BankingAgency) tableView.getSelectionModel().getSelectedItem()));
            } else {
                hBox.getChildren().add(gridPane);
            }
        });
        gridPane.add(tableView, 0, 2);
        hBox.getChildren().add(gridPane);

        return hBox;
    }
}

class CreateBankingAgency {
    private static CreateBankingAgency instance;

    private CreateBankingAgency() {
    }

    static CreateBankingAgency getInstance() {
        if (instance == null) {
            instance = new CreateBankingAgency();
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

        Label labelCash = new Label("EFECTIVO:");
        gridPane.add(labelCash, 0, 9);
        Spinner<Double> spinnerCash = new Spinner<>(0.00, 100000.00, 0, 1);
        spinnerCash.setEditable(true);
        spinnerCash.setPrefWidth(x);
        gridPane.add(spinnerCash, 1, 9);

        JFXButton buttonAdd = new JFXButton("Agregar");
        buttonAdd.getStyleClass().addAll("customButton", "primaryButton");
        buttonAdd.setButtonType(JFXButton.ButtonType.FLAT);
        buttonAdd.setPrefSize(x, y / 20);
        buttonAdd.setOnAction(event -> {
            if (fieldName.getText().length() == 0 || fieldAddress.getText().length() == 0
                    || fieldPhoneNumber.getText().length() == 0
                    || !fieldPhoneNumber.getText().matches("^(?=(?:[1-9]){1})\\d{4}-\\d{4}")
                    || !Verifications.getInstance().isNumeric(spinnerNoPayOffice.getEditor().getText())
                    || !Verifications.getInstance().isNumeric(spinnerNoCustomerService.getEditor().getText())
                    || !Verifications.getInstance().isNumeric(spinnerCash.getEditor().getText())) {
                Alert.getInstance().showAlert(gridPane, "ERROR", "UNO O MÁS DATOS SON INCORRECTOS");
            } else {
                BankingAgency searchNameBankingAgency = ControllerBankingAgency.getInstance()
                        .searchBankingAgency(fieldName.getText().trim().toUpperCase());
                if (searchNameBankingAgency != null) {
                    Alert.getInstance().showAlert(gridPane, "ERROR", "LA AGENCIA BANCARIA YA EXISTE");
                } else {
                    ControllerBankingAgency.getInstance().createBankingAgency(fieldName.getText().trim().toUpperCase(),
                            fieldAddress.getText().trim().toUpperCase(), fieldPhoneNumber.getText().trim(),
                            Integer.parseInt(spinnerNoPayOffice.getEditor().getText()),
                            Integer.parseInt(spinnerNoCustomerService.getEditor().getText()),
                            Double.parseDouble(spinnerCash.getEditor().getText()));
                    ViewAgency.getInstance().updateTableViewItems();
                }
            }
        });
        gridPane.add(buttonAdd, 0, 10);

        JFXButton buttonCancel = new JFXButton("Cancelar");
        buttonCancel.getStyleClass().addAll("customButton", "dangerButton");
        buttonCancel.setButtonType(JFXButton.ButtonType.FLAT);
        buttonCancel.setPrefSize(x, y / 20);
        buttonCancel.setOnAction(event -> ViewAgency.getInstance().restartHBox());
        gridPane.add(buttonCancel, 1, 10);

        return gridPane;
    }
}

class UpdateBankingAgency {
    private static UpdateBankingAgency instance;

    private UpdateBankingAgency() {
    }

    static UpdateBankingAgency getInstance() {
        if (instance == null) {
            instance = new UpdateBankingAgency();
        }
        return instance;
    }

    GridPane getGridPane(BankingAgency bankingAgency) {
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

        JFXTextField fieldName = new JFXTextField(bankingAgency.getName());
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

        JFXTextField fieldAddress = new JFXTextField(bankingAgency.getAddress());
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

        JFXTextField fieldPhoneNumber = new JFXTextField(bankingAgency.getPhoneNumber());
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
        Spinner<Integer> spinnerNoPayOffice = new Spinner<>(0, 1000, bankingAgency.getNoPayOffice(), 1);
        spinnerNoPayOffice.setEditable(true);
        spinnerNoPayOffice.setPrefWidth(x);
        gridPane.add(spinnerNoPayOffice, 1, 7);

        Label labelNoCustomerService = new Label("ESCRITORIOS:");
        gridPane.add(labelNoCustomerService, 0, 8);
        Spinner<Integer> spinnerNoCustomerService = new Spinner<>(0, 100, bankingAgency.getNoCustomerService(), 1);
        spinnerNoCustomerService.setEditable(true);
        spinnerNoCustomerService.setPrefWidth(x);
        gridPane.add(spinnerNoCustomerService, 1, 8);

        Label labelCash = new Label("EFECTIVO:");
        gridPane.add(labelCash, 0, 9);
        Spinner<Double> spinnerCash = new Spinner<>(0.00, 100000.00, bankingAgency.getCash(), 1);
        spinnerCash.setEditable(true);
        spinnerCash.setPrefWidth(x);
        gridPane.add(spinnerCash, 1, 9);

        JFXButton buttonUpdate = new JFXButton("Modificar");
        buttonUpdate.getStyleClass().addAll("customButton", "primaryButton");
        buttonUpdate.setButtonType(JFXButton.ButtonType.FLAT);
        buttonUpdate.setPrefSize(x, y / 20);
        buttonUpdate.setOnAction(event -> {
            if (fieldName.getText().length() == 0 || fieldAddress.getText().length() == 0
                    || fieldPhoneNumber.getText().length() == 0
                    || !fieldPhoneNumber.getText().matches("^(?=(?:[1-9]){1})\\d{4}-\\d{4}")
                    || !Verifications.getInstance().isNumeric(spinnerNoPayOffice.getEditor().getText())
                    || !Verifications.getInstance().isNumeric(spinnerNoCustomerService.getEditor().getText())
                    || !Verifications.getInstance().isNumeric(spinnerCash.getEditor().getText())) {
                Alert.getInstance().showAlert(gridPane, "ERROR", "UNO O MÁS DATOS SON INCORRECTOS");
            } else {
                ControllerBankingAgency.getInstance().updateBankingAgency(bankingAgency.getId(),
                        fieldName.getText().trim().toUpperCase(), fieldAddress.getText().trim().toUpperCase(),
                        fieldPhoneNumber.getText().trim(), Integer.parseInt(spinnerNoPayOffice.getEditor().getText()),
                        Integer.parseInt(spinnerNoCustomerService.getEditor().getText()),
                        Double.parseDouble(spinnerCash.getEditor().getText()));
                if (!ControllerBankingAgency.getInstance().updateBankingAgency()) {
                    Alert.getInstance().showAlert(gridPane, "ERROR", "ERROR AL MODIFICAR LA AGENCIA BANCARIA");
                }
                ViewAgency.getInstance().updateTableViewItems();
            }
        });
        gridPane.add(buttonUpdate, 0, 10);

        JFXButton buttonCancel = new JFXButton("Cancelar");
        buttonCancel.getStyleClass().addAll("customButton", "dangerButton");
        buttonCancel.setButtonType(JFXButton.ButtonType.FLAT);
        buttonCancel.setPrefSize(x, y / 20);
        buttonCancel.setOnAction(event -> ViewAgency.getInstance().restartHBox());
        gridPane.add(buttonCancel, 1, 10);

        return gridPane;
    }
}

class ShowBankingAgency {
    private static ShowBankingAgency instance;

    private ShowBankingAgency() {
    }

    static ShowBankingAgency getInstance() {
        if (instance == null) {
            instance = new ShowBankingAgency();
        }
        return instance;
    }

    GridPane getGridPane(BankingAgency bankingAgency) {
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

        JFXTextField fieldName = new JFXTextField(bankingAgency.getName());
        fieldName.setPromptText("NOMBRE");
        fieldName.setLabelFloat(true);
        fieldName.setPrefWidth(x);
        fieldName.setEditable(false);
        gridPane.add(fieldName, 0, 4, 2, 1);

        JFXTextField fieldAddress = new JFXTextField(bankingAgency.getAddress());
        fieldAddress.setPromptText("DIRECCIÓN");
        fieldAddress.setLabelFloat(true);
        fieldAddress.setPrefWidth(x);
        fieldAddress.setEditable(false);
        gridPane.add(fieldAddress, 0, 5, 2, 1);

        JFXTextField fieldPhoneNumber = new JFXTextField(bankingAgency.getPhoneNumber());
        fieldPhoneNumber.setPromptText("NÚMERO TELEFÓNICO");
        fieldPhoneNumber.setLabelFloat(true);
        fieldPhoneNumber.setPrefWidth(x);
        fieldPhoneNumber.setEditable(false);
        gridPane.add(fieldPhoneNumber, 0, 6, 2, 1);

        JFXTextField fieldNoPayOffice = new JFXTextField(bankingAgency.getNoPayOffice().toString());
        fieldNoPayOffice.setPromptText("CAJAS");
        fieldNoPayOffice.setLabelFloat(true);
        fieldNoPayOffice.setPrefWidth(x);
        fieldNoPayOffice.setEditable(false);
        gridPane.add(fieldNoPayOffice, 0, 7, 2, 1);

        JFXTextField fieldNoCustomerService = new JFXTextField(bankingAgency.getNoCustomerService().toString());
        fieldNoCustomerService.setPromptText("ESCRITORIOS");
        fieldNoCustomerService.setLabelFloat(true);
        fieldNoCustomerService.setPrefWidth(x);
        fieldNoCustomerService.setEditable(false);
        gridPane.add(fieldNoCustomerService, 0, 8, 2, 1);

        JFXTextField fieldCash = new JFXTextField(bankingAgency.getCash().toString());
        fieldCash.setPromptText("EFECTIVO");
        fieldCash.setLabelFloat(true);
        fieldCash.setPrefWidth(x);
        fieldCash.setEditable(false);
        gridPane.add(fieldCash, 0, 9, 2, 1);

        JFXButton buttonCopy = new JFXButton("Copiar");
        buttonCopy.getStyleClass().addAll("customButton", "primaryButton");
        buttonCopy.setButtonType(JFXButton.ButtonType.FLAT);
        buttonCopy.setPrefSize(x, y / 20);
        buttonCopy.setOnAction(event -> {
            final Clipboard clipboard = Clipboard.getSystemClipboard();
            final ClipboardContent content = new ClipboardContent();
            content.putString("NOMBRE:                " + bankingAgency.getName() + "\nDIRECCIÓN:             "
                    + bankingAgency.getAddress() + "\nNÚMERO TELEFÓNICO:     " + bankingAgency.getPhoneNumber()
                    + "\nCAJAS:                 " + bankingAgency.getNoPayOffice().toString()
                    + "\nESCRITORIOS:           " + bankingAgency.getNoCustomerService().toString()
                    + "\nEFECTIVO:              " + bankingAgency.getCash().toString());
            clipboard.setContent(content);
        });
        gridPane.add(buttonCopy, 0, 10);

        JFXButton buttonCancel = new JFXButton("Cancelar");
        buttonCancel.getStyleClass().addAll("customButton", "dangerButton");
        buttonCancel.setButtonType(JFXButton.ButtonType.FLAT);
        buttonCancel.setPrefSize(x, y / 20);
        buttonCancel.setOnAction(event -> ViewAgency.getInstance().restartHBox());
        gridPane.add(buttonCancel, 1, 10);

        return gridPane;
    }
}
