package org.didierdominguez.controller;

import org.didierdominguez.bean.CreditCard;
import org.didierdominguez.bean.Customer;
import org.didierdominguez.bean.Purchase;

public class ControllerPurchase {
    private static ControllerPurchase instance;
    private Purchase[] purchases = new Purchase[100];
    private int id;

    private ControllerPurchase(){
        id = 0;
    }

    public static ControllerPurchase getInstance() {
        if (instance == null) {
            instance = new ControllerPurchase();
        }
        return instance;
    }

    public void createPurchase(Customer customer, CreditCard creditCard, Double amount) {
        for (int i = 0; i < purchases.length; i++) {
            if (purchases[i] == null) {
                id++;
                purchases[i] = new Purchase(id, customer, creditCard, amount);
                ControllerCustomer.getInstance().updateDebt(customer.getId(), amount);
                ControllerCustomer.getInstance().updatePurchase(customer.getId());
                System.out.println("Purchase added successfully");
                break;
            }
        }
    }
}
