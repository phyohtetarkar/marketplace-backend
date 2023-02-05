package com.shoppingcenter.app.controller.product;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.shoppingcenter.app.controller.product.dto.ProductDTO;
import com.shoppingcenter.app.controller.product.dto.ProductEditDTO;
import com.shoppingcenter.service.PageData;
import com.shoppingcenter.service.product.ProductQuery;
import com.shoppingcenter.service.product.ProductQueryService;
import com.shoppingcenter.service.product.ProductService;
import com.shoppingcenter.service.product.model.Product;

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

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void create(@ModelAttribute ProductEditDTO product) {
        service.save(modelMapper.map(product, Product.class));
    }

    @PutMapping
    public void update(@ModelAttribute ProductEditDTO product) {
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
    public ProductDTO findBySlug(@PathVariable String slug) {
        return modelMapper.map(productQueryService.findBySlug(slug), ProductDTO.class);
    }

    @GetMapping("{slug}/exists")
    public boolean existsBySlug(@PathVariable String slug) {
        return productQueryService.existsBySlug(slug);
    }

    @GetMapping("hints")
    public List<ProductDTO> searchHints(@RequestParam String q) {
        return modelMapper.map(productQueryService.getHints(q), ProductDTO.listType());
    }

    @GetMapping
    public PageData<ProductDTO> findAll(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) Product.Status status,
            @RequestParam(required = false, name = "brand") String[] brands,
            @RequestParam(required = false, name = "category-slug") String categorySlug,
            @RequestParam(required = false, name = "shop-id") Long shopId,
            @RequestParam(required = false, name = "max-price") Double maxPrice,
            @RequestParam(required = false) Integer page) {
        ProductQuery query = ProductQuery.builder()
                .q(q)
                .categorySlug(categorySlug)
                .shopId(shopId)
                .maxPrice(maxPrice)
                .status(status)
                .brands(brands)
                .page(page)
                .build();

        return modelMapper.map(productQueryService.findAll(query), ProductDTO.pageType());
    }
}
