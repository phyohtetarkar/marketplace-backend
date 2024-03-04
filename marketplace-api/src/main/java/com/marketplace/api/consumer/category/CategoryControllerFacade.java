package com.marketplace.api.consumer.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketplace.api.consumer.ConsumerDataMapper;
import com.marketplace.api.consumer.product.ProductFilterDTO;
import com.marketplace.domain.category.usecase.GetCategoryBySlugUseCase;
import com.marketplace.domain.category.usecase.GetHierarchicalCategoryUseCase;
import com.marketplace.domain.category.usecase.GetRootCategoriesUseCase;
import com.marketplace.domain.product.usecase.GetProductFilterByCategoryUseCase;

@Component
public class CategoryControllerFacade {

	@Autowired
	private GetCategoryBySlugUseCase getCategoryBySlugUseCase;

	@Autowired
	private GetHierarchicalCategoryUseCase getHierarchicalCategoryUseCase;

	@Autowired
	private GetRootCategoriesUseCase getRootCategoriesUseCase;
	
	@Autowired
	private GetProductFilterByCategoryUseCase getProductFilterByCategoryUseCase;
	
	@Autowired
	private ConsumerDataMapper mapper;

	public CategoryDTO findBySlug(String slug) {
		var source = getCategoryBySlugUseCase.apply(slug);
		return mapper.map(source);
	}

	public List<CategoryDTO> getCategoryTree() {
		var source = getHierarchicalCategoryUseCase.apply(true);
		return mapper.mapCategoryList(source);
	}

	public List<CategoryDTO> getRootCategories() {
		var source = getRootCategoriesUseCase.apply();
		return mapper.mapCategoryList(source);
	}
	
	public ProductFilterDTO getProductFilter(int categoryId) {
    	var source = getProductFilterByCategoryUseCase.apply(categoryId);
    	return mapper.map(source);
    }

}
