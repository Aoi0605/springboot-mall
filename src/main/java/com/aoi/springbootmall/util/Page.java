package com.aoi.springbootmall.util;

import java.util.List;

//泛型 T 屬於進階用法，要另外學。
public class Page<T> {

    private Integer limit;
    private Integer offset;

    //資料總筆數的變數
    private Integer total;

    //存放所查詢出的商品數據
    private List<T> Results;

    public List<T> getResults() {
        return Results;
    }

    public void setResults(List<T> results) {
        Results = results;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
