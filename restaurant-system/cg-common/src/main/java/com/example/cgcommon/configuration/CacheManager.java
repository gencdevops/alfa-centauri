package com.example.cgcommon.configuration;



import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;


import static redis.clients.jedis.Protocol.DEFAULT_TIMEOUT;

public class CacheManager implements CacheClient {
    private final JedisPool pool;
    public CacheManager(String host, int port, boolean failOnError, JedisPoolConfig poolConfig) {
        pool = new JedisPool(poolConfig, host, port, DEFAULT_TIMEOUT);
    }


    @Override
    public void set(String key, Object value) {

    }

    @Override
    public Object get(String key) {
        try(Jedis jedis = pool.getResource()) {
            return jedis.get(key);
        }
    }

    @Override
    public void delete(String key) {
        try (Jedis jedis = pool.getResource()) {
            jedis.del(key);
        }
    }

    @Override
    public void deleteAll(List<String> keys) {
        int retry = 0;
        try (Jedis jedis = pool.getResource()) {
            while (retry < 3) {
                try {
                    jedis.del(keys.toArray(new String[0]));
                    break;
                } catch (Exception e) {
                    retry++;
                }
            }
        }
    }

    @Override
    public void shutdown() {
        pool.close();
    }
}