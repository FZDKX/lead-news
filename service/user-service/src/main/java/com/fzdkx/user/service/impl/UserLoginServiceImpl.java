package com.fzdkx.user.service.impl;

import com.fzdkx.model.common.dto.Result;
import com.fzdkx.model.common.enums.AppHttpCodeEnum;
import com.fzdkx.model.user.bean.User;
import com.fzdkx.model.user.dto.UserLoginDto;
import com.fzdkx.model.user.vo.UserLoginVo;
import com.fzdkx.user.mapper.UserMapper;
import com.fzdkx.user.service.UserLoginService;
import com.fzdkx.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author 发着呆看星
 * @create 2024/2/3
 */
@Service
public class UserLoginServiceImpl implements UserLoginService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JWTUtils jwtUtils;

    @Override
    public Result login(UserLoginDto userLoginDto) {
        UserLoginVo userLoginVo = new UserLoginVo();
        // 手机号或密码为空，则为游客登录
        if (!StringUtils.hasLength(userLoginDto.getPhone()) || !StringUtils.hasLength(userLoginDto.getPassword())) {
            String token = jwtUtils.getToken(0L);
            userLoginVo.setToken(token);
            return Result.success(userLoginVo);
        }
        // 根据手机号查询用户
        User user = userMapper.getUserByPhone(userLoginDto.getPhone());
        // 用户不存在
        if (user == null) {
            return Result.error(AppHttpCodeEnum.LOGIN_USER_EXIST);
        }
        // 用户存在，比对密码
        if (!userLoginDto.getPassword().equals(user.getPassword())) {
            return Result.error(AppHttpCodeEnum.LOGIN_PASSWORD_ERROR);
        }
        // 密码正确，获取token，返回
        String token = jwtUtils.getToken(user.getId());
        // 屏蔽敏感字段
        user.setSalt("");
        user.setPassword("");
        // 设置响应结果
        userLoginVo.setUser(user);
        userLoginVo.setToken(token);
        return Result.success(userLoginVo);
    }
}
