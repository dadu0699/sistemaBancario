package org.didierdominguez.bean;

public class ATM {
    private Integer id;
    private String location;
    private Double cash;
    private Boolean state;
    private Integer transactions;

    public ATM(Integer id, String location, Double cash, Boolean state, Integer transactions) {
        this.id = id;
        this.location = location;
        this.cash = cash;
        this.state = state;
        this.transactions = transactions;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getCash() {
        return cash;
    }

    public void setCash(Double cash) {
        this.cash = cash;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public Integer getTransactions() {
        return transactions;
    }

    public void setTransactions(Integer transactions) {
        this.transactions = transactions;
    }

    @Override
    public String toString(){
        return this.location;
    }
}
