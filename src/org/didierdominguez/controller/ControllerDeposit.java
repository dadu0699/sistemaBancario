package org.didierdominguez.controller;

import org.didierdominguez.bean.Account;
import org.didierdominguez.bean.Customer;
import org.didierdominguez.bean.Deposit;
import org.didierdominguez.bean.MonetaryAccount;
import org.didierdominguez.util.Verifications;

public class ControllerDeposit {
    private static ControllerDeposit instance;
    private Deposit[] deposits = new Deposit[100];
    private int id;

    private ControllerDeposit(){
        id = 0;
    }

    public static ControllerDeposit getInstance() {
        if (instance == null) {
            instance = new ControllerDeposit();
        }
        return instance;
    }

    public void createDeposit(String noAccount, Double amount, Object depositType, Customer customer) {
        for (int i = 0; i < deposits.length; i++) {
            if (deposits[i] == null) {
                id++;
                Object account;

                if (ControllerAccount.getInstance().searchAccount(noAccount) != null) {
                    account = ControllerAccount.getInstance().searchAccount(noAccount);
                    ControllerAccount.getInstance().updateAccount(((Account) account).getId(), amount);
                } else {
                    account = ControllerMonetaryAccount.getInstance().searchAccount(noAccount);
                    ControllerMonetaryAccount.getInstance().updateAccount(((MonetaryAccount) account).getId(), amount, false);
                }
                deposits[i] = new Deposit(id, account, amount, Verifications.getInstance().getDate(), depositType);
                ControllerCustomer.getInstance().updateTransactionsCarriedOut(customer.getId());
                ControllerCustomer.getInstance().updateBalance(customer.getId(), amount);
                System.out.println("Deposit added successfully");
                break;
            }
        }
    }
}
