package org.didierdominguez.controller;

import org.didierdominguez.bean.Customer;

public class ControllerCustomer {
    private static ControllerCustomer instance;
    private Customer[] customers = new Customer[100];
    private int id;
    private boolean update;

    private ControllerCustomer() {
        id = 0;
    }

    public static ControllerCustomer getInstance() {
        if (instance == null) {
            instance = new ControllerCustomer();
        }
        return instance;
    }

    public void createCustomer(String name, String address, String phoneNumber) {
        for (int i = 0; i < customers.length; i++) {
            if (customers[i] == null && searchCustomer(name) == null) {
                id++;
                customers[i] = new Customer(id, name.toUpperCase(), phoneNumber, address.toUpperCase(),
                        0, 0, 0, 0, 0,
                        0.00, 0.00, 0);
                System.out.println("Customer added successfully");
                break;
            }
        }
    }

    public Customer[] getCustomers() {
        return customers;
    }

    public void updateCustomer(Integer id, String name, String address, String phoneNumber) {
        Customer customer = searchCustomer(id);
        Customer customerName = searchCustomer(name);
        update = false;
        if ((customer != null && customerName != null && customer.getId() == customerName.getId())
                || (customer != null && customerName == null)) {
            customer.setName(name);
            customer.setAddress(address);
            customer.setPhoneNumber(phoneNumber);
            update = true;
            System.out.println("Customer updated successfully");
        }
    }

    public boolean updateCustomer() {
        return update;
    }

    public void deleteCustomer(Integer id) {
        for (int i = 0; i < customers.length; i++) {
            if (customers[i] != null && customers[i].getId() == id) {
                customers[i] = null;
                System.out.println("Customer deleted successfully");
            }
        }
    }

    public Customer searchCustomer(Integer id) {
        for (Customer customer: customers) {
            if (customer != null && customer.getId() == id) {
                return customer;
            }
        }
        return null;
    }

    public Customer searchCustomer(String param) {
        for (Customer customer: customers) {
            if (customer != null && customer.getName().equalsIgnoreCase(param.trim())) {
                return customer;
            }
        }
        return null;
    }

    public Customer[] searchCustomers(String param) {
        Customer[] customers = new Customer[100];
        int count = 0;
        for (Customer employee : getCustomers()) {
            if (employee != null) {
                if (employee.getName().contains(param) || employee.getAddress().contains(param)) {
                    customers[count] = employee;
                    count++;
                }
            }
        }
        return customers;
    }

    public void updateSavingsAccounts(Integer id) {
        Customer customer = searchCustomer(id);
        if (customer != null) {
            customer.setSavingsAccounts(customer.getSavingsAccounts() + 1);
        }
    }

    public void updateMonetaryAccounts(Integer id) {
        Customer customer = searchCustomer(id);
        if (customer != null) {
            customer.setMonetaryAccounts(customer.getMonetaryAccounts() + 1);
        }
    }

    public void updateLoans(Integer id) {
        Customer customer = searchCustomer(id);
        if (customer != null) {
            customer.setLoans(customer.getLoans() + 1);
        }
    }

    public void updateCreditCards(Integer id) {
        Customer customer = searchCustomer(id);
        if (customer != null) {
            customer.setCreditCards(customer.getCreditCards() + 1);
        }
    }

    public void updateTransactionsCarriedOut(Integer id) {
        Customer customer = searchCustomer(id);
        if (customer != null) {
            customer.setTransactionsCarriedOut(customer.getTransactionsCarriedOut() + 1);
        }
    }

    public void updateBalance(Integer id, Double amount) {
        Customer customer = searchCustomer(id);
        if (customer != null) {
            customer.setBalance(customer.getBalance() + amount);
        }
    }

    public void updateDebt(Integer id, Double amount) {
        Customer customer = searchCustomer(id);
        if (customer != null) {
            customer.setDebt(customer.getDebt() + amount);
        }
    }

    public void updatePurchase(Integer id) {
        Customer customer = searchCustomer(id);
        if (customer != null) {
            customer.setPurchase(customer.getPurchase() + 1);
        }
    }

    public Customer[] getCustomersMoreAccounts() {
        Customer[] moreAccounts = customers;
        Customer [] customersTopAccounts = new Customer[100];
        Customer tempCustomer;

        for(int i=0; i < moreAccounts.length; i++) {
            for (int j = 1; j < (moreAccounts.length - i); j++) {
                if(moreAccounts[j-1] != null && moreAccounts[j] != null) {
                    if ((moreAccounts[j - 1].getMonetaryAccounts() +  moreAccounts[j - 1].getSavingsAccounts())
                            < (moreAccounts[j].getMonetaryAccounts() +  moreAccounts[j].getSavingsAccounts())) {
                        tempCustomer = moreAccounts[j - 1];
                        moreAccounts[j - 1] = moreAccounts[j];
                        moreAccounts[j] = tempCustomer;
                    }
                }
            }
        }

        System.arraycopy(moreAccounts, 0, customersTopAccounts, 0, 3);
        return customersTopAccounts;
    }

    public Customer[] getCustomersMostMoney() {
        Customer[] mostMoney = customers;
        Customer [] customersTopMoney = new Customer[100];
        Customer tempCustomer;

        for(int i=0; i < mostMoney.length; i++) {
            for (int j = 1; j < (mostMoney.length - i); j++) {
                if(mostMoney[j-1] != null && mostMoney[j] != null) {
                    if ((mostMoney[j - 1].getBalance()) < mostMoney[j].getBalance()) {
                        tempCustomer = mostMoney[j - 1];
                        mostMoney[j - 1] = mostMoney[j];
                        mostMoney[j] = tempCustomer;
                    }
                }
            }
        }

        System.arraycopy(mostMoney, 0, customersTopMoney, 0, 3);
        return customersTopMoney;
    }

    public Customer[] getCustomersMostDebt() {
        Customer[] mostDebt = customers;
        Customer [] customersTopDebt = new Customer[100];
        Customer tempCustomer;

        for(int i=0; i < mostDebt.length; i++) {
            for (int j = 1; j < (mostDebt.length - i); j++) {
                if(mostDebt[j-1] != null && mostDebt[j] != null) {
                    if ((mostDebt[j - 1].getDebt()) < mostDebt[j].getDebt()) {
                        tempCustomer = mostDebt[j - 1];
                        mostDebt[j - 1] = mostDebt[j];
                        mostDebt[j] = tempCustomer;
                    }
                }
            }
        }

        System.arraycopy(mostDebt, 0, customersTopDebt, 0, 3);
        return customersTopDebt;
    }

    public Customer[] getCustomersMostPurchase() {
        Customer[] mostPurchase = customers;
        Customer [] customersTopPurchase = new Customer[100];
        Customer tempCustomer;

        for(int i=0; i < mostPurchase.length; i++) {
            for (int j = 1; j < (mostPurchase.length - i); j++) {
                if(mostPurchase[j-1] != null && mostPurchase[j] != null) {
                    if ((mostPurchase[j - 1].getPurchase()) < mostPurchase[j].getPurchase()) {
                        tempCustomer = mostPurchase[j - 1];
                        mostPurchase[j - 1] = mostPurchase[j];
                        mostPurchase[j] = tempCustomer;
                    }
                }
            }
        }

        System.arraycopy(mostPurchase, 0, customersTopPurchase, 0, 3);
        return customersTopPurchase;
    }
}
