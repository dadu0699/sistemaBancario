package org.didierdominguez.bean;

public class Customer extends Person{
    private String address;
    private Integer savingsAccounts;
    private Integer monetaryAccounts;
    private Integer loans;
    private Integer creditCards;
    private Integer transactionsCarriedOut;
    private Double balance;
    private Double debt;
    private Integer purchase;

    public Customer(Integer id, String name, String phoneNumber, String address, Integer savingsAccounts,
                    Integer monetaryAccounts, Integer loans, Integer creditCards,
                    Integer transactionsCarriedOut, Double balance, Double debt, Integer purchase) {
        super(id, name, phoneNumber);
        this.address = address;
        this.savingsAccounts = savingsAccounts;
        this.monetaryAccounts = monetaryAccounts;
        this.loans = loans;
        this.creditCards = creditCards;
        this.transactionsCarriedOut = transactionsCarriedOut;
        this.balance = balance;
        this.debt = debt;
        this.purchase = purchase;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getSavingsAccounts() {
        return savingsAccounts;
    }

    public void setSavingsAccounts(Integer savingsAccounts) {
        this.savingsAccounts = savingsAccounts;
    }

    public Integer getMonetaryAccounts() {
        return monetaryAccounts;
    }

    public void setMonetaryAccounts(Integer monetaryAccounts) {
        this.monetaryAccounts = monetaryAccounts;
    }

    public Integer getLoans() {
        return loans;
    }

    public void setLoans(Integer loans) {
        this.loans = loans;
    }

    public Integer getCreditCards() {
        return creditCards;
    }

    public void setCreditCards(Integer creditCards) {
        this.creditCards = creditCards;
    }

    public Integer getTransactionsCarriedOut() {
        return transactionsCarriedOut;
    }

    public void setTransactionsCarriedOut(Integer transactionsCarriedOut) {
        this.transactionsCarriedOut = transactionsCarriedOut;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getDebt() {
        return debt;
    }

    public void setDebt(Double debt) {
        this.debt = debt;
    }

    public Integer getPurchase() {
        return purchase;
    }

    public void setPurchase(Integer purchase) {
        this.purchase = purchase;
    }
}
