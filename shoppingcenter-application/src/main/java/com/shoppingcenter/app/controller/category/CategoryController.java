package com.shoppingcenter.app.controller.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcenter.app.controller.category.dto.CategoryDTO;
import com.shoppingcenter.app.controller.product.ProductService;
import com.shoppingcenter.app.controller.product.dto.ProductFilterDTO;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/categories")
@Tag(name = "Category")
public class CategoryController {

    @Autowired
    private CategoryFacade categoryFacade;

    @Autowired
    private ProductService productService;

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

    @GetMapping("{id:\\d+}/filter")
    public ProductFilterDTO getProductFilter(@PathVariable int id) {
        return productService.getProductFilterByCategory(id);
    }

}
