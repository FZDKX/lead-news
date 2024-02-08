package com.fzdkx.model.article.bean;

import lombok.Data;

import java.util.Date;
@Data
public class ApAuthor {
    private Long id;

    private String name;

    private Byte type;

    private Long userId;

    private Date createdTime;

    private Long wmUserId;
}