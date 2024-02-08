package com.fzdkx.model.common.enums;

/**
 * @author 发着呆看星
 * @create 2024/2/3
 * 应用状态码枚举类
 */
public enum AppHttpCodeEnum {
    // 成功操作
    SUCCESS(200, "操作成功"),
    // 登录操作
    NEED_LOGIN(1, "需要登录后操作"),
    LOGIN_PASSWORD_ERROR(2, "密码错误"),
    LOGIN_USER_EXIST(3, "用户不存在"),
    APP_LOGIN_EMPTY(4, "手机号或密码为空"),
    // token判断
    TOKEN_INVALID(50, "无效的TOKEN"),
    TOKEN_EXPIRE(51, "TOKEN已过期"),
    TOKEN_REQUIRE(52, "必须携带TOKEN"),
    // SIGN验签 100~120
    SIGN_INVALID(100, "无效的SIGN"),
    SIG_TIMEOUT(101, "SIGN已过期"),
    // 参数错误 500~1000
    PARAM_REQUIRE(500, "缺少参数"),
    PARAM_INVALID(501, "无效参数"),
    PARAM_IMAGE_FORMAT_ERROR(502, "图片格式有误"),
    SERVER_ERROR(503, "服务器内部错误"),
    // 数据错误 1000~2000
    DATA_EXIST(1000, "数据已经存在"),
    AP_USER_DATA_NOT_EXIST(1001, "ApUser数据不存在"),
    DATA_NOT_EXIST(1002, "数据不存在"),
    // 权限错误 3000~3500
    NO_OPERATOR_AUTH(3000, "无权限操作"),
    NEED_ADMIN(3001, "需要管理员权限"),
    // media错误
    MEDIA_LOGIN_EMPTY(4001, "账号或密码为空"),
    MEDIA_LOGIN_ERROR(4002, "用户名或密码错误"),
    // 文件操作错误
    FILE_UPLOAD_ERROR(5001, "文件上传失败"),
    CREATE_HTML_ERROR(5002, "html文件生成错误"),
    MATERIAL_REFERENCE_FAIL(5002,"素材已被删除" ),
    ARTICLE_PUBLISH_ERROR(5003,"文章发布失败" ),
    // aliyun 操作错误
    ALIYUN_ERROR(6001,"文件检测失败" );
    int code;
    String message;

    AppHttpCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
