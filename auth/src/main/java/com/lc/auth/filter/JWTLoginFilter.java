package com.lc.auth.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lc.auth.entity.UserDetail;
import com.lc.auth.service.UserDetailsServiceImpl;
import com.fasterxml.jackson.databind.JsonNode;
import com.lc.common.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.ServletRequestUtils;

/**
 * 自定义JWT登录过滤器
 * 收集登录信息，并在登录成功后生成 token 返回客户端
 * @author zhaoxinguo on 2017/9/12.
 */
@Component
public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {

    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Value("${jwt.privateSecret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    @Override
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }


    /**
     * 搜集参数（username、password），封装为验证请求（UsernamePasswordAuthenticationToken）
     * 然后调用 AuthenticationManager 进行身份认证。
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) {
        long start = System.currentTimeMillis();
        if (!"POST".equals(req.getMethod())) {
            throw new AuthenticationServiceException("Authentication method not supported: " + req.getMethod());
        }
        try {
            JsonNode jsonNode = new ObjectMapper().readValue(req.getInputStream(), JsonNode.class);
            String username = jsonNode.get("username").textValue();
            String password = jsonNode.get("password").textValue();
            Authentication authenticate = super.getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(username, password));
            System.out.println("耗时:" + (System.currentTimeMillis() - start) + "ms");
            return authenticate;

        } catch (Exception e) {
            throw new AuthenticationServiceException(e.getMessage());
        }
    }

    /**
     * 认证成功(用户成功登录后，这个方法会被调用，我们在这个方法里生成token)
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication auth) {
        // builder the token
        String token;
        try {
            UserDetail user = (UserDetail) userDetailsService.loadUserByUsername(auth.getName());
            // 生成token
            token = createToken(user, secret, expiration);
            // 登录成功后，返回token
            response.setStatus(HttpStatus.OK.value());
            response.getWriter().print(tokenHead + token);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String createToken(UserDetail user, String secret, long expiration) throws Exception {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", user.getUsername());
        claims.put("rol",user.getRoles());
        claims.put("per", user.getPerms());
        return JwtUtil.createToken(claims,secret, expiration);
    }

}
