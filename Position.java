package com.example;

import java.math.BigDecimal;

public class Position {

    private String securityId;
    private String securityType;
    private String description;

    private BigDecimal quantity;
    private BigDecimal marketPrice;
    private BigDecimal marketValue;

    public Position() {
        quantity = BigDecimal.ZERO;
        marketPrice = BigDecimal.ZERO;
        marketValue = BigDecimal.ZERO;
    }

    public Position(
            String securityId,
            String securityType,
            String description) {

        this();

        this.securityId = securityId;
        this.securityType = securityType;
        this.description = description;
    }

    public void recalculate() {

        if (quantity == null) {
            quantity = BigDecimal.ZERO;
        }

        if (marketPrice == null) {
            marketPrice = BigDecimal.ZERO;
        }

        marketValue =
            quantity.multiply(marketPrice);
    }

    public String getSecurityId() {
        return securityId;
    }

    public void setSecurityId(String securityId) {
        this.securityId = securityId;
    }

    public String getSecurityType() {
        return securityType;
    }

    public void setSecurityType(String securityType) {
        this.securityType = securityType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
        recalculate();
    }

    public BigDecimal getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(BigDecimal marketValue) {
        this.marketValue = marketValue;
    }
}