package com.shoppingcenter.app.controller.product;

import java.util.List;

import com.shoppingcenter.app.controller.product.dto.ProductDTO;
import com.shoppingcenter.app.controller.product.dto.ProductEditDTO;
import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.product.Product;
import com.shoppingcenter.domain.product.ProductQuery;

public interface ProductFacade {

    void save(ProductEditDTO product);

    void delete(long id);

    void updateStatus(long id, Product.Status status);

    ProductDTO findById(long id);

    ProductDTO findBySlug(String slug);

    List<ProductDTO> getHints(String q);

    List<String> getProductBrandsByCategory(String categorySlug);

    List<String> getProductBrandsByCategoryId(int categoryId);

    List<ProductDTO> getRelatedProducts(long productId);

    PageData<ProductDTO> findAll(ProductQuery query);

}
