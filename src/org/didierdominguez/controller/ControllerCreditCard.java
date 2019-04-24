package org.didierdominguez.controller;

import org.didierdominguez.bean.CreditCard;
import org.didierdominguez.bean.Customer;
import org.didierdominguez.util.Verifications;

public class ControllerCreditCard {
    private static ControllerCreditCard instance;
    private CreditCard[] creditCards = new CreditCard[100];
    private int id;
    private boolean update;

    private ControllerCreditCard(){
        id = 0;
    }

    public static ControllerCreditCard getInstance() {
        if (instance == null) {
            instance = new ControllerCreditCard();
        }
        return instance;
    }

    public void createCreditCard(Customer customer, Double creditLimit) {
        for (int i = 0; i < creditCards.length; i++) {
            if (creditCards[i] == null) {
                id++;
                creditCards[i] = new CreditCard(id, Verifications.getInstance().serial(), customer,
                        Verifications.getInstance().getExpirationDate(), creditLimit,
                        0.0, 0.0, null);
                System.out.println("Credit Card: " + creditCards[i].getCardNo());
                System.out.println("Credit Card added successfully");
                break;
            }
        }
    }

    public CreditCard[] getCreditCards() {
        return creditCards;
    }

    public void updateCreditCard(Integer id, Boolean authorization) {
        CreditCard creditCard = searchCreditCard(id);
        update = false;
        if (creditCard != null) {
            creditCard.setAuthorization(authorization);
            update = true;
            System.out.println("Credit Card authorization updated successfully");
        }
    }

    public void updateCreditCard(Integer id, Double amount) {
        CreditCard creditCard = searchCreditCard(id);
        update = false;
        if (creditCard != null) {
            creditCard.setAmountOwed(creditCard.getAmountOwed() + amount);
            update = true;
            System.out.println("Credit Card updated successfully");
        }
    }

    public boolean updateCreditCard() {
        return update;
    }

    public void deleteCreditCard(Integer id) {
        for (int i = 0; i < creditCards.length; i++) {
            if (creditCards[i] != null && creditCards[i].getId() == id) {
                creditCards[i] = null;
                System.out.println("Credit Card deleted successfully");
            }
        }
    }

    public CreditCard searchCreditCard(Integer id) {
        for (CreditCard creditCard: creditCards) {
            if (creditCard != null && creditCard.getId() == id) {
                return creditCard;
            }
        }
        return null;
    }

    public CreditCard searchCreditCard(String param) {
        for (CreditCard creditCard: creditCards) {
            if (creditCard != null && creditCard.getCardNo().equalsIgnoreCase(param.trim())) {
                return creditCard;
            }
        }
        return null;
    }

    public CreditCard[] searchsearchCreditCards(String param) {
        CreditCard[] creditCards = new CreditCard[100];
        int count = 0;
        for (CreditCard creditCard : getCreditCards()) {
            if (creditCard != null) {
                if (creditCard.getCardNo().contains(param) || creditCard.getCustomer().getName().contains(param)) {
                    creditCards[count] = creditCard;
                    count++;
                }
            }
        }
        return creditCards;
    }
}
