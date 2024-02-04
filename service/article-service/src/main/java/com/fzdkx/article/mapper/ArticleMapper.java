package com.fzdkx.article.mapper;

import com.fzdkx.model.article.bean.Article;
import com.fzdkx.model.article.dto.ArticleHomeDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 发着呆看星
 * @create 2024/2/4
 */
public interface ArticleMapper {
    List<Article> loadArticleList(@Param("articleHomeDto") ArticleHomeDto articleHomeDto, @Param("loadType") Integer loadType);
}
