package com.fzdkx.media.mapper;

import com.fzdkx.model.media.bean.MediaUser;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * @author 发着呆看星
 * @create 2024/2/6
 */
public interface MediaUserMapper {

    MediaUser selectUserByName(@Param("name") String name);
    void updateLoginTime(@Param("user") MediaUser user);
}
