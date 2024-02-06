package com.fzdkx.article.service.impl;

import com.fzdkx.article.mapper.ArticleMapper;
import com.fzdkx.article.service.ArticleService;
import com.fzdkx.common.constants.ArticleConstants;
import com.fzdkx.model.article.bean.ApArticle;
import com.fzdkx.model.article.dto.ApArticleHomeDto;
import com.fzdkx.model.common.vo.Result;
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
}
