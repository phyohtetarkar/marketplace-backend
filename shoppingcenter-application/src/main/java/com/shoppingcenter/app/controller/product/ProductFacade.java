package com.shoppingcenter.app.controller.product;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.shoppingcenter.app.annotation.Facade;
import com.shoppingcenter.app.controller.PageDataDTO;
import com.shoppingcenter.app.controller.product.dto.ProductDTO;
import com.shoppingcenter.app.controller.product.dto.ProductEditDTO;
import com.shoppingcenter.domain.product.ProductEditInput;
import com.shoppingcenter.domain.product.ProductQuery;
import com.shoppingcenter.domain.product.usecase.DeleteProductUseCase;
import com.shoppingcenter.domain.product.usecase.GetAllProductUseCase;
import com.shoppingcenter.domain.product.usecase.GetProductBrandsByCategoryUseCase;
import com.shoppingcenter.domain.product.usecase.GetProductByIdUseCase;
import com.shoppingcenter.domain.product.usecase.GetProductBySlugUseCase;
import com.shoppingcenter.domain.product.usecase.GetProductHintsUseCase;
import com.shoppingcenter.domain.product.usecase.GetRelatedProductsUseCase;
import com.shoppingcenter.domain.product.usecase.SaveProductUseCase;

@Facade
public class ProductFacade {

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
    private ModelMapper modelMapper;

    @Transactional
    public void save(ProductEditDTO product) {
        saveProductUseCase.apply(modelMapper.map(product, ProductEditInput.class));
    }

    @Transactional
    public void delete(long id) {
        deleteProductUseCase.apply(id);
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
        return getProductHintsUseCase.apply(q);
    }

    public List<String> getProductBrandsByCategory(int categoryId) {
        return getProductBrandsByCategoryUseCase.apply(categoryId);
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
