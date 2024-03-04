package com.marketplace.api.consumer.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketplace.api.PageDataDTO;
import com.marketplace.api.consumer.ConsumerDataMapper;
import com.marketplace.domain.product.ProductQuery;
import com.marketplace.domain.product.usecase.AddProductToFavoriteUseCase;
import com.marketplace.domain.product.usecase.CheckFavoriteProductUseCase;
import com.marketplace.domain.product.usecase.GetAllProductUseCase;
import com.marketplace.domain.product.usecase.GetProductBySlugUseCase;
import com.marketplace.domain.product.usecase.GetProductFilterByQueryUseCase;
import com.marketplace.domain.product.usecase.GetRelatedProductsUseCase;
import com.marketplace.domain.product.usecase.RemoveProductFromFavoriteUseCase;

@Component
public class ProductControllerFacade {

	@Autowired
	private GetProductBySlugUseCase getProductBySlugUseCase;
	
	@Autowired
	private GetRelatedProductsUseCase getRelatedProductsUseCase;
	
	@Autowired
	private GetAllProductUseCase getAllProductUseCase;
	
	@Autowired
    private AddProductToFavoriteUseCase addProductToFavoriteUseCase;

    @Autowired
    private RemoveProductFromFavoriteUseCase removeProductFromFavoriteUseCase;

    @Autowired
    private CheckFavoriteProductUseCase checkFavoriteProductUseCase;
    
    @Autowired
    private GetProductFilterByQueryUseCase getProductFilterByQueryUseCase;
    
    @Autowired
	private ConsumerDataMapper mapper;
	
	public ProductDTO findBySlug(String slug) {
        var source = getProductBySlugUseCase.apply(slug);
        return mapper.map(source);
    }
	
	public List<ProductDTO> getRelatedProducts(long productId) {
        return mapper.mapProductList(getRelatedProductsUseCase.apply(productId, 8));
    }

    public PageDataDTO<ProductDTO> findAll(ProductQuery query) {
        return mapper.mapProductPage(getAllProductUseCase.apply(query));
    }
    
    public void addToFavorite(long userId, long productId) {
        addProductToFavoriteUseCase.apply(userId, productId);
    }

    public void removeFromFavorite(long userId, long productId) {
        removeProductFromFavoriteUseCase.apply(userId, productId);
    }

    public boolean checkFavoriteByUser(long userId, long productId) {
        return checkFavoriteProductUseCase.apply(userId, productId);
    }
    
    public ProductFilterDTO getProductFilter(String q) {
    	var source = getProductFilterByQueryUseCase.apply(q);
    	return mapper.map(source);
    }
}
