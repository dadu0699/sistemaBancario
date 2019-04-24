package org.didierdominguez.view.admin;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.didierdominguez.bean.Account;
import org.didierdominguez.bean.Customer;
import org.didierdominguez.bean.MonetaryAccount;
import org.didierdominguez.controller.ControllerAccount;
import org.didierdominguez.controller.ControllerCustomer;
import org.didierdominguez.controller.ControllerMonetaryAccount;
import org.didierdominguez.util.ReportGenerator;
import org.didierdominguez.util.ScreenSize;
import org.didierdominguez.view.Alert;

import java.util.ArrayList;

public class ViewCustomer extends Stage {
    private static ViewCustomer instance;
    private HBox hBox;
    private GridPane gridPane;
    private TableView tableView;
    private ObservableList observableList;

    private ViewCustomer() {
    }

    public static ViewCustomer getInstance() {
        if (instance == null) {
            instance = new ViewCustomer();
        }
        return instance;
    }

    public void restartHBox() {
        hBox.getChildren().clear();
        hBox.getChildren().add(gridPane);
    }

    private void updateObservableList() {
        Customer[] customers = ControllerCustomer.getInstance().getCustomers();
        ArrayList<Customer> arrayListCustomer = new ArrayList<>();
        for (Customer customer : customers) {
            if (customer != null) {
                arrayListCustomer.add(customer);
            }
        }
        if (observableList != null){
            observableList.clear();
        }
        observableList = FXCollections.observableArrayList(arrayListCustomer);
    }

    private void updateObservableList(String param) {
        Customer[] customers = ControllerCustomer.getInstance().searchCustomers(param);
        ArrayList<Customer> arrayListCustomer = new ArrayList<>();
        for (Customer customer : customers) {
            if (customer != null) {
                arrayListCustomer.add(customer);
            }
        }
        observableList = FXCollections.observableArrayList(arrayListCustomer);
    }

    void updateTableViewItems() {
        updateObservableList();
        tableView.setItems(observableList);
    }

    private void updateTableViewItems(String param) {
        updateObservableList(param);
        tableView.setItems(observableList);
    }

    HBox getViewCustomer() {
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

        Text textTitle = new Text("CLIENTES");
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
            String[] titles = {"ID", "NOMBRE", "NÚMERO TELEFÓNICO", "DIRECCIÓN", "CUENTAS DE AHORRO",
                    "CUENTAS MONETARIAS", "PRESTAMOS", "TARJETAS DE CRÉDITO", "TRANSACCIONES", "SALDO A FAVOR", "DEUDA",
                    "COMPRAS"};
            Customer[] dataCustomers = ControllerCustomer.getInstance().getCustomers();
            try {
                ReportGenerator.getInstance().generatePDF("Customers.pdf",
                        "CLIENTES", titles, dataCustomers);
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
            hBox.getChildren().addAll(gridPane, CreateCustomer.getInstance().getGridPane());
        });

        JFXButton buttonUpdate = new JFXButton("Modificar");
        buttonUpdate.getStyleClass().addAll("customButton", "warningButton");
        buttonUpdate.setButtonType(JFXButton.ButtonType.FLAT);
        buttonUpdate.setPrefSize(x, y);
        buttonUpdate.setOnAction(event -> {
            hBox.getChildren().clear();
            if (tableView.getSelectionModel().getSelectedItem() != null) {
                hBox.getChildren().addAll(gridPane, UpdateCustomer.getInstance()
                        .getGridPane((Customer) tableView.getSelectionModel().getSelectedItem()));
            } else {
                hBox.getChildren().add(gridPane);
            }
        });

        JFXButton buttonDelete = new JFXButton("Eliminar");
        buttonDelete.getStyleClass().addAll("customButton", "dangerButton");
        buttonDelete.setButtonType(JFXButton.ButtonType.FLAT);
        buttonDelete.setPrefSize(x, y);
        buttonDelete.setOnAction(event -> {
            Customer customer = (Customer) tableView.getSelectionModel().getSelectedItem();
            if (customer != null) {
                restartHBox();
                ControllerCustomer.getInstance().deleteCustomer(customer.getId());
                updateTableViewItems();
            }
        });

