package com.example;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

public class Actsum {

    private int firmId;
    private String accountId;
    private String correspondent;
    private String branch;
    private String rep;
    private long systemId;
    private String currency;
    private int accountType;
    private int cshAct;
    private int trdAct;
    private int stkAct;

    private BigDecimal cashBal;
    private BigDecimal cashMV;
    private BigDecimal cashEq;

    private BigDecimal marginBal;
	private BigDecimal marginLMV;
    private BigDecimal marginSMV;
    private BigDecimal marginEq;
	private BigDecimal otherBal;
	private BigDecimal otherMV;
	private BigDecimal otherEq;
    private BigDecimal foreignBal;
	private BigDecimal foreignMV;
	private BigDecimal foreignEq;
    private BigDecimal totalEquity;
    private BigDecimal marginSecT1;
    private BigDecimal cashAvailT1;
    private BigDecimal mmktBal;

    private BigDecimal creditAmount;
    private BigDecimal debitAmount;
    private BigDecimal checkAmount;
    private BigDecimal achAmount;
    private BigDecimal wireAmount;
    private BigDecimal intAmount;
    private BigDecimal divAmount;

    private Date processDate;
	
	    public Actsum() {
    }

    public int getFirmId() {
        return firmId;
    }

    public void setFirmId(int firmId) {
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

    public long getSystemId() {
        return systemId;
    }

    public void setSystemId(long systemId) {
        this.systemId = systemId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public int getCshAct() {
        return cshAct;
    }

    public void setCshAct(int cshAct) {
        this.cshAct = cshAct;
    }

    public int getTrdAct() {
        return trdAct;
    }

    public void setTrdAct(int trdAct) {
        this.trdAct = trdAct;
    }

    public int getStkAct() {
        return stkAct;
    }

    public void setStkAct(int stkAct) {
        this.stkAct = stkAct;
    }

    public BigDecimal getCashBal() {
        return cashBal;
    }

    public void setCashBal(BigDecimal cashBal) {
        this.cashBal = cashBal;
    }

    public BigDecimal getCashMV() {
        return cashMV;
    }

    public void setCashMV(BigDecimal cashMV) {
        this.cashMV = cashMV;
    }

    public BigDecimal getCashEq() {
        return cashEq;
    }

    public void setCashEq(BigDecimal cashEq) {
        this.cashEq = cashEq;
    }

    public BigDecimal getMarginBal() {
        return marginBal;
    }

    public void setMarginBal(BigDecimal marginBal) {
        this.marginBal = marginBal;
    }

    public BigDecimal getMarginLMV() {
        return marginLMV;
    }

    public void setMarginLMV(BigDecimal marginLMV) {
        this.marginLMV = marginLMV;
    }

    public BigDecimal getMarginSMV() {
        return marginSMV;
    }

    public void setMarginSMV(BigDecimal marginSMV) {
        this.marginSMV = marginSMV;
    }

    public BigDecimal getMarginEq() {
        return marginEq;
    }

    public void setMarginEq(BigDecimal marginEq) {
        this.marginEq = marginEq;
    }

    public BigDecimal getOtherBal() {
        return otherBal;
    }

    public void setOtherBal(BigDecimal otherBal) {
        this.otherBal = otherBal;
    }

    public BigDecimal getOtherMV() {
        return otherMV;
    }

    public void setOtherMV(BigDecimal otherMV) {
        this.otherMV = otherMV;
    }

    public BigDecimal getOtherEq() {
        return otherEq;
    }

    public void setOtherEq(BigDecimal otherEq) {
        this.otherEq = otherEq;
    }

    public BigDecimal getForeignBal() {
        return foreignBal;
    }

    public void setForeignBal(BigDecimal foreignBal) {
        this.foreignBal = foreignBal;
    }

    public BigDecimal getForeignMV() {
        return foreignMV;
    }

    public void setForeignMV(BigDecimal foreignMV) {
        this.foreignMV = foreignMV;
    }

    public BigDecimal getForeignEq() {
        return foreignEq;
    }

    public void setForeignEq(BigDecimal foreignEq) {
        this.foreignEq = foreignEq;
    }

    public BigDecimal getTotalEquity() {
        return totalEquity;
    }

    public void setTotalEquity(BigDecimal totalEquity) {
        this.totalEquity = totalEquity;
    }

    public BigDecimal getMarginSecT1() {
        return marginSecT1;
    }

    public void setMarginSecT1(BigDecimal marginSecT1) {
        this.marginSecT1 = marginSecT1;
    }

    public BigDecimal getCashAvailT1() {
        return cashAvailT1;
    }

    public void setCashAvailT1(BigDecimal cashAvailT1) {
        this.cashAvailT1 = cashAvailT1;
    }

    public BigDecimal getMmktBal() {
        return mmktBal;
    }

    public void setMmktBal(BigDecimal mmktBal) {
        this.mmktBal = mmktBal;
    }

    public BigDecimal getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(BigDecimal creditAmount) {
        this.creditAmount = creditAmount;
    }

    public BigDecimal getDebitAmount() {
        return debitAmount;
    }

    public void setDebitAmount(BigDecimal debitAmount) {
        this.debitAmount = debitAmount;
    }

    public BigDecimal getCheckAmount() {
        return checkAmount;
    }

    public void setCheckAmount(BigDecimal checkAmount) {
        this.checkAmount = checkAmount;
    }

    public BigDecimal getAchAmount() {
        return achAmount;
    }

    public void setAchAmount(BigDecimal achAmount) {
        this.achAmount = achAmount;
    }

    public BigDecimal getWireAmount() {
        return wireAmount;
    }

    public void setWireAmount(BigDecimal wireAmount) {
        this.wireAmount = wireAmount;
    }

    public BigDecimal getIntAmount() {
        return intAmount;
    }

    public void setIntAmount(BigDecimal intAmount) {
        this.intAmount = intAmount;
    }

    public BigDecimal getDivAmount() {
        return divAmount;
    }

    public void setDivAmount(BigDecimal divAmount) {
        this.divAmount = divAmount;
    }

    public Date getProcessDate() {
        return processDate;
    }

    public void setProcessDate(Date processDate) {
        this.processDate = processDate;
    }
}