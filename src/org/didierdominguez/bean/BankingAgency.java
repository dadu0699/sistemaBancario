package org.didierdominguez.bean;

public class BankingAgency {
    private Integer id;
    private String name;
    private String address;
    private String phoneNumber;
    private Integer noPayOffice;
    private Integer noCustomerService;
    private Double cash;
    private Integer employees;

    public BankingAgency(Integer id, String name, String address, String phoneNumber, Integer noPayOffice,
            Integer noCustomerService, Double cash , Integer employees) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.noPayOffice = noPayOffice;
        this.noCustomerService = noCustomerService;
        this.cash = cash;
        this.employees = employees;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getNoPayOffice() {
        return noPayOffice;
    }

    public void setNoPayOffice(Integer noPayOffice) {
        this.noPayOffice = noPayOffice;
    }

    public Integer getNoCustomerService() {
        return noCustomerService;
    }

    public void setNoCustomerService(Integer noCustomerService) {
        this.noCustomerService = noCustomerService;
    }

    public Double getCash() {
        return cash;
    }

    public void setCash(Double cash) {
        this.cash = cash;
    }

    public Integer getEmployees() {
        return employees;
    }

    public void setEmployees(Integer employees) {
        this.employees = employees;
    }

    @Override
    public String toString(){
        return this.name;
    }
}
