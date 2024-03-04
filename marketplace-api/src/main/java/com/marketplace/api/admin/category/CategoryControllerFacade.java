package com.marketplace.api.admin.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketplace.api.PageDataDTO;
import com.marketplace.api.admin.AdminDataMapper;
import com.marketplace.domain.category.usecase.DeleteCategoryUseCase;
import com.marketplace.domain.category.usecase.GetAllCategoryUseCase;
import com.marketplace.domain.category.usecase.GetCategoryByIdUseCase;
import com.marketplace.domain.category.usecase.GetCategoryByParentUseCase;
import com.marketplace.domain.category.usecase.SaveCategoryUseCase;

@Component
public class CategoryControllerFacade {

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
	
	@Autowired
    private AdminDataMapper mapper;
	
	public void save(CategoryEditDTO values) {
        saveCategoryUseCase.apply(mapper.map(values));
    }

    public void delete(int id) {
        deleteCategoryUseCase.apply(id);
    }

    public CategoryDTO findById(int id) {
    	var source = getCategoryByIdUseCase.apply(id);
        return mapper.map(source);
    }
    
    public List<CategoryDTO> findByParent(int categoryId) {
    	var source = getCategoryByParentUseCase.apply(categoryId);
        return mapper.mapCategoryList(source);
    }
    
    public PageDataDTO<CategoryDTO> findAll(Integer page) {
        return mapper.mapCategoryPage(getAllCategoryUseCase.apply(page));
    }
}
