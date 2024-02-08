package com.fzdkx.article.mapper;

import com.fzdkx.model.article.bean.ApCollection;

public interface CollectionMapper {
    int deleteById(Long id);

    int insert(ApCollection row);

    ApCollection selectById(Long id);

    int update(ApCollection row);
}