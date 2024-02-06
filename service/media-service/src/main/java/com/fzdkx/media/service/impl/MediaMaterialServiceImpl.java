package com.fzdkx.media.service.impl;

import com.fzdkx.common.exception.CustomException;
import com.fzdkx.file.service.FileStorageService;
import com.fzdkx.media.mapper.MediaMaterialMapper;
import com.fzdkx.media.service.MediaMaterialService;
import com.fzdkx.model.common.dto.Result;
import com.fzdkx.model.common.enums.AppHttpCodeEnum;
import com.fzdkx.model.media.bean.MediaMaterial;
import com.fzdkx.utils.MediaThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;


/**
 * @author 发着呆看星
 * @create 2024/2/6
 */
@Service
@Slf4j
public class MediaMaterialServiceImpl implements MediaMaterialService {
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private MediaMaterialMapper materialMapper;
    @Override
    public Result<MediaMaterial> uploadPicture(MultipartFile file) {
        // 参数校验
        if (file == null || file.getSize() == 0){
            throw new CustomException(AppHttpCodeEnum.PARAM_INVALID);
        }
        // 上传图片到minio中
        // 1.生成图片名称前缀
        String fileNamePrefix = UUID.randomUUID().toString().replace("-", "");
        // 2.获取上传图片名称
        String filename = file.getOriginalFilename();
        // 3.获取图片后缀 带.
        String postfix = filename.substring(filename.lastIndexOf("."));
        String url = null;
        try {
            // 上传图片至minio 文件名称为 uuid + 上传文件后缀
            url = fileStorageService.uploadImgFile("",fileNamePrefix + postfix,file.getInputStream());
            log.info("图片上传成功，fileId：{}",url);
        } catch (IOException e) {
            log.error("图片上传失败");
            throw new CustomException(AppHttpCodeEnum.FILE_UPLOAD_ERROR);
        }

        // 将信息保存至数据库中
        // 创建对象
        MediaMaterial mediaMaterial = new MediaMaterial();
        // 初始化操作
        mediaMaterial.init(MediaThreadLocalUtil.getUser().getId(), url);
        // 保存至数据库
        materialMapper.insert(mediaMaterial);
        return Result.success(mediaMaterial);
    }
}
