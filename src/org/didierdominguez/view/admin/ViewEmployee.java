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
import org.didierdominguez.bean.AutoBank;
import org.didierdominguez.bean.BankingAgency;
import org.didierdominguez.bean.Employee;
import org.didierdominguez.controller.ControllerEmployee;
import org.didierdominguez.util.ReportGenerator;
import org.didierdominguez.util.ScreenSize;

import java.util.ArrayList;

public class ViewEmployee extends Stage {
    private static ViewEmployee instance;
    private HBox hBoxPanels;
    private TableView tableViewAgency;
    private TableView tableViewAutoBank;
    private TableView tableViewCallCenter;
    private ObservableList observableListAgency;
    private ObservableList observableListAutoBank;
    private ObservableList observableListCallCenter;

    private ViewEmployee() {
    }

    public static ViewEmployee getInstance() {
        if (instance == null) {
            instance = new ViewEmployee();
        }
        return instance;
    }

    public void restartHBox() {
        hBoxPanels.getChildren().clear();
        hBoxPanels.getChildren().addAll(getViewEmployeeAgency(), getViewEmployeeAutoBank(), getViewEmployeeCallCenter());
    }

    private void updateObservableListAgency() {
        Employee[] employees = ControllerEmployee.getInstance().getEmployees();
        ArrayList<Employee> arrayListEmployeeAgency = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee != null && employee.getJob() instanceof BankingAgency
                    && !(employee.getJob() instanceof AutoBank)) {
                arrayListEmployeeAgency.add(employee);
            }
        }
        if (observableListAgency != null){
            observableListAgency.clear();
        }
        observableListAgency = FXCollections.observableArrayList(arrayListEmployeeAgency);
    }

    private void updateObservableListAutoBank() {
        Employee[] employees = ControllerEmployee.getInstance().getEmployees();
        ArrayList<Employee> arrayListEmployeeAutoBank = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee != null && employee.getJob() instanceof AutoBank) {
                arrayListEmployeeAutoBank.add(employee);
            }
        }
        if (observableListAutoBank != null){
            observableListAutoBank.clear();
        }
        observableListAutoBank = FXCollections.observableArrayList(arrayListEmployeeAutoBank);
    }

    private void updateObservableListCallCenter() {
        Employee[] employees = ControllerEmployee.getInstance().getEmployees();
        ArrayList<Employee> arrayListEmployeeCallCenter = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee != null && employee.getJob() instanceof String
                    && ((String) employee.getJob()).equalsIgnoreCase("CALL-CENTER")) {
                arrayListEmployeeCallCenter.add(employee);
            }
        }
        if (observableListCallCenter != null){
            observableListCallCenter.clear();
        }
        observableListCallCenter = FXCollections.observableArrayList(arrayListEmployeeCallCenter);
    }

    private void updateObservableListAgency(String param) {
        Employee[] employees = ControllerEmployee.getInstance().searchEmployees(param);
        ArrayList<Employee> arrayListBankingEmployeeAgency = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee != null && employee.getJob() instanceof BankingAgency
                    && !(employee.getJob() instanceof AutoBank)) {
                arrayListBankingEmployeeAgency.add(employee);
            }
        }
        observableListAgency = FXCollections.observableArrayList(arrayListBankingEmployeeAgency);
    }

    private void updateObservableListAutoBank(String param) {
        Employee[] employees = ControllerEmployee.getInstance().searchEmployees(param);
        ArrayList<Employee> arrayListBankingEmployeeAutoBank = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee != null && employee.getJob() instanceof AutoBank) {
                arrayListBankingEmployeeAutoBank.add(employee);
            }
        }
        observableListAutoBank = FXCollections.observableArrayList(arrayListBankingEmployeeAutoBank);
    }

    private void updateObservableListCallCenter(String param) {
        Employee[] employees = ControllerEmployee.getInstance().searchEmployees(param);
        ArrayList<Employee> arrayListBankingCallCenter = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee != null && employee.getJob() instanceof String
                    && ((String) employee.getJob()).equalsIgnoreCase("CALL-CENTER")) {
                arrayListBankingCallCenter.add(employee);
            }
        }
        observableListCallCenter = FXCollections.observableArrayList(arrayListBankingCallCenter);
    }

    public void updateTableViewItemsAgency() {
        updateObservableListAgency();
        tableViewAgency.setItems(observableListAgency);
    }

    public void updateTableViewItemsAutoBank() {
        updateObservableListAutoBank();
        tableViewAutoBank.setItems(observableListAutoBank);
    }

    public void updateTableViewItemsCallCenter() {
        updateObservableListCallCenter();
        tableViewCallCenter.setItems(observableListCallCenter);
    }

    public void updateTableViewItems(String param) {
        updateObservableListAgency(param);
        updateObservableListAutoBank(param);
        updateObservableListCallCenter(param);
        tableViewAgency.setItems(observableListAgency);
        tableViewAutoBank.setItems(observableListAutoBank);
        tableViewCallCenter.setItems(observableListCallCenter);
    }

    public VBox getViewEmployee() {
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

        Text textTitle = new Text("EMPLEADOS");
        textTitle.getStyleClass().add("textTitle");
        textTitle.setFont(new Font(25));
        gridPaneTitle.add(textTitle, 0, 0);

        JFXTextField textFieldSearch = new JFXTextField();
        textFieldSearch.setPromptText("BUSCAR");
        textFieldSearch.setPrefSize(x, y);
        textFieldSearch.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue,
                                                    String newValue) ->
                updateTableViewItems(textFieldSearch.getText().trim().toUpperCase())
        );
        gridPaneTitle.add(textFieldSearch, 0, 1);
        gridPaneTitle.setPadding(new Insets(20, 20, -10, 20));
        hBoxPanels = new HBox();
        hBoxPanels.setPrefSize(x, 7 * y / 8);
        hBoxPanels.getChildren().addAll(getViewEmployeeAgency(), getViewEmployeeAutoBank(), getViewEmployeeCallCenter());

        vBox.getChildren().addAll(gridPaneTitle, hBoxPanels);
        return vBox;
    }

    private GridPane getViewEmployeeAgency() {
        GridPane gridPane = new GridPane();

        double x = ScreenSize.getInstance().getX();
        double y = ScreenSize.getInstance().getY();

        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20));
        gridPane.setPrefSize(x, y);

        Text textTitle = new Text("AGENCIAS");
        textTitle.getStyleClass().add("textTitle");
        textTitle.setFont(new Font(20));
        gridPane.add(textTitle, 0, 0);

        HBox hBoxButtons = new HBox();
        JFXButton buttonReport = new JFXButton("Reporte");
        buttonReport.getStyleClass().addAll("customButton", "reportButton");
        buttonReport.setButtonType(JFXButton.ButtonType.FLAT);
        buttonReport.setPrefSize(x, y);
        buttonReport.setOnAction(event -> {
            String[] titles = {"ID", "NOMBRE", "NÚMERO TELEFÓNICO", "AGENCIA"};
            Employee[] employees = ControllerEmployee.getInstance().getEmployees();
            Employee[] dataCustomers = new Employee[100];
            for (int i = 0; i < employees.length; i++) {
                if (employees[i] != null && employees[i].getJob() instanceof BankingAgency
                        && !(employees[i].getJob() instanceof AutoBank)) {
                    dataCustomers[i] = employees[i];
                }
            }

            try {
                ReportGenerator.getInstance().generatePDF("EmployeesAgencies.pdf",
                        "EMPLEADOS DE AGENCIAS BANCARIAS", titles, dataCustomers);
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
            gridPane.add(CreateEmployeeAgency.getInstance().getGridPane(), 0, 1);
        });

        JFXButton buttonUpdate = new JFXButton("Modificar");
        buttonUpdate.getStyleClass().addAll("customButton", "warningButton");
        buttonUpdate.setButtonType(JFXButton.ButtonType.FLAT);
        buttonUpdate.setPrefSize(x, y);
        buttonUpdate.setOnAction(event -> {
            if (tableViewAgency.getSelectionModel().getSelectedItem() != null) {
                gridPane.getChildren().clear();
                gridPane.add(textTitle, 0, 0);
                gridPane.add(UpdateEmployeeAgency.getInstance()
                        .getGridPane((Employee) tableViewAgency.getSelectionModel().getSelectedItem()),
                        0, 1);
            }
        });

        JFXButton buttonDelete = new JFXButton("Eliminar");
        buttonDelete.getStyleClass().addAll("customButton", "dangerButton");
        buttonDelete.setButtonType(JFXButton.ButtonType.FLAT);
        buttonDelete.setPrefSize(x, y);
        buttonDelete.setOnAction(event -> {
            Employee agency = (Employee) tableViewAgency.getSelectionModel().getSelectedItem();
            if (agency != null) {
                ViewEmployee.getInstance().restartHBox();
                ControllerEmployee.getInstance().deleteEmployee(agency.getId());
                updateTableViewItemsAgency();
            }
        });

        hBoxButtons.getChildren().addAll(buttonReport, buttonAdd, buttonUpdate, buttonDelete);
        hBoxButtons.setPrefSize(x, y / 8);
        gridPane.add(hBoxButtons, 0, 1);

        TableColumn<Employee, Integer> columnID = new TableColumn<>("ID");
        columnID.setPrefWidth(50);
        columnID.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Employee, String> columnName = new TableColumn<>("NOMBRE");
        columnName.setPrefWidth(x / 16);
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Employee, String> columnPhoneNumber = new TableColumn<>("TELEFONO");
        columnPhoneNumber.setPrefWidth(x / 16);
        columnPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        TableColumn<Employee, Object> columnJob = new TableColumn<>("AGENCIA");
        columnJob.setPrefWidth(x / 16);
        columnJob.setCellValueFactory(new PropertyValueFactory<>("job"));

        updateObservableListAgency();
        tableViewAgency = new TableView<>(observableListAgency);
        tableViewAgency.getColumns().addAll(columnID, columnName, columnPhoneNumber, columnJob);
        tableViewAgency.setPrefSize(x, 7 * y / 8);

        gridPane.add(tableViewAgency, 0, 2);
        gridPane.setPadding(new Insets(20, 5, 20, 20));

        return gridPane;
    }

    private GridPane getViewEmployeeAutoBank() {
        GridPane gridPane = new GridPane();

        double x = ScreenSize.getInstance().getX();
        double y = ScreenSize.getInstance().getY();

        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20));
        gridPane.setPrefSize(x, y);

        Text textTitle = new Text("AUTO BANCOS");
        textTitle.getStyleClass().add("textTitle");
        textTitle.setFont(new Font(20));
        gridPane.add(textTitle, 0, 0);

        HBox hBoxButtons = new HBox();
        JFXButton buttonReport = new JFXButton("Reporte");
        buttonReport.getStyleClass().addAll("customButton", "reportButton");
        buttonReport.setButtonType(JFXButton.ButtonType.FLAT);
        buttonReport.setPrefSize(x, y);
        buttonReport.setOnAction(event -> {
            String[] titles = {"ID", "NOMBRE", "NÚMERO TELEFÓNICO", "AGENCIA"};
            Employee[] employees = ControllerEmployee.getInstance().getEmployees();
            Employee[] dataCustomers = new Employee[100];
            for (int i = 0; i < employees.length; i++) {
                if (employees[i] != null && employees[i].getJob() instanceof AutoBank) {
                    dataCustomers[i] = employees[i];
                }
            }

            try {
                ReportGenerator.getInstance().generatePDF("EmployeesAgenciesAutoBank.pdf",
                        "EMPLEADOS DE AGENCIAS BANCARIAS CON AUTOBANCO", titles, dataCustomers);
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
            gridPane.add(CreateEmployeeAutoBank.getInstance().getGridPane(), 0, 1);
        });

        JFXButton buttonUpdate = new JFXButton("Modificar");
        buttonUpdate.getStyleClass().addAll("customButton", "warningButton");
        buttonUpdate.setButtonType(JFXButton.ButtonType.FLAT);
        buttonUpdate.setPrefSize(x, y);
        buttonUpdate.setOnAction(event -> {
            if (tableViewAutoBank.getSelectionModel().getSelectedItem() != null) {
                gridPane.getChildren().clear();
                gridPane.add(textTitle, 0, 0);
                gridPane.add(UpdateEmployeeAutoBank.getInstance()
                                .getGridPane((Employee) tableViewAutoBank.getSelectionModel().getSelectedItem()),
                        0, 1);
            }
        });

        JFXButton buttonDelete = new JFXButton("Eliminar");
        buttonDelete.getStyleClass().addAll("customButton", "dangerButton");
        buttonDelete.setButtonType(JFXButton.ButtonType.FLAT);
        buttonDelete.setPrefSize(x, y);
        buttonDelete.setOnAction(event -> {
            Employee agency = (Employee) tableViewAutoBank.getSelectionModel().getSelectedItem();
            if (agency != null) {
                ViewEmployee.getInstance().restartHBox();
                ControllerEmployee.getInstance().deleteEmployee(agency.getId());
                updateTableViewItemsAutoBank();
            }
        });

        hBoxButtons.getChildren().addAll(buttonReport, buttonAdd, buttonUpdate, buttonDelete);
        hBoxButtons.setPrefSize(x, y / 8);
        gridPane.add(hBoxButtons, 0, 1);

        TableColumn<Employee, Integer> columnID = new TableColumn<>("ID");
        columnID.setPrefWidth(50);
        columnID.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Employee, String> columnName = new TableColumn<>("NOMBRE");
        columnName.setPrefWidth(x / 16);
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Employee, String> columnPhoneNumber = new TableColumn<>("TELEFONO");
        columnPhoneNumber.setPrefWidth(x / 16);
        columnPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        TableColumn<Employee, Object> columnJob = new TableColumn<>("AGENCIA");
        columnJob.setPrefWidth(x / 8);
        columnJob.setCellValueFactory(new PropertyValueFactory<>("job"));

        updateObservableListAutoBank();
        tableViewAutoBank = new TableView<>(observableListAutoBank);
        tableViewAutoBank.getColumns().addAll(columnID, columnName, columnPhoneNumber, columnJob);
        tableViewAutoBank.setPrefSize(x, 7 * y / 8);

        gridPane.add(tableViewAutoBank, 0, 2);
        gridPane.setPadding(new Insets(20, 12.5, 20, 12.5));

        return gridPane;
    }

    private GridPane getViewEmployeeCallCenter() {
        GridPane gridPane = new GridPane();

        double x = ScreenSize.getInstance().getX();
        double y = ScreenSize.getInstance().getY();

        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20));
        gridPane.setPrefSize(x, y);

        Text textTitle = new Text("CALL CENTER");
        textTitle.getStyleClass().add("textTitle");
        textTitle.setFont(new Font(20));
        gridPane.add(textTitle, 0, 0);

        HBox hBoxButtons = new HBox();
        JFXButton buttonReport = new JFXButton("Reporte");
        buttonReport.getStyleClass().addAll("customButton", "reportButton");
        buttonReport.setButtonType(JFXButton.ButtonType.FLAT);
        buttonReport.setPrefSize(x, y);
        buttonReport.setOnAction(event -> {
            String[] titles = {"ID", "NOMBRE", "NÚMERO TELEFÓNICO", "AGENCIA"};
            Employee[] employees = ControllerEmployee.getInstance().getEmployees();
            Employee[] dataCustomers = new Employee[100];
            for (int i = 0; i < employees.length; i++) {
                if (employees[i] != null && employees[i].getJob() instanceof String) {
                    dataCustomers[i] = employees[i];
                }
            }

            try {
                ReportGenerator.getInstance().generatePDF("EmployeesCallCenter.pdf",
                        "EMPLEADOS DE CALL-CENTER", titles, dataCustomers);
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
            gridPane.add(CreateEmployeeCallCenter.getInstance().getGridPane(), 0, 1);
        });

        JFXButton buttonUpdate = new JFXButton("Modificar");
        buttonUpdate.getStyleClass().addAll("customButton", "warningButton");
        buttonUpdate.setButtonType(JFXButton.ButtonType.FLAT);
        buttonUpdate.setPrefSize(x, y);
        buttonUpdate.setOnAction(event -> {
            if (tableViewCallCenter.getSelectionModel().getSelectedItem() != null) {
                gridPane.getChildren().clear();
                gridPane.add(textTitle, 0, 0);
                gridPane.add(UpdateEmployeeCallCenter.getInstance()
                                .getGridPane((Employee) tableViewCallCenter.getSelectionModel().getSelectedItem()),
                        0, 1);
            }
        });

        JFXButton buttonDelete = new JFXButton("Eliminar");
        buttonDelete.getStyleClass().addAll("customButton", "dangerButton");
        buttonDelete.setButtonType(JFXButton.ButtonType.FLAT);
        buttonDelete.setPrefSize(x, y);
        buttonDelete.setOnAction(event -> {
            Employee agency = (Employee) tableViewCallCenter.getSelectionModel().getSelectedItem();
            if (agency != null) {
                ViewEmployee.getInstance().restartHBox();
                ControllerEmployee.getInstance().deleteEmployee(agency.getId());
                updateTableViewItemsCallCenter();
            }
        });

        hBoxButtons.getChildren().addAll(buttonReport, buttonAdd, buttonUpdate, buttonDelete);
        hBoxButtons.setPrefSize(x, y / 8);
        gridPane.add(hBoxButtons, 0, 1);

        TableColumn<Employee, Integer> columnID = new TableColumn<>("ID");
        columnID.setPrefWidth(50);
        columnID.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Employee, String> columnName = new TableColumn<>("NOMBRE");
        columnName.setPrefWidth((x / 16));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Employee, String> columnPhoneNumber = new TableColumn<>("TELEFONO");
        columnPhoneNumber.setPrefWidth((x / 16));
        columnPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        updateObservableListCallCenter();
        tableViewCallCenter = new TableView<>(observableListCallCenter);
        tableViewCallCenter.getColumns().addAll(columnID, columnName, columnPhoneNumber);
        tableViewCallCenter.setPrefSize(x, 7 * y / 8);

        gridPane.add(tableViewCallCenter, 0, 2);
        gridPane.setPadding(new Insets(20, 20, 20, 5));

        return gridPane;
    }
}
