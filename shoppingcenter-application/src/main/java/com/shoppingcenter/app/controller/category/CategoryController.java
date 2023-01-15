package com.shoppingcenter.app.controller.category;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.ModelMapper;
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

import com.shoppingcenter.app.controller.category.dto.CategoryDTO;
import com.shoppingcenter.app.controller.category.dto.CategoryEditDTO;
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

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public PageData<CategoryDTO> getCategories(@RequestParam(required = false) Integer page) {
        return modelMapper.map(service.findAll(page), CategoryDTO.pageType());
    }

    @GetMapping("structural")
    public List<CategoryDTO> getCategories(@RequestParam boolean flat) {
        Type listType = CategoryDTO.listType();

        if (flat) {
            return modelMapper.map(service.findFlat(), listType);
        }

        return modelMapper.map(service.findHierarchical(), listType);
    }

    @Secured({ "ROLE_ADMIN", "ROLE_OWNER" })
    @GetMapping("{id:\\d+}")
    public CategoryDTO findById(@PathVariable int id) {
        return modelMapper.map(service.findById(id), CategoryDTO.class);
    }

    @GetMapping("{slug}/exists")
    public boolean existsBySlug(@PathVariable String slug) {
        return service.existsBySlug(slug);
    }

    @GetMapping("{slug}")
    public CategoryDTO findBySlug(@PathVariable String slug) {
        return modelMapper.map(service.findBySlug(slug), CategoryDTO.class);
    }

    @Secured({ "ROLE_ADMIN", "ROLE_OWNER" })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void create(@ModelAttribute CategoryEditDTO category) {
        service.save(modelMapper.map(category, Category.class));
    }

    @Secured({ "ROLE_ADMIN", "ROLE_OWNER" })
    @PutMapping
    public void update(@ModelAttribute CategoryEditDTO category) {
        service.save(modelMapper.map(category, Category.class));
    }

    @Secured({ "ROLE_ADMIN", "ROLE_OWNER" })
    @DeleteMapping("{id:\\d+}")
    public void delete(@PathVariable int id) {
        service.delete(id);
    }
}
