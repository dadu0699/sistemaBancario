package org.didierdominguez.controller;

import org.didierdominguez.bean.Customer;
import org.didierdominguez.bean.MonetaryAccount;
import org.didierdominguez.util.Verifications;

public class ControllerMonetaryAccount {
    private static ControllerMonetaryAccount instance;
    private MonetaryAccount[] monetaryAccounts = new MonetaryAccount[100];
    private int id;
    private boolean update;

    private ControllerMonetaryAccount(){
        id = 0;
    }

    public static ControllerMonetaryAccount getInstance() {
        if (instance == null) {
            instance = new ControllerMonetaryAccount();
        }
        return instance;
    }

    public void createAccount(Customer customer, Double startingAmount, Integer checks) {
        for (int i = 0; i < monetaryAccounts.length; i++) {
            if (monetaryAccounts[i] == null) {
                id++;
                monetaryAccounts[i] = new MonetaryAccount(id, Verifications.getInstance().serial(), customer,
                        Verifications.getInstance().getDate(), startingAmount, checks);
                ControllerCustomer.getInstance().updateMonetaryAccounts(customer.getId());
                ControllerCustomer.getInstance().updateBalance(customer.getId(), startingAmount);
                System.out.println("Monetary Account: " + monetaryAccounts[i].getAccountNo());
                System.out.println("Monetary Account added successfully");
                break;
            }
        }
    }

    public MonetaryAccount[] getMonetaryAccounts() {
        return monetaryAccounts;
    }

    public void updateAccount(Integer id, Double amount, Boolean verification) {
        MonetaryAccount monetaryAccount = searchAccount(id);
        update = false;
        if (monetaryAccount != null) {
            monetaryAccount.setStartingAmount(monetaryAccount.getStartingAmount() + amount);
            if (verification) {
                monetaryAccount.setCheck(monetaryAccount.getCheck() - 1);
            }
            // System.out.println(monetaryAccount.getStartingAmount() + " " + monetaryAccount.getAccountNo() + " " + monetaryAccount.getCheck());
            update = true;
            System.out.println("Monetary Account updated successfully");
        }
    }

    public boolean updateAccount() {
        return update;
    }

    public void deleteAccount(Integer id) {
        for (int i = 0; i < monetaryAccounts.length; i++) {
            if (monetaryAccounts[i] != null && monetaryAccounts[i].getId() == id) {
                monetaryAccounts[i] = null;
                System.out.println("Monetary Account deleted successfully");
            }
        }
    }

    public MonetaryAccount searchAccount(Integer id) {
        for (MonetaryAccount account: monetaryAccounts) {
            if (account != null && account.getId() == id) {
                return account;
            }
        }
        return null;
    }

    public MonetaryAccount searchAccount(String param) {
        for (MonetaryAccount account: monetaryAccounts) {
            if (account != null && account.getAccountNo().equalsIgnoreCase(param.trim())) {
                return account;
            }
        }
        return null;
    }

    public MonetaryAccount searchAccount(String param, Customer customer) {
        for (MonetaryAccount account: monetaryAccounts) {
            if (account != null && account.getAccountNo().equalsIgnoreCase(param.trim())
                    && account.getCustomer() == customer) {
                return account;
            }
        }
        return null;
    }

    public MonetaryAccount[] searchAccounts(String param) {
        MonetaryAccount[] accounts = new MonetaryAccount[100];
        int count = 0;
        for (MonetaryAccount account : getMonetaryAccounts()) {
            if (account != null) {
                if (account.getAccountNo().contains(param) || account.getCustomer().getName().contains(param)) {
                    accounts[count] = account;
                    count++;
                }
            }
        }
        return accounts;
    }
}
