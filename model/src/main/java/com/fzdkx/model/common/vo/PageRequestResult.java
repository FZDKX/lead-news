package com.fzdkx.model.common.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 发着呆看星
 * @create 2024/2/6
 */
@Data

public class PageRequestResult<T> extends Result<T> implements Serializable {
    private Integer size;
    private Long total;
    private Integer currentPage;

    public PageRequestResult(Integer size, Integer currentPage, Long total) {
        this.size = size;
        this.total = total;
        this.currentPage = currentPage;
    }

}
