package com.marketplace.domain.product.dao;

import java.util.List;

import com.marketplace.domain.product.ProductImage;
import com.marketplace.domain.product.ProductCreateInput;

public interface ProductImageDao {
	
	ProductImage create(long productId, ProductCreateInput.Image values);
	
	ProductImage update(ProductCreateInput.Image values);
	
	String getImageName(long id);

    void saveAll(long productId, List<ProductCreateInput.Image> list);
    
    void deleteAll(List<Long> list);

}
