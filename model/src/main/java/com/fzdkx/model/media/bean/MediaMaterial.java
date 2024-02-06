package com.fzdkx.model.media.bean;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 自媒体图文素材信息表
 *
 * @author 发着呆看星
 */
@Data
public class MediaMaterial implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 自媒体用户ID
     */
    private Long userId;

    /**
     * 图片地址
     */
    private String url;

    /**
     * 素材类型
     * 0 图片
     * 1 视频
     */
    private Short type;

    /**
     * 是否收藏
     */
    private Short isCollection;

    /**
     * 创建时间
     */
    private Date createdTime;

    public void init(Long userId, String url) {
        this.userId = userId;
        this.url = url;
        this.isCollection = 0;
        this.type = 0;
        this.createdTime = new Date();
    }

}