package com.zjht.manager.util;

/**
 * ${DESCRIPTION}
 *
 * @outhor caozk
 * @create 2017-09-05 20:41
 */
public class PageUtil {
    /**
     * 检查页码 checkPageNo
     *
     * @param pageNum
     * @return if pageNo==null or pageNo<1 then return 1 else return pageNo
     *
     */
    private static int DEFAULT_PAGE_SIZE = 20;

    public static int cpn(Integer pageNum) {
        return (pageNum == null || pageNum < 1) ? 1 : pageNum;
    }

    public static int pageSize(Integer pageSize) {
        return (pageSize == null || pageSize < 1) ? DEFAULT_PAGE_SIZE : pageSize;
    }
}
