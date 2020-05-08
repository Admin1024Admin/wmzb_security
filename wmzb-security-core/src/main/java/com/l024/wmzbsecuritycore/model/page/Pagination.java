package com.l024.wmzbsecuritycore.model.page;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页数据
 */
public class Pagination {
    private int pageNum; //第几页
    private int pageSize;//页数条数
    private long totalCount;//总条数
    private List<Integer> pages = new ArrayList(); //页面列表 如，1,2,3,4,5

    //构建页数
    public Pagination(Integer pageSize, Integer pageNum, Long totalCount){
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        //添加前面的页数
        for(int i=1;i<=pageNum;i++){
            pages.add(i);
        }
        //添加后面的页面
        //计算总页面
        Long pageCount = totalCount/pageSize+((totalCount%pageSize==0)?0:1);
        if(pageCount>pageNum){
            for(int i=pageNum+1;i<pageCount;i++){
                pages.add(i);
            }
        }
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public List<Integer> getPages() {
        return pages;
    }

    public void setPages(List<Integer> pages) {
        this.pages = pages;
    }
}
