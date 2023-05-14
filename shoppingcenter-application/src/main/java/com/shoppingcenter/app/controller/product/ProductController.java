package com.shoppingcenter.app.controller.product;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
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

import com.shoppingcenter.app.controller.PageDataDTO;
import com.shoppingcenter.app.controller.product.dto.ProductDTO;
import com.shoppingcenter.app.controller.product.dto.ProductEditDTO;
import com.shoppingcenter.domain.common.AuthenticationContext;
import com.shoppingcenter.domain.product.ProductQuery;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/products")
@Tag(name = "Product")
public class ProductController {

    @Autowired
    private ProductFacade productFacade;
    
    @Autowired
    private AuthenticationContext authentication;

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
    public void delete(@PathVariable long id) {
        productFacade.delete(authentication.getUserId(), id);
    }

    @GetMapping("{slugOrId}")
    public ProductDTO findBySlug(@PathVariable String slugOrId, Authentication authentication) {
        if (slugOrId.matches("[0-9]+") && authentication != null && authentication.isAuthenticated()) {
            return productFacade.findById(Long.parseLong(slugOrId));
        }
        return productFacade.findBySlug(slugOrId);
    }

    @GetMapping("{id:\\d+}/related")
    public List<ProductDTO> getRelatedProducts(@PathVariable long id) {
        return productFacade.getRelatedProducts(id);
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
                .hidden(false)
                .disabled(false)
                .brands(brands)
                .page(page)
                .build();

        return productFacade.findAll(query);
    }
}
