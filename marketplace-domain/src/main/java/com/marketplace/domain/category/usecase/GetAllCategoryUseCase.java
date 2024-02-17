package com.marketplace.domain.category.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.Constants;
import com.marketplace.domain.PageData;
import com.marketplace.domain.category.Category;
import com.marketplace.domain.category.CategoryDao;
import com.marketplace.domain.common.PageQuery;

@Component
public class GetAllCategoryUseCase {

	@Autowired
    private CategoryDao dao;

	@Transactional(readOnly = true)
    public PageData<Category> apply(Integer page) {
        return dao.findAll(PageQuery.of(page, Constants.PAGE_SIZE));
    }

}
