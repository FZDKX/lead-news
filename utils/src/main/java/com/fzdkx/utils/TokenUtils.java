package com.fzdkx.utils;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author 发着呆看星
 * @create 2024/2/4
 */
@Component
@Data
public class TokenUtils {
    // 过期时间(单位消息)，默认两小时
    @Value("${token.timeOut}")
    private Integer TIME_OUT = 2;
}
