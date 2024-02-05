package com.fzdkx.file.config;

import com.fzdkx.file.service.FileStorageService;
import com.fzdkx.file.service.impl.FileStorageServiceImpl;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 发着呆看星
 * @create 2024/2/5
 */
@Configuration
@EnableConfigurationProperties({MinIOConfigProperties.class})
public class MinIOAutoConfig {
    @Autowired
    private MinIOConfigProperties properties;

    @Bean
    public MinioClient minioClient(){
        return MinioClient
                .builder()
                .credentials(properties.getAccessKey(), properties.getSecretKey())
                .endpoint(properties.getEndpoint())
                .build();
    }

    @Bean
    public FileStorageService fileStorageService(MinIOConfigProperties minIOConfigProperties,
                                                     MinioClient minioClient){
        FileStorageServiceImpl fileStorageService = new FileStorageServiceImpl();
        fileStorageService.setProperties(minIOConfigProperties);
        fileStorageService.setMinioClient(minioClient);
        return fileStorageService;
    }
}
