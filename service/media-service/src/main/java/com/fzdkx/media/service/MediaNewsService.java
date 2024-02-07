package com.fzdkx.media.service;

import com.fzdkx.model.common.vo.Result;
import com.fzdkx.model.media.bean.MediaNews;
import com.fzdkx.model.media.dto.MediaNewsDto;
import com.fzdkx.model.media.dto.MediaNewsPageReqDto;

import java.util.List;

/**
 * @author 发着呆看星
 * @create 2024/2/6
 */
public interface MediaNewsService {
    Result<List<MediaNews>> getNewsList(MediaNewsPageReqDto dto);

    Result<String> submit(MediaNewsDto news);
}
