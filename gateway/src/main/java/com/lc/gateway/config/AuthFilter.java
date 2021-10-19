package com.lc.gateway.config;

import com.lc.common.utils.JwtUtil;
import com.netflix.discovery.EurekaClient;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.applet.AppletContext;
import java.util.Arrays;
import java.util.Map;

/**
 * @author denny
 * @Description token过滤器
 * @date 2019/12/12 13:55
 */
@Component
public class AuthFilter implements GlobalFilter, Ordered {

    @Autowired
    private EurekaClient client;


    private final Logger log = LoggerFactory.getLogger(AuthFilter.class);

    private static final String[] whiteList = {"/auth/login", "/auth/register",
            "/system/v2/api-docs", "/auth/captcha/check", "/auth/captcha/get"};

    @Value("${jwt.header}")
    private  String AUTHORIZE_TOKEN = "Authorization";
    @Value("${jwt.tokenHead}")
    private String BEARER = "Bearer";
    @Value("${jwt.secret}")
    private String jwtSecret;


    /**
     * token过滤
     */
    @Override

    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        client.getApplications()
                .getRegisteredApplications()
                .forEach(item -> System.out.println(item.getName()));
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        String url = request.getURI().getPath();
        log.info("当前环境已开启token校验，当前路径：{} ", url);

        if (Arrays.asList(whiteList).contains(url)) {
            return chain.filter(exchange);
        }
        // 取Authorization
        try {
            String tokenHeader = request.getHeaders().getFirst(AUTHORIZE_TOKEN);
            // token不存在
            if (StringUtils.isEmpty(tokenHeader)) {
                throw new RuntimeException("token不存在");
            }
            // 取token
            String token = this.getToken(tokenHeader);
            // token不存在
            if (StringUtils.isEmpty(token)) {
                throw new RuntimeException("token不存在");
            }
            long start = System.currentTimeMillis();
            // 校验 token是否正确
            Map<String, Object> claims = JwtUtil.parseToken(token, jwtSecret);
            //有token 这里可根据具体情况，看是否需要在gateway直接把解析出来的用户信息塞进请求中，我们最终没有使用
            request.getHeaders().set("UserDetail", claims.toString());
            log.info("parse token: {}", System.currentTimeMillis() - start);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        return chain.filter(exchange);
    }


    @Override
    public int getOrder() {
        return -200;
    }

    /**
     * 解析Token
     */
    public String getToken(String requestHeader) {
        //2.Cookie中没有从header中获取
        if (requestHeader != null && requestHeader.startsWith(BEARER)) {
            return requestHeader.substring(6);
        }
        return "";
    }
}