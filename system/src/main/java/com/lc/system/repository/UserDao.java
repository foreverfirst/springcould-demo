package com.lc.system.repository;

import com.lc.system.entity.User;

import java.util.List;

/**
 * @author zhaoxinguo on 2017/9/13.
 */
public interface UserDao {

    User findByUsername(String username);

    User createUser(User user);

    List<User> findAll();


}
