package com.lc.system.dao;

import com.lc.system.entity.SysRoleMenu;
import com.lc.system.entity.SysUserRole;

import java.util.List;

public interface SysUserRoleDao {

    int batchInsert(List<SysUserRole> sysUserRoles);

    int deleteByRoleId(Long roleId);

    int deleteByUserId(Long userId);

}
