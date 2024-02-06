package com.fzdkx.media;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 发着呆看星
 * @create 2024/2/6
 */
@SpringBootApplication
@MapperScan("com.fzdkx.media.mapper")
public class MediaApplication {
    public static void main(String[] args) {
        SpringApplication.run(MediaApplication.class,args);
    }
}
