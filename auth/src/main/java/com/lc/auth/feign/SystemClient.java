package com.lc.auth.feign;

import com.lc.auth.entity.UserDetail;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("system")
public interface SystemClient {
    @GetMapping("/user/{username}")
    UserDetail findByUsername(@PathVariable("username") String username);

    @PostMapping("/user")
    UserDetail create(@RequestBody UserDetail user);
}

