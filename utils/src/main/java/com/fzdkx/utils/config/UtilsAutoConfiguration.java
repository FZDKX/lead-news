package com.fzdkx.utils.config;

import com.fzdkx.utils.JWTUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 发着呆看星
 * @create 2024/2/3
 */
@Configuration
public class UtilsAutoConfiguration {
    @Bean
    public JWTUtils jwtUtils() {
        return new JWTUtils();
    }
}
