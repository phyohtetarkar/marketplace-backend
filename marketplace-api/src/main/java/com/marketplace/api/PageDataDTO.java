package com.marketplace.api;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageDataDTO<T> {

	private List<T> contents;

	private int totalPage;

	private int currentPage;

	private int pageSize;

	private long totalElements;
	
	public List<T> getContents() {
		if (contents == null) {
			return new ArrayList<T>();
		}
		return contents;
	}
}
