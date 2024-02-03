package com.fzdkx.common.exception;

import com.fzdkx.model.common.enums.AppHttpCodeEnum;

/**
 * @author 发着呆看星
 * @create 2024/2/3
 * 自定义异常顶级类
 */
public class CustomException extends RuntimeException{
    private AppHttpCodeEnum appHttpCodeEnum;

    public CustomException(AppHttpCodeEnum appHttpCodeEnum) {
        super(appHttpCodeEnum.getMessage());
        this.appHttpCodeEnum = appHttpCodeEnum;
    }

    public AppHttpCodeEnum getAppHttpCodeEnum() {
        return appHttpCodeEnum;
    }
}