        hBoxButtons.getChildren().addAll(textFieldSearch, buttonReport, buttonAdd, buttonUpdate, buttonDelete);
        hBoxButtons.setPrefSize(x, y / 8);
        gridPane.add(hBoxButtons, 0, 1);

        TableColumn<Customer, Integer> columnID = new TableColumn<>("ID");
        columnID.setPrefWidth(50);
        columnID.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Customer, String> columnName = new TableColumn<>("NOMBRE");
        columnName.setPrefWidth((3 * x / 4) / 9);
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Customer, String> columnAddress = new TableColumn<>("DIRECCIÓN");
        columnAddress.setPrefWidth((3 * x / 4) / 9);
        columnAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        TableColumn<Customer, String> columnPhoneNumber = new TableColumn<>("NÚMERO TELEFÓNICO");
        columnPhoneNumber.setPrefWidth((3 * x / 4) / 9);
        columnPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        TableColumn<Customer, Integer> columnSavingsAccounts = new TableColumn<>("CUENTAS DE AHORRO");
        columnSavingsAccounts.setPrefWidth((3 * x / 4) / 9);
        columnSavingsAccounts.setCellValueFactory(new PropertyValueFactory<>("savingsAccounts"));
        TableColumn<Customer, Integer> columnMonetaryAccounts = new TableColumn<>("CUENTAS MONETARIAS");
        columnMonetaryAccounts.setPrefWidth((3 * x / 4) / 9);
        columnMonetaryAccounts.setCellValueFactory(new PropertyValueFactory<>("monetaryAccounts"));
        TableColumn<Customer, Integer> columnLoans = new TableColumn<>("PRESTAMOS");
        columnLoans.setPrefWidth((3 * x / 4) / 9);
        columnLoans.setCellValueFactory(new PropertyValueFactory<>("loans"));
        TableColumn<Customer, Integer> columncreditCards = new TableColumn<>("TARJETAS DE CRÉDITO");
        columncreditCards.setPrefWidth((3 * x / 4) / 9);
        columncreditCards.setCellValueFactory(new PropertyValueFactory<>("creditCards"));
        TableColumn<Customer, Integer> columnTransactionsCarriedOut = new TableColumn<>("TRANSACCIONES REALIZADAS");
        columnTransactionsCarriedOut.setPrefWidth((3 * x / 4) / 9);
        columnTransactionsCarriedOut.setCellValueFactory(new PropertyValueFactory<>("transactionsCarriedOut"));

        updateObservableList();
        tableView = new TableView<>(observableList);
        tableView.getColumns().addAll(columnID, columnName, columnAddress, columnPhoneNumber, columnSavingsAccounts,
                columnMonetaryAccounts, columnLoans, columncreditCards, columnTransactionsCarriedOut);
        tableView.setPrefSize(x, 7 * y / 8);
        tableView.setOnMouseClicked(event -> {
            hBox.getChildren().clear();
            if (tableView.getSelectionModel().getSelectedItem() != null) {
                hBox.getChildren().addAll(gridPane, ShowCustomer.getInstance()
                        .getGridPane((Customer) tableView.getSelectionModel().getSelectedItem()));
            } else {
                hBox.getChildren().add(gridPane);
            }
        });
        gridPane.add(tableView, 0, 2);
        hBox.getChildren().add(gridPane);

        return hBox;
    }
}

class CreateCustomer {
    private static CreateCustomer instance;

    private CreateCustomer() {}

