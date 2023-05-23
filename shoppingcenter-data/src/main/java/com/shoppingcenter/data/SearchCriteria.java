package com.shoppingcenter.data;

import lombok.Getter;

@Getter
public class SearchCriteria {

    public enum Operator {
        EQUAL, EQUAL_IGNORE_CASE, GREATER_THAN_EQ, GREATER_THAN, LESS_THAN_EQ, LESS_THAN, LIKE, NOT_EQ, IN
    }

    private String key;

    private String joinPath;

    private Operator operator;

    private Object value;

    private Object[] values;

    public static SearchCriteria in(String key, Object... values) {
        return new SearchCriteria(key, Operator.IN, null, values);
    }

    public static SearchCriteria joinIn(String key, String joinPath, Object... values) {
        return new SearchCriteria(key, Operator.IN, joinPath, values);
    }

    public SearchCriteria(String key, Operator operator, Object value) {
        this(key, operator, value, null);
    }

    public SearchCriteria(String key, Operator operator, Object value, String joinPath) {
        this.key = key;
        this.operator = operator;
        this.value = value;
        this.joinPath = joinPath;
    }

    public SearchCriteria(String key, Operator operator, String joinPath, Object... values) {
        this.key = key;
        this.operator = operator;
        this.values = values;
        this.joinPath = joinPath;
    }

}
