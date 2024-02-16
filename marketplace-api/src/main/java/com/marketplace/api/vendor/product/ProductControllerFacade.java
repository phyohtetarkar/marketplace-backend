package com.marketplace.api.vendor.product;

import java.util.List;

import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketplace.api.AbstractControllerFacade;
import com.marketplace.api.PageDataDTO;
import com.marketplace.domain.discount.usecase.RemoveFromProductDiscountUseCase;
import com.marketplace.domain.product.Product;
import com.marketplace.domain.product.ProductCreateInput;
import com.marketplace.domain.product.ProductQuery;
import com.marketplace.domain.product.ProductUpdateInput;
import com.marketplace.domain.product.usecase.CreateProductUseCase;
import com.marketplace.domain.product.usecase.DeleteProductUseCase;
import com.marketplace.domain.product.usecase.GetAllProductUseCase;
import com.marketplace.domain.product.usecase.GetProductByIdUseCase;
import com.marketplace.domain.product.usecase.UpdateProductDescriptionUseCase;
import com.marketplace.domain.product.usecase.UpdateProductImagesUseCase;
import com.marketplace.domain.product.usecase.UpdateProductStatusUseCase;
import com.marketplace.domain.product.usecase.UpdateProductUseCase;
import com.marketplace.domain.product.usecase.UpdateProductVariantsUseCase;

@Component
public class ProductControllerFacade extends AbstractControllerFacade {
	
	@Autowired
    private CreateProductUseCase createProductUseCase;
	
	@Autowired
	private UpdateProductUseCase updateProductUseCase;
	
	@Autowired
	private UpdateProductVariantsUseCase updateProductVariantsUseCase;
	
	@Autowired
	private UpdateProductImagesUseCase updateProductImagesUseCase;
	
	@Autowired
	private UpdateProductDescriptionUseCase updateProductDescriptionUseCase;
	
	@Autowired
	private UpdateProductStatusUseCase updateProductStatusUseCase;

    @Autowired
    private DeleteProductUseCase deleteProductUseCase;

    @Autowired
    private GetProductByIdUseCase getProductByIdUseCase;
    
    @Autowired
    private GetAllProductUseCase getAllProductUseCase;
    
    @Autowired
    private RemoveFromProductDiscountUseCase removeFromProductDiscountUseCase;

    public void create(ProductCreateDTO values) {
        createProductUseCase.apply(map(values, ProductCreateInput.class));
    }
    
    public void update(ProductUpdateDTO values) {
        updateProductUseCase.apply(map(values, ProductUpdateInput.class));
    }
    
    public void updateDescription(long shopId, long productId, String value) {
    	updateProductDescriptionUseCase.apply(shopId, productId, value);
    }
    
    public void updateVariants(long shopId, long productId, List<ProductCreateDTO.Variant> values) {
    	var type = new TypeToken<List<ProductCreateInput.Variant>>() {}.getType();
    	updateProductVariantsUseCase.apply(shopId, productId, map(values, type));
    }
    
    public void updateImages(long shopId, long productId, List<ProductCreateDTO.Image> values) {
    	var type = new TypeToken<List<ProductCreateInput.Image>>() {}.getType();
    	updateProductImagesUseCase.apply(shopId, productId, map(values, type));
    }
    
    public void updateStatus(long shopId, long productId, Product.Status status) {
    	updateProductStatusUseCase.apply(shopId, productId, status);
    }

    public void delete(long productId) {
        deleteProductUseCase.apply(productId);
    }
    
    public void removeDiscountFromProduct(long shopId, long productId) {
    	removeFromProductDiscountUseCase.apply(shopId, productId);
    }

    public ProductDTO findById(long id) {
        var source = getProductByIdUseCase.apply(id);
        return map(source, ProductDTO.class);
    }
    
    public PageDataDTO<ProductDTO> findAll(ProductQuery query) {
        return map(getAllProductUseCase.apply(query), ProductDTO.pageType());
    }
}
