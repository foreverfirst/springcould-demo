package com.lc.role;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient("user")
public interface UserClient {
    @GetMapping("/user/all")
    public List<User> getAllUser();
}

