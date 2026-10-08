package com.example;

import java.sql.Date;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AccountBookRepository {

    private final JdbcTemplate jdbcTemplate;

    public AccountBookRepository(
            JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }

    public AccountBook findLatest(
            Integer firmId,
            String accountId) {

        String sql =
            "SELECT TOP 1 " +
            "accountBookID, firmId, accountId, " +
            "processDate, bookJson " +
            "FROM dbo.accountBook " +
            "WHERE firmId = ? " +
            "AND accountId = ? " +
            "ORDER BY processDate DESC, " +
            "accountBookID DESC";

        List<AccountBook> list =
            jdbcTemplate.query(
                sql,
                (rs, rowNum) -> map(rs),
                firmId,
                accountId
            );

        if (list.isEmpty()) {
            return null;
        }

        return list.get(0);
    }

    public AccountBook findByDate(
            Integer firmId,
            String accountId,
            Date processDate) {

        String sql =
            "SELECT TOP 1 " +
            "accountBookID, firmId, accountId, " +
            "processDate, bookJson " +
            "FROM dbo.accountBook " +
            "WHERE firmId = ? " +
            "AND accountId = ? " +
            "AND processDate = ? " +
            "ORDER BY accountBookID DESC";

        List<AccountBook> list =
            jdbcTemplate.query(
                sql,
                (rs, rowNum) -> map(rs),
                firmId,
                accountId,
                processDate
            );

        if (list.isEmpty()) {
            return null;
        }

        return list.get(0);
    }

    public void save(
            Integer firmId,
            String accountId,
            Date processDate,
            String bookJson) {

        String updateSql =
            "UPDATE dbo.accountBook " +
            "SET bookJson = ? " +
            "WHERE firmId = ? " +
            "AND accountId = ? " +
            "AND processDate = ?";

        int rows =
            jdbcTemplate.update(
                updateSql,
                bookJson,
                firmId,
                accountId,
                processDate
            );

        if (rows == 0) {

            String insertSql =
                "INSERT INTO dbo.accountBook " +
                "(firmId, accountId, processDate, bookJson) " +
                "VALUES (?, ?, ?, ?)";

            jdbcTemplate.update(
                insertSql,
                firmId,
                accountId,
                processDate,
                bookJson
            );
        }
    }

    private AccountBook map(
            java.sql.ResultSet rs)
            throws java.sql.SQLException {

        AccountBook b =
            new AccountBook();

        b.setAccountBookID(
            rs.getInt("accountBookID")
        );

        b.setFirmId(
            (Integer)
            rs.getObject("firmId")
        );

        String accountId =
            rs.getString("accountId");

        if (accountId != null) {
            accountId = accountId.trim();
        }

        b.setAccountId(accountId);

        b.setProcessDate(
            rs.getDate("processDate")
        );

        b.setBookJson(
            rs.getString("bookJson")
        );

        return b;
    }
}