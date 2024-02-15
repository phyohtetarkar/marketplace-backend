package com.marketplace.api.admin.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.marketplace.api.PageDataDTO;
import com.marketplace.api.vendor.product.ProductDTO;
import com.marketplace.domain.product.Product;
import com.marketplace.domain.product.ProductQuery;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/admin/products")
@Tag(name = "Admin")
public class ProductController {

    @Autowired
    private ProductControllerFacade productFacade;
    
    @PreAuthorize("hasPermission('PRODUCT', 'WRITE')")
    @PutMapping("{id:\\d+}/make-featured")
    public void makeFetured(@PathVariable long id) {
    	productFacade.updateFeatured(id, true);
    }
    
    @PreAuthorize("hasPermission('PRODUCT', 'WRITE')")
    @PutMapping("{id:\\d+}/remove-featured")
    public void removeFetured(@PathVariable long id) {
    	productFacade.updateFeatured(id, false);
    }
    
    @PreAuthorize("hasPermission('PRODUCT', 'READ') or hasPermission('PRODUCT', 'WRITE')")
    @GetMapping("{id:\\d+}")
    public ProductDTO findById(@PathVariable long id) {
        return productFacade.findById(id);
    }

    @PreAuthorize("hasPermission('PRODUCT', 'READ') or hasPermission('PRODUCT', 'WRITE')")
    @GetMapping
    public PageDataDTO<ProductDTO> findAll(
            @RequestParam(required = false) String q,
            @RequestParam(required = false, name = "brand") String[] brands,
            @RequestParam(required = false, name = "category-id") Integer categoryId,
            @RequestParam(required = false, name = "shop-id") Long shopId,
            @RequestParam(required = false) Boolean featured,
            @RequestParam(required = false) Boolean discount,
            @RequestParam(required = false) Integer page) {
    	
        ProductQuery query = ProductQuery.builder()
                .q(q)
                .categoryId(categoryId)
                .shopId(shopId)
                .featured(featured)
                .discount(discount)
                .brands(brands)
                .status(Product.Status.PUBLISHED)
                .page(page)
                .build();

        return productFacade.findAll(query);
    }

}
