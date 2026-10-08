package com.example;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class AccountRepository {

    private final JdbcTemplate jdbcTemplate;


    public AccountRepository(
            JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }


    // =========================================================
    // DATABASE TEST
    // =========================================================

    public String getDatabaseName() {

        return jdbcTemplate.queryForObject(
            "SELECT DB_NAME()",
            String.class
        );
    }


    // =========================================================
    // GET ACCOUNTS FOR FIRM
    // =========================================================

    public List<AccountSummary> findByFirmId(
            Integer firmId) {

        String sql =
            "SELECT accountId, accountName " +
            "FROM dbo.accounts " +
            "WHERE firmId = ? " +
            "ORDER BY accountId";


        return jdbcTemplate.query(
            sql,
            (rs, rowNum) ->
                new AccountSummary(
                    trim(rs.getString("accountId")),
                    rs.getString("accountName")
                ),
            firmId
        );
    }
	
	public List<AccountSummary> findAllAccounts() {

    String sql =
        "SELECT accountId, accountName " +
        "FROM dbo.accounts " +
        "ORDER BY firmId, accountId";

    return jdbcTemplate.query(
        sql,
        (rs, rowNum) -> {

            String accountId =
                rs.getString("accountId");

            String accountName =
                rs.getString("accountName");

            if (accountId != null) {
                accountId = accountId.trim();
            }

            if (accountName != null) {
                accountName = accountName.trim();
            }

            return new AccountSummary(
                accountId,
                accountName
            );
        }
    );
}


    // =========================================================
    // FIND ONE ACCOUNT
    // =========================================================

    public Account findByFirmAndAccount(
            Integer firmId,
            String accountId) {

        String sql =
            "SELECT " +
            "firmId, accountId, correspondent, " +
            "branch, rep, accountName, accountCat, " +
            "marginable, aggregate, taxlotMethod " +
            "FROM dbo.accounts " +
            "WHERE firmId = ? " +
            "AND accountId = ?";


        List<Account> list =
            jdbcTemplate.query(
                sql,
                (rs, rowNum) -> {

                    Account a =
                        new Account();

                    a.setFirmId(
                        (Integer)
                        rs.getObject("firmId")
                    );

                    a.setAccountId(
                        trim(
                            rs.getString("accountId")
                        )
                    );

                    a.setCorrespondent(
                        trim(
                            rs.getString("correspondent")
                        )
                    );

                    a.setBranch(
                        trim(
                            rs.getString("branch")
                        )
                    );

                    a.setRep(
                        trim(
                            rs.getString("rep")
                        )
                    );

                    a.setAccountName(
                        rs.getString("accountName")
                    );

                    a.setAccountCat(
                        trim(
                            rs.getString("accountCat")
                        )
                    );

                    Object marginable =
                        rs.getObject("marginable");

                    if (marginable != null) {
                        a.setMarginable(
                            rs.getBoolean("marginable")
                        );
                    }


                    Object aggregate =
                        rs.getObject("aggregate");

                    if (aggregate != null) {
                        a.setAggregate(
                            rs.getBoolean("aggregate")
                        );
                    }


                    a.setTaxlotMethod(
                        trim(
                            rs.getString("taxlotMethod")
                        )
                    );

                    return a;
                },
                firmId,
                accountId
            );


        if (list.isEmpty()) {
            return null;
        }

        return list.get(0);
    }


    // =========================================================
    // INSERT ACCOUNT
    // =========================================================

    public int insert(Account a) {

        String sql =
            "INSERT INTO dbo.accounts (" +
            "firmId, accountId, correspondent, " +
            "branch, rep, accountName, accountCat, " +
            "marginable, aggregate, taxlotMethod" +
            ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";


        return jdbcTemplate.update(
            sql,
            a.getFirmId(),
            a.getAccountId(),
            a.getCorrespondent(),
            a.getBranch(),
            a.getRep(),
            a.getAccountName(),
            a.getAccountCat(),
            a.getMarginable(),
            a.getAggregate(),
            a.getTaxlotMethod()
        );
    }


    // =========================================================
    // UPDATE ACCOUNT
    // =========================================================

    public int update(
            Integer firmId,
            String accountId,
            Account a) {

        String sql =
            "UPDATE dbo.accounts SET " +
            "correspondent = ?, " +
            "branch = ?, " +
            "rep = ?, " +
            "accountName = ?, " +
            "accountCat = ?, " +
            "marginable = ?, " +
            "aggregate = ?, " +
            "taxlotMethod = ? " +
            "WHERE firmId = ? " +
            "AND accountId = ?";


        return jdbcTemplate.update(
            sql,
            a.getCorrespondent(),
            a.getBranch(),
            a.getRep(),
            a.getAccountName(),
            a.getAccountCat(),
            a.getMarginable(),
            a.getAggregate(),
            a.getTaxlotMethod(),
            firmId,
            accountId
        );
    }


    // =========================================================
    // DELETE ACCOUNT
    // =========================================================

    public int delete(
            Integer firmId,
            String accountId) {

        String sql =
            "DELETE FROM dbo.accounts " +
            "WHERE firmId = ? " +
            "AND accountId = ?";


        return jdbcTemplate.update(
            sql,
            firmId,
            accountId
        );
    }


    private String trim(String value) {

        if (value == null) {
            return null;
        }

        return value.trim();
    }
}