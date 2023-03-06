package com.example.cgorder.configuration;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.SerializationException;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.*;
import java.util.List;

import static redis.clients.jedis.Protocol.DEFAULT_TIMEOUT;

public class CacheManager implements CacheClient {
    private final JedisPool pool;
    public CacheManager(String host, int port, boolean failOnError, JedisPoolConfig poolConfig) {
        pool = new JedisPool(poolConfig, host, port, DEFAULT_TIMEOUT);
    }


    @Override
    public void set(String key, Object value) {
        final ObjectMapper objectMapper = new ObjectMapper();

        try (Jedis jedis = pool.getResource()) {
            jedis.set(key, objectMapper.writeValueAsString(value));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] toBytes(Object object) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             ObjectOutput output = new ObjectOutputStream(outputStream)) {

            output.writeObject(object);

            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new SerializationException(e);
        }
    }

    private Object toObject(byte[] bytes) {
        if(bytes == null) {
            return null;
        }

        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
             ObjectInput input = new ObjectInputStream(inputStream)) {

            return input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new SerializationException(e);
        }
    }

    @Override
    public Object get(String key) {
        try (Jedis jedis = pool.getResource()) {
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
