package com.example;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.time.LocalDate;

import java.util.List;

@Repository
public class ActsumRepository {

    private final JdbcTemplate jdbcTemplate;

    public ActsumRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /*
     * Get all actsum/accounts
     */
    public List<Actsum> findAll() {

        String sql =
                "SELECT " +
                "firmId, accountId, correspondent, branch, rep, systemId, " +
                "currency, accountType, cshAct, trdAct, stkAct, cashBal, cashMV, cashEq, " +
                "marginBal, marginLMV, marginSMV, marginEq, otherBal, otherMV, otherEq, foreignBal, foreignMV, foreignEq, totalEquity, " +
                "marginSecT1, cashAvailT1, mmktBal, creditAmount, debitAmount, " +
                "checkAmount, achAmount, wireAmount, intAmount, divAmount, " +
                "processDate " +
                "FROM Actsum";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {

            Actsum actsum = new Actsum();

            actsum.setFirmId(rs.getInt("firmId"));
            actsum.setAccountId(rs.getString("accountId"));
            actsum.setCorrespondent(rs.getString("correspondent"));
            actsum.setBranch(rs.getString("branch"));
            actsum.setRep(rs.getString("rep"));
            actsum.setSystemId(rs.getLong("systemId"));
            actsum.setCurrency(rs.getString("currency"));
			actsum.setAccountType(rs.getInt("accountType"));
            actsum.setCshAct(rs.getInt("cshAct"));
            actsum.setTrdAct(rs.getInt("trdAct"));
			actsum.setStkAct(rs.getInt("stkAct"));
            actsum.setCashBal(rs.getBigDecimal("cashBal"));
            actsum.setCashMV(rs.getBigDecimal("cashMV"));
            actsum.setCashEq(rs.getBigDecimal("cashEq"));

            actsum.setMarginBal(rs.getBigDecimal("marginBal"));
			actsum.setMarginLMV(rs.getBigDecimal("marginLMV"));
            actsum.setMarginSMV(rs.getBigDecimal("marginSMV"));
            actsum.setMarginEq(rs.getBigDecimal("marginEq"));
			actsum.setOtherBal(rs.getBigDecimal("otherBal"));
			actsum.setOtherMV(rs.getBigDecimal("otherMV"));
			actsum.setOtherEq(rs.getBigDecimal("otherEq"));
            actsum.setForeignBal(rs.getBigDecimal("foreignBal"));
			actsum.setForeignMV(rs.getBigDecimal("foreignMV"));
			actsum.setForeignEq(rs.getBigDecimal("foreignEq"));
            actsum.setTotalEquity(rs.getBigDecimal("totalEquity"));

            actsum.setMarginSecT1(rs.getBigDecimal("marginSecT1"));
            actsum.setCashAvailT1(rs.getBigDecimal("cashAvailT1"));

            actsum.setMmktBal(rs.getBigDecimal("mmktBal"));

            actsum.setCreditAmount(rs.getBigDecimal("creditAmount"));
            actsum.setDebitAmount(rs.getBigDecimal("debitAmount"));
            actsum.setCheckAmount(rs.getBigDecimal("checkAmount"));
            actsum.setAchAmount(rs.getBigDecimal("achAmount"));
            actsum.setWireAmount(rs.getBigDecimal("wireAmount"));
            actsum.setIntAmount(rs.getBigDecimal("intAmount"));
            actsum.setDivAmount(rs.getBigDecimal("divAmount"));

            actsum.setProcessDate(rs.getDate("processDate"));

            return actsum;
        });
    }

    /*
     * Find one account
     */
    public Actsum findByAccountId(String accountId) {

        String sql =
                "SELECT " +
                "firmId, accountId, correspondent, branch, rep, systemId, " +
                "currency, accountType, cshAct, trdAct, stkAct, cashBal, cashMV, cashEq, " +
                "marginBal, marginLMV, marginSMV, marginEq, otherBal, otherMV, otherEq, foreignBal, foreignMV, foreignEq, totalEquity, " +
                "marginSecT1, cashAvailT1, mmktBal, creditAmount, debitAmount, " +
                "checkAmount, achAmount, wireAmount, intAmount, divAmount, " +
                "processDate " +
                "FROM actsum " +
                "WHERE accountId = ?";

        return jdbcTemplate.queryForObject(
                sql,
                (rs, rowNum) -> {

                    Actsum actsum = new Actsum();

                    actsum.setFirmId(rs.getInt("firmId"));
                    actsum.setAccountId(rs.getString("accountId"));
                    actsum.setCorrespondent(rs.getString("correspondent"));
                    actsum.setBranch(rs.getString("branch"));
                    actsum.setRep(rs.getString("rep"));
                    actsum.setSystemId(rs.getLong("systemId"));
                    actsum.setCurrency(rs.getString("currency"));
					actsum.setAccountType(rs.getInt("accountType"));
                    actsum.setCshAct(rs.getInt("cshAct"));
                    actsum.setTrdAct(rs.getInt("trdAct"));
					actsum.setStkAct(rs.getInt("stkAct"));
                    actsum.setCashBal(rs.getBigDecimal("cashBal"));
                    actsum.setCashMV(rs.getBigDecimal("cashMV"));
                    actsum.setCashEq(rs.getBigDecimal("cashEq"));

                    actsum.setMarginBal(rs.getBigDecimal("marginBal"));
					actsum.setMarginLMV(rs.getBigDecimal("marginLMV"));
                    actsum.setMarginSMV(rs.getBigDecimal("marginSMV"));
                    actsum.setMarginEq(rs.getBigDecimal("marginEq"));
					actsum.setOtherBal(rs.getBigDecimal("otherBal"));
					actsum.setOtherMV(rs.getBigDecimal("otherMV"));
					actsum.setOtherEq(rs.getBigDecimal("otherEq"));
                    actsum.setForeignBal(rs.getBigDecimal("foreignBal"));
					actsum.setForeignMV(rs.getBigDecimal("foreignMV"));
					actsum.setForeignEq(rs.getBigDecimal("foreignEq"));
                    actsum.setTotalEquity(rs.getBigDecimal("totalEquity"));

                    actsum.setMarginSecT1(rs.getBigDecimal("marginSecT1"));
                    actsum.setCashAvailT1(rs.getBigDecimal("cashAvailT1"));

                    actsum.setMmktBal(rs.getBigDecimal("mmktBal"));

                    actsum.setCreditAmount(rs.getBigDecimal("creditAmount"));
                    actsum.setDebitAmount(rs.getBigDecimal("debitAmount"));
                    actsum.setCheckAmount(rs.getBigDecimal("checkAmount"));
                    actsum.setAchAmount(rs.getBigDecimal("achAmount"));
                    actsum.setWireAmount(rs.getBigDecimal("wireAmount"));
                    actsum.setIntAmount(rs.getBigDecimal("intAmount"));
                    actsum.setDivAmount(rs.getBigDecimal("divAmount"));

                    actsum.setProcessDate(rs.getDate("processDate"));

                    return actsum;
                },
                accountId
        );
    }
}