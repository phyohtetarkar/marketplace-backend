package com.marketplace.domain.product.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.product.dao.ProductDao;
import com.marketplace.domain.shoppingcart.CartItemDao;

@Component
public class DeleteProductUseCase {

	@Autowired
    private ProductDao productDao;
	
	@Autowired
	private CartItemDao cartItemDao;
    
	@Transactional
    public void apply(long productId) {

//        var product = productDao.findById(productId);

        if (!productDao.existsById(productId)) {
            throw new ApplicationException("Product not found");
        }
        
        productDao.updateDeleted(productId, true);
//
//        favoriteProductDao.deleteByProduct(productId);
//
        cartItemDao.deleteByProduct(productId);
//
//        var images = product.getImages().stream().map(ProductImage::getName).collect(Collectors.toList());
//
//        productDao.delete(productId);
//
//        var dir = Constants.IMG_PRODUCT_ROOT;
//
//        fileStorageAdapter.delete(dir, images);
    }

}
