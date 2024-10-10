package com.bankapp.depositservice.config;

import io.lettuce.core.RedisClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class RedisConfig {

    @ConditionalOnMissingBean
    public RedisClient configureAndConnectRedis() {

        try (RedisClient redisClient = RedisClient.create("redis://password@localhost:6379/")) {

            return redisClient;

        } catch (Exception e) {

            log.error("cannot connect to redis server", e);

            throw e;
        }
    }
}
