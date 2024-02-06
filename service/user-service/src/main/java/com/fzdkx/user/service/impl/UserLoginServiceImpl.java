package com.fzdkx.user.service.impl;

import com.fzdkx.model.common.dto.Result;
import com.fzdkx.model.common.enums.AppHttpCodeEnum;
import com.fzdkx.model.user.bean.ApUser;
import com.fzdkx.model.user.dto.ApUserLoginDto;
import com.fzdkx.model.user.vo.ApUserLoginVo;
import com.fzdkx.user.mapper.UserMapper;
import com.fzdkx.user.service.UserLoginService;
import com.fzdkx.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * @author 发着呆看星
 * @create 2024/2/3
 */
@Service
public class UserLoginServiceImpl implements UserLoginService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private TokenUtils tokenUtils;
    private final String TOKEN_PREFIX = "token:user:";
    private final String TOURIST_ID = "0";

    @Override
    public Result login(ApUserLoginDto apUserLoginDto) {
        ApUserLoginVo apUserLoginVo = new ApUserLoginVo();
        // 手机号和密码为空，则为游客登录
        if (!StringUtils.hasLength(apUserLoginDto.getPhone()) && !StringUtils.hasLength(apUserLoginDto.getPassword())) {
            String token = tokenUtils.getToken(0L);
            apUserLoginVo.setToken(token);
            redisTemplate.opsForValue().set(TOKEN_PREFIX + token, TOURIST_ID, tokenUtils.getTIME_OUT() * 2, TimeUnit.HOURS);
            return Result.success(apUserLoginVo);
        }
        // 手机号或密码为空，返回错误
        if (!StringUtils.hasLength(apUserLoginDto.getPhone()) || !StringUtils.hasLength(apUserLoginDto.getPassword())) {
            return Result.error(AppHttpCodeEnum.APP_LOGIN_EMPTY);
        }
        // 根据手机号查询用户
        ApUser apUser = userMapper.getUserByPhone(apUserLoginDto.getPhone());
        // 用户不存在
        if (apUser == null) {
            return Result.error(AppHttpCodeEnum.LOGIN_USER_EXIST);
        }
        // 用户存在，比对密码
        // 获取用户输入的密码
        String password = apUserLoginDto.getPassword();
        // 盐
        String salt = apUser.getSalt();
        // 加盐后加密的密码
        String pswd = DigestUtils.md5DigestAsHex((password + salt).getBytes());
        if (!pswd.equals(apUser.getPassword())) {
            return Result.error(AppHttpCodeEnum.LOGIN_PASSWORD_ERROR);
        }
        String token = tokenUtils.getToken(apUser.getId());
        redisTemplate.opsForValue().set(TOKEN_PREFIX + token, String.valueOf(apUser.getId()), tokenUtils.getTIME_OUT() * 2, TimeUnit.HOURS);
        // 屏蔽敏感字段
        apUser.setSalt("");
        apUser.setPassword("");
        // 设置响应结果
        apUserLoginVo.setApUser(apUser);
        apUserLoginVo.setToken(token);
        return Result.success(apUserLoginVo);
    }
}
