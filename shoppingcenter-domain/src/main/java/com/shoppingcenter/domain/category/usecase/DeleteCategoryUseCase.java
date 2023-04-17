package com.shoppingcenter.domain.category.usecase;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.Constants;
import com.shoppingcenter.domain.Utils;
import com.shoppingcenter.domain.category.CategoryDao;
import com.shoppingcenter.domain.common.FileStorageAdapter;
import com.shoppingcenter.domain.product.dao.ProductDao;

import lombok.Setter;

@Setter
public class DeleteCategoryUseCase {

    private CategoryDao categoryDao;

    private ProductDao productDao;

    private FileStorageAdapter fileStorageAdapter;

    public void apply(int id) {
        if (!categoryDao.existsById(id)) {
            throw new ApplicationException("Category not found");
        }

        if (categoryDao.existsByCategoryId(id)) {
            throw new ApplicationException("Referenced by other categories");
        }

        if (productDao.existsByCategoryId(id)) {
            throw new ApplicationException("Referenced by products");
        }

        var image = categoryDao.getCategoryImage(id);


        categoryDao.delete(id);

        if (Utils.hasText(image)) {
            fileStorageAdapter.delete(Constants.IMG_CATEGORY_ROOT, image);
        }

    }

}
