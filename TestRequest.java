package com.example;

import java.math.BigDecimal;

public class TestRequest {

    private int accountBookID;
    private String accountId;
    private BigDecimal amount;
    private String description;

    public TestRequest() {
    }

    public int getAccountBookID() {
        return accountBookID;
    }

    public void setAccountBookID(int accountBookID) {
        this.accountBookID = accountBookID;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}