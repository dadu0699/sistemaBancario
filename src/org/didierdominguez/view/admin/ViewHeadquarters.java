package org.didierdominguez.view.admin;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.didierdominguez.bean.CreditCard;
import org.didierdominguez.bean.Customer;
import org.didierdominguez.bean.Employee;
import org.didierdominguez.bean.Loan;
import org.didierdominguez.controller.ControllerCreditCard;
import org.didierdominguez.controller.ControllerEmployee;
import org.didierdominguez.controller.ControllerLoan;
import org.didierdominguez.util.ReportGenerator;
import org.didierdominguez.util.ScreenSize;

import java.util.ArrayList;

public class ViewHeadquarters extends Stage {
    private static ViewHeadquarters instance;
    private HBox hBoxPanels;
    private VBox vBoxApplications;
    private TableView tableViewEmployee;
    private TableView tableViewLoan;
    private TableView tableViewCreditCard;
    private ObservableList observableListEmployee;
    private ObservableList observableListLoan;
    private ObservableList observableListCreditCard;

    private ViewHeadquarters() {
    }

    public static ViewHeadquarters getInstance() {
        if (instance == null) {
            instance = new ViewHeadquarters();
        }
        return instance;
    }

    public void restartHBox() {
        hBoxPanels.getChildren().clear();
        hBoxPanels.getChildren().addAll(getViewEmployeeHeadquarters(), vBoxApplications);
    }

