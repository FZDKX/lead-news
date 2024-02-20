package com.fzdkx.schedule.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * @author 发着呆看星
 * @create 2024/2/20
 */
public interface TaskMapper {
    void insert(@Param("id") Long id);
    Long select(@Param("newsId") Long id);
    void delete(@Param("newsId") Long id);
}
