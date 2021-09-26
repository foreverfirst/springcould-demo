package com.lc.system.repository;

import com.lc.system.entity.RoleMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class RoleMenuDaoImpl implements RoleMenuDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public int batchInsert(List<RoleMenu> roleMenus) {
         jdbcTemplate.batchUpdate("Insert INTO `tb_role_menu` (role_id, menu_id) VALUES (?, ?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setLong(1, roleMenus.get(i).getRoleId());
                        ps.setLong(2, roleMenus.get(i).getMenuId());
                    }
                    @Override
                    public int getBatchSize() {
                        return roleMenus.size();
                    }
                });
        return roleMenus.size();
    }

    public int deleteRoleMenuByRoleId(Long roleId){
        return jdbcTemplate.update("DELETE FROM `tb_role_menu` WHERE role_id = ?", roleId);
    }


    @Override
    public List<String> getMenuIdByRoleId(Long roleId) {
        return jdbcTemplate.queryForList("SELECT menu_id FROM `tb_role_menu` WHERE role_id = ?", String.class, roleId);
    }
}
