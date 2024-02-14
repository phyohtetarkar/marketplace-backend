package com.marketplace.api.consumer.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.marketplace.api.consumer.product.ProductFilterDTO;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/content/categories")
@Tag(name = "Consumer")
public class CategoryController {

	@Autowired
	private CategoryControllerFacade categoryFacade;

	@GetMapping("{slug}")
	public CategoryDTO findBySlug(@PathVariable String slug) {
		return categoryFacade.findBySlug(slug);
	}
	
	@GetMapping("{id:\\d+}/product-filter")
	public ProductFilterDTO getProductFilter(@PathVariable int id) {
		return categoryFacade.getProductFilter(id);
	}

	@GetMapping
	public List<CategoryDTO> getCategories(
			@RequestParam(required = false, defaultValue = "false") boolean root) {
		if (root) {
			return categoryFacade.getRootCategories();
		}
		return categoryFacade.getCategoryTree();
	}
	
}
