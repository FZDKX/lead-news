package com.fzdkx.model.user.bean;

import lombok.Data;

import java.util.Date;

/**
 * @author 发着呆看星
 * @create 2024/2/3
 */
@Data
public class ApUser {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 密码、通信等加密盐
     */
    private String salt;

    /**
     * 用户名
     */
    private String name;

    /**
     * 密码,md5加密
     */
    private String password;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 头像
     */
    private String image;

    /**
     * 0 男
     * 1 女
     * 2 未知
     */
    private Boolean sex;

    /**
     * 0 未
     * 1 是
     */
    private Boolean isCertification;

    /**
     * 是否身份认证
     */
    private Boolean isIdentityAuthentication;

    /**
     * 0正常
     * 1锁定
     */
    private Boolean status;

    /**
     * 0 普通用户
     * 1 自媒体人
     * 2 大V
     */
    private Short flag;

    /**
     * 注册时间
     */
    private Date createdTime;
}
