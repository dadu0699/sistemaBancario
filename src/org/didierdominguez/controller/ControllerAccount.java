package org.didierdominguez.controller;

import org.didierdominguez.bean.Account;
import org.didierdominguez.bean.Customer;
import org.didierdominguez.util.Verifications;

public class ControllerAccount {
    private static ControllerAccount instance;
    private Account[] accounts = new Account[100];
    private int id;
    private boolean update;

    private ControllerAccount(){
        id = 0;
    }

    public static ControllerAccount getInstance() {
        if (instance == null) {
            instance = new ControllerAccount();
        }
        return instance;
    }

    public void createAccount(Customer customer, Double startingAmount) {
        for (int i = 0; i < accounts.length; i++) {
            if (accounts[i] == null) {
                id++;
                accounts[i] = new Account(id, Verifications.getInstance().serial(), customer,
                        Verifications.getInstance().getDate(), startingAmount);
                ControllerCustomer.getInstance().updateSavingsAccounts(customer.getId());
                ControllerCustomer.getInstance().updateBalance(customer.getId(), startingAmount);
                System.out.println("Saving Account: " + accounts[i].getAccountNo());
                System.out.println("Saving Account added successfully");
                break;
            }
        }
    }

    public Account[] getAccounts() {
        return accounts;
    }

    public void updateAccount(Integer id, Double amount) {
        Account account = searchAccount(id);
        update = false;
        if (account != null) {
            account.setStartingAmount(account.getStartingAmount() + amount);
            update = true;
            // System.out.println(account.getStartingAmount() + " " + account.getAccountNo());
            System.out.println("Saving Account updated successfully");
        }
    }

    public boolean updateAccount() {
        return update;
    }

    public void deleteAccount(Integer id) {
        for (int i = 0; i < accounts.length; i++) {
            if (accounts[i] != null && accounts[i].getId() == id) {
                accounts[i] = null;
                System.out.println("Saving Account deleted successfully");
            }
        }
    }

    public Account searchAccount(Integer id) {
        for (Account account: accounts) {
            if (account != null && account.getId() == id) {
                return account;
            }
        }
        return null;
    }

    public Account searchAccount(String param) {
        for (Account account: accounts) {
            if (account != null && account.getAccountNo().equalsIgnoreCase(param.trim())) {
                return account;
            }
        }
        return null;
    }

    public Account searchAccount(String param, Customer customer) {
        for (Account account: accounts) {
            if (account != null && account.getAccountNo().equalsIgnoreCase(param.trim())
                    && account.getCustomer() == customer) {
                return account;
            }
        }
        return null;
    }

    public Account[] searchAccounts(String param) {
        Account[] accounts = new Account[100];
        int count = 0;
        for (Account account : getAccounts()) {
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
