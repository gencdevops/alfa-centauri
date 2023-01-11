package com.example.rediscustomserializer.configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.lettuce.core.codec.RedisCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class GsonRedisSerializer implements RedisCodec<String, Object> {
    Logger logger = LoggerFactory.getLogger(GsonRedisSerializer.class);
    private Charset charset = Charset.forName("UTF-8");
    private ByteArrayOutputStream byteArrayEmpty = new ByteArrayOutputStream();
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public ByteBuffer encodeKey(String key) {
        return charset.encode(key);
    }

    @Override
    public String decodeKey(ByteBuffer byteBuffer) {
        return charset.decode(byteBuffer).toString();
    }

    @Override
    public ByteBuffer encodeValue(Object value) {

        try {
            return ByteBuffer.wrap(gson.toJson(value).getBytes());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ByteBuffer.wrap(byteArrayEmpty.toByteArray());
        }
    }

    @Override
    public Object decodeValue(ByteBuffer byteBuffer) {

        try {
            byte[] byteArray = new byte[byteBuffer.remaining()];
            byteBuffer.get(byteArray);
            Reader reader = new InputStreamReader(new ByteArrayInputStream(byteArray));
            return gson.fromJson(reader, Object.class);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }
}
