package com.fzdkx.model.media.vo;

import com.fzdkx.model.media.bean.MediaUser;
import lombok.Data;

/**
 * @author 发着呆看星
 * @create 2024/2/6
 */
@Data
public class MediaLoginVo {
    private String token;
    private MediaUser user;
}
