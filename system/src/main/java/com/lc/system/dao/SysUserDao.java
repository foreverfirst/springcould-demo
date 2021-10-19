package com.lc.system.dao;

import com.lc.system.entity.SysUser;

import java.util.List;

/**
 * @author zhaoxinguo on 2017/9/13.
 */
public interface SysUserDao {

    SysUser findByUsername(String username);

    SysUser createUser(SysUser sysUser);

    List<SysUser> findAll();


}
