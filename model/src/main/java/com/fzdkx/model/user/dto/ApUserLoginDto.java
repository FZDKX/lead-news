package com.fzdkx.model.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 发着呆看星
 * @create 2024/2/3
 */
@Data
@Schema(name = "用户登录请求实体类")
public class ApUserLoginDto {
    @Schema(name = "手机号")
    private String phone;
    @Schema(name = "密码")
    private String password;
}
