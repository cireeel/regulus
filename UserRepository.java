package com.example;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /*
     * Get all users/accounts
     */
    public List<User> findAll() {

        String sql =
                "SELECT " +
                "id, name, email FROM users";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {

            User user = new User();

            user.setId(rs.getInt("id"));
			user.setName(rs.getString("name"));
			user.setEmail(rs.getString("email"));
			

            return user;
        });
    }

    /*
     * Find one account
     */
    public User findById(String id) {

        String sql =
                "SELECT " +
                "id, name, email " +
                "FROM Users " +
                "WHERE id = ?";

        return jdbcTemplate.queryForObject(
                sql,
                (rs, rowNum) -> {

                    User user = new User();

                    user.setId(rs.getInt("id"));
					user.setName(rs.getString("name"));
					user.setEmail(rs.getString("email"));
					

                    return user;
                },
                id
        );
    }
}