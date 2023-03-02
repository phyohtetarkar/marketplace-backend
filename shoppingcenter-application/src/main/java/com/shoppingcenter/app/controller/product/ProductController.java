package com.shoppingcenter.app.controller.product;

import java.util.List;

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
import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.product.Product;
import com.shoppingcenter.domain.product.ProductQuery;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/products")
@Tag(name = "Product")
public class ProductController {

    @Autowired
    private ProductFacade productFacade;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void create(@ModelAttribute ProductEditDTO product) {
        productFacade.save(product);
    }

    @PutMapping
    public void update(@ModelAttribute ProductEditDTO product) {
        productFacade.save(product);
    }

    @DeleteMapping("{id:\\d+}")
    public void delete(long id) {
        productFacade.delete(id);
    }

    // @GetMapping("{id:\\d+}")
    // public Product findById(long id) {
    // return productQueryService.findById(id);
    // }

    @GetMapping("{slug}")
    public ProductDTO findBySlug(@PathVariable String slug) {
        return productFacade.findBySlug(slug);
    }

    @GetMapping("{productId:\\d+}/related")
    public List<ProductDTO> getRelatedProducts(
            @PathVariable long productId,
            @RequestParam("category-id") int categoryId) {
        return productFacade.getRelatedProducts(productId, categoryId);
    }

    @GetMapping
    public PageData<ProductDTO> findAll(
            @RequestParam(required = false) String q,
            @RequestParam(required = false, name = "brand") String[] brands,
            @RequestParam(required = false, name = "category-slug") String categorySlug,
            @RequestParam(required = false, name = "shop-id") Long shopId,
            @RequestParam(required = false, name = "discount-id") Long discountId,
            @RequestParam(required = false, name = "max-price") Double maxPrice,
            @RequestParam(required = false) Integer page) {
        ProductQuery query = ProductQuery.builder()
                .q(q)
                .categorySlug(categorySlug)
                .shopId(shopId)
                .discountId(discountId)
                .maxPrice(maxPrice)
                .status(Product.Status.PUBLISHED)
                .brands(brands)
                .page(page)
                .build();

        return productFacade.findAll(query);
    }
}
