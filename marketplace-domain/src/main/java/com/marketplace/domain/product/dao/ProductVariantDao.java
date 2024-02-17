package com.marketplace.domain.product.dao;

import java.util.List;

import com.marketplace.domain.product.ProductCreateInput;
import com.marketplace.domain.product.ProductVariant;

public interface ProductVariantDao {

    ProductVariant save(long productId, ProductCreateInput.Variant values);
    
    void createProductAttributes(long productId, List<ProductCreateInput.Attribute> list);
    
    void createVariantAttributes(long productId, long variantId, List<ProductCreateInput.VariantAttribute> list);
    
    void deleteAll(List<Long> list);
    
    void updateDeleted(long variantId, boolean deleted);

    boolean existsById(long id);
    
    ProductVariant findById(long id);
    
}
