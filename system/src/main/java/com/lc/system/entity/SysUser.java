package com.lc.system.entity;

import java.util.List;

/**
 * @author zhaoxinguo on 2017/9/13.
 */

public class SysUser {

    private long id;
    private String username;
    private String password;

    private List<SysRole> sysRoles;

    private List<SysMenu> sysMenus;

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<SysRole> getRoles() {
        return sysRoles;
    }

    public void setRoles(List<SysRole> sysRoles) {
        this.sysRoles = sysRoles;
    }

    public List<SysMenu> getMenus() {
        return sysMenus;
    }

    public void setMenus(List<SysMenu> sysMenus) {
        this.sysMenus = sysMenus;
    }
}
