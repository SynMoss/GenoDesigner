package com.hmzhkj.auth.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {
    @Value("${spring.redis.host}")
    private String address;
    @Value("${spring.redis.password}")
    private String password;
    @Value("${spring.redis.port}")
    private String port;
    @Bean
    public RedissonClient getRedisson()
    {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://"+address+":"+port).setPassword(password)
                .setRetryInterval(5000)
                .setTimeout(10000)
                .setConnectTimeout(10000);
        return Redisson.create(config);
    }
}
