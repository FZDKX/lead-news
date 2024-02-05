package com.fzdkx.article.mapper;

import com.fzdkx.model.article.bean.Article;
import com.fzdkx.model.article.bean.ArticleContent;
import org.apache.ibatis.annotations.Param;

/**
 * @author 发着呆看星
 * @create 2024/2/5
 */
public interface ArticleContentMapper {
    ArticleContent selectArticleContentById(@Param("articleId") Long articleId);
}
