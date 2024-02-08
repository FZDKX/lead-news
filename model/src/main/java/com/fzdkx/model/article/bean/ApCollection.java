package com.fzdkx.model.article.bean;

import lombok.Data;

import java.util.Date;
@Data
public class ApCollection {
    private Long id;

    private Integer entryId;

    private Long articleId;

    private Byte type;

    private Date collectionTime;

    private Date publishedTime;

}