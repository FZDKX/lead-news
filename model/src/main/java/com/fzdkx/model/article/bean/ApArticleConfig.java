package com.fzdkx.model.article.bean;

import lombok.Data;

@Data
public class ApArticleConfig {
    public ApArticleConfig(Long articleId) {
        this.articleId = articleId;
        this.isDelete = false;
        this.isDown = false;
        this.isComment = true;
        this.isForward = true;
    }

    private Long id;

    private Long articleId;

    private Boolean isComment;

    private Boolean isForward;

    private Boolean isDown;

    private Boolean isDelete;
}