package com.lc.system.dao;

import com.lc.system.entity.SysRole;

import java.util.List;

public interface SysRoleDao {

    List<SysRole> findAll();

    List<SysRole> queryRole(SysRole sysRole);

    List<SysRole> getRolesByUserId(Long userId);

    void addRole(SysRole sysRole);

    void updateRole(SysRole sysRole);

    void deleteRole(Long id);

}
