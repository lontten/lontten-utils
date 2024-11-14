package com.lontten.util.obj;

import cn.hutool.core.bean.copier.CopyOptions;

public class BeanCopyUtil {
    //只复制非空字段
    public static void copySoft(Object source, Object target, String... ignore) {
        cn.hutool.core.bean.BeanUtil.copyProperties(
                source,
                target,
                CopyOptions.create()
                        .setIgnoreProperties(ignore)
                        .setIgnoreNullValue(true)
                        .setIgnoreError(true));
    }

    //完全复制，null字段，也复制
    public static void copyAll(Object source, Object target, String... ignore) {
        cn.hutool.core.bean.BeanUtil.copyProperties(
                source,
                target,
                CopyOptions.create()
                        .setIgnoreProperties(ignore)
                        .setIgnoreError(true));
    }
}

