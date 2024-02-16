package com.marketplace.api.consumer.product;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.marketplace.api.AuthenticationUtil;
import com.marketplace.api.PageDataDTO;
import com.marketplace.domain.product.Product;
import com.marketplace.domain.product.ProductQuery;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/content/products")
@Tag(name = "Consumer")
public class ProductController {

	@Autowired
	private ProductControllerFacade productFacade;
	
	@PostMapping("{id:\\d+}/favorite")
    public void addToFavorite(@PathVariable long id) {
		productFacade.addToFavorite(AuthenticationUtil.getAuthenticatedUserId(), id);
    }
	
	@DeleteMapping("{id:\\d+}/favorite")
    public void removeFromFavorite(@PathVariable long id) {
		productFacade.removeFromFavorite(AuthenticationUtil.getAuthenticatedUserId(), id);
    }

	@GetMapping("{slug}")
	public ProductDTO findBySlug(@PathVariable String slug) {
		return productFacade.findBySlug(slug);
	}
	
	@GetMapping("{id:\\d+}/related")
    public List<ProductDTO> getRelatedProducts(@PathVariable long id) {
        return productFacade.getRelatedProducts(id);
    }
	
	@GetMapping("{q}/product-filter")
	public ProductFilterDTO getProductFilter(@PathVariable String q) {
		return productFacade.getProductFilter(q);
	}
	
	@GetMapping
    public PageDataDTO<ProductDTO> findAll(
            @RequestParam(required = false) String q,
            @RequestParam(required = false, name = "brand") String[] brands,
            @RequestParam(required = false, name = "category-id") Integer categoryId,
            @RequestParam(required = false, name = "shop-id") Long shopId,
            @RequestParam(required = false, name = "max-price") BigDecimal maxPrice,
            @RequestParam(required = false) Boolean discount,
            @RequestParam(required = false) Boolean featured,
            @RequestParam(required = false) Integer page) {
        ProductQuery query = ProductQuery.builder()
                .q(q)
                .categoryId(categoryId)
                .shopId(shopId)
                .maxPrice(maxPrice)
                .status(Product.Status.PUBLISHED)
                .featured(featured)
                .discount(discount)
                .brands(brands)
                .page(page)
                .build();

        return productFacade.findAll(query);
    }
}
