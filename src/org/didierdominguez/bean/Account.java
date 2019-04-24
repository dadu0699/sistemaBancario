package org.didierdominguez.bean;

public class Account {
    private Integer id;
    private String accountNo;
    private Customer customer;
    private String openingDate;
    private Double startingAmount;

    public Account(Integer id, String accountNo, Customer customer, String openingDate,
                   Double startingAmount) {
        this.id = id;
        this.accountNo = accountNo;
        this.customer = customer;
        this.openingDate = openingDate;
        this.startingAmount = startingAmount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(String openingDate) {
        this.openingDate = openingDate;
    }

    public Double getStartingAmount() {
        return startingAmount;
    }

    public void setStartingAmount(Double startingAmount) {
        this.startingAmount = startingAmount;
    }
}
