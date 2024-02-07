package com.fzdkx.media.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 发着呆看星
 * @create 2024/2/6
 * 素材文章关联表
 */
public interface MediaNewsMaterialMapper {

    void deleteByNewsId(@Param("newsId") Long id);

    void save(@Param("ids") List<Long> ids,@Param("newsId") Long id,@Param("type") Short type);

}
