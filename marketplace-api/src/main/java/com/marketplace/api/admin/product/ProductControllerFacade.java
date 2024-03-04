package com.marketplace.api.admin.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketplace.api.PageDataDTO;
import com.marketplace.api.vendor.VendorDataMapper;
import com.marketplace.api.vendor.product.ProductDTO;
import com.marketplace.domain.product.ProductQuery;
import com.marketplace.domain.product.usecase.GetAllProductUseCase;
import com.marketplace.domain.product.usecase.GetProductByIdUseCase;
import com.marketplace.domain.product.usecase.UpdateProductFeaturedUseCase;

@Component
public class ProductControllerFacade {

	@Autowired
	private UpdateProductFeaturedUseCase updateProductFeaturedUseCase;
	
    @Autowired
    private GetProductByIdUseCase getProductByIdUseCase;
    
    @Autowired
    private GetAllProductUseCase getAllProductUseCase;
    
    @Autowired
    private VendorDataMapper mapper;
    
    public void updateFeatured(long productId, boolean featured) {
    	updateProductFeaturedUseCase.apply(productId, featured);
    }

    public ProductDTO findById(long id) {
        var source = getProductByIdUseCase.apply(id);
        return mapper.map(source);
    }
    
    public PageDataDTO<ProductDTO> findAll(ProductQuery query) {
        return mapper.mapProductPage(getAllProductUseCase.apply(query));
    }
}
