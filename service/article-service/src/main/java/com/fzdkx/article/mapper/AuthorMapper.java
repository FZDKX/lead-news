package com.fzdkx.article.mapper;

import com.fzdkx.model.article.bean.ApAuthor;

public interface AuthorMapper {
    int deleteById(Long id);


    int insert(ApAuthor row);

    ApAuthor selectById(Long id);

    int update(ApAuthor row);

}