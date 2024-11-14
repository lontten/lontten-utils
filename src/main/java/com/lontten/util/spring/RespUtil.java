package com.lontten.util.spring;


import com.lontten.util.json.LnJsonUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class RespUtil {

    public static void json(HttpServletResponse resp, Object o) throws IOException {
        if (resp == null) {
            return;
        }
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter out = resp.getWriter();
        String s = LnJsonUtil.bean2json(o);
        if (s == null) {
            s = "";
        }
        out.write(s);
        out.flush();
        out.close();
    }
}
