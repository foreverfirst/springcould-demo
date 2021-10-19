package com.lc.system.dao.impl;

import com.lc.system.dao.SysUserDao;
import com.lc.system.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class SysUserDaoImpl implements SysUserDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public SysUser findByUsername(String username) {
        List<SysUser> sysUsers = jdbcTemplate.query("SELECT * FROM `tb_users` WHERE `username` = ?", new UserRowMapper(), username);
        return sysUsers.isEmpty() ? null : sysUsers.get(0);

    }

    @Override
    public SysUser createUser(SysUser sysUser) {
        jdbcTemplate.update("INSERT INTO `tb_users` (username,password) values (?,?)", sysUser.getUsername(), sysUser.getPassword());
        return sysUser;
    }

    @Override
    public List<SysUser> findAll() {
        return jdbcTemplate.query("SELECT * FROM `tb_users`", new UserRowMapper());
    }

    static class UserRowMapper implements RowMapper<SysUser> {
        @Override
        public SysUser mapRow(ResultSet resultSet, int i) throws SQLException {
            SysUser sysUser = new SysUser();
            sysUser.setUsername(resultSet.getString("username"));
            sysUser.setPassword(resultSet.getString("password"));
            return sysUser;
        }
    }


}
