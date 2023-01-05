package com.shoppingcenter.app.controller.category;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcenter.core.PageData;
import com.shoppingcenter.core.category.CategoryService;
import com.shoppingcenter.core.category.model.Category;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/categories")
@Tag(name = "Category")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @GetMapping
    public PageData<Category> getCategories(@RequestParam(required = false) Integer page) {
        return service.findAll(page);
    }

    @GetMapping("structural")
    public List<Category> getCategories(@RequestParam boolean flat) {
        if (flat) {
            return service.findFlat();
        }

        return service.findHierarchical();
    }

    @Secured({ "ROLE_ADMIN", "ROLE_OWNER" })
    @GetMapping("{id:\\d+}")
    public Category findById(@PathVariable int id) {
        return service.findById(id);
    }

    @GetMapping("{slug}/exists")
    public boolean existsBySlug(@PathVariable String slug) {
        return service.existsBySlug(slug);
    }

    @GetMapping("{slug}")
    public Category findBySlug(@PathVariable String slug) {
        return service.findBySlug(slug);
    }

    @Secured({ "ROLE_ADMIN", "ROLE_OWNER" })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void create(@ModelAttribute Category category) {
        service.save(category);
    }

    @Secured({ "ROLE_ADMIN", "ROLE_OWNER" })
    @PutMapping
    public void update(@ModelAttribute Category category) {
        service.save(category);
    }

    @Secured({ "ROLE_ADMIN", "ROLE_OWNER" })
    @DeleteMapping("{id:\\d+}")
    public void delete(@PathVariable int id) {
        service.delete(id);
    }
}
