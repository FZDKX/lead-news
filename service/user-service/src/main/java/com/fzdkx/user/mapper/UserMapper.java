package com.fzdkx.user.mapper;

import com.fzdkx.model.user.bean.ApUser;
import org.apache.ibatis.annotations.Param;

/**
 * @author 发着呆看星
 * @create 2024/2/3
 */
public interface UserMapper {
    ApUser getUserByPhone(@Param("phone") String phone);
}
