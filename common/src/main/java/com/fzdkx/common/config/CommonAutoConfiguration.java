package com.fzdkx.common.config;

import com.fzdkx.common.exception.ExceptionCatch;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 发着呆看星
 * @create 2024/2/3
 */
@Configuration
public class CommonAutoConfiguration {

    @Bean
    public ExceptionCatch exceptionCatch() {
        return new ExceptionCatch();
    }
}
