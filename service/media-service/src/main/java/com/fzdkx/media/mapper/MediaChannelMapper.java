package com.fzdkx.media.mapper;

import com.fzdkx.model.common.vo.Result;
import com.fzdkx.model.media.bean.MediaChannel;

import java.util.List;

/**
 * @author 发着呆看星
 * @create 2024/2/6
 */
public interface MediaChannelMapper {
    List<MediaChannel> selectChannelList();

    MediaChannel selectById(Integer channelId);
}
