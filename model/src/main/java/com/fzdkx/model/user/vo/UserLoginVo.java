package com.fzdkx.model.user.vo;

import com.fzdkx.model.user.bean.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 发着呆看星
 * @create 2024/2/3
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginVo {
    private String token;
    private User user;
}
