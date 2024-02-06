package com.fzdkx.media.service.impl;

import com.fzdkx.common.exception.CustomException;
import com.fzdkx.media.mapper.MediaUserMapper;
import com.fzdkx.media.service.MediaLoginService;
import com.fzdkx.model.common.vo.Result;
import com.fzdkx.model.common.enums.AppHttpCodeEnum;
import com.fzdkx.model.media.bean.MediaUser;
import com.fzdkx.model.media.dto.MediaLoginDto;
import com.fzdkx.model.media.vo.MediaLoginVo;
import com.fzdkx.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author 发着呆看星
 * @create 2024/2/6
 */
@Service
public class MediaLoginServiceImpl implements MediaLoginService {
    @Autowired
    private MediaUserMapper mediaUserMapper;
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private StringRedisTemplate template;
    private final String TOKEN_PREFIX = "token:media:";
    @Override
    public Result<MediaLoginVo> login(MediaLoginDto dto) {
        // 参数校验
        if (!StringUtils.hasLength(dto.getName()) || !StringUtils.hasLength(dto.getPassword())){
            throw new CustomException(AppHttpCodeEnum.MEDIA_LOGIN_EMPTY);
        }
        // 查询数据库
        MediaUser user = mediaUserMapper.selectUserByName(dto.getName());
        // 获取用户输入的密码
        String password = dto.getPassword();
        // 盐
        String salt = user.getSalt();
        // 加盐后加密的密码
        String pswd = DigestUtils.md5DigestAsHex((password + salt).getBytes());
        // 密码校验
        if (!pswd.equals(user.getPassword())){
            throw new CustomException(AppHttpCodeEnum.MEDIA_LOGIN_ERROR);
        }
        // 记录登录时间
        user.setLoginTime(new Date());
        // 修改登录时间
        mediaUserMapper.updateLoginTime(user);
        // 数据脱敏
        user.setPassword("");
        user.setSalt("");
        // 生成token
        String token = tokenUtils.getToken(user.getId());
        // 设置Token进入Redis
        template.opsForValue().set(TOKEN_PREFIX+token,String.valueOf(user.getId()),tokenUtils.getTIME_OUT() * 2, TimeUnit.HOURS);
        // 封装数据返回
        MediaLoginVo mediaLoginVo = new MediaLoginVo();
        mediaLoginVo.setUser(user);
        mediaLoginVo.setToken(token);
        return Result.success(mediaLoginVo);
    }
}
