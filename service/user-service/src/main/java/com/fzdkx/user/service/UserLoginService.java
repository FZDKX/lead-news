package com.fzdkx.user.service;

import com.fzdkx.model.common.dto.Result;
import com.fzdkx.model.user.dto.UserLoginDto;

/**
 * @author 发着呆看星
 * @create 2024/2/3
 */
public interface UserLoginService {
    Result<String> login(UserLoginDto userLoginDto);
}
