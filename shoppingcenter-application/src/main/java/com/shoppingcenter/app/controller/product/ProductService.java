package com.shoppingcenter.app.controller.product;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shoppingcenter.app.controller.PageDataDTO;
import com.shoppingcenter.app.controller.product.dto.ProductDTO;
import com.shoppingcenter.app.controller.product.dto.ProductEditDTO;
import com.shoppingcenter.app.controller.product.dto.ProductFilterDTO;
import com.shoppingcenter.data.category.CategoryRepo;
import com.shoppingcenter.data.product.ProductRepo;
import com.shoppingcenter.data.product.view.ProductBrandView;
import com.shoppingcenter.domain.Utils;
import com.shoppingcenter.domain.common.AuthenticationContext;
import com.shoppingcenter.domain.product.ProductEditInput;
import com.shoppingcenter.domain.product.ProductQuery;
import com.shoppingcenter.domain.product.usecase.DeleteProductUseCase;
import com.shoppingcenter.domain.product.usecase.GetAllProductUseCase;
import com.shoppingcenter.domain.product.usecase.GetProductBrandByNameLikeUseCase;
import com.shoppingcenter.domain.product.usecase.GetProductBrandsByCategoryUseCase;
import com.shoppingcenter.domain.product.usecase.GetProductByIdUseCase;
import com.shoppingcenter.domain.product.usecase.GetProductBySlugUseCase;
import com.shoppingcenter.domain.product.usecase.GetProductHintsUseCase;
import com.shoppingcenter.domain.product.usecase.GetRelatedProductsUseCase;
import com.shoppingcenter.domain.product.usecase.SaveProductUseCase;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepo productRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;

    @Autowired
    private SaveProductUseCase saveProductUseCase;

    @Autowired
    private DeleteProductUseCase deleteProductUseCase;

    @Autowired
    private GetProductByIdUseCase getProductByIdUseCase;

    @Autowired
    private GetProductBySlugUseCase getProductBySlugUseCase;

    @Autowired
    private GetProductHintsUseCase getProductHintsUseCase;

    @Autowired
    private GetProductBrandsByCategoryUseCase getProductBrandsByCategoryUseCase;

    @Autowired
    private GetRelatedProductsUseCase getRelatedProductsUseCase;

    @Autowired
    private GetAllProductUseCase getAllProductUseCase;
    
    @Autowired
    private GetProductBrandByNameLikeUseCase getProductBrandByNameLikeUseCase;
    
    @Autowired
    private AuthenticationContext authentication;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public void save(ProductEditDTO product) {
        saveProductUseCase.apply(modelMapper.map(product, ProductEditInput.class));
    }

    @Transactional
    public void delete(long productId) {
        deleteProductUseCase.apply(authentication.getUserId(), productId);
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(long id) {
        var source = getProductByIdUseCase.apply(id);
        return source != null ? modelMapper.map(source, ProductDTO.class) : null;
    }

    @Transactional(readOnly = true)
    public ProductDTO findBySlug(String slug) {
        var source = getProductBySlugUseCase.apply(slug);
        return source != null ? modelMapper.map(source, ProductDTO.class) : null;
    }

    @Transactional(readOnly = true)
    public List<String> getHints(String q) {
        //return getProductHintsUseCase.apply(q);
        return new ArrayList<String>();
    }

    public ProductFilterDTO getProductFilterByCategory(int categoryId) {
    	var dto = new ProductFilterDTO();
    	var category = categoryRepo.findById(categoryId).orElse(null);
    	if (category == null) {
    		return dto;
    	}
    	var lft = category.getLft();        
    	var rgt = category.getRgt();
    	var brands = productRepo.findDistinctBrandsByCategory(lft, rgt).stream()
    			.map(ProductBrandView::getBrand)
    			.toList();
        var maxPrice = productRepo.getMaxPriceByCategory(lft, rgt);
        
        dto.setBrands(brands);     
        dto.setMaxPrice(maxPrice);
        return dto;
    }
    
    public ProductFilterDTO getProductFilterByNameLike(String q) {
    	var dto = new ProductFilterDTO();
    	if (Utils.hasText(q)) {
    		var query = "%" + q.trim() + "%";
    		dto.setBrands(productRepo.findDistinctBrandsByNameLike(query).stream()
    				.map(ProductBrandView::getBrand)
        			.toList());
    		
    		dto.setMaxPrice(productRepo.getMaxPriceByNameLike(query));
    	};
    	
    	return dto;
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> getRelatedProducts(long productId) {
        return modelMapper.map(getRelatedProductsUseCase.apply(productId, 8), ProductDTO.listType());
    }

    @Transactional(readOnly = true)
    public PageDataDTO<ProductDTO> findAll(ProductQuery query) {
        return modelMapper.map(getAllProductUseCase.apply(query), ProductDTO.pageType());
    }

}
