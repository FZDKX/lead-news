package com.fzdkx.minio;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @author 发着呆看星
 * @create 2024/2/5
 */
@SpringBootTest
public class MinIoTest {
    @Test
    public void uploadTest(){
        FileInputStream fileInputStream = null;
        try {
            // 创建文件输入流，获取要上传的文件
            fileInputStream = new FileInputStream("D://list.html");

            // 创建 minio连接客户端
            MinioClient client = MinioClient.builder()
                    .credentials("minio@123", "minio@123")
                    .endpoint("http://192.168.16.166:9000")
                    .build();

            // 上传
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .object("list.html")
                    .contentType("text/html")
                    .bucket("leadnews")
                    .stream(fileInputStream, fileInputStream.available(), -1)
                    .build();
            client.putObject(putObjectArgs);

            System.out.println("上传成功");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
