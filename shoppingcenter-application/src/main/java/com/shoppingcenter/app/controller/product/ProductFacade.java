package com.shoppingcenter.app.controller.product;

import java.util.List;

import com.shoppingcenter.app.controller.product.dto.ProductDTO;
import com.shoppingcenter.app.controller.product.dto.ProductEditDTO;
import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.product.ProductQuery;

public interface ProductFacade {

    void save(ProductEditDTO product);

    void delete(long id);

    ProductDTO findById(long id);

    ProductDTO findBySlug(String slug);

    List<ProductDTO> getHints(String q);

    List<String> findProductBrandsByCategory(String categorySlug);

    List<ProductDTO> getRelatedProducts(long productId);

    PageData<ProductDTO> findAll(ProductQuery query);

}
