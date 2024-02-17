package com.marketplace.domain.category.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.Constants;
import com.marketplace.domain.Utils;
import com.marketplace.domain.category.CategoryDao;
import com.marketplace.domain.common.FileStorageAdapter;
import com.marketplace.domain.product.dao.ProductDao;

@Component
public class DeleteCategoryUseCase {

	@Autowired
    private CategoryDao categoryDao;

	@Autowired
    private ProductDao productDao;

	@Autowired
    private FileStorageAdapter fileStorageAdapter;

	@Transactional
    public void apply(int id) {
        if (!categoryDao.existsById(id)) {
            throw new ApplicationException("Category not found");
        }

        if (categoryDao.existsByCategory(id)) {
            throw new ApplicationException("Referenced by other categories");
        }

        if (productDao.existsByCategory(id)) {
            throw new ApplicationException("Referenced by products");
        }

        var image = categoryDao.getCategoryImage(id);

        categoryDao.delete(id);

        if (Utils.hasText(image)) {
            fileStorageAdapter.delete(Constants.IMG_CATEGORY_ROOT, image);
        }

    }

}
