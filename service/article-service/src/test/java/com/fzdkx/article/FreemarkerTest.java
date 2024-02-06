package com.fzdkx.article;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fzdkx.article.mapper.ArticleContentMapper;
import com.fzdkx.article.mapper.ArticleMapper;
import com.fzdkx.file.service.FileStorageService;
import com.fzdkx.model.article.bean.ApArticle;
import com.fzdkx.model.article.bean.ApArticleContent;
import com.fzdkx.model.article.bean.ApArticleContentNode;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 发着呆看星
 * @create 2024/2/5
 */
@SpringBootTest(classes = ArticleApplication.class)
@RunWith(SpringRunner.class)
public class FreemarkerTest {
    @Autowired
    private ArticleContentMapper articleContentMapper;
    @Autowired
    private Configuration configuration;
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private ArticleMapper articleMapper;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void createHtml() throws Exception {
        ApArticleContent apArticleContent =
                articleContentMapper.selectArticleContentById(1302862387124125698L);
        if (apArticleContent != null && StringUtils.hasLength(apArticleContent.getContent())){
            StringWriter stringWriter = new StringWriter();
            Template template = configuration.getTemplate("apArticle.ftl");
            List<ApArticleContentNode> list = objectMapper.readValue(apArticleContent.getContent(), List.class);
            Map<String, Object> map = new HashMap<>();
            map.put("content",list);
            template.process(map,stringWriter);
            ByteArrayInputStream bais = new ByteArrayInputStream(stringWriter.toString().getBytes());
            String path = fileStorageService.uploadHtmlFile("", apArticleContent.getArticleId() + ".html", bais);
            ApArticle apArticle = new ApArticle();
            apArticle.setId(apArticleContent.getArticleId());
            apArticle.setStaticUrl(path);
            articleMapper.updateStaticUrl(apArticle);
        }
    }
}
