package com.kenzie.appserver.config;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;

import javax.inject.Inject;
import java.util.Optional;

public class CacheClient {

    @Inject
    public CacheClient() {

    }

    public void setValue(String key, int minutes, String value) {
        try (Jedis jedis = DaggerServiceComponent.create().provideJedis()) {
            jedis.setex(key, minutes, value);
        }
    }

    // method to get the Value
    public Optional<String> getValue(String key) {
        try (Jedis jedis = DaggerServiceComponent.create().provideJedis()) {
            return Optional.ofNullable(jedis.get(key));
        }
    }
    //  method to invalidate.
    public void invalidate(String key) {
        Jedis jedis = DaggerServiceComponent.create().provideJedis();
        try (jedis; jedis) {
            jedis.del(key);
        }
        jedis.get(key);
    }
}
