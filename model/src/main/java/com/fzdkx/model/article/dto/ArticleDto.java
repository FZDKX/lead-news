package com.fzdkx.model.article.dto;

import com.fzdkx.model.article.bean.ApArticle;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ArticleDto  extends ApArticle {
    /**
     * 文章内容+
     */
    private String content;
}