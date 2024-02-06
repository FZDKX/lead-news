package com.fzdkx.model.common.dto;

import lombok.Data;

/**
 * @author 发着呆看星
 * @create 2024/2/6
 */
@Data
public class PageRequestDto {
    private Integer page;
    private Integer size;
    public void checkParam() {
        if (this.page == null || this.page < 0) {
            setPage(1);
        }
        if (this.size == null || this.size < 0 || this.size > 100) {
            setSize(10);
        }
    }
}
