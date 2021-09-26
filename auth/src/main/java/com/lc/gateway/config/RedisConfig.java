package com.lc.gateway.config;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

import java.time.Duration;

/**
 * @FileName: RedisConfig
 * @Author: zhaoxinguo
 * @Date: 2019/3/20 17:07
 * @Description: Redis配置类
 */
@Configuration
@ComponentScan("boss.portal.config")
public class RedisConfig {

    @Bean
    public GenericObjectPoolConfig<RedisConnection> jedisPoolConfig() {
        GenericObjectPoolConfig<RedisConnection> poolConfig = new GenericObjectPoolConfig<>();
        poolConfig.setMaxIdle(10);
        poolConfig.setMaxTotal(10);
        return poolConfig;
    }
    @Bean
    public LettuceConnectionFactory jedisConnectionFactory(GenericObjectPoolConfig<RedisConnection> jedisPoolConfig) {
        RedisStandaloneConfiguration standaloneConfig = new RedisStandaloneConfiguration();
        standaloneConfig.setHostName("127.0.0.1");
        standaloneConfig.setPort(6379);
        standaloneConfig.setDatabase(7);
//         这里我本地的Reids没有设置密码，暂时注释掉，如果有密码，请放开下面的注释
//        standaloneConfig.setPassword("ydw@123");
        LettucePoolingClientConfiguration lettuceClient = LettucePoolingClientConfiguration.builder()
                .poolConfig(jedisPoolConfig)
                .commandTimeout(Duration.ofMillis(5000))
                .shutdownTimeout(Duration.ofMillis(100))
                .build();
        return new LettuceConnectionFactory(standaloneConfig, lettuceClient);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory jedisConnectionFactory) {
        final RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory);
        template.setValueSerializer(new GenericToStringSerializer<>(Object.class));
        return template;
    }

}
