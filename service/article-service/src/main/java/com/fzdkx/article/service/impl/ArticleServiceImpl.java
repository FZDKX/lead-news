package com.fzdkx.article.service.impl;

import com.fzdkx.article.mapper.ArticleMapper;
import com.fzdkx.article.service.ArticleService;
import com.fzdkx.common.constants.ArticleConstants;
import com.fzdkx.model.article.bean.Article;
import com.fzdkx.model.article.dto.ArticleHomeDto;
import com.fzdkx.model.common.dto.Result;
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
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public Result<List<Article>> loadArticleList(ArticleHomeDto articleHomeDto, Integer loadType) {
        // size参数校验
        Integer size = articleHomeDto.getSize();
        if (size == null || size <= 0) {
            size = ArticleConstants.DEFAULT_SIZE;
        }
        articleHomeDto.setSize(Math.min(size, ArticleConstants.MAX_SIZE));
        // tag校验
        String tag = articleHomeDto.getTag();
        if (!StringUtils.hasLength(tag)) {
            articleHomeDto.setTag(ArticleConstants.LOAD_TAG_DEFAULT);
        }
        // 时间校验
        if (articleHomeDto.getMaxBehotTime() == null) {
            articleHomeDto.setMaxBehotTime(new Date());
        }
        if (articleHomeDto.getMinBehotTime() == null) {
            articleHomeDto.setMinBehotTime(new Date());
        }
        // 查询数据库
        List<Article> articles = articleMapper.loadArticleList(articleHomeDto, loadType);
        return Result.success(articles);
    }
}
