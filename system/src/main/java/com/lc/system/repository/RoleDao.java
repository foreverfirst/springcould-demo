package com.lc.system.repository;

import com.lc.system.entity.Role;

import java.util.List;

public interface RoleDao {
    List<Role> findAll();

    void addRole(Role role);

    void updateRole(Role role);

    void deleteRole(Long id);

}
