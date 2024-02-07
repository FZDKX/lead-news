package com.fzdkx.media.controller;

import com.fzdkx.media.service.MediaNewsService;
import com.fzdkx.model.common.vo.Result;
import com.fzdkx.model.media.bean.MediaNews;
import com.fzdkx.model.media.dto.MediaNewsDto;
import com.fzdkx.model.media.dto.MediaNewsPageReqDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 发着呆看星
 * @create 2024/2/6
 */
@RestController
@RequestMapping("/api/v1/news")
public class MediaNewsController {
    @Autowired
    private MediaNewsService newsService;

    /**
     * 查看文章列表
     */
    @PostMapping("/list")
    public Result<List<MediaNews>> getNewsList(@RequestBody MediaNewsPageReqDto dto){
        return newsService.getNewsList(dto);
    }

    /**
     * 新增或修改文章
     */
    @PostMapping("/submit")
    public Result<String> submit(@RequestBody MediaNewsDto news){
        return newsService.submit(news);
    }

}
