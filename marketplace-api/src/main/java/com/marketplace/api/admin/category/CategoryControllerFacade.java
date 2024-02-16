package com.marketplace.api.admin.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketplace.api.AbstractControllerFacade;
import com.marketplace.api.PageDataDTO;
import com.marketplace.domain.category.CategoryInput;
import com.marketplace.domain.category.usecase.DeleteCategoryUseCase;
import com.marketplace.domain.category.usecase.GetAllCategoryUseCase;
import com.marketplace.domain.category.usecase.GetCategoryByIdUseCase;
import com.marketplace.domain.category.usecase.GetCategoryByParentUseCase;
import com.marketplace.domain.category.usecase.SaveCategoryUseCase;

@Component
public class CategoryControllerFacade extends AbstractControllerFacade {

	@Autowired
	private SaveCategoryUseCase saveCategoryUseCase;

	@Autowired
	private DeleteCategoryUseCase deleteCategoryUseCase;

	@Autowired
	private GetCategoryByIdUseCase getCategoryByIdUseCase;

	@Autowired
	private GetAllCategoryUseCase getAllCategoryUseCase;
	
	@Autowired
	private GetCategoryByParentUseCase getCategoryByParentUseCase;
	
	public void save(CategoryEditDTO values) {
        saveCategoryUseCase.apply(modelMapper.map(values, CategoryInput.class));
    }

    public void delete(int id) {
        deleteCategoryUseCase.apply(id);
    }

    public CategoryDTO findById(int id) {
    	var source = getCategoryByIdUseCase.apply(id);
        return map(source, CategoryDTO.class);
    }
    
    public List<CategoryDTO> findByParent(int categoryId) {
    	var source = getCategoryByParentUseCase.apply(categoryId);
        return map(source, CategoryDTO.listType());
    }
    
    public PageDataDTO<CategoryDTO> findAll(Integer page) {
        return map(getAllCategoryUseCase.apply(page), CategoryDTO.pageType());
    }
}
