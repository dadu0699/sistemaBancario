package org.didierdominguez.bean;

public class MonetaryAccount extends Account {
    private Integer check;

    public MonetaryAccount(Integer id, String accountNo, Customer customer, String openingDate,
                           Double startingAmount, Integer check) {
        super(id, accountNo, customer, openingDate, startingAmount);
        this.check = check;
    }

    public Integer getCheck() {
        return check;
    }

    public void setCheck(Integer check) {
        this.check = check;
    }
}
