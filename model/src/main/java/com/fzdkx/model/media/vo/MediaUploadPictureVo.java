package com.fzdkx.model.media.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author 发着呆看星
 * @create 2024/2/6
 */
@Data
public class MediaUploadPictureVo {
    private Long id;
    private Long userId;
    private String url;
    private Integer type;
    private Integer isCollection;
    private Date createdTime;
}
