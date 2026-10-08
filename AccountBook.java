package com.example;

import java.sql.Date;

public class AccountBook {

    private Integer accountBookID;
    private Integer firmId;
    private String accountId;
    private Date processDate;
    private String bookJson;

    public AccountBook() {
    }

    public Integer getAccountBookID() {
        return accountBookID;
    }

    public void setAccountBookID(Integer accountBookID) {
        this.accountBookID = accountBookID;
    }

    public Integer getFirmId() {
        return firmId;
    }

    public void setFirmId(Integer firmId) {
        this.firmId = firmId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Date getProcessDate() {
        return processDate;
    }

    public void setProcessDate(Date processDate) {
        this.processDate = processDate;
    }

    public String getBookJson() {
        return bookJson;
    }

    public void setBookJson(String bookJson) {
        this.bookJson = bookJson;
    }
}