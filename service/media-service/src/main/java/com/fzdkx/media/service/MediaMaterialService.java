package com.fzdkx.media.service;

import com.fzdkx.model.common.dto.Result;
import com.fzdkx.model.media.bean.MediaMaterial;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 发着呆看星
 * @create 2024/2/6
 */
public interface MediaMaterialService {
    Result<MediaMaterial> uploadPicture(MultipartFile file);
}
