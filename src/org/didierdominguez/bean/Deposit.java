package org.didierdominguez.bean;

public class Deposit {
    private Integer id;
    private Object account;
    private Double amount;
    private String date;
    private Object depositType;

    public Deposit(Integer id, Object account, Double amount, String date, Object depositType) {
        this.id = id;
        this.account = account;
        this.amount = amount;
        this.date = date;
        this.depositType = depositType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Object getAccount() {
        return account;
    }

    public void setAccount(Object account) {
        this.account = account;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Object getDepositType() {
        return depositType;
    }

    public void setDepositType(Object depositType) {
        this.depositType = depositType;
    }
}
