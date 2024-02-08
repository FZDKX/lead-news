package com.fzdkx.model.article.bean;

import lombok.Data;

import java.util.Date;
@Data
public class ApArticle {
    private Long id;

    private String title;

    private Long authorId;

    private String authorName;

    private Integer channelId;

    private String channelName;

    private Short layout;

    private Short flag;

    private String images;

    private String labels;

    private Integer likes;

    private Integer collection;

    private Integer comment;

    private Integer views;

    private Integer provinceId;

    private Integer cityId;

    private Integer countyId;

    private Date createdTime;

    private Date publishTime;

    private Boolean syncStatus;

    private Boolean origin;

    private String staticUrl;
}