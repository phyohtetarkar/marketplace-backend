package com.shoppingcenter.domain.category.usecase;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.Constants;
import com.shoppingcenter.domain.Utils;
import com.shoppingcenter.domain.category.Category;
import com.shoppingcenter.domain.category.CategoryDao;
import com.shoppingcenter.domain.common.FileStorageAdapter;
import com.shoppingcenter.domain.product.dao.ProductDao;

import lombok.Setter;

@Setter
public class DeleteCategoryUseCaseImpl implements DeleteCategoryUseCase {

    private CategoryDao categoryDao;

    private ProductDao productDao;

    private FileStorageAdapter fileStorageAdapter;

    @Override
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

        Category category = categoryDao.findById(id);

        var imageName = category.getImage();

        categoryDao.delete(id);

        if (Utils.hasText(imageName)) {
            fileStorageAdapter.delete(Constants.IMG_CATEGORY_ROOT, imageName);
        }

    }

}