    private void updateObservableListEmployee() {
        Employee[] employees = ControllerEmployee.getInstance().getEmployees();
        ArrayList<Employee> arrayListEmployee = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee != null && employee.getJob() instanceof String
                    && !((String) employee.getJob()).equalsIgnoreCase("CALL-CENTER")) {
                arrayListEmployee.add(employee);
            }
        }
        if (observableListEmployee != null){
            observableListEmployee.clear();
        }
        observableListEmployee = FXCollections.observableArrayList(arrayListEmployee);
    }

    private void updateObservableListEmployee(String param) {
        Employee[] employees = ControllerEmployee.getInstance().searchEmployees(param);
        ArrayList<Employee> arrayListEmployee = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee != null && employee.getJob() instanceof String
                    && !((String) employee.getJob()).equalsIgnoreCase("CALL-CENTER")) {
                arrayListEmployee.add(employee);
            }
        }
        observableListEmployee = FXCollections.observableArrayList(arrayListEmployee);
    }

    private void updateObservableListLoans() {
        Loan[] loans = ControllerLoan.getInstance().getLoans();
        ArrayList<Loan> arrayListLoans = new ArrayList<>();
        for (Loan loan : loans) {
            if (loan != null && loan.getAuthorization() == null) {
                arrayListLoans.add(loan);
            }
        }
        if (observableListLoan != null) {
            observableListLoan.clear();
        }
        observableListLoan = FXCollections.observableArrayList(arrayListLoans);
    }

    private void updateObservableListCreditCards() {
        CreditCard[] creditCards = ControllerCreditCard.getInstance().getCreditCards();
        ArrayList<CreditCard> arrayListCreditCards = new ArrayList<>();
        for (CreditCard creditCard : creditCards) {
            if (creditCard != null && creditCard.getAuthorization() == null) {
                arrayListCreditCards.add(creditCard);
            }
        }
        if (observableListCreditCard != null) {
            observableListCreditCard.clear();
        }
        observableListCreditCard = FXCollections.observableArrayList(arrayListCreditCards);
    }

    public void updateTableViewItemsEmployee() {
        updateObservableListEmployee();
        tableViewEmployee.setItems(observableListEmployee);
    }

    public void updateTableViewItemsLoan() {
        updateObservableListLoans();
        tableViewLoan.setItems(observableListLoan);
    }

    public void updateTableViewItemsCreditCard() {
        updateObservableListCreditCards();
        tableViewCreditCard.setItems(observableListCreditCard);
    }

    public void updateTableViewItemsEmployee(String param) {
        updateObservableListEmployee(param);
        tableViewEmployee.setItems(observableListEmployee);
    }

    public VBox getViewHeadquarters() {
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

        Text textTitle = new Text("OFICINAS CENTRALES");
        textTitle.getStyleClass().add("textTitle");
        textTitle.setFont(new Font(25));
        gridPaneTitle.add(textTitle, 0, 0);

        gridPaneTitle.setPadding(new Insets(20, 20, -10, 20));
        hBoxPanels = new HBox();
        hBoxPanels.setPrefSize(x, 7 * y / 8);

        vBoxApplications = new VBox();
        vBoxApplications.setPrefSize(x, 7 * y / 8);
        vBoxApplications.getChildren().addAll(getViewLoans(), getViewCreditCards());

        hBoxPanels.getChildren().addAll(getViewEmployeeHeadquarters(), vBoxApplications);
        vBox.getChildren().addAll(gridPaneTitle, hBoxPanels);
        return vBox;
    }

    private GridPane getViewEmployeeHeadquarters() {
        GridPane gridPane = new GridPane();

        double x = ScreenSize.getInstance().getX();
        double y = ScreenSize.getInstance().getY();

        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20));
        gridPane.setPrefSize(x, y);

        Text textTitle = new Text("EMPLEADOS");
        textTitle.getStyleClass().add("textTitle");
        textTitle.setFont(new Font(20));
        gridPane.add(textTitle, 0, 0);

        JFXTextField textFieldSearch = new JFXTextField();
        textFieldSearch.setPromptText("BUSCAR");
        textFieldSearch.setPrefSize(x, y / 8);
        textFieldSearch.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue,
                                                    String newValue) ->
                updateTableViewItemsEmployee(textFieldSearch.getText().trim().toUpperCase())
        );
        gridPane.add(textFieldSearch, 0, 1);

        HBox hBoxButtons = new HBox();
        JFXButton buttonReport = new JFXButton("Reporte");
        buttonReport.getStyleClass().addAll("customButton", "reportButton");
        buttonReport.setButtonType(JFXButton.ButtonType.FLAT);
        buttonReport.setPrefSize(x, y);
        buttonReport.setOnAction(event -> {
            String[] titles = {"ID", "NOMBRE", "NÚMERO TELEFÓNICO", "DEPARTAMENTO"};
            Employee[] employees = ControllerEmployee.getInstance().getEmployees();
            Employee[] dataCustomers = new Employee[100];
            for (int i = 0; i < employees.length; i++) {
                if (employees[i] != null && employees[i].getJob() instanceof String
                        && !((String) employees[i].getJob()).equalsIgnoreCase("CALL-CENTER")) {
                    dataCustomers[i] = employees[i];
                }
            }

            try {
                ReportGenerator.getInstance().generatePDF("EmployeesHeadquarters.pdf",
                        "EMPLEADOS DE OFICINAS CENTRALES", titles, dataCustomers);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        JFXButton buttonAdd = new JFXButton("Agregar");
        buttonAdd.getStyleClass().addAll("customButton", "primaryButton");
        buttonAdd.setButtonType(JFXButton.ButtonType.FLAT);
        buttonAdd.setPrefSize(x, y);
        buttonAdd.setOnAction(event -> {
            gridPane.getChildren().clear();
            gridPane.add(textTitle, 0, 0);
            gridPane.add(CreateEmployeeHeadquarters.getInstance().getGridPane(), 0, 1);
        });

        JFXButton buttonUpdate = new JFXButton("Modificar");
        buttonUpdate.getStyleClass().addAll("customButton", "warningButton");
        buttonUpdate.setButtonType(JFXButton.ButtonType.FLAT);
        buttonUpdate.setPrefSize(x, y);
        buttonUpdate.setOnAction(event -> {
            if (tableViewEmployee.getSelectionModel().getSelectedItem() != null) {
                gridPane.getChildren().clear();
                gridPane.add(textTitle, 0, 0);
                gridPane.add(UpdateEmployeeHeadquarters.getInstance()
                                .getGridPane((Employee) tableViewEmployee.getSelectionModel().getSelectedItem()),
                        0, 1);
            }
        });

        JFXButton buttonDelete = new JFXButton("Eliminar");
        buttonDelete.getStyleClass().addAll("customButton", "dangerButton");
        buttonDelete.setButtonType(JFXButton.ButtonType.FLAT);
        buttonDelete.setPrefSize(x, y);
        buttonDelete.setOnAction(event -> {
            Employee agency = (Employee) tableViewEmployee.getSelectionModel().getSelectedItem();
            if (agency != null) {
                ViewHeadquarters.getInstance().restartHBox();
                ControllerEmployee.getInstance().deleteEmployee(agency.getId());
                updateTableViewItemsEmployee();
            }
        });

        hBoxButtons.getChildren().addAll(buttonReport, buttonAdd, buttonUpdate, buttonDelete);
        hBoxButtons.setPrefSize(x, y / 8);
        gridPane.add(hBoxButtons, 0, 2);

        TableColumn<Employee, Integer> columnID = new TableColumn<>("ID");
        columnID.setPrefWidth(50);
        columnID.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Employee, String> columnName = new TableColumn<>("NOMBRE");
        columnName.setPrefWidth(x / 10);
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Employee, String> columnPhoneNumber = new TableColumn<>("TELEFONO");
        columnPhoneNumber.setPrefWidth(x / 10);
        columnPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        TableColumn<Employee, Object> columnJob = new TableColumn<>("DEPARTAMENTO");
        columnJob.setPrefWidth(x / 10);
        columnJob.setCellValueFactory(new PropertyValueFactory<>("job"));

        updateObservableListEmployee();
        tableViewEmployee = new TableView<>(observableListEmployee);
        tableViewEmployee.getColumns().addAll(columnID, columnName, columnPhoneNumber, columnJob);
        tableViewEmployee.setPrefSize(x, 7 * y / 8);

        gridPane.add(tableViewEmployee, 0, 3);
        gridPane.setPadding(new Insets(-10, 10, 20, 20));

        return gridPane;
    }

    private GridPane getViewLoans() {
        GridPane gridPane = new GridPane();

        double x = ScreenSize.getInstance().getX();
        double y = ScreenSize.getInstance().getY();

        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20));
        gridPane.setPrefSize(x, y);

        // LOAN
        Text textTitle = new Text("SOLICITUDES DE PRÉSTAMOS");
        textTitle.getStyleClass().add("textTitle");
        textTitle.setFont(new Font(15));
        gridPane.add(textTitle, 0, 0);

        HBox hBoxButtons = new HBox();
        JFXButton buttonAuthorize = new JFXButton("Autorizar");
        buttonAuthorize.getStyleClass().addAll("customButton", "primaryButton");
        buttonAuthorize.setButtonType(JFXButton.ButtonType.FLAT);
        buttonAuthorize.setPrefSize(x, y);
        buttonAuthorize.setOnAction(event -> {
            Loan loan = (Loan) tableViewLoan.getSelectionModel().getSelectedItem();
            if (loan != null) {
                ViewHeadquarters.getInstance().restartHBox();
                ControllerLoan.getInstance().updateLoan(loan.getId(), true);
                updateTableViewItemsLoan();
            }
        });

        JFXButton buttonDeny = new JFXButton("Rechazar");
        buttonDeny.getStyleClass().addAll("customButton", "dangerButton");
        buttonDeny.setButtonType(JFXButton.ButtonType.FLAT);
        buttonDeny.setPrefSize(x, y);
        buttonDeny.setOnAction(event -> {
            Loan loan = (Loan) tableViewLoan.getSelectionModel().getSelectedItem();
            if (loan != null) {
                ViewHeadquarters.getInstance().restartHBox();
                ControllerLoan.getInstance().updateLoan(loan.getId(), false);
                updateTableViewItemsLoan();
            }
        });

        hBoxButtons.getChildren().addAll(buttonAuthorize, buttonDeny);
        hBoxButtons.setPrefSize(x, y / 8);
        gridPane.add(hBoxButtons, 0, 1);

        TableColumn<Loan, Integer> columnID = new TableColumn<>("ID");
        columnID.setPrefWidth(50);
        columnID.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Loan, String> columnLoanNo = new TableColumn<>("NO. DE PRÉSTAMO");
        columnLoanNo.setPrefWidth(x / 12);
        columnLoanNo.setCellValueFactory(new PropertyValueFactory<>("loanNo"));
        TableColumn<Loan, Customer> columnCustomer = new TableColumn<>("CLIENTE");
        columnCustomer.setPrefWidth(x / 12);
        columnCustomer.setCellValueFactory(new PropertyValueFactory<>("customer"));
        TableColumn<Loan, String> columnDate = new TableColumn<>("FECHA DE SOLICITUD");
        columnDate.setPrefWidth(x / 12);
        columnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        TableColumn<Loan, Double> columnAmount = new TableColumn<>("SALDO");
        columnAmount.setPrefWidth(x / 12);
        columnAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));

        updateObservableListLoans();
        tableViewLoan = new TableView<>(observableListLoan);
        tableViewLoan.getColumns().addAll(columnID, columnLoanNo, columnCustomer, columnDate, columnAmount);
        tableViewLoan.setPrefSize(x, 7 * y / 8);
        gridPane.add(tableViewLoan, 0, 2);
        gridPane.setPadding(new Insets(-10, 20, 10, 10));
        return gridPane;
    }

    private GridPane getViewCreditCards() {
        GridPane gridPane = new GridPane();

        double x = ScreenSize.getInstance().getX();
        double y = ScreenSize.getInstance().getY();

        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20));
        gridPane.setPrefSize(x, y);

        // CREDIT CARD
        Text textTitleC = new Text("SOLICITUDES DE TARJETAS DE CRÉDITO");
        textTitleC.getStyleClass().add("textTitle");
        textTitleC.setFont(new Font(15));
        gridPane.add(textTitleC, 0, 0);

        HBox hBoxButtons = new HBox();
        JFXButton buttonAuthorize = new JFXButton("Autorizar");
        buttonAuthorize.getStyleClass().addAll("customButton", "primaryButton");
        buttonAuthorize.setButtonType(JFXButton.ButtonType.FLAT);
        buttonAuthorize.setPrefSize(x, y);
        buttonAuthorize.setOnAction(event -> {
            CreditCard creditCard = (CreditCard) tableViewCreditCard.getSelectionModel().getSelectedItem();
            if (creditCard != null) {
                ViewHeadquarters.getInstance().restartHBox();
                ControllerCreditCard.getInstance().updateCreditCard(creditCard.getId(), true);
                updateTableViewItemsCreditCard();
            }
        });

        JFXButton buttonDeny = new JFXButton("Rechazar");
        buttonDeny.getStyleClass().addAll("customButton", "dangerButton");
        buttonDeny.setButtonType(JFXButton.ButtonType.FLAT);
        buttonDeny.setPrefSize(x, y);
        buttonDeny.setOnAction(event -> {
            CreditCard creditCard = (CreditCard) tableViewCreditCard.getSelectionModel().getSelectedItem();
            if (creditCard != null) {
                ViewHeadquarters.getInstance().restartHBox();
                ControllerCreditCard.getInstance().updateCreditCard(creditCard.getId(), false);
                updateTableViewItemsCreditCard();
            }
        });

        hBoxButtons.getChildren().addAll(buttonAuthorize, buttonDeny);
        hBoxButtons.setPrefSize(x, y / 8);
        gridPane.add(hBoxButtons, 0, 1);

        TableColumn<CreditCard, Integer> columnIDC = new TableColumn<>("ID");
        columnIDC.setPrefWidth(50);
        columnIDC.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<CreditCard, String> columnCardNo = new TableColumn<>("NO. DE TARJETA");
        columnCardNo.setPrefWidth(x / 12);
        columnCardNo.setCellValueFactory(new PropertyValueFactory<>("cardNo"));
        TableColumn<CreditCard, Customer> columnCustomer = new TableColumn<>("CLIENTE");
        columnCustomer.setPrefWidth(x / 12);
        columnCustomer.setCellValueFactory(new PropertyValueFactory<>("customer"));
        TableColumn<CreditCard, Double> columnCreditLimit = new TableColumn<>("LIMITE DE CREDITO");
        columnCreditLimit.setPrefWidth(x / 12);
        columnCreditLimit.setCellValueFactory(new PropertyValueFactory<>("creditLimit"));

        updateObservableListCreditCards();
        tableViewCreditCard = new TableView<>(observableListCreditCard);
        tableViewCreditCard.getColumns().addAll(columnIDC, columnCardNo, columnCustomer, columnCreditLimit);
        tableViewCreditCard.setPrefSize(x, 7 * y / 8);
        gridPane.add(tableViewCreditCard, 0, 2);

        gridPane.setPadding(new Insets(10, 20, 20, 10));
        return gridPane;
    }
}
