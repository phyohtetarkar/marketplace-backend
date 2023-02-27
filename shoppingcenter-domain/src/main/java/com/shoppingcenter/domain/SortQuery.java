package com.shoppingcenter.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SortQuery {

    public enum Direction {
        ASC, DESC
    }

    private String[] properties;

    private Direction direction;

    public static SortQuery of(String... properties) {
        return of(Direction.ASC, properties);
    }

    public static SortQuery of(Direction direction, String... properties) {
        var query = new SortQuery();
        query.setDirection(direction);
        query.setProperties(properties == null || properties.length == 0 ? new String[] { "createdAt" } : properties);
        return query;
    }

}
