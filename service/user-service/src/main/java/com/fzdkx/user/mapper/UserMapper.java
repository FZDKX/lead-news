package com.fzdkx.user.mapper;

import com.fzdkx.model.user.bean.User;
import org.apache.ibatis.annotations.Param;

/**
 * @author 发着呆看星
 * @create 2024/2/3
 */
public interface UserMapper {
    User getUserByPhone(@Param("phone") String phone);
}
