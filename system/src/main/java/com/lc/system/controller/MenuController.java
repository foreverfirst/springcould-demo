package com.lc.system.controller;

import com.lc.system.entity.Menu;
import com.lc.system.repository.MenuDao;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu")
@Api(value = "菜单管理")
public class MenuController {

    @Autowired
    MenuDao menuDao;

    @ApiOperation(value = "查询菜单列表")
    @GetMapping
    public List<Menu> queryMenu(Menu menu) {
        return menuDao.queryMenu(menu);
    }

    @ApiOperation(value = "新增菜单")
    @PostMapping
    public void addMenu(@RequestBody Menu menu){
        menuDao.addMenu(menu);
    }

    @ApiOperation(value = "修改菜单")
    @PutMapping
    public void updateMenu(@RequestBody Menu menu){
        menuDao.updateMenu(menu);
    }

    @ApiOperation(value = "删除菜单")
    @DeleteMapping("/{id}")
    public void deleteMenu(@PathVariable Long id){
        menuDao.deleteMenu(id);
    }

}
