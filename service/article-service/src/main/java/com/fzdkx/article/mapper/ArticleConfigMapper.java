package com.fzdkx.article.mapper;

import com.fzdkx.model.article.bean.ApArticleConfig;

/**
 * @author 发着呆看星
 * @create 2024/2/8
 */
public interface ArticleConfigMapper {
    int deleteById(Long id);

    int insert(ApArticleConfig row);

    ApArticleConfig selectById(Long id);

    int update(ApArticleConfig row);

}
