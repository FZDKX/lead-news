package com.fzdkx.model.common.vo;

import com.fzdkx.model.common.enums.AppHttpCodeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 发着呆看星
 * @create 2024/2/3
 * 统一响应结果
 */
@Data
@Schema
public class Result<T> implements Serializable {
    private String host;
    @Schema(name = "响应状态码")
    private Integer code;
    @Schema(name = "响应消息")
    private String errorMessage;
    @Schema(name = "响应数据")
    private T data;

    /**
     * @param code         状态码
     * @param errorMessage 提示信息
     */
    public static Result<String> error(int code, String errorMessage) {
        Result<String> result = new Result<>();
        result.setCode(code);
        result.setErrorMessage(errorMessage);
        return result;
    }

    /**
     * @param appHttpCodeEnum 已规定的枚举状态信息
     */
    public static Result<String> error(AppHttpCodeEnum appHttpCodeEnum) {
        return error(appHttpCodeEnum.getCode(), appHttpCodeEnum.getMessage());
    }

    /**
     * 使用已有状态码，自定义异常信息
     * @param appHttpCodeEnum 已规定的枚举状态信息
     */
    public static Result<String> error(AppHttpCodeEnum appHttpCodeEnum, String errorMessage) {
        return error(appHttpCodeEnum.getCode(), errorMessage);
    }

    /**
     * 成功响应，不携带响应体
     */
    public static <T> Result<T> success() {
        Result<T> result = new Result<>();
        result.setCode(AppHttpCodeEnum.SUCCESS.getCode());
        result.setErrorMessage(AppHttpCodeEnum.SUCCESS.getMessage());
        return result;
    }

    /**
     * 成功响应，不携带响应体
     * 自定义成功响应信息
     */
    public static <T> Result<T> success(String message) {
        Result<T> result = new Result<>();
        result.setCode(AppHttpCodeEnum.SUCCESS.getCode());
        result.setErrorMessage(message);
        return result;
    }

    /**
     * 成功响应，携带响应体
     * @param data 响应体
     */
    public static <T> Result<T> success(T data) {
        Result<T> result = success();
        result.setData(data);
        return result;
    }

}
