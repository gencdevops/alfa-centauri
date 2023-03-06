package com.example.cgrestaurant.configuration;


import redis.clients.jedis.JedisPoolConfig;

import java.util.List;
import java.util.Objects;

public interface CacheClient {
    void set(String key, Object value);
    Object get(String key);
    void delete(String key);
    void deleteAll(List<String> keys);

    void shutdown();

    class Builder {
        private String type;
        private String host;
        private String port;
        private boolean failOnError = true;

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder host(String host) {
            this.host = host;
            return this;
        }

        public Builder port(String port) {
            this.port = port;
            return this;
        }

        public Builder failOnError(boolean failOnError) {
            this.failOnError = failOnError;
            return this;
        }

        public CacheClient build() {
            if (Objects.equals("redis", type)) {
                JedisPoolConfig poolConfig = new JedisPoolConfig();

                poolConfig.setTestOnBorrow(false);
                poolConfig.setTestOnReturn(false);
                poolConfig.setTestWhileIdle(true);
                poolConfig.setMaxTotal(16);

                return new CacheManager(host, Integer.parseInt(port), failOnError, poolConfig);
            }
            throw new IllegalStateException("Unknown cache type");
        }
    }
}
