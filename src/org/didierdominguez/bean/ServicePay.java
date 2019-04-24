package org.didierdominguez.bean;

public class ServicePay {
    private Integer id;
    private String service;
    private String type;
    private String date;
    private Customer customer;
    private Double amount;

    public ServicePay(Integer id, String service, String type, String date, Customer customer, Double amount) {
        this.id = id;
        this.service = service;
        this.type = type;
        this.date = date;
        this.customer = customer;
        this.amount = amount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
