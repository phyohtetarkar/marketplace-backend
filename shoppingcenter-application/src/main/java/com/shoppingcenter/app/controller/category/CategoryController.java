package com.shoppingcenter.app.controller.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcenter.app.controller.category.dto.CategoryDTO;
import com.shoppingcenter.app.controller.product.ProductFacade;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/categories")
@Tag(name = "Category")
public class CategoryController {

    @Autowired
    private CategoryFacade categoryFacade;

    @Autowired
    private ProductFacade productFacade;

    @GetMapping
    public ResponseEntity<?> getCategories(
    		@RequestParam(defaultValue = "false") boolean tree, 
    		@RequestParam(required = false) Integer page) {
    	
    	if (tree) {
    		return ResponseEntity.ok(categoryFacade.findHierarchical());
    	}
    	
    	
    	return ResponseEntity.ok(categoryFacade.findAll(page));
    }

//    @GetMapping("structural")
//    public List<CategoryDTO> getCategories(@RequestParam boolean flat) {
//        // Type listType = CategoryDTO.listType();
//
//        // if (flat) {
//        // return modelMapper.map(service.findFlat(), listType);
//        // }
//
//        return categoryFacade.findHierarchical();
//    }

    @GetMapping("{slug}")
    public CategoryDTO findBySlug(@PathVariable String slug) {
        return categoryFacade.findBySlug(slug);
    }

    @GetMapping("{id:\\d+}/brands")
    public List<String> getProductBrands(@PathVariable int id) {
        return productFacade.getProductBrandsByCategory(id);
    }

}
