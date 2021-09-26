package com.lc.system.controller;

import com.lc.system.entity.User;
import com.lc.system.repository.UserDao;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhaoxinguo on 2017/9/13.
 */
@RestController
@RequestMapping("/user")
@Api(value = "用户管理")
public class UserController extends BaseController {

    @Autowired
    UserDao userDao;
    /**
     * 获取用户列表
     * @return
     */
    @ApiOperation(value = "查询用户列表")
    @GetMapping("/userList")
    public Map<String, Object> userList(){
        List<User> users = userDao.findAll();
        logger.info("users: {}", users);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("users",users);
        return map;
    }

    /**
     * 获取用户列表
     * @return
     */
    @ApiOperation(value = "查询用户")
    @GetMapping("/{username}")
    public User getUserByUsername(@PathVariable("username") String username){
        User user = userDao.findByUsername(username);
        return user;
    }



}
