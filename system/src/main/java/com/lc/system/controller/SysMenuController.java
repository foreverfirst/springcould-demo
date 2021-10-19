package com.lc.system.controller;

import com.lc.system.dao.SysRoleMenuDao;
import com.lc.system.entity.SysMenu;
import com.lc.system.dao.SysMenuDao;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu")
@Api(value = "菜单管理")
public class SysMenuController {

    @Autowired
    SysMenuDao sysMenuDao;

    @Autowired
    SysRoleMenuDao roleMenuDao;

    @ApiOperation(value = "查询菜单列表")
    @GetMapping
    public List<SysMenu> queryMenu(SysMenu sysMenu) {
        return sysMenuDao.queryMenu(sysMenu);
    }

    @ApiOperation(value = "新增菜单")
    @PostMapping
    public void addMenu(@RequestBody SysMenu sysMenu){
        sysMenuDao.addMenu(sysMenu);
    }

    @ApiOperation(value = "修改菜单")
    @PutMapping
    public void updateMenu(@RequestBody SysMenu sysMenu){
        sysMenuDao.updateMenu(sysMenu);
    }

    @ApiOperation(value = "删除菜单")
    @DeleteMapping("/{id}")
    public void deleteMenu(@PathVariable Long id){
        int count = sysMenuDao.getChildMenus(id).size();
        if (count > 0) {
            throw new RuntimeException("请先删除子菜单");
        }
        sysMenuDao.deleteMenu(id);
        // 删除中间表
        roleMenuDao.deleteRoleMenuByMenuId(id);
    }

}
