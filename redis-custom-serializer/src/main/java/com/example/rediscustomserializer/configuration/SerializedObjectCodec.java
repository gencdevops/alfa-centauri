package com.example.rediscustomserializer.configuration;

import io.lettuce.core.codec.RedisCodec;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

@Slf4j
public class SerializedObjectCodec implements RedisCodec<String, Object> {
    private Charset charset = Charset.forName("UTF-8");
    private ByteArrayOutputStream byteArrayOutputStreamForNonSerializableObjects = new ByteArrayOutputStream();

    @Override
    public String decodeKey(ByteBuffer byteBuffer) {
        return charset.decode(byteBuffer).toString();
    }

    @Override
    public Object decodeValue(ByteBuffer bytes) {
        try {
            byte[] byteArray = new byte[bytes.remaining()];
            bytes.get(byteArray);
            ObjectInputStream is = new ObjectInputStream(new ByteArrayInputStream(byteArray));
            return is.readObject();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public ByteBuffer encodeKey(String key) {
        return charset.encode(key);
    }

    @Override
    public ByteBuffer encodeValue(Object value) {
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(bytes);
            os.writeObject(value);
            return ByteBuffer.wrap(bytes.toByteArray());
        } catch (IOException e) {
            if (log.isDebugEnabled()) {
                log.debug("Non serializable Object : ", e);
            }
            return ByteBuffer.wrap(byteArrayOutputStreamForNonSerializableObjects.toByteArray());
        }
    }
}
