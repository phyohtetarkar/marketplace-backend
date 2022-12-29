package com.shoppingcenter.app.controller.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcenter.core.PageData;
import com.shoppingcenter.core.product.ProductQuery;
import com.shoppingcenter.core.product.ProductQueryService;
import com.shoppingcenter.core.product.ProductService;
import com.shoppingcenter.core.product.model.Product;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/products")
@Tag(name = "Product")
public class ProductController {

    @Autowired
    private ProductService service;

    @Autowired
    private ProductQueryService productQueryService;

    @PostMapping
    public void create(@RequestBody Product product) {
        service.save(product);
    }

    @PutMapping
    public void update(@RequestBody Product product) {
        service.save(product);
    }

    @DeleteMapping("{id:\\d+}")
    public void delete(long id) {
        service.delete(id);
    }

    // @GetMapping("{id:\\d+}")
    // public Product findById(long id) {
    // return productQueryService.findById(id);
    // }

    @GetMapping("{slug}")
    public Product findBySlug(String slug) {
        return productQueryService.findBySlug(slug);
    }

    @GetMapping
    public PageData<Product> findAll(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Integer page) {
        ProductQuery query = ProductQuery.builder()
                .q(q)
                .categorySlug(category)
                .page(page)
                .build();

        return productQueryService.findAll(query);
    }
}
