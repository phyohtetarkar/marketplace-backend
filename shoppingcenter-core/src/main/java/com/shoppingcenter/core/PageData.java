package com.shoppingcenter.core;

import java.util.List;
import java.util.function.Function;

import org.springframework.data.domain.Page;

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

	public static <T, E> PageData<T> build(Page<E> pageResult, Function<E, T> mapper) {
		PageData<T> data = new PageData<>();
		data.setContents(pageResult.map(mapper).toList());
		data.setCurrentPage(pageResult.getNumber());
		data.setTotalPage(pageResult.getTotalPages());
		data.setPageSize(pageResult.getNumberOfElements());
		data.setTotalElements(pageResult.getTotalElements());

		return data;
	}

}
