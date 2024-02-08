package com.fzdkx.article.service;

import com.fzdkx.model.article.bean.ApArticle;

/**
 * @author 发着呆看星
 * @create 2024/2/8
 */
public interface ArticleFreemarkerService {
    void buildArticleToMinIO(ApArticle apArticle, String content);
}
