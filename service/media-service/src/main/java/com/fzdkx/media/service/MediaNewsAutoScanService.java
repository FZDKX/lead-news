package com.fzdkx.media.service;

import com.fzdkx.model.media.bean.MediaNews;

import java.util.Map;

/**
 * @author 发着呆看星
 * @create 2024/2/8
 */
public interface MediaNewsAutoScanService {
    /**
     * 自媒体文章审核
     * @param id  自媒体文章id
     */
    void autoScanWmNews(MediaNews id);


    Map<String,Object> getImageAndText(MediaNews news);
}
