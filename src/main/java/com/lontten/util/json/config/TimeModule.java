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

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.PackageVersion;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.lontten.common.util.lang.LnTimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TimeModule extends SimpleModule {

    public TimeModule() {
        super(PackageVersion.VERSION);
        this.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(LnTimeUtil.dateTimeFormatter));
        this.addSerializer(LocalDate.class, new LocalDateSerializer(LnTimeUtil.dateFormatter));
        this.addSerializer(LocalTime.class, new LocalTimeSerializer(LnTimeUtil.timeFormatter));
        this.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(LnTimeUtil.dateTimeFormatter));
        this.addDeserializer(LocalDate.class, new LocalDateDeserializer(LnTimeUtil.dateFormatter));
        this.addDeserializer(LocalTime.class, new LocalTimeDeserializer(LnTimeUtil.timeFormatter));
    }
}