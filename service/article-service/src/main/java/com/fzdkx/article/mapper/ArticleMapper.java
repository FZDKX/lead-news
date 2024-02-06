package com.fzdkx.article.mapper;

import com.fzdkx.model.article.bean.ApArticle;
import com.fzdkx.model.article.dto.ApArticleHomeDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 发着呆看星
 * @create 2024/2/4
 */
public interface ArticleMapper {
    List<ApArticle> loadArticleList(@Param("apArticleHomeDto") ApArticleHomeDto apArticleHomeDto, @Param("loadType") Integer loadType);

    void updateStaticUrl(@Param("apArticle") ApArticle apArticle);
}
