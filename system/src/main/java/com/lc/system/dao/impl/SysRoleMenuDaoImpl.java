package com.lc.system.dao.impl;

import com.lc.system.dao.SysRoleMenuDao;
import com.lc.system.entity.SysRoleMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class SysRoleMenuDaoImpl implements SysRoleMenuDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public int batchInsert(List<SysRoleMenu> sysRoleMenus) {
         jdbcTemplate.batchUpdate("Insert INTO `tb_role_menu` (role_id, menu_id) VALUES (?, ?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setLong(1, sysRoleMenus.get(i).getRoleId());
                        ps.setLong(2, sysRoleMenus.get(i).getMenuId());
                    }
                    @Override
                    public int getBatchSize() {
                        return sysRoleMenus.size();
                    }
                });
        return sysRoleMenus.size();
    }

    public int deleteRoleMenuByRoleId(Long roleId){
        return jdbcTemplate.update("DELETE FROM `tb_role_menu` WHERE role_id = ?", roleId);
    }

    public int deleteRoleMenuByMenuId(Long menuId){
        return jdbcTemplate.update("DELETE FROM `tb_role_menu` WHERE menu_id = ?", menuId);
    }

    @Override
    public List<String> getMenuIdByRoleId(Long roleId) {
        return jdbcTemplate.queryForList("SELECT menu_id FROM `tb_role_menu` WHERE role_id = ?", String.class, roleId);
    }
}
