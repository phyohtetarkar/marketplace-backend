package com.shoppingcenter.app.controller.category;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.shoppingcenter.app.annotation.Facade;
import com.shoppingcenter.app.controller.MultipartFileMapper;
import com.shoppingcenter.app.controller.PageDataDTO;
import com.shoppingcenter.app.controller.category.dto.CategoryDTO;
import com.shoppingcenter.app.controller.category.dto.CategoryEditDTO;
import com.shoppingcenter.domain.category.Category;
import com.shoppingcenter.domain.category.usecase.DeleteCategoryUseCase;
import com.shoppingcenter.domain.category.usecase.GetAllCategoryUseCase;
import com.shoppingcenter.domain.category.usecase.GetCategoryByIdUseCase;
import com.shoppingcenter.domain.category.usecase.GetCategoryBySlugUseCase;
import com.shoppingcenter.domain.category.usecase.GetHierarchicalCategoryUseCase;
import com.shoppingcenter.domain.category.usecase.GetRootCategoriesUseCase;
import com.shoppingcenter.domain.category.usecase.SaveCategoryUseCase;

@Facade
public class CategoryFacade {

    @Autowired
    private SaveCategoryUseCase saveCategoryUseCase;

    @Autowired
    private DeleteCategoryUseCase deleteCategoryUseCase;

    @Autowired
    private GetCategoryBySlugUseCase getCategoryBySlugUseCase;
    
    @Autowired
    private GetCategoryByIdUseCase getCategoryByIdUseCase;

    @Autowired
    private GetHierarchicalCategoryUseCase getHierarchicalCategoryUseCase;

    @Autowired
    private GetRootCategoriesUseCase getRootCategoriesUseCase;

    @Autowired
    private GetAllCategoryUseCase getAllCategoryUseCase;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public void save(CategoryEditDTO category) {
    	var file = MultipartFileMapper.toUploadFile(category.getFile());
        saveCategoryUseCase.apply(modelMapper.map(category, Category.class), file);
    }

    @Transactional
    public void delete(int id) {
        deleteCategoryUseCase.apply(id);
    }

    public CategoryDTO findById(int id) {
    	var source = getCategoryByIdUseCase.apply(id);
    	if (source != null) {
    		return modelMapper.map(source, CategoryDTO.class);
    	}
        return null;
    }

    public CategoryDTO findBySlug(String slug) {
    	var source = getCategoryBySlugUseCase.apply(slug);
    	if (source != null) {
    		return modelMapper.map(source, CategoryDTO.class);
    	}
        
    	return null;
    }

    public List<CategoryDTO> findHierarchical() {
        return modelMapper.map(getHierarchicalCategoryUseCase.apply(), CategoryDTO.listType());
    }

    public List<CategoryDTO> findRootCategories() {
        return modelMapper.map(getRootCategoriesUseCase.apply(), CategoryDTO.listType());
    }

    public PageDataDTO<CategoryDTO> findAll(Integer page) {
        return modelMapper.map(getAllCategoryUseCase.apply(page), CategoryDTO.pageType());
    }

}
