package com.fzdkx.media.mapper;

import com.fzdkx.model.media.bean.MediaMaterial;
import org.apache.ibatis.annotations.Param;

/**
 * @author 发着呆看星
 * @create 2024/2/6
 */
public interface MediaMaterialMapper {
    void insert(@Param("mediaMaterial") MediaMaterial mediaMaterial);
}
