package com.lc.system.dao.impl;

import com.lc.system.dao.SysRoleDao;
import com.lc.system.entity.SysRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class SysRoleDaoImpl implements SysRoleDao {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<SysRole> findAll() {
        return jdbcTemplate.query("SELECT * FROM `tb_role`", new RoleRowMapper());
    }

    @Override
    public List<SysRole> queryRole(SysRole sysRole) {
        StringBuilder sql = new StringBuilder("SELECT * FROM `tb_role` WHERE TRUE ");
        if (null != sysRole.getName()) {
            sql.append(String.format("AND name LIKE '%%%s%%' ", sysRole.getName()));
        }
        if (null != sysRole.getKey()) {
            sql.append(String.format("AND key LIKE '%%%s%%' ", sysRole.getKey()));
        }
        if (null != sysRole.getStatus()) {
            sql.append(String.format("AND status = %d ", sysRole.getStatus()));
        }
        return jdbcTemplate.query(sql.toString(), new RoleRowMapper());
    }

    @Override
    public List<SysRole> getRolesByUserId(Long userId) {
        return jdbcTemplate.query("SELECT * FROM `tb_role` a LEFT JOIN `tb_user_role` b ON a.`id` = b.`role_id` " +
                " WHERE b.`user_id` = ? ", new RoleRowMapper(), userId);
    }

    @Override
    public void addRole(SysRole sysRole) {
        jdbcTemplate.update("INSERT INTO `tb_role` (`name`, `key`, `status`) values (?, ?, ?) ",
                sysRole.getName(), sysRole.getKey(), sysRole.getStatus());
    }

    @Override
    public void updateRole(SysRole sysRole) {
        jdbcTemplate.update("UPDATE `tb_role` SET `name` = ?, `key` = ?, `status` = ? WHERE id = ? ",
                sysRole.getName(), sysRole.getKey(), sysRole.getStatus(), sysRole.getId());
    }

    @Override
    public void deleteRole(Long id) {
        jdbcTemplate.update("DELETE FROM `tb_role` WHERE id = ? ", id);
    }


    static class RoleRowMapper implements RowMapper<SysRole> {
        @Override
        public SysRole mapRow(ResultSet resultSet, int i) throws SQLException {
            SysRole sysRole = new SysRole();
            sysRole.setId(resultSet.getLong("id"));
            sysRole.setName(resultSet.getString("name"));
            sysRole.setKey(resultSet.getString("key"));
            sysRole.setStatus(resultSet.getInt("status"));
            return sysRole;
        }
    }
}
