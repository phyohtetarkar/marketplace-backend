package com.marketplace.api.admin.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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

import com.marketplace.api.PageDataDTO;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/admin/categories")
@Tag(name = "Admin")
public class CategoryController {

	@Autowired
	private CategoryControllerFacade categoryFacade;
	
	@PreAuthorize("hasPermission('CATEGORY', 'WRITE')")
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public void create(@ModelAttribute CategoryEditDTO body) {
		categoryFacade.save(body);
	}

	@PreAuthorize("hasPermission('CATEGORY', 'WRITE')")
	@PutMapping
	public void update(@ModelAttribute CategoryEditDTO body) {
		categoryFacade.save(body);
	}

	@PreAuthorize("hasPermission('CATEGORY', 'WRITE')")
	@DeleteMapping("{id:\\d+}")
	public void delete(@PathVariable int id) {
		categoryFacade.delete(id);
	}

	@PreAuthorize("hasPermission('CATEGORY', 'READ') or hasPermission('CATEGORY', 'WRITE')")
	@GetMapping("{id:\\d+}")
	public CategoryDTO findById(@PathVariable int id) {
		return categoryFacade.findById(id);
	}
	
	@PreAuthorize("hasPermission('CATEGORY', 'READ') or hasPermission('CATEGORY', 'WRITE')")
	@GetMapping("{categoryId:\\d+}/list")
    public List<CategoryDTO> findByParent(@PathVariable int categoryId) {
    	return categoryFacade.findByParent(categoryId);
    }
	
	@PreAuthorize("hasPermission('CATEGORY', 'READ') or hasPermission('CATEGORY', 'WRITE')")
	@GetMapping
    public PageDataDTO<CategoryDTO> findAll(
    		@RequestParam(required = false) Integer page) {
    	return categoryFacade.findAll(page);
    }
}
