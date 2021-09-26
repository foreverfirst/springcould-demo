package com.lc.system.repository;

import com.lc.system.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public User findByUsername(String username) {
        List<User> users = jdbcTemplate.query("SELECT * FROM `tb_users` WHERE `username` = ?", new UserRowMapper(), username);
        return users.isEmpty() ? null : users.get(0);

    }

    @Override
    public User createUser(User user) {
        jdbcTemplate.update("INSERT INTO `tb_users` (username,password) values (?,?)", user.getUsername(), user.getPassword());
        return user;
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query("SELECT * FROM `tb_users`", new UserRowMapper());
    }

    static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new User();
            user.setUsername(resultSet.getString("username"));
            user.setPassword(resultSet.getString("password"));
            return user;
        }
    }


}
