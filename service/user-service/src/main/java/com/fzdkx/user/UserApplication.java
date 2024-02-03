package com.fzdkx.user;

import com.fzdkx.user.service.UserLoginService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author 发着呆看星
 * @create 2024/2/3
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.fzdkx.user.mapper")
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}
