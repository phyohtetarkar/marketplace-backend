package com.marketplace.data;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.marketplace.domain.common.PageQuery;
import com.marketplace.domain.common.SortQuery;

public class PageQueryMapper {

	public static Pageable fromPageQuery(PageQuery query) {
		return fromPageQuery(query, query.getSort());
	}

	public static Pageable fromPageQuery(PageQuery query, SortQuery sortQuery) {
		if (query.getSize() <= 0) {
			if (sortQuery == null) {
				return Pageable.unpaged();
			}

			return Pageable.unpaged(SortQueryMapper.fromQuery(sortQuery));
		}

		if (sortQuery == null) {
			return PageRequest.of(query.getPage(), query.getSize());
		}

		return PageRequest.of(query.getPage(), query.getSize(), SortQueryMapper.fromQuery(sortQuery));
	}

}
