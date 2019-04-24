package org.didierdominguez.view.transaction;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.didierdominguez.bean.Account;
import org.didierdominguez.bean.CreditCard;
import org.didierdominguez.bean.Loan;
import org.didierdominguez.bean.MonetaryAccount;
import org.didierdominguez.controller.ControllerAccount;
import org.didierdominguez.controller.ControllerCreditCard;
import org.didierdominguez.controller.ControllerLoan;
import org.didierdominguez.controller.ControllerMonetaryAccount;
import org.didierdominguez.util.ScreenSize;

import java.util.ArrayList;

public class ViewCustomerDetail extends Stage {
    private static ViewCustomerDetail instance;
    private HBox hBoxPanels;
    private TableView tableViewSavingsAccounts;
    private TableView tableViewMonetaryAccounts;
    private TableView tableViewLoans;
    private TableView tableViewCreditCards;
    private ObservableList observableListSavingsAccounts;
    private ObservableList observableListMonetaryAccounts;
    private ObservableList observableListLoans;
    private ObservableList observableListCreditCards;

    private ViewCustomerDetail() {
    }

    public static ViewCustomerDetail getInstance() {
        if (instance == null) {
            instance = new ViewCustomerDetail();
        }
        return instance;
    }

    public void restartHBox() {
        hBoxPanels.getChildren().clear();
        hBoxPanels.getChildren().addAll(getViewSavingsMonetaryAccounts(), getViewLoansCreditCards());
    }

    private void updateObservableListSavingsAccounts() {
        Account[] accounts = ControllerAccount.getInstance().getAccounts();
        ArrayList<Account> arrayListAccounts = new ArrayList<>();
        for (Account account : accounts) {
            if (account != null && account.getCustomer() == TransactionPanel.getInstance().getCustomer()) {
                arrayListAccounts.add(account);
            }
        }
        if (observableListSavingsAccounts != null) {
            observableListSavingsAccounts.clear();
        }
        observableListSavingsAccounts = FXCollections.observableArrayList(arrayListAccounts);
    }

    private void updateObservableListMonetaryAccounts() {
        MonetaryAccount[] monetaryAccounts = ControllerMonetaryAccount.getInstance().getMonetaryAccounts();
        ArrayList<MonetaryAccount> arrayListMonetaryAccounts = new ArrayList<>();
        for (MonetaryAccount account : monetaryAccounts) {
            if (account != null && account.getCustomer() == TransactionPanel.getInstance().getCustomer()) {
                arrayListMonetaryAccounts.add(account);
            }
        }
        if (observableListMonetaryAccounts != null) {
            observableListMonetaryAccounts.clear();
        }
        observableListMonetaryAccounts = FXCollections.observableArrayList(arrayListMonetaryAccounts);
    }

    private void updateObservableListLoans() {
        Loan[] loans = ControllerLoan.getInstance().getLoans();
        ArrayList<Loan> arrayListLoans = new ArrayList<>();
        for (Loan loan : loans) {
            if (loan != null && loan.getCustomer() == TransactionPanel.getInstance().getCustomer()
                    && loan.getAuthorization() != null) {
                arrayListLoans.add(loan);
            }
        }
        if (observableListLoans != null) {
            observableListLoans.clear();
        }
        observableListLoans = FXCollections.observableArrayList(arrayListLoans);
    }

    private void updateObservableListCreditCards() {
        CreditCard[] creditCards = ControllerCreditCard.getInstance().getCreditCards();
        ArrayList<CreditCard> arrayListCreditCards = new ArrayList<>();
        for (CreditCard creditCard : creditCards) {
            if (creditCard != null && creditCard.getCustomer() == TransactionPanel.getInstance().getCustomer()
                    && creditCard.getAuthorization() != null) {
                arrayListCreditCards.add(creditCard);
            }
        }
        if (observableListCreditCards != null) {
            observableListCreditCards.clear();
        }
        observableListCreditCards = FXCollections.observableArrayList(arrayListCreditCards);
    }

    public void updateTableViewItemsSavingsAccounts() {
        updateObservableListSavingsAccounts();
        tableViewSavingsAccounts.setItems(observableListSavingsAccounts);
    }

    public void updateTableViewItemsMonetaryAccounts() {
        updateObservableListMonetaryAccounts();
        tableViewMonetaryAccounts.setItems(observableListMonetaryAccounts);
    }

    public void updateTableViewItemsLoans() {
        updateObservableListLoans();
        tableViewLoans.setItems(observableListLoans);
    }

    public void updateTableViewItemsCreditCards() {
        updateObservableListCreditCards();
        tableViewCreditCards.setItems(observableListCreditCards);
    }

