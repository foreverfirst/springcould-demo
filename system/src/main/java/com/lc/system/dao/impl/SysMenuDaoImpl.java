package com.lc.system.dao.impl;

import com.lc.system.dao.SysMenuDao;
import com.lc.system.entity.SysMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class SysMenuDaoImpl implements SysMenuDao {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<SysMenu> queryMenu(SysMenu sysMenu) {
        StringBuilder sql = new StringBuilder("SELECT * FROM `tb_menu` WHERE TRUE ");
        if (null != sysMenu.getName()) {
            sql.append(String.format("AND name LIKE '%%%s%%' ", sysMenu.getName()));
        }
        if (null != sysMenu.getStatus()) {
            sql.append(String.format("AND status = %d ", sysMenu.getStatus()));
        }
        return jdbcTemplate.query(sql.toString(), new MenuRowMapper());
    }

    @Override
    public List<SysMenu> getChildMenus(Long parentId){
        return jdbcTemplate.query("SELECT * FROM `tb_menu` WHERE parent_id = ?", new MenuRowMapper(), parentId);
    }

    @Override
    public List<SysMenu> getMenusByRoleId(Long roleId) {
        return jdbcTemplate.query("SELECT * FROM `tb_menu` a LEFT JOIN `tb_role_menu` b ON a.`id` = b.`menu_id` " +
                " WHERE b.`role_id` = ? ", new MenuRowMapper(), roleId);
    }

    @Override
    public List<SysMenu> getMenusByUserId(Long userId) {
        return jdbcTemplate.query("SELECT * FROM `tb_menu` a LEFT JOIN `tb_role_menu` b  ON a.`id` = b.`menu_id` " +
                " LEFT JOIN `tb_user_role` c ON b.`role_id` = c.`role_id` " +
                " WHERE c.`user_id` = ? ", new MenuRowMapper(), userId);
    }

    @Override
    public void addMenu(SysMenu sysMenu) {
        jdbcTemplate.update("INSERT INTO `tb_menu` (name,path,parent_id,status,icon) values (?,?,?,?,?)",
                sysMenu.getName(), sysMenu.getPath(), sysMenu.getParentId(), sysMenu.getStatus());
    }

    @Override
    public void updateMenu(SysMenu sysMenu) {
        jdbcTemplate.update("update `tb_menu` SET name=?,path=?,parent_id=?,status=?,icon=? WHERE id = ?",
                sysMenu.getName(), sysMenu.getPath(), sysMenu.getParentId(), sysMenu.getStatus(), sysMenu.getIcon(), sysMenu.getId());
    }

    @Override
    public void deleteMenu(Long id){
        jdbcTemplate.update("DELETE FROM `tb_menu` WHERE id = ?", id);
    }

    static class MenuRowMapper implements RowMapper<SysMenu> {
        @Override
        public SysMenu mapRow(ResultSet rs, int i) throws SQLException {
            SysMenu sysMenu = new SysMenu();
            sysMenu.setId(rs.getLong("id"));
            sysMenu.setName(rs.getString("name"));
            sysMenu.setPath(rs.getString("path"));
            sysMenu.setParentId(rs.getLong("parent_id"));
            sysMenu.setIcon(rs.getString("icon"));
            sysMenu.setStatus(rs.getInt("status"));
            return sysMenu;
        }
    }
}
