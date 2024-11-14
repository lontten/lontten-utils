package com.lontten.util.json.redis;


import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

public class GenericLonttenSecurityRedisSerializer implements RedisSerializer<Object> {
    public GenericLonttenSecurityRedisSerializer() {
    }

    public byte[] serialize(Object object) throws SerializationException {
        if (object == null) {
            return new byte[0];
        } else {
            return AutoTypeObjectMapperUtil.bean2Bytes(object);
        }
    }

    public Object deserialize(byte[] bytes) throws SerializationException {
        if (bytes != null && bytes.length != 0) {
            return AutoTypeObjectMapperUtil.bytes2bean(bytes);
        } else {
            return null;
        }
    }
}

