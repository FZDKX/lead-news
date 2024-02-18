package com.fzdkx.article.service.impl;

import com.fzdkx.article.mapper.ArticleConfigMapper;
import com.fzdkx.article.mapper.ArticleContentMapper;
import com.fzdkx.article.mapper.ArticleMapper;
import com.fzdkx.article.service.ArticleFreemarkerService;
import com.fzdkx.article.service.ArticleService;
import com.fzdkx.common.constants.ArticleConstants;
import com.fzdkx.common.exception.CustomException;
import com.fzdkx.model.article.bean.ApArticle;
import com.fzdkx.model.article.bean.ApArticleConfig;
import com.fzdkx.model.article.bean.ApArticleContent;
import com.fzdkx.model.article.dto.ApArticleHomeDto;
import com.fzdkx.model.article.dto.ArticleDto;
import com.fzdkx.model.common.enums.AppHttpCodeEnum;
import com.fzdkx.model.common.vo.Result;
import com.github.yitter.idgen.YitIdHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * @author 发着呆看星
 * @create 2024/2/4
 */
@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private ArticleConfigMapper articleConfigMapper;
    @Autowired
    private ArticleContentMapper articleContentMapper;
    @Autowired
    private ArticleFreemarkerService articleFreemarkerService;

    @Override
    public Result<List<ApArticle>> loadArticleList(ApArticleHomeDto apArticleHomeDto, Integer loadType) {
        // size参数校验
        Integer size = apArticleHomeDto.getSize();
        if (size == null || size <= 0) {
            size = ArticleConstants.DEFAULT_SIZE;
        }
        apArticleHomeDto.setSize(Math.min(size, ArticleConstants.MAX_SIZE));
        // tag校验
        String tag = apArticleHomeDto.getTag();
        if (!StringUtils.hasLength(tag)) {
            apArticleHomeDto.setTag(ArticleConstants.LOAD_TAG_DEFAULT);
        }
        // 时间校验
        if (apArticleHomeDto.getMaxBehotTime() == null) {
            apArticleHomeDto.setMaxBehotTime(new Date());
        }
        if (apArticleHomeDto.getMinBehotTime() == null) {
            apArticleHomeDto.setMinBehotTime(new Date());
        }
        // 查询数据库
        List<ApArticle> apArticles = articleMapper.loadArticleList(apArticleHomeDto, loadType);
        return Result.success(apArticles);
    }

    @Override
    public Result<Long> saveArticle(ArticleDto dto) {
        if (dto == null){
            throw new CustomException(AppHttpCodeEnum.PARAM_REQUIRE);
        }
        ApArticle article = new ApArticle();
        BeanUtils.copyProperties(dto,article);
        ApArticleContent apArticleContent = new ApArticleContent();
        // 判断ID是否存在，如果存在，则为修改，如果不存在，则为保存
        Long id = dto.getId();
        if (id == null){
            // 生成分布式ID
            article.setId(YitIdHelper.nextId());
            // 保存
            articleMapper.insert(article);
            // 创建对象配置，保存
            ApArticleConfig apArticleConfig = new ApArticleConfig(article.getId());
            // 分布式ID
            apArticleConfig.setId(YitIdHelper.nextId());
            articleConfigMapper.insert(apArticleConfig);
            // 保存文章内容
            apArticleContent.setArticleId(article.getId());
            apArticleContent.setContent(dto.getContent());
            // 分布式ID
            apArticleContent.setId(YitIdHelper.nextId());
            articleContentMapper.insert(apArticleContent);
            log.info("文章发布完成，articleId：{}",id);
        }else {
            // 修改文章
            articleMapper.update(article);
            // 修改文章内容
            apArticleContent.setContent(dto.getContent());
            articleContentMapper.update(apArticleContent);
        }
        // 异步调用，生成html文件，并上传至minio
        articleFreemarkerService.buildArticleToMinIO(article, dto.getContent());
        return Result.success(article.getId());
    }
}
