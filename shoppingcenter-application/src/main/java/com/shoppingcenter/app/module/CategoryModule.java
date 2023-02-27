package com.shoppingcenter.app.module;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.shoppingcenter.domain.category.CategoryDao;
import com.shoppingcenter.domain.category.usecase.DeleteCategoryUseCase;
import com.shoppingcenter.domain.category.usecase.DeleteCategoryUseCaseImpl;
import com.shoppingcenter.domain.category.usecase.GetAllCategoryUseCase;
import com.shoppingcenter.domain.category.usecase.GetAllCategoryUseCaseImpl;
import com.shoppingcenter.domain.category.usecase.GetCategoryBySlugUseCase;
import com.shoppingcenter.domain.category.usecase.GetCategoryBySlugUseCaseImpl;
import com.shoppingcenter.domain.category.usecase.GetHierarchicalCategoryUseCase;
import com.shoppingcenter.domain.category.usecase.GetHierarchicalCategoryUseCaseImpl;
import com.shoppingcenter.domain.category.usecase.GetRootCategoriesUseCase;
import com.shoppingcenter.domain.category.usecase.GetRootCategoriesUseCaseImpl;
import com.shoppingcenter.domain.category.usecase.SaveCategoryUseCase;
import com.shoppingcenter.domain.category.usecase.SaveCategoryUseCaseImpl;
import com.shoppingcenter.domain.common.FileStorageAdapter;
import com.shoppingcenter.domain.product.dao.ProductDao;

@Configuration
public class CategoryModule {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private FileStorageAdapter fileStorageAdapter;

    @Bean
    SaveCategoryUseCase saveCategoryUseCase() {
        var usecase = new SaveCategoryUseCaseImpl();
        usecase.setDao(categoryDao);
        usecase.setFileStorageAdapter(fileStorageAdapter);
        return usecase;
    }

    @Bean
    DeleteCategoryUseCase deleteCategoryUseCase() {
        var usecase = new DeleteCategoryUseCaseImpl();
        usecase.setCategoryDao(categoryDao);
        usecase.setProductDao(productDao);
        usecase.setFileStorageAdapter(fileStorageAdapter);
        return usecase;
    }

    @Bean
    GetCategoryBySlugUseCase getCategoryBySlugUseCase() {
        return new GetCategoryBySlugUseCaseImpl(categoryDao);
    }

    @Bean
    GetHierarchicalCategoryUseCase getHierarchicalCategoryUseCase() {
        return new GetHierarchicalCategoryUseCaseImpl(categoryDao);
    }

    @Bean
    GetRootCategoriesUseCase getRootCategoriesUseCase() {
        return new GetRootCategoriesUseCaseImpl(categoryDao);
    }

    @Bean
    GetAllCategoryUseCase getAllCategoryUseCase() {
        return new GetAllCategoryUseCaseImpl(categoryDao);
    }

}
