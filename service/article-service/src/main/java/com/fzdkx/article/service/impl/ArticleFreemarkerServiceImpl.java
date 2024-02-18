package com.fzdkx.article.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fzdkx.article.mapper.ArticleMapper;
import com.fzdkx.article.service.ArticleFreemarkerService;
import com.fzdkx.common.exception.CustomException;
import com.fzdkx.file.service.FileStorageService;
import com.fzdkx.model.article.bean.ApArticle;
import com.fzdkx.model.article.bean.ApArticleContentNode;
import com.fzdkx.model.common.enums.AppHttpCodeEnum;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 发着呆看星
 * @create 2024/2/8
 */
@Service
@Slf4j
public class ArticleFreemarkerServiceImpl implements ArticleFreemarkerService {
    @Autowired
    private Configuration configuration;
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private ArticleMapper articleMapper;
    @Override
    @Async
    public void buildArticleToMinIO(ApArticle article, String content) {
        if (!StringUtils.hasLength(content) || article == null) {
            return;
        }
        log.info("正在准备生成文章html页面：articleId{}",article.getId());
        try {
            // 创建输出流
            StringWriter stringWriter = new StringWriter();
            // 获取模板
            Template template = configuration.getTemplate("article.ftl");
            // content json 转 对象 ，进行封装
            ObjectMapper objectMapper = new ObjectMapper();
            List<ApArticleContentNode> list = objectMapper.readValue(content, List.class);
            Map<String, Object> map = new HashMap<>();
            map.put("content",list);
            // template整合
            template.process(map,stringWriter);
            // 获取生成的html文件输出流
            InputStream in = new ByteArrayInputStream(stringWriter.toString().getBytes());
            // 上传html文件至minio
            String path = fileStorageService.uploadHtmlFile("", article.getId() + ".html", in);
            // 设置文件地址，更新数据库数据
            article.setStaticUrl(path);
            article.setSyncStatus(true);
            articleMapper.update(article);
        } catch (Exception e) {
            throw new CustomException(AppHttpCodeEnum.CREATE_HTML_ERROR);
        }
    }
}
