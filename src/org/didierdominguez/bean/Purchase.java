package org.didierdominguez.bean;

public class Purchase {
    private Integer id;
    private Customer customer;
    private CreditCard creditCard;
    private Double amount;

    public Purchase(Integer id, Customer customer, CreditCard creditCard) {
        this.id = id;
        this.customer = customer;
        this.creditCard = creditCard;
    }

    public Purchase(Integer id, Customer customer, CreditCard creditCard, Double amount) {
        this.id = id;
        this.customer = customer;
        this.creditCard = creditCard;
        this.amount = amount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
