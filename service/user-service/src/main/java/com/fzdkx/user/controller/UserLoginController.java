package com.fzdkx.user.controller;

import com.fzdkx.model.common.dto.Result;
import com.fzdkx.model.user.dto.UserLoginDto;
import com.fzdkx.user.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 发着呆看星
 * @create 2024/2/3
 */
@RestController
@RequestMapping("/api/v1/login")
public class UserLoginController {

    @Autowired
    private UserLoginService userLoginService;

    @PostMapping("/login_auth")
    public Result<String> login(@RequestBody UserLoginDto userLoginDto) {
        return userLoginService.login(userLoginDto);
    }
}
