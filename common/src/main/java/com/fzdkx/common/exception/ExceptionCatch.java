package com.fzdkx.common.exception;

import com.fzdkx.model.common.vo.Result;
import com.fzdkx.model.common.enums.AppHttpCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 发着呆看星
 * @create 2024/2/3
 * 自定义异常处理器
 */
@ControllerAdvice
@Slf4j
public class ExceptionCatch {
    /**
     * 处理自定义异常
     */
    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public Result<String> exception(CustomException e) {
        e.printStackTrace();
        log.error("捕获到异常：{}", e.getMessage());
        return Result.error(e.getAppHttpCodeEnum());
    }

    /**
     * 处理非自定义异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result<String> exception(Exception e){
        // 打印异常堆栈信息
        e.printStackTrace();
        log.error("捕获到系统异常：{}",e.getMessage());
        return Result.error(AppHttpCodeEnum.SERVER_ERROR);
    }
}
