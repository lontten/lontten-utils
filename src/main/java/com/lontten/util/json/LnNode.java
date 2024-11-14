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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LnNode {

    JsonNode value;

    @Override
    public String toString() {
        return value.toString();
    }

    public LnNode() {
        value = LnJsonUtil.createObjectNode();
    }

    public LnNode(JsonNode value) {
        this.value = value;
    }

    private ObjectNode asObject() {
        return (ObjectNode) value;
    }

    private ArrayNode asArray() {
        return (ArrayNode) value;
    }

    // -------------------- api -------------------------

    public <T> List<T> getList(String str, Class<T> clazz) {
        ArrayNode nodes = (ArrayNode) asObject().get(str);
        ArrayList<T> list = new ArrayList<>();
        nodes.forEach(node -> list.add(LnJsonUtil.node2bean(node, clazz)));
        return list;
    }

    public List<LnNode> getList(String str) {
        ArrayNode nodes = (ArrayNode) asObject().get(str);
        ArrayList<LnNode> list = new ArrayList<>();
        nodes.forEach(node -> list.add(new LnNode(node)));
        return list;
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String str, Class<T> clazz) {
        JsonNode node = asObject().get(str);
        if (clazz.isAssignableFrom(JsonNode.class)) {
            return (T) node;
        } else {
            return LnJsonUtil.node2bean(node, clazz);
        }
    }

    /**
     * 使用示例：
     * <pre>
     *   HashMap<String, HashMap<String, String>> result
     *      = node.get(key, new TypeReference<HashMap<String, HashMap<String, String>>>() {});
     * </pre>
     *
     * @param str
     * @param toValueTypeRef
     * @param <T>
     * @return
     */
    public <T> T get(String str, TypeReference<T> toValueTypeRef) {
        JsonNode node = asObject().get(str);
        return LnJsonUtil.node2bean(node, toValueTypeRef);
    }

    public LnNode getNode(String str) {
        return new LnNode(asObject().get(str));
    }

    public String getString(String str) {
        return asObject().get(str).asText();
    }

    public Short getShort(String str) {
        return asObject().get(str).shortValue();
    }

    public Integer getInt(String str) {
        return asObject().get(str).asInt();
    }

    public Long getLong(String str) {
        return asObject().get(str).asLong();
    }

    public Float getFloat(String str) {
        return asObject().get(str).floatValue();
    }

    public Double getDouble(String str) {
        return asObject().get(str).asDouble();
    }

    public BigDecimal getDecimal(String str) {
        return asObject().get(str).decimalValue();
    }


    public Boolean getBool(String str) {
        return asObject().get(str).asBoolean();
    }


    public LocalDateTime getDateTime(String str) {
        return get(str, LocalDateTime.class);
    }

    public LocalTime getTime(String str) {
        return get(str, LocalTime.class);
    }

    public LocalDate getDate(String str) {
        return get(str, LocalDate.class);
    }

    public UUID getUuid(@Nullable String str) {
        return get(str, UUID.class);
    }


    // ------------------ api set ---------------
    @Nonnull
    public LnNode put(@Nullable String key, @Nullable Object value) {
        if (key == null) return this;
        if (value == null) {
            asObject().putNull(key);
            return this;
        }
        asObject().set(key, LnJsonUtil.bean2jsonNode(value));
        return this;
    }

    @Nonnull
    public LnNode remove(@Nullable String key) {
        if (key == null) return this;
        asObject().remove(key);
        return this;
    }

    @Nonnull
    public LnNode deepCopy() {
        return new LnNode(value.deepCopy());
    }

}
