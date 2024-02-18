package com.fzdkx.media.mapper;

import com.fzdkx.model.media.bean.MediaNews;
import com.fzdkx.model.media.dto.MediaNewsPageReqDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 发着呆看星
 * @create 2024/2/6
 */
public interface MediaNewsMapper {
    List<MediaNews> selectNewsList(@Param("dto") MediaNewsPageReqDto dto, @Param("userId") Long userId);

    void updateNews(@Param("mediaNews") MediaNews mediaNews);

    void saveNews(@Param("mediaNews") MediaNews mediaNews);

    MediaNews selectById(@Param("id") Long id);
}
