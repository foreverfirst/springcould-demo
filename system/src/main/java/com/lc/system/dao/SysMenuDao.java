package com.lc.system.dao;

import com.lc.system.entity.SysMenu;
import com.lc.system.entity.SysRole;

import java.util.List;

public interface SysMenuDao {

    List<SysMenu> queryMenu(SysMenu sysMenu);

    List<SysMenu> getChildMenus(Long parentId);

    List<SysMenu> getMenusByRoleId(Long roleId);

    List<SysMenu> getMenusByUserId(Long userId);

    void addMenu(SysMenu sysMenu);

    void updateMenu(SysMenu sysMenu);

    void deleteMenu(Long id);

}
