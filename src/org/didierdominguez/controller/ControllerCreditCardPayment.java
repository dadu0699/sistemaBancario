package org.didierdominguez.controller;

import org.didierdominguez.bean.CreditCard;
import org.didierdominguez.bean.CreditCardPayment;
import org.didierdominguez.util.Verifications;

public class ControllerCreditCardPayment {
    private static ControllerCreditCardPayment instance;
    private CreditCardPayment[] creditCardPayments = new CreditCardPayment[100];
    private int id;

    private ControllerCreditCardPayment(){
        id = 0;
    }

    public static ControllerCreditCardPayment getInstance() {
        if (instance == null) {
            instance = new ControllerCreditCardPayment();
        }
        return instance;
    }

    public void createCreditCardPayment(CreditCard creditCard, String type, Double amount) {
        for (int i = 0; i < creditCardPayments.length; i++) {
            if (creditCardPayments[i] == null) {
                id++;
                creditCardPayments[i] = new CreditCardPayment(id, creditCard, type, Verifications.getInstance().getDate(),
                        amount);
                ControllerCustomer.getInstance().updateDebt(creditCard.getCustomer().getId(), amount * -1);
                System.out.println("Credit Card Payment added successfully");
                break;
            }
        }
    }
}
