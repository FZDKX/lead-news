package com.fzdkx.article.service;

import com.fzdkx.model.article.bean.ApArticle;
import com.fzdkx.model.article.dto.ApArticleHomeDto;
import com.fzdkx.model.common.dto.Result;

import java.util.List;

/**
 * @author 发着呆看星
 * @create 2024/2/4
 */
public interface ArticleService {
    Result<List<ApArticle>> loadArticleList(ApArticleHomeDto apArticleHomeDto, Integer loadType);
}
