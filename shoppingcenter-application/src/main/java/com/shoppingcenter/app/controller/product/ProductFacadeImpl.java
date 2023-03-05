package com.shoppingcenter.app.controller.product;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.shoppingcenter.app.annotation.Facade;
import com.shoppingcenter.app.controller.product.dto.ProductDTO;
import com.shoppingcenter.app.controller.product.dto.ProductEditDTO;
import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.product.Product;
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
public class ProductFacadeImpl implements ProductFacade {

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
    @Override
    public void save(ProductEditDTO product) {
        saveProductUseCase.apply(modelMapper.map(product, Product.class));
    }

    @Transactional
    @Override
    public void delete(long id) {
        deleteProductUseCase.apply(id);
    }

    @Transactional(readOnly = true)
    @Override
    public ProductDTO findById(long id) {
        return modelMapper.map(getProductByIdUseCase.apply(id), ProductDTO.class);
    }

    @Transactional(readOnly = true)
    @Override
    public ProductDTO findBySlug(String slug) {
        return modelMapper.map(getProductBySlugUseCase.apply(slug), ProductDTO.class);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductDTO> getHints(String q) {
        return modelMapper.map(getProductHintsUseCase.apply(q), ProductDTO.listType());
    }

    @Override
    public List<String> findProductBrandsByCategory(String categorySlug) {
        return getProductBrandsByCategoryUseCase.apply(categorySlug);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductDTO> getRelatedProducts(long productId) {
        return modelMapper.map(getRelatedProductsUseCase.apply(productId, 8), ProductDTO.listType());
    }

    @Transactional(readOnly = true)
    @Override
    public PageData<ProductDTO> findAll(ProductQuery query) {
        return modelMapper.map(getAllProductUseCase.apply(query), ProductDTO.pageType());
    }

}