    static CreateCustomer getInstance() {
        if (instance == null) {
            instance = new CreateCustomer();
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

        JFXButton buttonAdd = new JFXButton("Agregar");
        buttonAdd.getStyleClass().addAll("customButton", "primaryButton");
        buttonAdd.setButtonType(JFXButton.ButtonType.FLAT);
        buttonAdd.setPrefSize(x, y / 20);
        buttonAdd.setOnAction(event -> {
            if (fieldName.getText().length() == 0
                    || fieldAddress.getText().length() == 0
                    || fieldPhoneNumber.getText().length() == 0
                    || !fieldPhoneNumber.getText().matches("^(?=(?:[1-9]){1})\\d{4}-\\d{4}")) {
                Alert.getInstance().showAlert(gridPane, "ERROR", "UNO O MÁS DATOS SON INCORRECTOS");
            } else {
                Customer searchNameCustomer = ControllerCustomer.getInstance()
                        .searchCustomer(fieldName.getText().trim().toUpperCase());
                if (searchNameCustomer != null) {
                    Alert.getInstance().showAlert(gridPane, "ERROR", "EL CLIENTE YA EXISTE");
                } else {
                    ControllerCustomer.getInstance().createCustomer(fieldName.getText().trim().toUpperCase(),
                            fieldAddress.getText().trim().toUpperCase(),
                            fieldPhoneNumber.getText().trim());
                    ViewCustomer.getInstance().updateTableViewItems();
                }
            }
        });
        gridPane.add(buttonAdd, 0, 7);

        JFXButton buttonCancel = new JFXButton("Cancelar");
        buttonCancel.getStyleClass().addAll("customButton", "dangerButton");
        buttonCancel.setButtonType(JFXButton.ButtonType.FLAT);
        buttonCancel.setPrefSize(x, y / 20);
        buttonCancel.setOnAction(event -> ViewCustomer.getInstance().restartHBox());
        gridPane.add(buttonCancel, 1, 7);

        return gridPane;
    }
}

class UpdateCustomer {
    private static UpdateCustomer instance;

    private UpdateCustomer(){}

    static UpdateCustomer getInstance() {
        if (instance == null) {
            instance = new UpdateCustomer();
        }
        return instance;
    }

    GridPane getGridPane(Customer customer) {
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

        JFXTextField fieldName = new JFXTextField(customer.getName());
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

        JFXTextField fieldAddress = new JFXTextField(customer.getAddress());
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

        JFXTextField fieldPhoneNumber = new JFXTextField(customer.getPhoneNumber());
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

        JFXButton buttonUpdate = new JFXButton("Modificar");
        buttonUpdate.getStyleClass().addAll("customButton", "primaryButton");
        buttonUpdate.setButtonType(JFXButton.ButtonType.FLAT);
        buttonUpdate.setPrefSize(x, y / 20);
        buttonUpdate.setOnAction(event -> {
            if (fieldName.getText().length() == 0
                    || fieldAddress.getText().length() == 0
                    || fieldPhoneNumber.getText().length() == 0
                    || !fieldPhoneNumber.getText().matches("^(?=(?:[1-9]){1})\\d{4}-\\d{4}")) {
                Alert.getInstance().showAlert(gridPane, "ERROR", "UNO O MÁS DATOS SON INCORRECTOS");
            } else {
                ControllerCustomer.getInstance().updateCustomer(customer.getId(),
                        fieldName.getText().trim().toUpperCase(),
                        fieldAddress.getText().trim().toUpperCase(),
                        fieldPhoneNumber.getText().trim());
                if (!ControllerCustomer.getInstance().updateCustomer()){
                    Alert.getInstance().showAlert(gridPane, "ERROR", "ERROR AL MODIFICAR EL CLIENTE");
                }
                ViewCustomer.getInstance().updateTableViewItems();
            }
        });
        gridPane.add(buttonUpdate, 0, 7);

        JFXButton buttonCancel = new JFXButton("Cancelar");
        buttonCancel.getStyleClass().addAll("customButton", "dangerButton");
        buttonCancel.setButtonType(JFXButton.ButtonType.FLAT);
        buttonCancel.setPrefSize(x, y / 20);
        buttonCancel.setOnAction(event -> ViewCustomer.getInstance().restartHBox());
        gridPane.add(buttonCancel, 1, 7);

        return gridPane;
    }
}

class ShowCustomer {
    private static ShowCustomer instance;

