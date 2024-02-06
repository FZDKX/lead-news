package com.fzdkx.media.service;

import com.fzdkx.model.common.vo.PageRequestResult;
import com.fzdkx.model.common.vo.Result;
import com.fzdkx.model.media.bean.MediaMaterial;
import com.fzdkx.model.media.dto.MaterialListDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author 发着呆看星
 * @create 2024/2/6
 */
public interface MediaMaterialService {
    Result<MediaMaterial> uploadPicture(MultipartFile file);

    PageRequestResult<List<MediaMaterial>> list(MaterialListDto dto);
}
