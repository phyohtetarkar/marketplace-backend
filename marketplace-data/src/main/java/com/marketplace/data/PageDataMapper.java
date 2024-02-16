package com.marketplace.data;

import java.util.function.Function;

import org.springframework.data.domain.Page;

import com.marketplace.domain.PageData;

public class PageDataMapper {

    public static <T, E> PageData<T> map(Page<E> pageResult, Function<E, T> mapper) {
        PageData<T> data = new PageData<>();
        data.setContents(pageResult.map(mapper).toList());
        data.setCurrentPage(pageResult.getNumber());
        data.setTotalPage(pageResult.getTotalPages());
        data.setPageSize(pageResult.getNumberOfElements());
        data.setTotalElements(pageResult.getTotalElements());

        return data;
    }

}
