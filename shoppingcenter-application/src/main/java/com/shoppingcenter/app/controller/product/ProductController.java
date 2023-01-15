package com.shoppingcenter.app.controller.product;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcenter.app.controller.product.dto.ProductDTO;
import com.shoppingcenter.app.controller.product.dto.ProductEditDTO;
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

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public void create(@RequestBody ProductEditDTO product) {
        service.save(modelMapper.map(product, Product.class));
    }

    @PutMapping
    public void update(@RequestBody ProductEditDTO product) {
        service.save(modelMapper.map(product, Product.class));
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
    public ProductDTO findBySlug(String slug) {
        return modelMapper.map(productQueryService.findBySlug(slug), ProductDTO.class);
    }

    @GetMapping("{slug}/exists")
    public boolean existsBySlug(String slug) {
        return productQueryService.existsBySlug(slug);
    }

    @GetMapping
    public PageData<ProductDTO> findAll(
            @RequestParam(required = false) String q,
            @RequestParam(required = false, name = "category-id") Integer categoryId,
            @RequestParam(required = false, name = "shop-id") Long shopId,
            @RequestParam(required = false, name = "max-price") Double maxPrice,
            @RequestParam(required = false) Integer page) {
        ProductQuery query = ProductQuery.builder()
                .q(q)
                .categoryId(categoryId)
                .shopId(shopId)
                .maxPrice(maxPrice)
                .page(page)
                .build();

        return modelMapper.map(productQueryService.findAll(query), ProductDTO.pageType());
    }
}
