package com.lc.system.repository;

import com.lc.system.entity.RoleMenu;

import java.util.List;

public interface RoleMenuDao {

    int batchInsert(List<RoleMenu> roleMenu);

    /**
     * 通过角色ID删除角色和菜单关联
     *
     * @param roleId 角色ID
     * @return 结果
     */
    public int deleteRoleMenuByRoleId(Long roleId);

    public List<String> getMenuIdByRoleId(Long roleId);

}
