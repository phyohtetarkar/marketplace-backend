package com.shoppingcenter.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageQuery {

    private int page;

    private int size;

    public PageQuery() {
    }

    public static PageQuery of(int page, int size) {
        var query = new PageQuery();
        query.setPage(page);
        query.setSize(size);
        return query;
    }

}
