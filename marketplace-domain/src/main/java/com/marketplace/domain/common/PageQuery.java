package com.marketplace.domain.common;

import com.marketplace.domain.Utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageQuery {

    private int page;

    private int size;
    
    private SortQuery sort;

    private PageQuery() {
    }

    public static PageQuery of(Integer page, int size) {
        var query = new PageQuery();
        query.setPage(Utils.normalizePage(page));
        query.setSize(size);
        return query;
    }
    
    public SortQuery getSort() {
    	if (sort == null) {
    		return SortQuery.desc("createdAt");
    	}
    	return sort;
    }

}
