package org.didierdominguez.bean;

public class CreditCard {
    private Integer id;
    private String cardNo;
    private Customer customer;
    private String expirationDate;
    private Double creditLimit;
    private Double amountOwed;
    private Double amountPaid;
    private Boolean authorization;

    public CreditCard(Integer id, String cardNo, Customer customer, String expirationDate,
                      Double creditLimit, Double amountOwed, Double amountPaid, Boolean authorization) {
        this.id = id;
        this.cardNo = cardNo;
        this.customer = customer;
        this.expirationDate = expirationDate;
        this.creditLimit = creditLimit;
        this.amountOwed = amountOwed;
        this.amountPaid = amountPaid;
        this.authorization = authorization;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Double getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(Double creditLimit) {
        this.creditLimit = creditLimit;
    }

    public Double getAmountOwed() {
        return amountOwed;
    }

    public void setAmountOwed(Double amountOwed) {
        this.amountOwed = amountOwed;
    }

    public Double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(Double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public Boolean getAuthorization() {
        return authorization;
    }

    public void setAuthorization(Boolean authorization) {
        this.authorization = authorization;
    }
}
