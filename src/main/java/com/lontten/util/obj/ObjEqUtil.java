package com.lontten.util.obj;

import com.lontten.common.util.lang.LnUUIDUtil;

import java.util.Objects;
import java.util.UUID;

public class ObjEqUtil {

    /**
     * 判断两个对象是否相等
     * 当 为 Integer Long 时，会先转为字符串，再进行比较
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean isEqual(String a, Object b) {
        if (b instanceof String) {
            return a.equals(b);
        }
        if (b instanceof Integer) {
            return a.equals(b.toString());
        }
        if (b instanceof Long) {
            return a.equals(b.toString());
        }
        if (b instanceof UUID) {
            return LnUUIDUtil.formStringToUUID(a).equals(b);
        }
        return Objects.equals(a, b);
    }

}
