package com.fzdkx.file.service.impl;

import com.fzdkx.file.config.MinIOConfigProperties;
import com.fzdkx.file.service.FileStorageService;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.errors.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.util.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author 发着呆看星
 * @create 2024/2/5
 */
@Slf4j
@Data
public class FileStorageServiceImpl implements FileStorageService {

    private MinioClient minioClient;

    private MinIOConfigProperties properties;

    private final String separator = "/";

    /**
     * @param dirPath  文件路径
     * @param filename 文件名
     * @return 完整路径 dirPath/ + yyyy/MM/dd/ + filename
     */
    public String builderFilePath(String dirPath, String filename) {
        // 构建文件路径
        // 创建StringBuilder
        StringBuilder stringBuilder = new StringBuilder(50);
        // 如果 dirPath 不为 null
        if (StringUtils.hasLength(dirPath)) {
            // 拼接路径
            stringBuilder.append(dirPath).append(separator);
        }
        // 格式化日期 SimpleDateFormat
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        // 使用当前时间，进行日期格式化，作为路径
        String todayStr = sdf.format(new Date());
        // dirPath/ + yyyy/MM/dd/ + filename
        stringBuilder.append(todayStr).append(separator);
        stringBuilder.append(filename);
        return stringBuilder.toString();
    }

    /**
     * 上传图片
     *
     * @param prefix      文件前缀
     * @param filename    文件名
     * @param inputStream 文件流
     * @return 图片路径 readPath + /bucket + /filepath
     */
    @Override
    public String uploadImgFile(String prefix, String filename, InputStream inputStream) {
        // 构建文件路径
        String filePath = builderFilePath(prefix, filename);
        // 上传文件
        try {
            // 构建上传文件对象
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .object(filePath)   // 文件名
                    .contentType("image/jpg") // 文件类型
                    .bucket(properties.getBucket()) // 文件所在桶
                    .stream(inputStream, inputStream.available(), -1) // 整合原文件
                    .build();
            return putObject(filePath, putObjectArgs);
        } catch (Exception e) {
            log.error("minio put file error.", e);
            throw new RuntimeException("上传文件失败");
        }
    }

    /**
     * @param prefix      文件前缀
     * @param filename    文件名
     * @param inputStream 文件流
     * @return html文件路径 readPath + /bucket + /filepath
     */
    @Override
    public String uploadHtmlFile(String prefix, String filename, InputStream inputStream) {
        // 构建文件路径
        String filePath = builderFilePath(prefix, filename);
        try {
            // 构建删除文件对象
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .object(filePath)
                    .contentType("text/html")
                    .bucket(properties.getBucket())
                    .stream(inputStream, inputStream.available(), -1)
                    .build();
            return putObject(filePath, putObjectArgs);
        } catch (Exception e) {
            log.error("minio put file error.", e);
            throw new RuntimeException("上传文件失败");
        }
    }

    @NotNull
    private String putObject(String filePath, PutObjectArgs putObjectArgs) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException {
        minioClient.putObject(putObjectArgs);
        StringBuilder urlPath = new StringBuilder(properties.getReadPath());
        urlPath.append(separator);
        urlPath.append(properties.getBucket());
        urlPath.append(separator);
        urlPath.append(filePath);
        return urlPath.toString();
    }

    @Override
    public void delete(String pathUrl) {
        // 将pathUrl中的 ip+port+/ 删除
        String key = pathUrl.replace(properties.getEndpoint() + "/", "");
        // 获取 / 第一次出现的位置 ，即为bucket后的 /
        int index = key.indexOf(separator);
        // 获取 bucket [ )
        String bucket = key.substring(0, index);
        // 获取文件路径，不带 /
        String filePath = key.substring(index + 1);
        // 删除文件
        // 构建删除文件对象
        RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder()
                .bucket(bucket)
                .object(filePath)
                .build();
        try {
            minioClient.removeObject(removeObjectArgs);
        } catch (Exception e) {
            log.error("minio remove file error.  pathUrl:{}", pathUrl);
            throw new RuntimeException("上传删除失败");
        }

    }

    /**
     * @param pathUrl 文件全路径
     * @return 文件字节流
     */
    @Override
    public byte[] downLoadFile(String pathUrl) {
        // 去除 ip + port + /
        String key = pathUrl.replace(properties.getEndpoint() + "/", "");
        // 获取第一个 /
        int index = key.indexOf(separator);
        // 获取bucket
        String bucket = key.substring(0, index);
        // 获取 filePath
        String filePath = key.substring(index + 1);
        // 下载文件
        InputStream inputStream = null;
        try {
            inputStream = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucket)
                            .object(filePath)
                            .build()
            );
        } catch (Exception e) {
            log.error("minio down file error.  pathUrl:{}", pathUrl);
            throw new RuntimeException("上传下载失败");
        }
        // 将下载文件转换成 byte[]
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] bytes = new byte[100];
        int i = 0;
        while (true) {
            try {
                if (!((i = inputStream.read(bytes, 0, 100)) > 0)) {
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            baos.write(bytes, 0, i);
        }
        return baos.toByteArray();
    }
}
