package com.lontten.util.spring;

import cn.hutool.extra.servlet.ServletUtil;

import javax.servlet.http.HttpServletRequest;

public class IpUtil {
    public static String getIpAddress(HttpServletRequest request) {
        return ServletUtil.getClientIP(request);
    }
}
