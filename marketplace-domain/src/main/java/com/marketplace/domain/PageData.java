package com.marketplace.domain;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageData<T> {

	private List<T> contents;

	private int totalPage;

	private int currentPage;

	private int pageSize;

	private long totalElements;

}
