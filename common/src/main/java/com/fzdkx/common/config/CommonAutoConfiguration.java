package com.fzdkx.common.config;

import com.fzdkx.common.exception.ExceptionCatch;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
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

    // OpenAPI swagger2
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("看星头条API文档")
                        .version("1.0")
                        .contact(new Contact().name("fzdkx").email("test@gmail.com"))
                        .description("看星头条API文档"));
    }
}
