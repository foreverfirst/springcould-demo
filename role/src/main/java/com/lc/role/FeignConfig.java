package com.lc.role;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

@Configuration
public class FeignConfig {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Bean
    public RequestInterceptor getRequestInterceptor(){
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate requestTemplate) {
                /** 设置请求头信息 **/
                requestTemplate.header("a", "a");
                 requestTemplate.header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE);
                // 可以做日志记录
                logger.info("自定义拦截器-----------");
            }
        };
    }
}
