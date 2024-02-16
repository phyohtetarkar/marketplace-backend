package com.marketplace.data;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.marketplace.domain.common.SortQuery;

public class SortQueryMapper {

    public static Sort fromQuery(SortQuery query) {
        var direction = query.getDirection() == SortQuery.Direction.DESC ? Direction.DESC : Direction.ASC;
        var sortBy = Sort.by(direction, query.getProperties());
        return sortBy;
    }

}
