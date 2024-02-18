package com.fzdkx.model.media.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Sensitive implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 敏感词
     */
    private String sensitives;

    /**
     * 创建时间
     */
    private Date createdTime;

}