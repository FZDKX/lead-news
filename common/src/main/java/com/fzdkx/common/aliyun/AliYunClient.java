package com.fzdkx.common.aliyun;

import com.aliyun.imageaudit20191230.Client;
import com.aliyun.teaopenapi.models.Config;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author 发着呆看星
 * @create 2024/2/7
 */
@ConfigurationProperties(prefix = "ali")
@Data
public class AliYunClient {
    private String accessKeyId;
    private String accessKeySecret;

    public Client getClient() throws Exception {
        Config config = new Config()
                .setAccessKeyId(accessKeyId)
                .setAccessKeySecret(accessKeySecret);
        config.endpoint = "imageaudit.cn-shanghai.aliyuncs.com";
        return new Client(config);
    }

}
