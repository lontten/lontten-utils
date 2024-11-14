package com.lontten.util.page;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lontten.util.obj.BeanCopyUtil;

import java.util.List;

public class MppUtil {


    public static <T> Page<T> createPageResult(long pageIndex, long pageSize, List<T> list, long total) {
        long pages = total / pageSize;
        long l = total % pageSize;
        if (l > 0) {
            pages++;
        }
        Page<T> page1 = new Page<>(pageIndex, pageSize);
        page1.setTotal(total);
        page1.setPages(pages);
        page1.setRecords(list);
        return page1;
    }


    public static <T> Page<T> createPageResult(long pageIndex, long pageSize, List<T> list) {
        Page<T> page1 = new Page<>(pageIndex, pageSize);
        page1.setTotal(list.size());
        page1.setPages(1);
        page1.setRecords(list);
        return page1;
    }

    /**
     * 替换分页结果
     *
     * @param page
     * @param list
     * @param <T>
     * @return
     */
    public static <T, K> Page<K> replacePageResult(Page<T> page, List<K> list) {
        Page<K> page1 = new Page<>();
        BeanCopyUtil.copyAll(page, page1);
        page1.setRecords(list);
        return page1;
    }
}
