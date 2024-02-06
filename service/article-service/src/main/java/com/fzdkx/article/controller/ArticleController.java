package com.fzdkx.article.controller;

import com.fzdkx.article.service.ArticleService;
import com.fzdkx.common.constants.ArticleConstants;
import com.fzdkx.model.article.bean.ApArticle;
import com.fzdkx.model.article.dto.ApArticleHomeDto;
import com.fzdkx.model.common.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Result<List<ApArticle>> load(@RequestBody ApArticleHomeDto apArticleHomeDto){
        return articleService.loadArticleList(apArticleHomeDto, ArticleConstants.LOAD_TYPE_MORE);
    }
    @PostMapping("/loadmore")
    public Result<List<ApArticle>> loadMore(@RequestBody ApArticleHomeDto apArticleHomeDto){
        return articleService.loadArticleList(apArticleHomeDto, ArticleConstants.LOAD_TYPE_MORE);
    }
    @PostMapping("/loadnew")
    public Result<List<ApArticle>> loadNew(@RequestBody ApArticleHomeDto apArticleHomeDto){
        return articleService.loadArticleList(apArticleHomeDto, ArticleConstants.LOAD_TYPE_NEW);
    }
}
