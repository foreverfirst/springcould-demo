package com.lc.auth.service;

import com.lc.auth.entity.UserDetail;
import com.lc.auth.feign.SystemClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SystemClient userClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetail user = userClient.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException(username);
        }
        System.out.println("auth获取用户信息:" + user.toString());
        return user;
    }

}
