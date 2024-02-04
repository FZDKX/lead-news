package com.fzdkx.model.article.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * 文章内容实体类
 */
@Data
public class ArticleContent implements Serializable {

    private Long id;

    /**
     * 文章id
     */
    private Long articleId;

    /**
     * 文章内容
     */
    private String content;
}