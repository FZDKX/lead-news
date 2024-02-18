package com.fzdkx.media.service.impl;

import com.fzdkx.common.constants.MediaConstants;
import com.fzdkx.common.exception.CustomException;
import com.fzdkx.media.mapper.MediaMaterialMapper;
import com.fzdkx.media.mapper.MediaNewsMapper;
import com.fzdkx.media.mapper.MediaNewsMaterialMapper;
import com.fzdkx.media.service.MediaNewsAutoScanService;
import com.fzdkx.media.service.MediaNewsService;
import com.fzdkx.model.common.enums.AppHttpCodeEnum;
import com.fzdkx.model.common.vo.PageRequestResult;
import com.fzdkx.model.common.vo.Result;
import com.fzdkx.model.media.bean.MediaNews;
import com.fzdkx.model.media.dto.MediaNewsDto;
import com.fzdkx.model.media.dto.MediaNewsPageReqDto;
import com.fzdkx.utils.MediaThreadLocalUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 发着呆看星
 * @create 2024/2/6
 */
@Service
@Slf4j
public class MediaNewsServiceImpl implements MediaNewsService {
    @Autowired
    private MediaNewsMapper newsMapper;
    @Autowired
    private MediaNewsMaterialMapper newsMaterialMapper;
    @Autowired
    private MediaMaterialMapper materialMapper;
    @Autowired
    private MediaNewsAutoScanService mediaNewsAutoScanService;

    @Override
    public Result<List<MediaNews>> getNewsList(MediaNewsPageReqDto dto) {
        if (dto == null) {
            throw new CustomException(AppHttpCodeEnum.PARAM_INVALID);
        }
        // 校验分页参数
        dto.checkParam();
        // 设置分页参数
        PageHelper.startPage(dto.getPage(), dto.getSize());
        // 开始分页查询
        List<MediaNews> list = newsMapper.selectNewsList(dto, MediaThreadLocalUtil.getUser().getId());
        // 获取结果
        PageInfo<MediaNews> pageInfo = new PageInfo<>(list);
        // 创建返回结果，设置分页数据
        PageRequestResult<List<MediaNews>> result
                = new PageRequestResult<>(dto.getSize(), dto.getPage(), pageInfo.getTotal());
        // 设置查询数据
        result.setData(pageInfo.getList());
        // 返回
        return result;
    }

    @Override
    public Result<String> submit(MediaNewsDto dto) {
        // 校验参数
        if (dto == null) {
            return Result.error(AppHttpCodeEnum.PARAM_INVALID);
        }
        MediaNews mediaNews = new MediaNews();
        BeanUtils.copyProperties(dto, mediaNews);
        // 获取封面图片
        List<String> images = dto.getImages();
        // 处理文章封面
        handleNewsImage(mediaNews, images);
        // 保存数据
        save(mediaNews);
        // 如果要发布文章
        if (MediaConstants.WM_NEWS_COMMIT.equals(mediaNews.getStatus())) {
            log.info("正在准备发布文章，newsId：{}",mediaNews.getId());
            // 开始进行自动审核
            mediaNewsAutoScanService.autoScanWmNews(mediaNews);
        }
        return Result.success();
    }

    /**
     * 处理news数据
     */
    public void handleNewsImage(MediaNews mediaNews, List<String> imageList) {
        String images = handleNewsType(mediaNews, imageList);
        mediaNews.setImages(images);
    }

    /**
     * 保存至数据库
     */
    public void save(MediaNews mediaNews) {
        // 判断文章是否已存在，如果已存在，就是修改文章，如果不存在，就是保存文章
        Long id = mediaNews.getId();
        if (id != null) {
            newsMapper.updateNews(mediaNews);
        } else {
            // 如果是新增，设置初始属性
            mediaNews.setUserId(MediaThreadLocalUtil.getUser().getId());
            mediaNews.setCreatedTime(new Date());
            mediaNews.setSubmitedTime(new Date());
            mediaNews.setEnable((short) 1);    //默认上架
            // 新增
            newsMapper.saveNews(mediaNews);
        }
    }

    /**
     * 处理文章布局
     */
    public String handleNewsType(MediaNews news, List<String> images) {
        StringBuilder str = new StringBuilder(100);
        // 传入的封面为空
        if (images.isEmpty()) {
            // 自动
            if (news.getType().equals(MediaConstants.WM_NEWS_TYPE_AUTO)){
                // 获取数据
                Map<String, Object> map = mediaNewsAutoScanService.getImageAndText(news.getContent());
                images = (List<String>) map.get("image");
                if (images.size() >= 3) {
                    news.setType(MediaConstants.WM_NEWS_MANY_IMAGE);
                    for (int i = 0; i < 3; i++) {
                        str.append(images.get(0)).append(",");
                    }
                    return str.toString();
                } else if (images.size() >= 1) {
                    news.setType(MediaConstants.WM_NEWS_SINGLE_IMAGE);
                    return images.get(0);
                } else {
                    news.setType(MediaConstants.WM_NEWS_NONE_IMAGE);
                    return "";
                }
            }
            // 无图
            if (news.getType().equals(MediaConstants.WM_NEWS_NONE_IMAGE)){
                news.setType(MediaConstants.WM_NEWS_NONE_IMAGE);
                return "";
            }
            // 其他
            throw new CustomException(AppHttpCodeEnum.PARAM_INVALID);
        }
        // 不为空
        // 单图
        if (images.size() == 1 && news.getType().equals(MediaConstants.WM_NEWS_SINGLE_IMAGE)){
            return images.get(0);
        }
        // 三图
        if (images.size() == 3 && news.getType().equals(MediaConstants.WM_NEWS_MANY_IMAGE)){
            for (String imag : images) {
                str.append(imag).append(",");
            }
            return str.toString();
        }
        throw new CustomException(AppHttpCodeEnum.PARAM_INVALID);
    }

}
