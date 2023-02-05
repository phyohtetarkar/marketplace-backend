package com.shoppingcenter.data;

import lombok.Getter;

@Getter
public class SearchCriteria {

    public enum Operator {
        EQUAL, EQUAL_IGNORE_CASE, GREATER_THAN_EQ, LESS_THAN_EQ, LIKE, NOT_EQ, IN
    }

    private String key;

    private String joinPath;

    private Operator operator;

    private Object value;

    public SearchCriteria(String key, Operator operator, Object value) {
        this(key, operator, value, null);
    }

    public SearchCriteria(String key, Operator operator, Object value, String joinPath) {
        this.key = key;
        this.operator = operator;
        this.value = value;
        this.joinPath = joinPath;
    }

}
