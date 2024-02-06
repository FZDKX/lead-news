package com.fzdkx.user.controller;

import com.fzdkx.model.common.vo.Result;
import com.fzdkx.model.user.dto.ApUserLoginDto;
import com.fzdkx.user.service.UserLoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 发着呆看星
 * @create 2024/2/3
 */
@RestController
@RequestMapping("/api/v1/login")
@Tag(name = "用户登录控制类")
public class UserLoginController {

    @Autowired
    private UserLoginService userLoginService;

    @PostMapping("/login_auth")
    @Operation(summary = "用户登录方法")
    public Result<String> login(@RequestBody ApUserLoginDto apUserLoginDto) {
        return userLoginService.login(apUserLoginDto);
    }
}
