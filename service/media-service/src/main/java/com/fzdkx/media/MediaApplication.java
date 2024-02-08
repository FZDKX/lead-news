package com.fzdkx.media;

import com.fzdkx.apis.article.IArticleClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author 发着呆看星
 * @create 2024/2/6
 */
@SpringBootApplication
@MapperScan("com.fzdkx.media.mapper")
@EnableFeignClients(clients = {IArticleClient.class})
@EnableAsync
public class MediaApplication {
    public static void main(String[] args) {
        SpringApplication.run(MediaApplication.class,args);
    }
}
