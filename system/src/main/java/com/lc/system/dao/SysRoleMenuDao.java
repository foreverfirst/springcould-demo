package com.lc.system.dao;

import com.lc.system.entity.SysRoleMenu;

import java.util.List;

public interface SysRoleMenuDao {

    int batchInsert(List<SysRoleMenu> SysRoleMenus);

    /**
     * 通过角色ID删除角色和菜单关联
     *
     * @param roleId 角色ID
     * @return 结果
     */
    int deleteRoleMenuByRoleId(Long roleId);

    int deleteRoleMenuByMenuId(Long menuId);

    List<String> getMenuIdByRoleId(Long roleId);

}