    public VBox getViewCustomerDetail() {
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

        Text textTitle = new Text("DETALLES");
        textTitle.getStyleClass().add("textTitle");
        textTitle.setFont(new Font(25));
        gridPaneTitle.add(textTitle, 0, 0);

        hBoxPanels = new HBox();
        hBoxPanels.setPrefSize(x, 7 * y / 8);
        hBoxPanels.getChildren().addAll(getViewSavingsMonetaryAccounts(), getViewLoansCreditCards());

        vBox.getChildren().addAll(gridPaneTitle, hBoxPanels);
        return vBox;
    }

    private GridPane getViewSavingsMonetaryAccounts() {
        GridPane gridPane = new GridPane();

        double x = ScreenSize.getInstance().getX();
        double y = ScreenSize.getInstance().getY();

        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20));
        gridPane.setPrefSize(x, y);

        Text textTitle = new Text("CUENTAS DE AHORRO");
        textTitle.getStyleClass().add("textTitle");
        textTitle.setFont(new Font(15));
        gridPane.add(textTitle, 0, 0);

        TableColumn<Account, Integer> columnID = new TableColumn<>("ID");
        columnID.setPrefWidth(50);
        columnID.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Account, String> columnAccountNo = new TableColumn<>("NO. DE CUENTA");
        columnAccountNo.setPrefWidth(x / 8);
        columnAccountNo.setCellValueFactory(new PropertyValueFactory<>("accountNo"));
        TableColumn<Account, String> columnOpeningDate = new TableColumn<>("FECHA DE APERTURA");
        columnOpeningDate.setPrefWidth(x / 8);
        columnOpeningDate.setCellValueFactory(new PropertyValueFactory<>("openingDate"));
        TableColumn<Account, Double> columnStartingAmount = new TableColumn<>("SALDO");
        columnStartingAmount.setPrefWidth(x / 8);
        columnStartingAmount.setCellValueFactory(new PropertyValueFactory<>("startingAmount"));

        updateObservableListSavingsAccounts();
        tableViewSavingsAccounts = new TableView<>(observableListSavingsAccounts);
        tableViewSavingsAccounts.getColumns().addAll(columnID, columnAccountNo, columnOpeningDate, columnStartingAmount);
        tableViewSavingsAccounts.setPrefSize(x, 7 * y / 8);
        gridPane.add(tableViewSavingsAccounts, 0, 1);

        Text textTitleM = new Text("CUENTAS MONETARIAS");
        textTitleM.getStyleClass().add("textTitle");
        textTitleM.setFont(new Font(15));
        gridPane.add(textTitleM, 0, 2);

        TableColumn<MonetaryAccount, Integer> columnIDM = new TableColumn<>("ID");
        columnIDM.setPrefWidth(50);
        columnIDM.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<MonetaryAccount, String> columnAccountNoM = new TableColumn<>("NO. DE CUENTA");
        columnAccountNoM.setPrefWidth(x / 10);
        columnAccountNoM.setCellValueFactory(new PropertyValueFactory<>("accountNo"));
        TableColumn<MonetaryAccount, String> columnOpeningDateM = new TableColumn<>("FECHA DE APERTURA");
        columnOpeningDateM.setPrefWidth(x / 10);
        columnOpeningDateM.setCellValueFactory(new PropertyValueFactory<>("openingDate"));
        TableColumn<MonetaryAccount, Integer> columnCheck = new TableColumn<>("CHEQUES");
        columnCheck.setPrefWidth(x / 10);
        columnCheck.setCellValueFactory(new PropertyValueFactory<>("check"));
        TableColumn<MonetaryAccount, Double> columnStartingAmountM = new TableColumn<>("SALDO");
        columnStartingAmountM.setPrefWidth(x / 10);
        columnStartingAmountM.setCellValueFactory(new PropertyValueFactory<>("startingAmount"));

        updateObservableListMonetaryAccounts();
        tableViewMonetaryAccounts = new TableView<>(observableListMonetaryAccounts);
        tableViewMonetaryAccounts.getColumns().addAll(columnIDM, columnAccountNoM, columnOpeningDateM,
                columnCheck, columnStartingAmountM);
        tableViewMonetaryAccounts.setPrefSize(x, 7 * y / 8);
        gridPane.add(tableViewMonetaryAccounts, 0, 3);

        gridPane.setPadding(new Insets(-10, 10, 20, 20));

        return gridPane;
    }

    private GridPane getViewLoansCreditCards() {
        GridPane gridPane = new GridPane();

        double x = ScreenSize.getInstance().getX();
        double y = ScreenSize.getInstance().getY();

        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20));
        gridPane.setPrefSize(x, y);

        // LOAN
        Text textTitle = new Text("PRÉSTAMOS");
        textTitle.getStyleClass().add("textTitle");
        textTitle.setFont(new Font(15));
        gridPane.add(textTitle, 0, 0);

        TableColumn<Loan, Integer> columnID = new TableColumn<>("ID");
        columnID.setPrefWidth(50);
        columnID.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Loan, String> columnLoanNo = new TableColumn<>("NO. DE PRÉSTAMO");
        columnLoanNo.setPrefWidth(x / 12);
        columnLoanNo.setCellValueFactory(new PropertyValueFactory<>("loanNo"));
        TableColumn<Loan, String> columnDate = new TableColumn<>("FECHA DE SOLICITUD");
        columnDate.setPrefWidth(x / 12);
        columnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        TableColumn<Loan, Double> columnAmount = new TableColumn<>("SALDO");
        columnAmount.setPrefWidth(x / 12);
        columnAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        TableColumn<Loan, Double> columnAmountPaid = new TableColumn<>("PAGADO");
        columnAmountPaid.setPrefWidth(x / 12);
        columnAmountPaid.setCellValueFactory(new PropertyValueFactory<>("amountPaid"));
        TableColumn<Loan, Boolean> columnAuthorization = new TableColumn<>("AUTORIZACIÓN");
        columnAuthorization.setPrefWidth(x / 12);
        columnAuthorization.setCellValueFactory(new PropertyValueFactory<>("authorization"));
        columnAuthorization.setCellFactory(col -> new TableCell<Loan, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty) ;
                setText(empty ? null : item ? "AUTORIZADO" : "RECHAZADO" );
            }
        });
        updateObservableListLoans();
        tableViewLoans = new TableView<>(observableListLoans);
        tableViewLoans.getColumns().addAll(columnID, columnLoanNo, columnDate, columnAmount,
                columnAmountPaid, columnAuthorization);
        tableViewLoans.setPrefSize(x, 7 * y / 8);
        gridPane.add(tableViewLoans, 0, 1);

        // CREDIT CARD
        Text textTitleC = new Text("TARJETAS DE CRÉDITO");
        textTitleC.getStyleClass().add("textTitle");
        textTitleC.setFont(new Font(15));
        gridPane.add(textTitleC, 0, 2);

        TableColumn<CreditCard, Integer> columnIDC = new TableColumn<>("ID");
        columnIDC.setPrefWidth(50);
        columnIDC.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<CreditCard, String> columnCardNo = new TableColumn<>("NO. DE TARJETA");
        columnCardNo.setPrefWidth(x / 12);
        columnCardNo.setCellValueFactory(new PropertyValueFactory<>("cardNo"));
        TableColumn<CreditCard, String> columnExpirationDate = new TableColumn<>("FECHA DE VENCIMIENTO");
        columnExpirationDate.setPrefWidth(x / 12);
        columnExpirationDate.setCellValueFactory(new PropertyValueFactory<>("expirationDate"));
        TableColumn<CreditCard, Double> columnCreditLimit = new TableColumn<>("LIMITE DE CREDITO");
        columnCreditLimit.setPrefWidth(x / 12);
        columnCreditLimit.setCellValueFactory(new PropertyValueFactory<>("creditLimit"));
        TableColumn<CreditCard, Double> columnAmountOwed = new TableColumn<>("DEUDA");
        columnAmountOwed.setPrefWidth(x / 12);
        columnAmountOwed.setCellValueFactory(new PropertyValueFactory<>("amountOwed"));
        TableColumn<CreditCard, Boolean> columnAuthorizationC = new TableColumn<>("AUTORIZACIÓN");
        columnAuthorizationC.setPrefWidth(x / 12);
        columnAuthorizationC.setCellValueFactory(new PropertyValueFactory<>("authorization"));
        columnAuthorizationC.setCellFactory(col -> new TableCell<CreditCard, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty) ;
                setText(empty ? null : item ? "AUTORIZADO" : "RECHAZADO" );
            }
        });

        updateObservableListCreditCards();
        tableViewCreditCards = new TableView<>(observableListCreditCards);
        tableViewCreditCards.getColumns().addAll(columnIDC, columnCardNo, columnExpirationDate, columnCreditLimit,
                columnAmountOwed, columnAuthorizationC);
        tableViewCreditCards.setPrefSize(x, 7 * y / 8);
        gridPane.add(tableViewCreditCards, 0, 3);

        gridPane.setPadding(new Insets(-10, 20, 20, 10));
        return gridPane;
    }
}
