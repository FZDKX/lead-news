package com.fzdkx.article.service;

import com.fzdkx.model.article.bean.Article;
import com.fzdkx.model.article.dto.ArticleHomeDto;
import com.fzdkx.model.common.dto.Result;

import java.util.List;

/**
 * @author 发着呆看星
 * @create 2024/2/4
 */
public interface ArticleService {
    Result<List<Article>> loadArticleList(ArticleHomeDto articleHomeDto, Integer loadType);
}
