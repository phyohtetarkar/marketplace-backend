package com.shoppingcenter.core;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageResult<T> {

	private List<T> contents;
	
	private int totalPage;
	
	private int currentPage;
	
	private int pageSize;
	
	private long offset;
}
