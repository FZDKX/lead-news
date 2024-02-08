package com.fzdkx.apis.article;

import com.fzdkx.model.article.dto.ArticleDto;
import com.fzdkx.model.common.vo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author 发着呆看星
 * @create 2024/2/8
 */
@FeignClient(value = "article-service")
public interface IArticleClient {
    @PostMapping("/api/v1/article/save")
    public Result<Long> save(@RequestBody ArticleDto dto);
}
