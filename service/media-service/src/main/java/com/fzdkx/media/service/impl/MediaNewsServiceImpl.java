package com.fzdkx.media.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fzdkx.common.constants.MediaConstants;
import com.fzdkx.common.exception.CustomException;
import com.fzdkx.media.mapper.MediaMaterialMapper;
import com.fzdkx.media.mapper.MediaNewsMapper;
import com.fzdkx.media.mapper.MediaNewsMaterialMapper;
import com.fzdkx.media.service.MediaNewsService;
import com.fzdkx.model.article.bean.ApArticleContentNode;
import com.fzdkx.model.common.enums.AppHttpCodeEnum;
import com.fzdkx.model.common.vo.PageRequestResult;
import com.fzdkx.model.common.vo.Result;
import com.fzdkx.model.media.bean.MediaNews;
import com.fzdkx.model.media.dto.MediaNewsDto;
import com.fzdkx.model.media.dto.MediaNewsPageReqDto;
import com.fzdkx.utils.MediaThreadLocalUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 发着呆看星
 * @create 2024/2/6
 */
@Service
public class MediaNewsServiceImpl implements MediaNewsService {
    @Autowired
    private MediaNewsMapper newsMapper;
    @Autowired
    private MediaNewsMaterialMapper newsMaterialMapper;
    @Autowired
    private MediaMaterialMapper materialMapper;

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
    @Transactional
    public Result<String> submit(MediaNewsDto dto) {
        // 校验参数
        if (dto == null) {
            return Result.error(AppHttpCodeEnum.PARAM_INVALID);
        }
        MediaNews mediaNews = new MediaNews();
        BeanUtils.copyProperties(dto, mediaNews);
        // 获取图片素材url
        List<String> imageList = getImageList(mediaNews.getContent());
        // 处理文章数据
        handleNewsData(mediaNews, imageList);
        // 保存数据
        save(mediaNews);
        // 如果要发布文章
        if (MediaConstants.WM_NEWS_COMMIT.equals(mediaNews.getStatus())) {
            // 清除文章之前与素材的联系
            newsMaterialMapper.deleteByNewsId(mediaNews.getId());
            // 重新建立素材与文章之间的联系
            relevanceNewsAndImage(mediaNews, dto.getImages());
        }
        return Result.success();
    }

    /**
     * 处理news数据
     */
    public void handleNewsData(MediaNews mediaNews, List<String> imageList) {
        // 建立文章与素材间的关系
        // 1.处理图片格式保存
        String images = handleNewsImages(imageList);
        mediaNews.setImages(images);
        // 2.设置文章布局
        handleNewsType(mediaNews, imageList);
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
     * 从文本中获取图片
     */
    public List<String> getImageList(String content){
        List<String> list = null;
        if (StringUtils.hasLength(content)){
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                List<ApArticleContentNode> jsonArray
                        = objectMapper.readValue(content, new TypeReference<List<ApArticleContentNode>>(){});
                list = new ArrayList<>();
                for (ApArticleContentNode node : jsonArray) {
                    if (MediaConstants.WM_NEWS_TYPE_IMAGE.equals(node.getType())){
                        list.add(node.getValue());
                    }
                }
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        return list;
    }
    /**
     * 处理图片保存格式
     * @param images 图片路径集合
     */
    public String handleNewsImages(List<String> images) {
        if (images == null || images.isEmpty()) {
            return "";
        }
        StringBuilder str = new StringBuilder(100);
        for (String imag : images) {
            str.append(imag).append(",");
        }
        return str.toString();
    }

    /**
     * 处理文章布局
     */
    public void handleNewsType(MediaNews news, List<String> images) {
        // 没有传入，默认自动
        if (news.getType() == null) {
            news.setType(MediaConstants.WM_NEWS_TYPE_AUTO);
        }
        // 如果是自动
        if (news.getType() == MediaConstants.WM_NEWS_TYPE_AUTO) {
            if (images.size() >= 3) {
                news.setType(MediaConstants.WM_NEWS_NONE_IMAGE);
            } else if (images.size() >= 1) {
                news.setType(MediaConstants.WM_NEWS_NONE_IMAGE);
            } else {
                news.setType(MediaConstants.WM_NEWS_NONE_IMAGE);
            }
        }
    }

    /**
     * 数据库关联素材与文章
     */
    public void relevanceNewsAndImage(MediaNews news, List<String> images) {
        if (images == null || images.isEmpty()) {
            return;
        }
        // 查询素材ID集合
        List<Long> ids = materialMapper.getIds(images);
        // 代表素材无效，已经被删除
        if (ids == null || ids.isEmpty()) {
            throw new CustomException(AppHttpCodeEnum.MATERIAL_REFERENCE_FAIL);
        }
        // 图片少了
        if (images.size() != ids.size()) {
            throw new CustomException(AppHttpCodeEnum.MATERIAL_REFERENCE_FAIL);
        }
        // 素材有效，进行关联
        newsMaterialMapper.save(ids, news.getId(), MediaConstants.WM_CONTENT_REFERENCE);
    }
}
