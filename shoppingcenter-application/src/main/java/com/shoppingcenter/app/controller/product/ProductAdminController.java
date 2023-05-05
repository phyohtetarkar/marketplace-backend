package com.shoppingcenter.app.controller.product;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcenter.app.controller.PageDataDTO;
import com.shoppingcenter.app.controller.product.dto.ProductDTO;
import com.shoppingcenter.domain.product.ProductQuery;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/admin/products")
@Tag(name = "ProductAdmin")
public class ProductAdminController {

    @Autowired
    private ProductFacade productFacade;

    @PutMapping("${id:\\d+}/publish")
    public void publishProduct(@PathVariable long id) {
    }

    @PutMapping("${id:\\d+}/disable")
    public void disableProduct(@PathVariable long id) {
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
                .brands(brands)
                .hidden(false)
                .page(page)
                .build();

        return productFacade.findAll(query);
    }

}
