package com.fzdkx.media.service.impl;

import com.fzdkx.media.mapper.MediaChannelMapper;
import com.fzdkx.media.service.MediaChannelService;
import com.fzdkx.model.common.vo.Result;
import com.fzdkx.model.media.bean.MediaChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 发着呆看星
 * @create 2024/2/6
 */
@Service
public class MediaChannelServiceImpl implements MediaChannelService {
    @Autowired
    private MediaChannelMapper channelMapper;
    @Override
    public Result<List<MediaChannel>> getChannelList() {
        List<MediaChannel> channelList = channelMapper.selectChannelList();
        return Result.success(channelList);
    }
}
