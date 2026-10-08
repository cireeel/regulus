package com.example;

public class Account {

    private Integer firmId;
    private String accountId;
    private String correspondent;
    private String branch;
    private String rep;
    private String accountName;
    private String accountCat;
    private Boolean marginable;
    private Boolean aggregate;
    private String taxlotMethod;


    public Account() {
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


    public String getCorrespondent() {
        return correspondent;
    }

    public void setCorrespondent(String correspondent) {
        this.correspondent = correspondent;
    }


    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }


    public String getRep() {
        return rep;
    }

    public void setRep(String rep) {
        this.rep = rep;
    }


    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }


    public String getAccountCat() {
        return accountCat;
    }

    public void setAccountCat(String accountCat) {
        this.accountCat = accountCat;
    }


    public Boolean getMarginable() {
        return marginable;
    }

    public void setMarginable(Boolean marginable) {
        this.marginable = marginable;
    }


    public Boolean getAggregate() {
        return aggregate;
    }

    public void setAggregate(Boolean aggregate) {
        this.aggregate = aggregate;
    }


    public String getTaxlotMethod() {
        return taxlotMethod;
    }

    public void setTaxlotMethod(String taxlotMethod) {
        this.taxlotMethod = taxlotMethod;
    }
}