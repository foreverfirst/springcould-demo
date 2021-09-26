package com.lc.system.controller;

import com.lc.common.annotation.HasPermissions;
import com.lc.system.entity.Role;
import com.lc.system.entity.RoleMenu;
import com.lc.system.repository.RoleDao;
import com.lc.system.repository.RoleMenuDao;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/role")
@Api(value = "角色管理")
public class RoleController {

    @Autowired
    RoleDao roleDao;

    @Autowired
    RoleMenuDao roleMenuDao;

    /**
     * 获取角色列表
     *
     * @return
     */
    @ApiOperation(value = "查询角色列表")
    @GetMapping("")
    public List<Role> listRole() {
        return roleDao.findAll();
    }

    @ApiOperation(value = "新增角色")
    @PostMapping("")
    public void addRole(@RequestBody Role role) {
        roleDao.addRole(role);
    }

    @ApiOperation(value = "修改角色")
    @PutMapping("")
    public void updateRole(@RequestBody Role role) {
        roleDao.updateRole(role);
    }

    @ApiOperation(value = "删除角色")
    @DeleteMapping("/{id}")
    @HasPermissions("ADMIN")
    public void deleteRole(@PathVariable Long id) {
        roleDao.deleteRole(id);
        roleMenuDao.deleteRoleMenuByRoleId(id);
    }

    @ApiOperation(value = "查询菜单权限")
    @GetMapping("/{id}/menu")
    public void getMenuIdByRoleId(@PathVariable Long id) {
        roleMenuDao.getMenuIdByRoleId(id);
    }

    @ApiOperation(value = "分配菜单权限")
    @PostMapping("/{id}/menu")
    public void updateMenuByRoleId(@PathVariable Long id, @RequestBody List<Long> menuIds) {
        List<RoleMenu> roleMenus = menuIds
                .stream()
                .map(menuId -> {
                    RoleMenu roleMenu = new RoleMenu();
                    roleMenu.setRoleId(id);
                    roleMenu.setMenuId(menuId);
                    return roleMenu;
                }).collect(Collectors.toList());
        roleMenuDao.batchInsert(roleMenus);
    }
}
