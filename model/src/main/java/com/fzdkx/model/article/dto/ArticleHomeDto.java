package com.fzdkx.model.article.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ArticleHomeDto {

    // 最大时间
    private Long maxBehotTime;
    // 最小时间
    private Long minBehotTime;
    // 分页size
    private Integer size;
    // 频道ID
    String tag;
    private Date maxTime;
    private Date minTime;
    public void setTime(){
        if (getMaxBehotTime() == null){
            setMaxTime(new Date());
        }else {
            setMinTime(new Date(maxBehotTime));
        }
        if (getMinBehotTime() == null){
            setMinTime(new Date());
        }else {
            setMinTime(new Date(minBehotTime));
        }
    }
}