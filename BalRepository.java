package com.example;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BalRepository {

    private final JdbcTemplate jdbcTemplate;

    public BalRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /*
     * Get all bal records
     */
    public List<Bal> findAll() {

        String sql =
                "SELECT " +
                "firmId, accountId, correspondent, branch, rep, systemId, " +
                "currency, accountType, source, balance, longMV, shortMV, " +
                "balanceSD, balanceEffective, longMVSD, shortMVSD, " +
                "sma, smaCash, smaTrade, smaStock, mmf, " +
                "cshCount, trdCount, stkCount, " +
                "secType, secId, secIdType, secDescription, " +
                "convFactor, defCurrRate, processDate " +
                "FROM bal";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {

            Bal bal = new Bal();

            bal.setFirmId(rs.getInt("firmId"));
            bal.setAccountId(rs.getString("accountId"));
            bal.setCorrespondent(rs.getString("correspondent"));
            bal.setBranch(rs.getString("branch"));
            bal.setRep(rs.getString("rep"));
            bal.setSystemId(rs.getLong("systemId"));
            bal.setCurrency(rs.getString("currency"));
            bal.setAccountType(rs.getInt("accountType"));
            bal.setSource(rs.getString("source"));

            bal.setBalance(rs.getBigDecimal("balance"));
            bal.setLongMV(rs.getBigDecimal("longMV"));
            bal.setShortMV(rs.getBigDecimal("shortMV"));

            bal.setBalanceSD(rs.getBigDecimal("balanceSD"));
            bal.setBalanceEffective(rs.getBigDecimal("balanceEffective"));
            bal.setLongMVSD(rs.getBigDecimal("longMVSD"));
            bal.setShortMVSD(rs.getBigDecimal("shortMVSD"));

            bal.setSma(rs.getBigDecimal("sma"));
            bal.setSmaCash(rs.getBigDecimal("smaCash"));
            bal.setSmaTrade(rs.getBigDecimal("smaTrade"));
            bal.setSmaStock(rs.getBigDecimal("smaStock"));

            bal.setMmf(rs.getBigDecimal("mmf"));

            bal.setCshCount(rs.getInt("cshCount"));
            bal.setTrdCount(rs.getInt("trdCount"));
            bal.setStkCount(rs.getInt("stkCount"));

            bal.setSecType(rs.getString("secType"));
            bal.setSecId(rs.getString("secId"));
            bal.setSecIdType(rs.getString("secIdType"));
            bal.setSecDescription(rs.getString("secDescription"));

            bal.setConvFactor(rs.getBigDecimal("convFactor"));
            bal.setDefCurrRate(rs.getBigDecimal("defCurrRate"));

            bal.setProcessDate(rs.getDate("processDate"));

            return bal;
        });
    }

    /*
     * Find bal records by account ID
     *
     * Unlike Actsum, an account can have multiple bal records,
     * so this returns List<Bal>.
     */
    public List<Bal> findByAccountId(String accountId) {

        String sql =
                "SELECT " +
                "firmId, accountId, correspondent, branch, rep, systemId, " +
                "currency, accountType, source, balance, longMV, shortMV, " +
                "balanceSD, balanceEffective, longMVSD, shortMVSD, " +
                "sma, smaCash, smaTrade, smaStock, mmf, " +
                "cshCount, trdCount, stkCount, " +
                "secType, secId, secIdType, secDescription, " +
                "convFactor, defCurrRate, processDate " +
                "FROM bal " +
                "WHERE accountId = ?";

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> {

                    Bal bal = new Bal();

                    bal.setFirmId(rs.getInt("firmId"));
                    bal.setAccountId(rs.getString("accountId"));
                    bal.setCorrespondent(rs.getString("correspondent"));
                    bal.setBranch(rs.getString("branch"));
                    bal.setRep(rs.getString("rep"));
                    bal.setSystemId(rs.getLong("systemId"));
                    bal.setCurrency(rs.getString("currency"));
                    bal.setAccountType(rs.getInt("accountType"));
                    bal.setSource(rs.getString("source"));

                    bal.setBalance(rs.getBigDecimal("balance"));
                    bal.setLongMV(rs.getBigDecimal("longMV"));
                    bal.setShortMV(rs.getBigDecimal("shortMV"));

                    bal.setBalanceSD(rs.getBigDecimal("balanceSD"));
                    bal.setBalanceEffective(rs.getBigDecimal("balanceEffective"));
                    bal.setLongMVSD(rs.getBigDecimal("longMVSD"));
                    bal.setShortMVSD(rs.getBigDecimal("shortMVSD"));

                    bal.setSma(rs.getBigDecimal("sma"));
                    bal.setSmaCash(rs.getBigDecimal("smaCash"));
                    bal.setSmaTrade(rs.getBigDecimal("smaTrade"));
                    bal.setSmaStock(rs.getBigDecimal("smaStock"));

                    bal.setMmf(rs.getBigDecimal("mmf"));

                    bal.setCshCount(rs.getInt("cshCount"));
                    bal.setTrdCount(rs.getInt("trdCount"));
                    bal.setStkCount(rs.getInt("stkCount"));

                    bal.setSecType(rs.getString("secType"));
                    bal.setSecId(rs.getString("secId"));
                    bal.setSecIdType(rs.getString("secIdType"));
                    bal.setSecDescription(rs.getString("secDescription"));

                    bal.setConvFactor(rs.getBigDecimal("convFactor"));
                    bal.setDefCurrRate(rs.getBigDecimal("defCurrRate"));

                    bal.setProcessDate(rs.getDate("processDate"));

                    return bal;
                },
                accountId
        );
    }
}