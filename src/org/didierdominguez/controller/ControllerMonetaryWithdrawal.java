package org.didierdominguez.controller;

import org.didierdominguez.bean.Account;
import org.didierdominguez.bean.Customer;
import org.didierdominguez.bean.MonetaryAccount;
import org.didierdominguez.bean.MonetaryWithdrawal;
import org.didierdominguez.util.Verifications;

public class ControllerMonetaryWithdrawal {
    private static ControllerMonetaryWithdrawal instance;
    private MonetaryWithdrawal[] monetaryWithdrawals = new MonetaryWithdrawal[100];
    private int id;

    private ControllerMonetaryWithdrawal(){
        id = 0;
    }

    public static ControllerMonetaryWithdrawal getInstance() {
        if (instance == null) {
            instance = new ControllerMonetaryWithdrawal();
        }
        return instance;
    }

    public void createMonetaryWithdrawal(String noAccount, Double amount, Customer customer) {
        for (int i = 0; i < monetaryWithdrawals.length; i++) {
            if (monetaryWithdrawals[i] == null) {
                id++;
                Object account;

                if (ControllerAccount.getInstance().searchAccount(noAccount) != null) {
                    account = ControllerAccount.getInstance().searchAccount(noAccount);
                    ControllerAccount.getInstance().updateAccount(((Account) account).getId(), amount);
                } else {
                    account = ControllerMonetaryAccount.getInstance().searchAccount(noAccount);
                    ControllerMonetaryAccount.getInstance().updateAccount(((MonetaryAccount) account).getId(), amount, false);
                }
                monetaryWithdrawals[i] = new MonetaryWithdrawal(id, account, amount, Verifications.getInstance().getDate());
                ControllerCustomer.getInstance().updateTransactionsCarriedOut(customer.getId());
                ControllerCustomer.getInstance().updateBalance(customer.getId(), amount);
                System.out.println("Monetary Withdrawal added successfully");
                break;
            }
        }
    }

    public void createExchangeCheck(String noAccount, Double amount, Customer customer) {
        for (int i = 0; i < monetaryWithdrawals.length; i++) {
            if (monetaryWithdrawals[i] == null) {
                id++;
                MonetaryAccount account = ControllerMonetaryAccount.getInstance().searchAccount(noAccount);
                ControllerMonetaryAccount.getInstance().updateAccount(account.getId(), amount, true);

                monetaryWithdrawals[i] = new MonetaryWithdrawal(id, account, amount, Verifications.getInstance().getDate());
                ControllerCustomer.getInstance().updateTransactionsCarriedOut(customer.getId());
                ControllerCustomer.getInstance().updateBalance(customer.getId(), amount);
                System.out.println("Exchange check added successfully");
                break;
            }
        }
    }
}
