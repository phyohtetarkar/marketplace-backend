package com.shoppingcenter.app.controller.category;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.shoppingcenter.app.annotation.Facade;
import com.shoppingcenter.app.controller.category.dto.CategoryDTO;
import com.shoppingcenter.app.controller.category.dto.CategoryEditDTO;
import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.category.Category;
import com.shoppingcenter.domain.category.usecase.DeleteCategoryUseCase;
import com.shoppingcenter.domain.category.usecase.GetAllCategoryUseCase;
import com.shoppingcenter.domain.category.usecase.GetCategoryBySlugUseCase;
import com.shoppingcenter.domain.category.usecase.GetHierarchicalCategoryUseCase;
import com.shoppingcenter.domain.category.usecase.GetRootCategoriesUseCase;
import com.shoppingcenter.domain.category.usecase.SaveCategoryUseCase;

@Facade
public class CategoryFacadeImpl implements CategoryFacade {

    @Autowired
    private SaveCategoryUseCase saveCategoryUseCase;

    @Autowired
    private DeleteCategoryUseCase deleteCategoryUseCase;

    @Autowired
    private GetCategoryBySlugUseCase getCategoryBySlugUseCase;

    @Autowired
    private GetHierarchicalCategoryUseCase getHierarchicalCategoryUseCase;

    @Autowired
    private GetRootCategoriesUseCase getRootCategoriesUseCase;

    @Autowired
    private GetAllCategoryUseCase getAllCategoryUseCase;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    @Override
    public void save(CategoryEditDTO category) {
        // saveCategoryUseCase.apply(modelMapper.map(category, Category.class));
    }

    @Transactional
    @Override
    public void delete(int id) {
        deleteCategoryUseCase.apply(id);
    }

    @Override
    public CategoryDTO findById(int id) {
        return null;
    }

    @Override
    public CategoryDTO findBySlug(String slug) {
        return modelMapper.map(getCategoryBySlugUseCase.apply(slug), CategoryDTO.class);
    }

    @Override
    public boolean existsBySlug(String slug, Integer excludeId) {
        return false;
    }

    @Override
    public List<CategoryDTO> findHierarchical() {
        return modelMapper.map(getHierarchicalCategoryUseCase.apply(), CategoryDTO.listType());
    }

    @Override
    public List<CategoryDTO> findRootCategories() {
        return modelMapper.map(getRootCategoriesUseCase.apply(), CategoryDTO.listType());
    }

    @Override
    public PageData<CategoryDTO> findAll(Integer page) {
        return modelMapper.map(getAllCategoryUseCase.apply(page), CategoryDTO.pageType());
    }

}
