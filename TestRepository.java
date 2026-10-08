package com.example;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class TestRepository {

    private final JdbcTemplate jdbcTemplate;

    public TestRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    // =========================================================
    // GET ALL
    // =========================================================
    public List<TestRequest> findAll() {

        String sql =
                "SELECT accountBookID, accountId, amount, description " +
                "FROM TestRequest " +
                "ORDER BY accountBookID";

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> {

                    TestRequest request = new TestRequest();

                    request.setAccountBookID(
                            rs.getInt("accountBookID")
                    );

                    request.setAccountId(
                            rs.getString("accountId")
                    );

                    request.setAmount(
                            rs.getBigDecimal("amount")
                    );

                    request.setDescription(
                            rs.getString("description")
                    );

                    return request;
                }
        );
    }


    // =========================================================
    // GET ONE BY accountBookID
    // =========================================================
    public TestRequest findById(int accountBookID) {

        String sql =
                "SELECT accountBookID, accountId, amount, description " +
                "FROM TestRequest " +
                "WHERE accountBookID = ?";

        List<TestRequest> results =
                jdbcTemplate.query(
                        sql,
                        (rs, rowNum) -> {

                            TestRequest request =
                                    new TestRequest();

                            request.setAccountBookID(
                                    rs.getInt("accountBookID")
                            );

                            request.setAccountId(
                                    rs.getString("accountId")
                            );

                            request.setAmount(
                                    rs.getBigDecimal("amount")
                            );

                            request.setDescription(
                                    rs.getString("description")
                            );

                            return request;
                        },
                        accountBookID
                );

        if (results.isEmpty()) {
            return null;
        }

        return results.get(0);
    }


    // =========================================================
    // OPTIONAL:
    // GET ALL ROWS FOR AN ACCOUNT
    // =========================================================
    public List<TestRequest> findByAccountId(
            String accountId) {

        String sql =
                "SELECT accountBookID, accountId, amount, description " +
                "FROM TestRequest " +
                "WHERE accountId = ? " +
                "ORDER BY accountBookID";

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> {

                    TestRequest request =
                            new TestRequest();

                    request.setAccountBookID(
                            rs.getInt("accountBookID")
                    );

                    request.setAccountId(
                            rs.getString("accountId")
                    );

                    request.setAmount(
                            rs.getBigDecimal("amount")
                    );

                    request.setDescription(
                            rs.getString("description")
                    );

                    return request;
                },
                accountId
        );
    }


    // =========================================================
    // INSERT
    //
    // Returns SQL Server generated accountBookID
    // =========================================================
    public int insert(TestRequest request) {

        String sql =
                "INSERT INTO TestRequest " +
                "(accountId, amount, description) " +
                "VALUES (?, ?, ?)";

        KeyHolder keyHolder =
                new GeneratedKeyHolder();

        int rows = jdbcTemplate.update(
                connection -> {

                    PreparedStatement ps =
                            connection.prepareStatement(
                                    sql,
                                    Statement.RETURN_GENERATED_KEYS
                            );

                    ps.setString(
                            1,
                            request.getAccountId()
                    );

                    ps.setBigDecimal(
                            2,
                            request.getAmount()
                    );

                    ps.setString(
                            3,
                            request.getDescription()
                    );

                    return ps;
                },
                keyHolder
        );

        if (rows == 0) {
            return 0;
        }

        Number key = keyHolder.getKey();

        if (key == null) {
            return 0;
        }

        int accountBookID = key.intValue();

        request.setAccountBookID(
                accountBookID
        );

        return accountBookID;
    }


    // =========================================================
    // UPDATE
    // =========================================================
    public int update(
            int accountBookID,
            TestRequest request) {

        String sql =
                "UPDATE TestRequest " +
                "SET accountId = ?, " +
                "    amount = ?, " +
                "    description = ? " +
                "WHERE accountBookID = ?";

        return jdbcTemplate.update(
                sql,
                request.getAccountId(),
                request.getAmount(),
                request.getDescription(),
                accountBookID
        );
    }


    // =========================================================
    // DELETE
    // =========================================================
    public int delete(int accountBookID) {

        String sql =
                "DELETE FROM TestRequest " +
                "WHERE accountBookID = ?";

        return jdbcTemplate.update(
                sql,
                accountBookID
        );
    }
}