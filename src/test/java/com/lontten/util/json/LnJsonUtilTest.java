package com.lontten.util.json;

import com.lontten.util.json.redis.AutoTypeObjectMapperUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class LnJsonUtilTest {

    @Test
    void bean2jsonStr() {
        DemoUser demoUser = new DemoUser();
        demoUser.setId(1L);
        demoUser.setAge(18);
        demoUser.setName("lontten");
        demoUser.setBool(true);
        demoUser.setUuid(UUID.randomUUID());
        demoUser.setDate(LocalDate.now());
        demoUser.setDateTime(LocalDateTime.now());
        demoUser.setTime(LocalTime.now());
        DemoUser demoUser2 = new DemoUser();
        demoUser2.setId(2L);
        demoUser2.setAge(222);
        demoUser2.setName("222");
        demoUser2.setBool(false);
        demoUser2.setUuid(UUID.randomUUID());
        demoUser2.setDate(LocalDate.now());
        demoUser2.setDateTime(LocalDateTime.now());
        demoUser2.setTime(LocalTime.now());

        ArrayList<DemoUser> demoUsers = new ArrayList<>();
        demoUsers.add(demoUser);
        demoUsers.add(demoUser2);

        LnNode node = LnJsonUtil.createNode();
        node.put("user", demoUser);
        node.put("list", demoUsers);

        LnNode lnNode1 = node.deepCopy();
        LnNode lnNode2 = node.deepCopy();

        ArrayList<LnNode> lnNodes = new ArrayList<>();
        lnNodes.add(lnNode1);
        lnNodes.add(lnNode2);

        node.put("list2", lnNodes);


//        --
        DemoUser demoUser1 = node.get("user", DemoUser.class);
        System.out.println(demoUser1);
        List<DemoUser> list = node.getList("list", DemoUser.class);
        list.forEach(System.out::println);
        List<LnNode> list2 = node.getList("list2");
        list2.forEach(System.out::println);


        String json = LnJsonUtil.bean2jsonStr(node);
        System.out.println(json);
    }

    /**
     * 纯json，没有类型信息
     */
    @Test
    void jsonNoType() {
        String json = "{\"name\":\"lontten\"}";

        LnNode node = LnJsonUtil.createNode();
        node.put("name", "lontten");

        String s = LnJsonUtil.bean2jsonStr(node);
        Assertions.assertEquals(json, s);

        LnNode node1 = LnJsonUtil.jsonStr2node(json);
        Assertions.assertEquals(node.getString("name"), node1.getString("name"));
    }

    /**
     * json，有类型信息
     */
    @Test
    void jsonType() {
        String json = """
                ["com.lontten.util.json.LnNode",{"value":{"name":"lontten"}}]""";

        LnNode node = LnJsonUtil.createNode();
        node.put("name", "lontten");

        String s = AutoTypeObjectMapperUtil.bean2jsonStr(node);
        Assertions.assertEquals(json, s);

        LnNode node1 = (LnNode) AutoTypeObjectMapperUtil.jsonStr2bean(json);
        Assertions.assertEquals(node.getString("name"), node1.getString("name"));
    }
}