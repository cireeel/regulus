package com.example;

import java.math.BigDecimal;
import java.util.Date;

public class Bal {

    private int firmId;
    private String accountId;
    private String correspondent;
    private String branch;
    private String rep;
    private long systemId;
    private String currency;
    private int accountType;
    private String source;

    private BigDecimal balance;
    private BigDecimal longMV;
    private BigDecimal shortMV;

    private BigDecimal balanceSD;
    private BigDecimal balanceEffective;
    private BigDecimal longMVSD;
    private BigDecimal shortMVSD;

    private BigDecimal sma;
    private BigDecimal smaCash;
    private BigDecimal smaTrade;
    private BigDecimal smaStock;

    private BigDecimal mmf;

    private int cshCount;
    private int trdCount;
    private int stkCount;

    private String secType;
    private String secId;
    private String secIdType;
    private String secDescription;

    private BigDecimal convFactor;
    private BigDecimal defCurrRate;

    private Date processDate;

    public Bal() {
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getLongMV() {
        return longMV;
    }

    public void setLongMV(BigDecimal longMV) {
        this.longMV = longMV;
    }

    public BigDecimal getShortMV() {
        return shortMV;
    }

    public void setShortMV(BigDecimal shortMV) {
        this.shortMV = shortMV;
    }

    public BigDecimal getBalanceSD() {
        return balanceSD;
    }

    public void setBalanceSD(BigDecimal balanceSD) {
        this.balanceSD = balanceSD;
    }

    public BigDecimal getBalanceEffective() {
        return balanceEffective;
    }

    public void setBalanceEffective(BigDecimal balanceEffective) {
        this.balanceEffective = balanceEffective;
    }

    public BigDecimal getLongMVSD() {
        return longMVSD;
    }

    public void setLongMVSD(BigDecimal longMVSD) {
        this.longMVSD = longMVSD;
    }

    public BigDecimal getShortMVSD() {
        return shortMVSD;
    }

    public void setShortMVSD(BigDecimal shortMVSD) {
        this.shortMVSD = shortMVSD;
    }

    public BigDecimal getSma() {
        return sma;
    }

    public void setSma(BigDecimal sma) {
        this.sma = sma;
    }

    public BigDecimal getSmaCash() {
        return smaCash;
    }

    public void setSmaCash(BigDecimal smaCash) {
        this.smaCash = smaCash;
    }

    public BigDecimal getSmaTrade() {
        return smaTrade;
    }

    public void setSmaTrade(BigDecimal smaTrade) {
        this.smaTrade = smaTrade;
    }

    public BigDecimal getSmaStock() {
        return smaStock;
    }

    public void setSmaStock(BigDecimal smaStock) {
        this.smaStock = smaStock;
    }

    public BigDecimal getMmf() {
        return mmf;
    }

    public void setMmf(BigDecimal mmf) {
        this.mmf = mmf;
    }

    public int getCshCount() {
        return cshCount;
    }

    public void setCshCount(int cshCount) {
        this.cshCount = cshCount;
    }

    public int getTrdCount() {
        return trdCount;
    }

    public void setTrdCount(int trdCount) {
        this.trdCount = trdCount;
    }

    public int getStkCount() {
        return stkCount;
    }

    public void setStkCount(int stkCount) {
        this.stkCount = stkCount;
    }

    public String getSecType() {
        return secType;
    }

    public void setSecType(String secType) {
        this.secType = secType;
    }

    public String getSecId() {
        return secId;
    }

    public void setSecId(String secId) {
        this.secId = secId;
    }

    public String getSecIdType() {
        return secIdType;
    }

    public void setSecIdType(String secIdType) {
        this.secIdType = secIdType;
    }

    public String getSecDescription() {
        return secDescription;
    }

    public void setSecDescription(String secDescription) {
        this.secDescription = secDescription;
    }

    public BigDecimal getConvFactor() {
        return convFactor;
    }

    public void setConvFactor(BigDecimal convFactor) {
        this.convFactor = convFactor;
    }

    public BigDecimal getDefCurrRate() {
        return defCurrRate;
    }

    public void setDefCurrRate(BigDecimal defCurrRate) {
        this.defCurrRate = defCurrRate;
    }

    public Date getProcessDate() {
        return processDate;
    }

    public void setProcessDate(Date processDate) {
        this.processDate = processDate;
    }
}