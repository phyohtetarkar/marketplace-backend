package com.marketplace.api.consumer.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketplace.api.AbstractControllerFacade;
import com.marketplace.api.consumer.product.ProductFilterDTO;
import com.marketplace.domain.category.usecase.GetCategoryBySlugUseCase;
import com.marketplace.domain.category.usecase.GetHierarchicalCategoryUseCase;
import com.marketplace.domain.category.usecase.GetRootCategoriesUseCase;
import com.marketplace.domain.product.usecase.GetProductFilterByCategoryUseCase;

@Component
public class CategoryControllerFacade extends AbstractControllerFacade {

	@Autowired
	private GetCategoryBySlugUseCase getCategoryBySlugUseCase;

	@Autowired
	private GetHierarchicalCategoryUseCase getHierarchicalCategoryUseCase;

	@Autowired
	private GetRootCategoriesUseCase getRootCategoriesUseCase;
	
	@Autowired
	private GetProductFilterByCategoryUseCase getProductFilterByCategoryUseCase;

	public CategoryDTO findBySlug(String slug) {
		var source = getCategoryBySlugUseCase.apply(slug);
		return map(source, CategoryDTO.class);
	}

	public List<CategoryDTO> getCategoryTree() {
		var source = getHierarchicalCategoryUseCase.apply(true);
		return map(source, CategoryDTO.listType());
	}

	public List<CategoryDTO> getRootCategories() {
		var source = getRootCategoriesUseCase.apply();
		return map(source, CategoryDTO.listType());
	}
	
	public ProductFilterDTO getProductFilter(int categoryId) {
    	var source = getProductFilterByCategoryUseCase.apply(categoryId);
    	return map(source, ProductFilterDTO.class);
    }

}
