package com.example.spring_redis_subscriber.config;

import lombok.RequiredArgsConstructor;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RedisClientConfig {

    @Value("${app.redis.host}")
    private String redisHost;

    @Bean
    public RedissonClient redissionClient() {
        Config config = new Config();
//        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        config.useSingleServer().setAddress(redisHost);
        return Redisson.create(config);
    }
}
