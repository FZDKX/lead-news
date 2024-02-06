package com.fzdkx.user.service;

import com.fzdkx.model.common.vo.Result;
import com.fzdkx.model.user.dto.ApUserLoginDto;

/**
 * @author 发着呆看星
 * @create 2024/2/3
 */
public interface UserLoginService {
    Result<String> login(ApUserLoginDto apUserLoginDto);
}
