package com.shoppingcenter.app.controller.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
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

	@Secured({ "ROLE_ADMIN", "ROLE_OWNER" })
	@GetMapping("{id:\\d+}")
	public CategoryDTO findById(@PathVariable int id) {
		return categoryFacade.findById(id);
	}
}
