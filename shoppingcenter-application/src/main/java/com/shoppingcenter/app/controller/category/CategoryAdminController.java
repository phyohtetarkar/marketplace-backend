package com.shoppingcenter.app.controller.category;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcenter.app.controller.category.dto.CategoryDTO;
import com.shoppingcenter.app.controller.category.dto.CategoryEditDTO;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/private/categories")
@Tag(name = "CategoryAdmin")
public class CategoryAdminController {

	@Autowired
	private CategoryFacade categoryFacade;

	@PreAuthorize("hasAnyAuthority('ROLE_OWNER', 'CATEGORY_WRITE')")
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public void create(@ModelAttribute CategoryEditDTO category) {
		categoryFacade.save(category);
	}

	@PreAuthorize("hasAnyAuthority('ROLE_OWNER', 'CATEGORY_WRITE')")
	@PutMapping
	public void update(@ModelAttribute CategoryEditDTO category) {
		categoryFacade.save(category);
	}

	@PreAuthorize("hasAnyAuthority('ROLE_OWNER', 'CATEGORY_DELETE')")
	@DeleteMapping("{id:\\d+}")
	public void delete(@PathVariable int id) {
		categoryFacade.delete(id);
	}

	@PreAuthorize("hasAnyAuthority('ROLE_OWNER', 'CATEGORY_READ')")
	@GetMapping("{id:\\d+}")
	public CategoryDTO findById(@PathVariable int id) {
		return categoryFacade.findById(id);
	}
}
