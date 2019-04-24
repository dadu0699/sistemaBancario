package org.didierdominguez.bean;

public class AutoBank extends BankingAgency {
    private Integer autobankingBoxes;

    public AutoBank(Integer id, String name, String address, String phoneNumber, Integer noPayOffice,
                    Integer noCustomerService, Double cash, Integer employees, Integer autobankingBoxes) {
        super(id, name, address, phoneNumber, noPayOffice, noCustomerService, cash, employees);
        this.autobankingBoxes = autobankingBoxes;
    }

    public Integer getAutobankingBoxes() {
        return autobankingBoxes;
    }

    public void setAutobankingBoxes(Integer autobankingBoxes) {
        this.autobankingBoxes = autobankingBoxes;
    }
}
