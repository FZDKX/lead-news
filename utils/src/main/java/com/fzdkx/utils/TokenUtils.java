package com.fzdkx.utils;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;

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

    public String getUserId(String token){
        int index = token.lastIndexOf(":");
        return token.substring(index + 1);
    }

    public String getToken(Long id){
        String uuid = UUID.randomUUID() + ":";
        // 密码正确，获取token，返回
        return uuid + id;
    }
}
