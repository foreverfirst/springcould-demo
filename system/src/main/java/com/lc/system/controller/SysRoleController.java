package com.lc.system.controller;

import com.lc.common.annotation.HasPermissions;
import com.lc.system.dao.SysUserRoleDao;
import com.lc.system.entity.SysRole;
import com.lc.system.entity.SysRoleMenu;
import com.lc.system.dao.SysRoleDao;
import com.lc.system.dao.SysRoleMenuDao;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/role")
@Api(value = "角色管理")
public class SysRoleController {

    @Autowired
    SysRoleDao roleDao;

    @Autowired
    SysRoleMenuDao roleMenuDao;

    @Autowired
    SysUserRoleDao userRoleDao;

    /**
     * 获取角色列表
     *
     * @return
     */
    @HasPermissions("ADMIN")
    @ApiOperation(value = "查询角色列表")
    @GetMapping("")
    public List<SysRole> queryRole(SysRole sysRole) {
        return roleDao.queryRole(sysRole);
    }

    @ApiOperation(value = "新增角色")
    @PostMapping("")
    public void addRole(@RequestBody SysRole sysRole) {
        roleDao.addRole(sysRole);
    }

    @ApiOperation(value = "修改角色")
    @PutMapping("")
    public void updateRole(@RequestBody SysRole sysRole) {
        roleDao.updateRole(sysRole);
    }

    @ApiOperation(value = "删除角色")
    @DeleteMapping("/{id}")
    public void deleteRole(@PathVariable Long id) {
        roleDao.deleteRole(id);
        // 删除中间表
        roleMenuDao.deleteRoleMenuByRoleId(id);
        userRoleDao.deleteByRoleId(id);
    }

    @ApiOperation(value = "查询菜单权限")
    @GetMapping("/{id}/menu")
    public void getMenuIdByRoleId(@PathVariable Long id) {
        roleMenuDao.getMenuIdByRoleId(id);
    }

    @ApiOperation(value = "分配菜单权限")
    @PostMapping("/{id}/menu")
    public void updateMenuByRoleId(@PathVariable Long id, @RequestBody List<Long> menuIds) {
        List<SysRoleMenu> SysRoleMenus = menuIds
                .stream()
                .map(menuId -> {
                    SysRoleMenu SYsRoleMenu = new SysRoleMenu();
                    SYsRoleMenu.setRoleId(id);
                    SYsRoleMenu.setMenuId(menuId);
                    return SYsRoleMenu;
                }).collect(Collectors.toList());
        // 先删除原来的记录，再保存新的
        roleMenuDao.deleteRoleMenuByRoleId(id);
        roleMenuDao.batchInsert(SysRoleMenus);
    }
}
