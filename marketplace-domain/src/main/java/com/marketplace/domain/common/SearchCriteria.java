package com.marketplace.domain.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchCriteria {

	public enum Operator {
		EQUAL, 
		EQUAL_IGNORE_CASE, 
		GREATER_THAN_EQ, 
		GREATER_THAN, 
		LESS_THAN_EQ, 
		LESS_THAN, 
		LIKE, 
		NOT_EQ, 
		IN, 
		NOT_NULL,
		NULL
	}

	private String key;

	private String joinPath;

	private Operator operator;

	private Object value;

	private Object[] values;
	
	private SearchCriteria orCriteria;
	
	public static SearchCriteria simple(String key, Operator op, Object value) {
		var c = new SearchCriteria();
		c.setKey(key);
		c.setOperator(op);
		c.setValue(value);
		return c;
	}
	
	public static SearchCriteria join(String key, Operator op, Object value, String joinPath) {
		var c = new SearchCriteria();
		c.setKey(key);
		c.setOperator(op);
		c.setValue(value);
		c.setJoinPath(joinPath);
		return c;
	}
	
	public static SearchCriteria in(String key, Object[] values) {
		var c = new SearchCriteria();
		c.setKey(key);
		c.setOperator(Operator.IN);
		c.setValues(values);
		return c;
	}
	
	public static SearchCriteria joinIn(String key, Object[] values, String joinPath) {
		var c = new SearchCriteria();
		c.setKey(key);
		c.setOperator(Operator.IN);
		c.setValues(values);
		c.setJoinPath(joinPath);
		return c;
	}
	
	public static SearchCriteria isNull(String key) {
		var c = new SearchCriteria();
		c.setKey(key);
		c.setOperator(Operator.NULL);
		return c;
	}
	
	public static SearchCriteria notNull(String key) {
		var c = new SearchCriteria();
		c.setKey(key);
		c.setOperator(Operator.NOT_NULL);
		return c;
	}

}
