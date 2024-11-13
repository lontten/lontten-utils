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

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.lontten.common.util.lang.LnUUIDUtil;

import java.io.IOException;

/**
 * 当json中有类型信息，自动识别类型，
 * 使用这个序列化器，解决ObjectNode深层嵌套时，无类型信息时，反序列化失败问题。
 **/
public class LnNodeJson {
    /**
     * 自定义序列化器,格式化数值
     */
    public static class MySerializer extends JsonSerializer<LnNode> {

        /**
         * 序列化操作,继承JsonSerializer，重写Serialize函数
         */
        @Override
        public void serialize(LnNode value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            value.value.serialize(jsonGenerator, serializerProvider);
        }
    }

    /**
     * 自定义反序列化器,格式化时间
     */
    public static class MyDeserializer extends JsonDeserializer<LnNode> {

        @Override
        public LnNode deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            return LnJsonUtil.jsonStr2node(jsonParser.getText());
        }
    }

}