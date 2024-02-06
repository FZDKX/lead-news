package com.fzdkx.media.controller;

import com.fzdkx.media.service.MediaLoginService;
import com.fzdkx.model.common.vo.Result;
import com.fzdkx.model.media.dto.MediaLoginDto;
import com.fzdkx.model.media.vo.MediaLoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 发着呆看星
 * @create 2024/2/6
 */
@RestController
@RequestMapping("/login")
public class MediaLoginController {
    @Autowired
    private MediaLoginService loginService;
    @PostMapping("/in")
    public Result<MediaLoginVo> login(@RequestBody MediaLoginDto mediaLoginDto){
        return loginService.login(mediaLoginDto);
    }
}
