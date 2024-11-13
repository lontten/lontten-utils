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
package com.lontten.util.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Preconditions;
import com.lontten.util.config.LonttenJsonConfig;
import com.lontten.util.json.config.LnNodeModule;
import com.lontten.util.json.config.LongModule;
import com.lontten.util.json.config.TimeModule;
import com.lontten.util.json.config.UUIDModule;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;

public class LnJsonUtil {
    private static final ObjectMapper objectMapper;

    public static ObjectMapper jsonOM() {
        return objectMapper;
    }

    public static ObjectMapper jsOM() {
        objectMapper.registerModule(new LongModule());
        return objectMapper;
    }

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
        if (LonttenJsonConfig.longToStr) {
            objectMapper.registerModule(new LongModule());
        }
        //uuid类型，去掉横线
        if (LonttenJsonConfig.removeUUIDLine) {
            objectMapper.registerModule(new UUIDModule());
        }

        // 纯json，使用LnNode 内部的 JsonNode自身序列化，反序列化。
        objectMapper.registerModule(new LnNodeModule());
    }


    //    -------------bean---------------------

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

    @SuppressWarnings("unchecked")
    public static HashMap<String, Object> bean2hashmap(Object o) {
        try {
            String s = objectMapper.writeValueAsString(o);
            return objectMapper.readValue(s, HashMap.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    //    -------------jsonStr---------------------
    public static JsonNode jsonStr2jsonNode(String str) {
        try {
            return objectMapper.readTree(str);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static LnNode jsonStr2node(String str) {
        return new LnNode(jsonStr2jsonNode(str));
    }

    public static <T> T jsonStr2bean(String str, Class<T> clazz) {
        try {
            return objectMapper.readValue(str, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    //    -------------jsonNode---------------------
    public static <T> T node2bean(JsonNode jsonNode, Class<T> clazz) {
        try {
            return objectMapper.treeToValue(jsonNode, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T node2bean(ObjectNode jsonNode, Class<T> clazz) {
        try {
            return objectMapper.treeToValue(jsonNode, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    @Nonnull
    public static <T> ArrayList<T> node2List(@Nullable JsonNode jsonNode, @Nullable Class<T> clazz) {
        if (jsonNode == null) {
            return new ArrayList<>();
        }
        Preconditions.checkArgument(clazz != null, "clazz不能为空");
        Preconditions.checkArgument(jsonNode.isArray(), "jsonNode不是数组");
        ArrayList<T> list = new ArrayList<>();
        ArrayNode arrayNode = (ArrayNode) jsonNode;
        for (JsonNode node : arrayNode) {
            T t = node2bean(node, clazz);
            list.add(t);
        }
        return list;
    }


    // --------------------- api ----------------------
    public static JsonNode bean2jsonNode(Object o) {
        return objectMapper.valueToTree(o);
    }

    public static ObjectNode createObjectNode(Object o) {
        return (ObjectNode) bean2jsonNode(o);
    }

    public static ObjectNode createObjectNode() {
        return objectMapper.createObjectNode();
    }

    public static ArrayNode createArrayNode() {
        return objectMapper.createArrayNode();
    }

    public static LnNode createNode() {
        return new LnNode();
    }

    public static LnNode createNode(Object o) {
        return new LnNode(bean2jsonNode(o));
    }
}
