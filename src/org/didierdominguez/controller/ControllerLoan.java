package org.didierdominguez.controller;

import org.didierdominguez.bean.Customer;
import org.didierdominguez.bean.Loan;
import org.didierdominguez.util.Verifications;

public class ControllerLoan {
    private static ControllerLoan instance;
    private Loan[] loans = new Loan[100];
    private int id;
    private boolean update;

    private ControllerLoan(){
        id = 0;
    }

    public static ControllerLoan getInstance() {
        if (instance == null) {
            instance = new ControllerLoan();
        }
        return instance;
    }

    public void createLoan(Customer customer, Double startingAmount) {
        for (int i = 0; i < loans.length; i++) {
            if (loans[i] == null) {
                id++;
                loans[i] = new Loan(id, Verifications.getInstance().serial(), customer,
                        Verifications.getInstance().getDate(), startingAmount, 0.0, null);
                System.out.println("Loan: " + loans[i].getLoanNo());
                System.out.println("Loan added successfully");
                break;
            }
        }
    }

    public Loan[] getLoans() {
        return loans;
    }

    public void updateLoan(Integer id, Double amount) {
        Loan loan = searchLoan(id);
        update = false;
        if (loan != null) {
            loan.setAmountPaid(loan.getAmountPaid() + amount);
            loan.setAmount(loan.getAmount() - amount);
            update = true;
            System.out.println("Loan updated successfully");
        }
    }

    public void updateLoan(Integer id, Boolean authorization) {
        Loan loan = searchLoan(id);
        update = false;
        if (loan != null) {
            loan.setAuthorization(authorization);
            update = true;
            if (authorization) {
                ControllerCustomer.getInstance().updateDebt(loan.getCustomer().getId(), loan.getAmount());
            }
            System.out.println("Loan updated successfully");
        }
    }

    public boolean updateLoan() {
        return update;
    }

    public void deleteLoan(Integer id) {
        for (int i = 0; i < loans.length; i++) {
            if (loans[i] != null && loans[i].getId() == id) {
                loans[i] = null;
                System.out.println("Loan deleted successfully");
            }
        }
    }

    public Loan searchLoan(Integer id) {
        for (Loan loan: loans) {
            if (loan != null && loan.getId() == id) {
                return loan;
            }
        }
        return null;
    }

    public Loan searchLoan(String param) {
        for (Loan loan: loans) {
            if (loan != null && loan.getLoanNo().equalsIgnoreCase(param.trim())) {
                return loan;
            }
        }
        return null;
    }

    public Loan[] searchLoans(String param) {
        Loan[] loans = new Loan[100];
        int count = 0;
        for (Loan loan : getLoans()) {
            if (loan != null) {
                if (loan.getLoanNo().contains(param) || loan.getCustomer().getName().contains(param)) {
                    loans[count] = loan;
                    count++;
                }
            }
        }
        return loans;
    }
}
