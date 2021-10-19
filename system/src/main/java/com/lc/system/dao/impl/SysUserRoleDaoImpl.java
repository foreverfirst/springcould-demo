package com.lc.system.dao.impl;

import com.lc.system.dao.SysUserRoleDao;
import com.lc.system.entity.SysUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class SysUserRoleDaoImpl implements SysUserRoleDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public int batchInsert(List<SysUserRole> sysUserRoles) {
        jdbcTemplate.batchUpdate("Insert INTO `tb_user_role` (user_id, role_id) VALUES (?, ?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setLong(1, sysUserRoles.get(i).getUserId());
                        ps.setLong(2, sysUserRoles.get(i).getRoleId());
                    }

                    @Override
                    public int getBatchSize() {
                        return sysUserRoles.size();
                    }
                });
        return sysUserRoles.size();
    }

    @Override
    public int deleteByRoleId(Long roleId) {
        return jdbcTemplate.update("DELETE FROM `tb_user_role` WHERE role_id = ?", roleId);
    }

    @Override
    public int deleteByUserId(Long userId) {
        return jdbcTemplate.update("DELETE FROM `tb_user_role` WHERE user_id = ?", userId);
    }
}
