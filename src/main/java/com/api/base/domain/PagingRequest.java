package com.api.base.domain;

import com.api.base.utils.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;

public class PagingRequest {

    private int offset = 0;

    private int limit = Constants.PAGE_SIZE_MAX;

    private String sort;

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public Sort getSort(Sort sortDefault) {
        if (StringUtils.isAllBlank(sort)) {
            return sortDefault;
        }
        String[] sortArr = sort.split(":");
        return Sort.by(Sort.Direction.valueOf(sortArr[1].toUpperCase()), sortArr[0]);
    }
}
