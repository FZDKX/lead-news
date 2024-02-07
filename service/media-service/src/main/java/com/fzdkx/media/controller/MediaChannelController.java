package com.fzdkx.media.controller;

import com.fzdkx.media.service.MediaChannelService;
import com.fzdkx.model.common.vo.Result;
import com.fzdkx.model.media.bean.MediaChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 发着呆看星
 * @create 2024/2/6
 */
@RestController
@RequestMapping("/api/v1/channel")
public class MediaChannelController {
    @Autowired
    private MediaChannelService channelService;
    @GetMapping("/channels")
    public Result<List<MediaChannel>> channelList(){
        return channelService.getChannelList();
    }
}
