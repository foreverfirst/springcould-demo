package com.lc.system.repository;

import com.lc.system.entity.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class MenuDaoImpl implements MenuDao{
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Menu> findAll() {
        return jdbcTemplate.query("SELECT * FROM `tb_menu`", new MenuRowMapper());
    }

    @Override
    public List<Menu> queryMenu(Menu menu) {
        StringBuilder sql = new StringBuilder("SELECT * FROM `tb_menu` WHERE TRUE ");
        if (null != menu.getName()) {
            sql.append(String.format("AND name LIKE '%%%s%%' ", menu.getName()));
        }
        if (null != menu.getStatus()) {
            sql.append(String.format("AND status = %d ", menu.getStatus()));
        }
        return jdbcTemplate.query(sql.toString(), new MenuRowMapper());
    }

    @Override
    public void addMenu(Menu menu) {
        jdbcTemplate.update("INSERT INTO `tb_menu` (name,path,parent_id,status,icon) values (?,?,?,?,?)",
                menu.getName(), menu.getPath(), menu.getParentId(), menu.getStatus());
    }

    @Override
    public void updateMenu(Menu menu) {
        jdbcTemplate.update("update `tb_menu` SET name=?,path=?,parent_id=?,status=?,icon=? WHERE id = ?",
                menu.getName(), menu.getPath(), menu.getParentId(), menu.getStatus(), menu.getIcon(), menu.getId());
    }

    @Override
    public void deleteMenu(Long id){
        jdbcTemplate.update("DELETE FROM `tb_menu` WHERE id = ?", id);
    }

    static class MenuRowMapper implements RowMapper<Menu> {
        @Override
        public Menu mapRow(ResultSet rs, int i) throws SQLException {
            Menu menu = new Menu();
            menu.setId(rs.getLong("id"));
            menu.setName(rs.getString("name"));
            menu.setPath(rs.getString("path"));
            menu.setParentId(rs.getLong("parent_id"));
            menu.setIcon(rs.getString("icon"));
            menu.setStatus(rs.getInt("status"));
            return menu;
        }
    }
}
