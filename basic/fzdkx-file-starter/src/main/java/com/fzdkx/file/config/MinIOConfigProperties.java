package com.fzdkx.file.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author 发着呆看星
 * @create 2024/2/5
 */
@Data
@ConfigurationProperties(prefix = "minio")
public class MinIOConfigProperties {
    // 账户
    private String accessKey;
    // 密码
    private String secretKey;
    // 桶
    private String bucket;
    // minio服务端 ip + port
    private String endpoint;
    // minio存储 ip + port
    private String readPath;
    // 这两个目前是一样的
}
