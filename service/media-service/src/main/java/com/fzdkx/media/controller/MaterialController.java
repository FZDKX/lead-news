package com.fzdkx.media.controller;

import com.fzdkx.media.service.MediaMaterialService;
import com.fzdkx.model.common.dto.PageRequestDto;
import com.fzdkx.model.common.vo.PageRequestResult;
import com.fzdkx.model.common.vo.Result;
import com.fzdkx.model.media.bean.MediaMaterial;
import com.fzdkx.model.media.dto.MaterialListDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author 发着呆看星
 * @create 2024/2/6
 */
@RestController
@RequestMapping("/api/v1/material")
public class MaterialController {
    @Autowired
    private MediaMaterialService materialService;

    @PostMapping("/upload_picture")
    public Result<MediaMaterial> uploadPicture(MultipartFile multipartFile){
        return materialService.uploadPicture(multipartFile);
    }

    @PostMapping("/list")
    public Result<List<MediaMaterial>> list(@RequestBody MaterialListDto dto){
        return materialService.list(dto);
    }
}
