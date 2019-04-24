package org.didierdominguez.bean;

public class LoanPayment {
    private Integer id;
    private Loan loan;
    private String type;
    private String date;
    private Double amount;

    public LoanPayment(Integer id, Loan loan, String type, String date, Double amount) {
        this.id = id;
        this.loan = loan;
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

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
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
