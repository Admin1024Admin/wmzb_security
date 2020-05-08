package com.l024.wmzbsecuritycore.model.page;

/**
 * 分页参数
 */
public class PageParams {
    private static final Integer PAGE_SIZE = 2;
    private Integer pageSize;
    private Integer pageNum;
    private Integer offSet;
    private Integer limit;

    public static PageParams build(Integer pageSize, Integer pageNum){
        if(pageSize==null){
            pageSize = PAGE_SIZE;
        }
        if(pageNum==null){
            pageNum = 1;
        }
        return new PageParams(pageSize,pageNum);
    }

    public PageParams() {
        this(PAGE_SIZE,1);
    }

    public PageParams(Integer pageSize, Integer pageNum) {
        this.pageSize = pageSize;
        this.pageNum = pageNum;
        this.offSet = pageSize*(pageNum-1);
        this.limit = pageSize;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getOffSet() {
        return offSet;
    }

    public void setOffSet(Integer offSet) {
        this.offSet = offSet;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
