package com.example;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AccountAsset {

    private Integer firmId;
    private String accountId;
    private Date processDate;

    private BigDecimal cashBalance;

    private List<Cash> cashes;
    private List<Trade> trades;
    private List<Stock> stocks;

    private Map<String, Position> positions;

    @JsonIgnore
    private transient AccountBookRepository repository;

    @JsonIgnore
    private static final ObjectMapper mapper =
        new ObjectMapper();

    public AccountAsset() {

        cashBalance = BigDecimal.ZERO;

        cashes = new ArrayList<Cash>();
        trades = new ArrayList<Trade>();
        stocks = new ArrayList<Stock>();

        positions =
            new LinkedHashMap<String, Position>();

        processDate =
            new Date(
                System.currentTimeMillis()
            );
    }

    public AccountAsset(
            AccountBookRepository repository) {

        this();
        this.repository = repository;
    }

    public void setRepository(
            AccountBookRepository repository) {

        this.repository = repository;
    }

    // =========================================================
    // LOAD BOOK
    // =========================================================

    public void loadBookSQL(
            Integer firmId,
            String accountId,
            String processDate)
            throws Exception {

        checkRepository();

        validateAccount(
            firmId,
            accountId
        );

        AccountBook book;

        if (processDate == null ||
            processDate.trim().isEmpty()) {

            book =
                repository.findLatest(
                    firmId,
                    accountId
                );

        } else {

            Date date =
                Date.valueOf(processDate);

            book =
                repository.findByDate(
                    firmId,
                    accountId,
                    date
                );
        }

        // No previous book.
        // Start a new book.
        if (book == null) {

            this.firmId = firmId;
            this.accountId = accountId;

            if (processDate != null &&
                !processDate.trim().isEmpty()) {

                this.processDate =
                    Date.valueOf(processDate);
            }

            return;
        }

        if (book.getBookJson() == null ||
            book.getBookJson().trim().isEmpty()) {

            throw new Exception(
                "Account book JSON is empty"
            );
        }

        AccountAsset loaded =
            mapper.readValue(
                book.getBookJson(),
                AccountAsset.class
            );

        copyFrom(loaded);

        this.repository = repository;
    }

    // =========================================================
    // SAVE BOOK
    // =========================================================

    public void saveBookSQL()
            throws Exception {

        checkRepository();

        validateAccount(
            firmId,
            accountId
        );

        if (processDate == null) {

            processDate =
                new Date(
                    System.currentTimeMillis()
                );
        }

        String json =
            mapper.writeValueAsString(this);

        repository.save(
            firmId,
            accountId,
            processDate,
            json
        );
    }

    // =========================================================
    // CASH
    // =========================================================

    public void addCash(Cash cash)
            throws Exception {

        if (cash == null) {
            throw new Exception(
                "Cash transaction is null"
            );
        }

        validateTransactionAccount(
            cash.getFirmId(),
            cash.getAccountId()
        );

        BigDecimal amount =
            zero(cash.getAmount());

        String type =
            upper(
                cash.getTransactionType()
            );

        if ("WITHDRAWAL".equals(type) ||
            "WITHDRAW".equals(type) ||
            "DEBIT".equals(type)) {

            cashBalance =
                zero(cashBalance)
                    .subtract(amount);

        } else {

            cashBalance =
                zero(cashBalance)
                    .add(amount);
        }

        cashes.add(cash);
    }

    // =========================================================
    // TRADE
    // =========================================================

    public void addTrade(Trade trade)
            throws Exception {

        if (trade == null) {
            throw new Exception(
                "Trade is null"
            );
        }

        validateTransactionAccount(
            trade.getFirmId(),
            trade.getAccountId()
        );

        if (isBlank(
                trade.getSecurityId())) {

            throw new Exception(
                "Trade securityId is required"
            );
        }

        BigDecimal quantity =
            zero(trade.getQuantity());

        BigDecimal price =
            zero(trade.getPrice());

        BigDecimal amount =
            trade.getAmount();

        if (amount == null) {

            amount =
                quantity.multiply(price);

            trade.setAmount(amount);
        }

        Position position =
            getOrCreatePosition(
                trade.getSecurityId(),
                trade.getSecurityType(),
                trade.getDescription()
            );

        String side =
            upper(trade.getBuySell());

        if ("BUY".equals(side) ||
            "B".equals(side)) {

            position.setQuantity(
                zero(position.getQuantity())
                    .add(quantity)
            );

            cashBalance =
                zero(cashBalance)
                    .subtract(amount);

        } else if (
            "SELL".equals(side) ||
            "S".equals(side)) {

            position.setQuantity(
                zero(position.getQuantity())
                    .subtract(quantity)
            );

            cashBalance =
                zero(cashBalance)
                    .add(amount);

        } else {

            throw new Exception(
                "buySell must be BUY/B or SELL/S"
            );
        }

        position.setMarketPrice(price);
        position.recalculate();

        trades.add(trade);
    }

    // =========================================================
    // STOCK MOVEMENT
    // =========================================================

    public void addStock(Stock stock)
            throws Exception {

        if (stock == null) {
            throw new Exception(
                "Stock transaction is null"
            );
        }

        validateTransactionAccount(
            stock.getFirmId(),
            stock.getAccountId()
        );

        if (isBlank(
                stock.getSecurityId())) {

            throw new Exception(
                "Stock securityId is required"
            );
        }

        BigDecimal quantity =
            zero(stock.getQuantity());

        Position position =
            getOrCreatePosition(
                stock.getSecurityId(),
                stock.getSecurityType(),
                stock.getDescription()
            );

        String type =
            upper(
                stock.getTransactionType()
            );

        if ("RECEIVE".equals(type) ||
            "RECEIPT".equals(type) ||
            "IN".equals(type)) {

            position.setQuantity(
                zero(position.getQuantity())
                    .add(quantity)
            );

        } else if (
            "DELIVER".equals(type) ||
            "DELIVERY".equals(type) ||
            "OUT".equals(type)) {

            position.setQuantity(
                zero(position.getQuantity())
                    .subtract(quantity)
            );

        } else {

            throw new Exception(
                "transactionType must be " +
                "RECEIVE/IN or DELIVER/OUT"
            );
        }

        if (stock.getPrice() != null) {
            position.setMarketPrice(
                stock.getPrice()
            );
        }

        position.recalculate();

        stocks.add(stock);
    }

    // =========================================================
    // POSITION
    // =========================================================

    private Position getOrCreatePosition(
            String securityId,
            String securityType,
            String description) {

        String key =
            securityId.trim().toUpperCase();

        Position position =
            positions.get(key);

        if (position == null) {

            position =
                new Position(
                    securityId,
                    securityType,
                    description
                );

            positions.put(
                key,
                position
            );
        }

        return position;
    }

    // =========================================================
    // COPY LOADED BOOK
    // =========================================================

    private void copyFrom(
            AccountAsset source) {

        this.firmId =
            source.firmId;

        this.accountId =
            source.accountId;

        this.processDate =
            source.processDate;

        this.cashBalance =
            zero(source.cashBalance);

        this.cashes =
            source.cashes != null
                ? source.cashes
                : new ArrayList<Cash>();

        this.trades =
            source.trades != null
                ? source.trades
                : new ArrayList<Trade>();

        this.stocks =
            source.stocks != null
                ? source.stocks
                : new ArrayList<Stock>();

        this.positions =
            source.positions != null
                ? source.positions
                : new LinkedHashMap<String, Position>();
    }

    // =========================================================
    // VALIDATION
    // =========================================================

    private void validateTransactionAccount(
            Integer transactionFirmId,
            String transactionAccountId)
            throws Exception {

        validateAccount(
            transactionFirmId,
            transactionAccountId
        );

        if (firmId == null) {
            firmId = transactionFirmId;
        }

        if (accountId == null) {
            accountId = transactionAccountId;
        }

        if (!firmId.equals(
                transactionFirmId)) {

            throw new Exception(
                "Transaction firmId does not match book"
            );
        }

        if (!accountId.trim().equals(
                transactionAccountId.trim())) {

            throw new Exception(
                "Transaction accountId does not match book"
            );
        }
    }

    private void validateAccount(
            Integer firmId,
            String accountId)
            throws Exception {

        if (firmId == null) {

            throw new Exception(
                "firmId is required"
            );
        }

        if (isBlank(accountId)) {

            throw new Exception(
                "accountId is required"
            );
        }
    }

    private void checkRepository()
            throws Exception {

        if (repository == null) {

            throw new Exception(
                "AccountBookRepository is not initialized"
            );
        }
    }

    private BigDecimal zero(
            BigDecimal value) {

        return value == null
            ? BigDecimal.ZERO
            : value;
    }

    private String upper(
            String value) {

        if (value == null) {
            return "";
        }

        return value
            .trim()
            .toUpperCase();
    }

    private boolean isBlank(
            String value) {

        return value == null ||
               value.trim().isEmpty();
    }

    // =========================================================
    // GETTERS / SETTERS
    // =========================================================

    public Integer getFirmId() {
        return firmId;
    }

    public void setFirmId(Integer firmId) {
        this.firmId = firmId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(
            String accountId) {
        this.accountId = accountId;
    }

    public Date getProcessDate() {
        return processDate;
    }

    public void setProcessDate(
            Date processDate) {
        this.processDate = processDate;
    }

    public BigDecimal getCashBalance() {
        return cashBalance;
    }

    public void setCashBalance(
            BigDecimal cashBalance) {
        this.cashBalance = cashBalance;
    }

    public List<Cash> getCashes() {
        return cashes;
    }

    public void setCashes(
            List<Cash> cashes) {
        this.cashes = cashes;
    }

    public List<Trade> getTrades() {
        return trades;
    }

    public void setTrades(
            List<Trade> trades) {
        this.trades = trades;
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(
            List<Stock> stocks) {
        this.stocks = stocks;
    }

    public Map<String, Position> getPositions() {
        return positions;
    }

    public void setPositions(
            Map<String, Position> positions) {
        this.positions = positions;
    }
}