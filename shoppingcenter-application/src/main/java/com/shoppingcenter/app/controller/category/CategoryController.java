package com.shoppingcenter.app.controller.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcenter.app.controller.category.dto.CategoryDTO;
import com.shoppingcenter.app.controller.category.dto.CategoryEditDTO;
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

    @Secured({ "ROLE_ADMIN", "ROLE_OWNER" })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void create(@ModelAttribute CategoryEditDTO category) {
        categoryFacade.save(category);
    }

    @Secured({ "ROLE_ADMIN", "ROLE_OWNER" })
    @PutMapping
    public void update(@ModelAttribute CategoryEditDTO category) {
        categoryFacade.save(category);
    }

    @Secured({ "ROLE_ADMIN", "ROLE_OWNER" })
    @DeleteMapping("{id:\\d+}")
    public void delete(@PathVariable int id) {
        categoryFacade.delete(id);
    }

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

    // @Secured({ "ROLE_ADMIN", "ROLE_OWNER" })
    // @GetMapping("{id:\\d+}")
    // public CategoryDTO findById(@PathVariable int id) {
    // return modelMapper.map(service.findById(id), CategoryDTO.class);
    // }

    @GetMapping("{slug}")
    public CategoryDTO findBySlug(@PathVariable String slug) {
        return categoryFacade.findBySlug(slug);
    }

    @GetMapping("{id:\\d+}/brands")
    public List<String> getProductBrands(@PathVariable int id) {
        return productFacade.getProductBrandsByCategory(id);
    }

}
