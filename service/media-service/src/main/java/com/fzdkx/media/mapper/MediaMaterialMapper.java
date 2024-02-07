package com.fzdkx.media.mapper;

import com.fzdkx.model.media.bean.MediaMaterial;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 发着呆看星
 * @create 2024/2/6
 */
public interface MediaMaterialMapper {
    void insert(@Param("mediaMaterial") MediaMaterial mediaMaterial);

    List<MediaMaterial> getListAll(@Param("isCollection") Short isCollection,@Param("userId") Long id);

    List<Long> getIds(@Param("images") List<String> images);
}
