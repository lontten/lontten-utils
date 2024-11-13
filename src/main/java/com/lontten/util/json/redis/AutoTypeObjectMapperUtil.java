/* ------------------------------------------------------------
 *   Copyright 2024 lontten lontten@163.com
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 * -------------------------------------------------------------
 * Project Name    :  lontten-utils
 * Project Authors :  lontten   <lontten@163.com>
 * Contributors    :  xxxx   <xx@xx.com>
 *                 |  yyyy   <yy@yy.com>
 * Created On      : <2024-11-13>
 * Last Modified   : <2024-11-13>
 *
 * lontten-utils: Lontten 工具库
 * ------------------------------------------------------------*/
package com.lontten.util.json.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.lontten.util.json.config.LongModule;
import com.lontten.util.json.config.TimeModule;

import javax.annotation.Nullable;
import java.io.IOException;

/**
 * 类型自推导的ObjectMapper
 * 依靠json字符串内部的类型信息，直接转成Object,然后直接强制转换为目标类型
 * 在反序列化时，无法指定目标类型，只能强转，所以需要在转成Object时，要有类型信息。
 * 应用场景：AOP cache 缓存，只能将缓存的json数据转成Object，再强转成目标类型
 */
public class AutoTypeObjectMapperUtil {
    public static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        //默认不序列化空值
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //反序列化时，遇到未知属性不报错；即忽略类中无法匹配的属性
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // 设置序列化反序列化采用直接处理字段的方式， 不依赖setter 和 getter
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        //支持jdk8时间类型
        objectMapper.registerModule(new TimeModule());
        // long 转 string
        objectMapper.registerModule(new LongModule());

        // 类型自推导json，直接用默认 实现，屏蔽 @JsonSerialize 和 @JsonDeserialize 的自定义实现

        //保留java类型信息
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
    }

    public static byte[] bean2Bytes(Object o) {
        try {
            return objectMapper.writeValueAsBytes(o);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Nullable
    public static String bean2jsonStr(@Nullable Object o) {
        if (o == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object bytes2bean(byte[] bs) {
        try {
            return objectMapper.readValue(bs, Object.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object jsonStr2bean(String str) {
        try {
            return objectMapper.readValue(str, Object.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
