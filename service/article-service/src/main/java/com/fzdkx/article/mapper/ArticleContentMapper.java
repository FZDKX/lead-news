package com.fzdkx.article.mapper;

import com.fzdkx.model.article.bean.ApArticleContent;
import org.apache.ibatis.annotations.Param;

/**
 * @author 发着呆看星
 * @create 2024/2/5
 */
public interface ArticleContentMapper {
    ApArticleContent selectArticleContentById(@Param("articleId") Long articleId);

    int deleteById(Long id);

    int insert(ApArticleContent row);

    ApArticleContent selectById(Long id);

    int update(ApArticleContent row);
}
