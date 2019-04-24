package org.didierdominguez.controller;

import org.didierdominguez.bean.Loan;
import org.didierdominguez.bean.LoanPayment;
import org.didierdominguez.util.Verifications;

public class ControllerLoanPayment {
    private static ControllerLoanPayment instance;
    private LoanPayment[] loanPayments = new LoanPayment[100];
    private int id;

    private ControllerLoanPayment(){
        id = 0;
    }

    public static ControllerLoanPayment getInstance() {
        if (instance == null) {
            instance = new ControllerLoanPayment();
        }
        return instance;
    }

    public void createLoanPayment(Loan loan, String type, Double amount) {
        for (int i = 0; i < loanPayments.length; i++) {
            if (loanPayments[i] == null) {
                id++;
                loanPayments[i] = new LoanPayment(id, loan, type, Verifications.getInstance().getDate(), amount);
                ControllerCustomer.getInstance().updateDebt(loan.getCustomer().getId(), amount* -1);
                System.out.println("Loan Payment added successfully");
                break;
            }
        }
    }
}
