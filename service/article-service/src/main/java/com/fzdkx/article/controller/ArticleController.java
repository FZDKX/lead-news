package com.fzdkx.article.controller;

import com.fzdkx.article.service.ArticleService;
import com.fzdkx.common.constants.ArticleConstants;
import com.fzdkx.model.article.bean.Article;
import com.fzdkx.model.common.dto.Result;
import com.fzdkx.model.article.dto.ArticleHomeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @author 发着呆看星
 * @create 2024/2/4
 */
@RestController
@RequestMapping("/api/v1/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping("/load")
    public Result<List<Article>> load(ArticleHomeDto articleHomeDto){
        return articleService.loadArticleList(articleHomeDto, ArticleConstants.LOAD_TYPE_MORE);
    }
    @PostMapping("/loadmore")
    public Result<List<Article>> loadMore(ArticleHomeDto articleHomeDto){
        return articleService.loadArticleList(articleHomeDto, ArticleConstants.LOAD_TYPE_MORE);
    }
    @PostMapping("/loadnew")
    public Result<List<Article>> loadNew(ArticleHomeDto articleHomeDto){
        return articleService.loadArticleList(articleHomeDto, ArticleConstants.LOAD_TYPE_NEW);
    }
}
