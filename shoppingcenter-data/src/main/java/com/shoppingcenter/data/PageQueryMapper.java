package com.shoppingcenter.data;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.shoppingcenter.domain.PageQuery;

public class PageQueryMapper {

    public static Pageable fromPageQuery(PageQuery query) {
        return fromPageQuery(query, null);
    }

    public static Pageable fromPageQuery(PageQuery query, Sort sortBy) {
        if (sortBy == null) {
            return PageRequest.of(query.getPage(), query.getSize());
        }
        return PageRequest.of(query.getPage(), query.getSize(), sortBy);
    }

}
