package com.lc.system.repository;

import com.lc.system.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao{
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Role> findAll() {
        return jdbcTemplate.query("SELECT * FROM `tb_role`", new RoleRowMapper());
    }

    @Override
    public void addRole(Role role) {
        jdbcTemplate.update("INSERT INTO `tb_role` (name) values (?)", role.getName());
    }

    @Override
    public void updateRole(Role role) {
        jdbcTemplate.update("UPDATE `tb_role` SET name = ? WHERE id= ? ", role.getName(), role.getId());
    }

    @Override
    public void deleteRole(Long id){
        jdbcTemplate.update("DELETE FROM `tb_role` WHERE id = ?", id);
    }


    static class RoleRowMapper implements RowMapper<Role> {
        @Override
        public Role mapRow(ResultSet resultSet, int i) throws SQLException {
            Role role = new Role();
            role.setId(resultSet.getLong("id"));
            role.setName(resultSet.getString("name"));
            return role;
        }
    }
}
