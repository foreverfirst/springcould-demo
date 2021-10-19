package com.lc.system.controller;

import com.lc.system.dao.SysMenuDao;
import com.lc.system.dao.SysRoleDao;
import com.lc.system.dao.SysUserRoleDao;
import com.lc.system.entity.SysUser;
import com.lc.system.dao.SysUserDao;
import com.lc.system.entity.SysUserRole;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liuchen 2021/9/10
 */
@RestController
@RequestMapping("/user")
@Api(value = "用户管理")
public class SysUserController extends BaseController {

    @Autowired
    SysUserDao userDao;

    @Autowired
    SysRoleDao roleDao;

    @Autowired
    SysMenuDao menuDao;

    @Autowired
    SysUserRoleDao userRoleDao;

    /**
     * 获取用户列表
     *
     * @return
     */
    @ApiOperation(value = "查询用户列表")
    @GetMapping("/userList")
    public List<SysUser> userList() {
        return userDao.findAll();
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    @ApiOperation(value = "获取用户信息")
    @GetMapping("/{username}")
    public SysUser getUserByUsername(@PathVariable("username") String username) {
        SysUser userDetail = userDao.findByUsername(username);
        userDetail.setRoles(roleDao.getRolesByUserId(userDetail.getId()));
        userDetail.setMenus(menuDao.getMenusByUserId(userDetail.getId()));
        return userDetail;
    }

    @ApiOperation(value = "分配角色")
    @PostMapping("/{id}/role")
    public void updateMenuByRoleId(@PathVariable Long id, @RequestBody List<Long> roleIds) {
        List<SysUserRole> sysUserRoles = roleIds
                .stream()
                .map(roleId -> {
                    SysUserRole sysUserRole = new SysUserRole();
                    sysUserRole.setUserId(id);
                    sysUserRole.setRoleId(roleId);
                    return sysUserRole;
                }).collect(Collectors.toList());
        // 先删除原来的记录，再保存新的
        userRoleDao.deleteByUserId(id);
        userRoleDao.batchInsert(sysUserRoles);
    }

}