    private ShowCustomer() {
    }

    static ShowCustomer getInstance() {
        if (instance == null) {
            instance = new ShowCustomer();
        }
        return instance;
    }

    GridPane getGridPane(Customer customer) {
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

        JFXTextField fieldName = new JFXTextField(customer.getName());
        fieldName.setPromptText("NOMBRE");
        fieldName.setLabelFloat(true);
        fieldName.setPrefWidth(x);
        fieldName.setEditable(false);
        gridPane.add(fieldName, 0, 4, 2, 1);

        JFXTextField fieldAddress = new JFXTextField(customer.getAddress());
        fieldAddress.setPromptText("DIRECCIÓN");
        fieldAddress.setLabelFloat(true);
        fieldAddress.setPrefWidth(x);
        fieldAddress.setEditable(false);
        gridPane.add(fieldAddress, 0, 5, 2, 1);

        JFXTextField fieldPhoneNumber = new JFXTextField(customer.getPhoneNumber());
        fieldPhoneNumber.setPromptText("NÚMERO TELEFÓNICO");
        fieldPhoneNumber.setLabelFloat(true);
        fieldPhoneNumber.setPrefWidth(x);
        fieldPhoneNumber.setEditable(false);
        gridPane.add(fieldPhoneNumber, 0, 6, 2, 1);

        JFXButton buttonReport = new JFXButton("Reporte");
        buttonReport.getStyleClass().addAll("customButton", "primaryButton");
        buttonReport.setButtonType(JFXButton.ButtonType.FLAT);
        buttonReport.setPrefSize(x, y / 20);
        buttonReport.setOnAction(event -> {
            String[] titlesSavingAccount = {"ID", "NUMERO DE CUENTA", "CLIENTE", "FECHA DE APERTURA", "SALDO"};
            Account[] savingsAccounts = ControllerAccount.getInstance().getAccounts();
            Account[] customerSavingsAccounts = new Account[100];
            int counterSavingsAccounts = 0;
            for (Account account : savingsAccounts) {
                if (account != null && account.getCustomer() == customer) {
                    customerSavingsAccounts[counterSavingsAccounts] = account;
                    counterSavingsAccounts++;
                }
            }

            String[] titlesMonetaryAccount = {"ID", "NUMERO DE CUENTA", "CLIENTE", "FECHA DE APERTURA", "SALDO",
                    "CHEQUES"};
            MonetaryAccount[] monetaryAccounts = ControllerMonetaryAccount.getInstance().getMonetaryAccounts();
            MonetaryAccount[] customerMonetaryAccounts = new MonetaryAccount[100];
            int counterMonetaryAccounts = 0;
            for (MonetaryAccount account : monetaryAccounts) {
                if (account != null && account.getCustomer() == customer) {
                    customerMonetaryAccounts[counterMonetaryAccounts] = account;
                    counterMonetaryAccounts++;
                }
            }

            try {
                ReportGenerator.getInstance().generatePDF(customer.getName() + "SavingsAccounts.pdf",
                        "CUENTAS DE AHORRO: " + customer.getName(),
                        titlesSavingAccount, customerSavingsAccounts);
                ReportGenerator.getInstance().generatePDF(customer.getName() + "MonetaryAccounts.pdf",
                        "CUENTAS MONETARIAS: " + customer.getName(),
                        titlesMonetaryAccount, customerMonetaryAccounts);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        gridPane.add(buttonReport, 0, 7);

        JFXButton buttonCancel = new JFXButton("Cancelar");
        buttonCancel.getStyleClass().addAll("customButton", "dangerButton");
        buttonCancel.setButtonType(JFXButton.ButtonType.FLAT);
        buttonCancel.setPrefSize(x, y / 20);
        buttonCancel.setOnAction(event -> ViewCustomer.getInstance().restartHBox());
        gridPane.add(buttonCancel, 1, 7);

        return gridPane;
    }
}