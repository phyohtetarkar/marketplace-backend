package com.marketplace.api.vendor.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.marketplace.api.PageDataDTO;
import com.marketplace.domain.product.Product;
import com.marketplace.domain.product.ProductQuery;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/vendor/shops/{shopId:\\d+}/products")
@PreAuthorize("@authz.isShopMember(#shopId)")
@Tag(name = "Vendor")
public class ProductController {

	@Autowired
	private ProductControllerFacade productFacade;
	
	@ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void create(@PathVariable long shopId, @ModelAttribute ProductCreateDTO body) {
		body.setShopId(shopId);
		productFacade.create(body);
    }

    @PutMapping
    public void update(@PathVariable long shopId, @RequestBody ProductUpdateDTO body) {
    	body.setShopId(shopId);
    	productFacade.update(body);
    }
    
    @PutMapping("{productId:\\d+}/draft")
    public void draftProduct(@PathVariable long shopId, @PathVariable long productId) {
    	productFacade.updateStatus(shopId, productId, Product.Status.DRAFT);
    }
    
    @PutMapping("{productId:\\d+}/publish")
    public void publishProduct(@PathVariable long shopId, @PathVariable long productId) {
    	productFacade.updateStatus(shopId, productId, Product.Status.PUBLISHED);
    }
    
    @PutMapping("{productId:\\d+}/description")
    public void updateDescription(
    		@PathVariable long shopId, 
    		@PathVariable long productId, 
    		@RequestParam String value) {
    	productFacade.updateDescription(shopId, productId, value);
    }
    
    @PutMapping("{productId:\\d+}/variants")
    public void updateVariants(
    		@PathVariable long shopId, 
    		@PathVariable long productId, 
    		@RequestBody List<ProductCreateDTO.Variant> variants) {
    	productFacade.updateVariants(shopId, productId, variants);
    }
    
    @PutMapping("{productId:\\d+}/images")
    public void updateImages(
    		@PathVariable long shopId, 
    		@PathVariable long productId, 
    		@ModelAttribute ProductImagesUpdateDTO body) {
    	productFacade.updateImages(shopId, productId, body.getImages());
    }

    @DeleteMapping("{id:\\d+}")
    public void delete(@PathVariable long shopId, @PathVariable long id) {
    	productFacade.delete(id);
    }
    
    @PutMapping("{id:\\d+}/remove-discount")
    public void removeDiscount(@PathVariable long shopId, @PathVariable long id) {
    	productFacade.removeDiscountFromProduct(shopId, id);
    }
    
    @GetMapping("{id:\\d+}")
    public ProductDTO findById(@PathVariable long shopId, @PathVariable long id) {
        return productFacade.findById(id);
    }

    @GetMapping
    public PageDataDTO<ProductDTO> findAll(
    		@PathVariable long shopId,
            @RequestParam(required = false) String q,
            @RequestParam(required = false) Product.Status status,
            @RequestParam(required = false, name = "category-id") Integer categoryId,
            @RequestParam(required = false, name = "discount-id") Long discountId,
            @RequestParam(required = false) Integer page) {
    	
        ProductQuery query = ProductQuery.builder()
                .q(q)
                .status(status)
                .categoryId(categoryId)
                .shopId(shopId)
                .discountId(discountId)
                .page(page)
                .build();

        return productFacade.findAll(query);
    }
}
