package org.didierdominguez.bean;

public class CreditCardPayment {
    private Integer id;
    private CreditCard creditCard;
    private String type;
    private String date;
    private Double amount;

    public CreditCardPayment(Integer id, CreditCard creditCard, String type, String date, Double amount) {
        this.id = id;
        this.creditCard = creditCard;
        this.type = type;
        this.date = date;
        this.amount = amount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
