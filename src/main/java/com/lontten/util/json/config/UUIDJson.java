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
package com.lontten.util.json.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.lontten.common.util.lang.LnUUIDUtil;

import java.io.IOException;
import java.util.UUID;

/**
 * 使用@JsonComponent注释会自动被注册到Jackson中.
 **/
public class UUIDJson {
    /**
     * 自定义序列化器,格式化数值
     */
    public static class MySerializer extends JsonSerializer<UUID> {

        /**
         * 序列化操作,继承JsonSerializer，重写Serialize函数
         */
        @Override
        public void serialize(UUID value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            String s = value.toString();
            jsonGenerator.writeString(s.replaceAll("-", ""));
        }
    }

    /**
     * 自定义反序列化器,格式化时间
     */
    public static class MyDeserializer extends JsonDeserializer<UUID> {

        @Override
        public UUID deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            return LnUUIDUtil.formStringToUUID(jsonParser.getText());
        }
    }

}