package com.shoppingcenter.app.controller.product;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcenter.app.controller.PageDataDTO;
import com.shoppingcenter.app.controller.product.dto.ProductDTO;
import com.shoppingcenter.app.controller.product.dto.ProductFilterDTO;
import com.shoppingcenter.domain.product.Product;
import com.shoppingcenter.domain.product.ProductQuery;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/products")
@Tag(name = "Product")
public class ProductController {

    @Autowired
    private ProductService productService;
    
    @GetMapping("{slug}")
    public ProductDTO findBySlug(@PathVariable String slug) {
        return productService.findBySlug(slug);
    }

    @GetMapping("{id:\\d+}/related")
    public List<ProductDTO> getRelatedProducts(@PathVariable long id) {
        return productService.getRelatedProducts(id);
    }
    
    @GetMapping("{q}/filter")
    public ProductFilterDTO getProductFilterByNameLike(@PathVariable String q) {
        return productService.getProductFilterByNameLike(q);
    }

    @GetMapping
    public PageDataDTO<ProductDTO> findAll(
            @RequestParam(required = false) String q,
            @RequestParam(required = false, name = "brand") String[] brands,
            @RequestParam(required = false, name = "category-id") Integer categoryId,
            @RequestParam(required = false, name = "shop-id") Long shopId,
            @RequestParam(required = false, name = "discount-id") Long discountId,
            @RequestParam(required = false, name = "max-price") Long maxPrice,
            @RequestParam(required = false) Integer page) {
        ProductQuery query = ProductQuery.builder()
                .q(q)
                .categoryId(categoryId)
                .shopId(shopId)
                .discountId(discountId)
                .maxPrice(maxPrice != null ? BigDecimal.valueOf(maxPrice) : null)
                .status(Product.Status.PUBLISHED)
                .brands(brands)
                .disabled(false)
                .page(page)
                .build();

        return productService.findAll(query);
    }
}
