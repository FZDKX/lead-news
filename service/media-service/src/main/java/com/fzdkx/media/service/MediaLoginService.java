package com.fzdkx.media.service;

import com.fzdkx.model.common.dto.Result;
import com.fzdkx.model.media.dto.MediaLoginDto;
import com.fzdkx.model.media.vo.MediaLoginVo;

/**
 * @author 发着呆看星
 * @create 2024/2/6
 */
public interface MediaLoginService {
    Result<MediaLoginVo> login(MediaLoginDto mediaLoginDto);
}
