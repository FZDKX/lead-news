package com.fzdkx.media.service.impl;

import com.aliyun.imageaudit20191230.models.ScanImageResponse;
import com.aliyun.imageaudit20191230.models.ScanTextResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fzdkx.apis.article.IArticleClient;
import com.fzdkx.common.aliyun.Scan;
import com.fzdkx.common.constants.MediaConstants;
import com.fzdkx.common.exception.CustomException;
import com.fzdkx.media.mapper.MediaChannelMapper;
import com.fzdkx.media.mapper.MediaNewsMapper;
import com.fzdkx.media.mapper.MediaUserMapper;
import com.fzdkx.media.service.MediaNewsAutoScanService;
import com.fzdkx.model.article.bean.ApArticleContentNode;
import com.fzdkx.model.article.dto.ArticleDto;
import com.fzdkx.model.common.enums.AppHttpCodeEnum;
import com.fzdkx.model.common.vo.Result;
import com.fzdkx.model.media.bean.MediaChannel;
import com.fzdkx.model.media.bean.MediaNews;
import com.fzdkx.model.media.bean.MediaUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 发着呆看星
 * @create 2024/2/8
 */
@Service
public class MediaNewsAutoScanServiceImpl implements MediaNewsAutoScanService {
    @Autowired
    private MediaNewsMapper mediaNewsMapper;
    @Autowired
    private MediaChannelMapper mediaChannelMapper;
    @Autowired
    private MediaUserMapper mediaUserMapper;
    @Autowired
    private Scan scan;
    @Autowired
    private IArticleClient iArticleClient;

    /**
     * 自媒体文章自动审核
     * @param id  自媒体文章id
     */
    @Override
    @Transactional
    @Async
    public void autoScanWmNews(Long id) {
        if (id == null){
            throw new CustomException(AppHttpCodeEnum.PARAM_INVALID);
        }
        MediaNews news = mediaNewsMapper.selectById(id);
        if (news == null){
            throw new CustomException(AppHttpCodeEnum.PARAM_INVALID);
        }
        // 如果文章存在，进行自动审核
        String content = news.getContent();
        // 获取文本和图片
        Map<String, Object> map = getImageAndText(content);
        List<String> imageList = (List<String>) map.get("image");
        String text = (String) map.get("text");
        try {
            // 审核文本
           text = text + "-" + news.getTitle();
            // 解析审核结果
            if (!handleText(news, text)){
                return;
            }
            // 审核图片
            if (!handleImage(news, imageList)) {
                return;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // 自动审核完成
        // 调用feign接口，发布文章
        Result<Long> result = publishNews(news);
        if (result == null || !result.getCode().equals(200)){
            throw new CustomException(AppHttpCodeEnum.ARTICLE_PUBLISH_ERROR);
        }
        // 获取文章ID
        Long articleId = result.getData();
        news.setArticleId(articleId);
        updateNewsStatus(news, MediaConstants.WM_NEWS_HAVE_PUBLISHED,"审核成功");
    }

    private void updateNewsStatus(MediaNews news, Short status, String reason) {
        news.setStatus(status);
        news.setReason(reason);
        mediaNewsMapper.updateNews(news);
    }

    /**
     * 发布文章
     */
    private Result<Long> publishNews(MediaNews news) {
        ArticleDto articleDto = new ArticleDto();
        BeanUtils.copyProperties(news,articleDto);
        // ID设置为null，新增
        articleDto.setId(null);
        // 设置布局
        articleDto.setLayout(news.getType());
        // 获取频道
        MediaChannel channel = mediaChannelMapper.selectById(news.getChannelId());
        if (channel != null){
            articleDto.setChannelName(channel.getName());
        }
        // 设置作者
        articleDto.setAuthorId(news.getUserId());
        MediaUser user = mediaUserMapper.selectById(news.getUserId());
        if (user != null){
            articleDto.setAuthorName(user.getName());
        }
        // 设置创建事件
        articleDto.setCreatedTime(new Date());
        // feign调用
       return iArticleClient.save(articleDto);
    }

    /**
     * 处理图片
     */
    private boolean handleImage(MediaNews news, List<String> imageList) {
        boolean b = true;
        if (imageList == null){
            return b;
        }
        // 图片去重
        imageList = imageList.stream().distinct().collect(Collectors.toList());
        // 审核图片
        try {
            ScanImageResponse response = scan.scanImage(imageList);
            String flag = scan.analyzeImageResponse(response);
            if (flag.equals("review")){
                updateNewsStatus(news, MediaConstants.WM_NEWS_REVIEW,"当前文章中存在不确定内容");
                b = false;
            }else if (flag.equals("block")){
                updateNewsStatus(news, MediaConstants.WM_NEWS_BLOCK,"当前文章中存在违规内容");
                b = false;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return b;
    }

    /**
     * 处理文本
     */
    private boolean handleText(MediaNews news, String text) {
        boolean b = true;
        if (text == null){
            return b;
        }
        // 审核图片
        try {
            ScanTextResponse response = scan.scanText(text);
            String flag = scan.analyzeTextResponse(response);
            if (flag.equals("review")){
                updateNewsStatus(news, MediaConstants.WM_NEWS_REVIEW,"当前文章中存在不确定内容");
                b = false;
            }else if (flag.equals("block")){
                updateNewsStatus(news, MediaConstants.WM_NEWS_BLOCK,"当前文章中存在违规内容");
                b = false;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return b;
    }

    /**
     * 从文本中获取图片
     */
    @Override
    public Map<String,Object> getImageAndText(String content){
        List<String> list = null;
        StringBuilder str = new StringBuilder(300);
        if (StringUtils.hasLength(content)){
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                List<ApArticleContentNode> jsonArray
                        = objectMapper.readValue(content, new TypeReference<List<ApArticleContentNode>>(){});
                list = new ArrayList<>();
                for (ApArticleContentNode node : jsonArray) {
                    if (MediaConstants.WM_NEWS_TYPE_IMAGE.equals(node.getType())){
                        list.add(node.getValue());
                    }else if (MediaConstants.WM_NEWS_TYPE_TEXT.equals(node.getType())){
                        str.append(node.getValue());
                    }
                }
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        Map<String,Object> map = new HashMap<>();
        map.put("text",str.toString());
        map.put("image",list);
        return map;
    }
}
