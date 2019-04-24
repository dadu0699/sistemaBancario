package org.didierdominguez.bean;

public class Loan {
    private Integer id;
    private String loanNo;
    private Customer customer;
    private String date;
    private Double amount;
    private Double amountPaid;
    private Boolean authorization;

    public Loan(Integer id, String loanNo, Customer customer, String date, Double amount,
                Double amountPaid, Boolean authorization) {
        this.id = id;
        this.loanNo = loanNo;
        this.customer = customer;
        this.date = date;
        this.amount = amount;
        this.amountPaid = amountPaid;
        this.authorization = authorization;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoanNo() {
        return loanNo;
    }

    public void setLoanNo(String loanNo) {
        this.loanNo = loanNo;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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
