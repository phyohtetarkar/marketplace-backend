package com.marketplace.domain.shoppingcart.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.product.Product;
import com.marketplace.domain.product.dao.ProductDao;
import com.marketplace.domain.product.dao.ProductVariantDao;
import com.marketplace.domain.shop.Shop;
import com.marketplace.domain.shoppingcart.CartItem;
import com.marketplace.domain.shoppingcart.CartItemDao;
import com.marketplace.domain.shoppingcart.CartItemInput;

@Component
public class AddProductToCartUseCase {

	@Autowired
    private CartItemDao cartItemDao;

	@Autowired
    private ProductDao productDao;

	@Autowired
    private ProductVariantDao variantDao;
    
	@Transactional
    public boolean apply(CartItemInput values) {
    	var product = productDao.findById(values.getProductId());
        if (product == null || product.getStatus() != Product.Status.PUBLISHED || product.isDeleted()) {
            throw new ApplicationException("Product not found");
        }
        
        var shop = product.getShop();
        
        if (shop.isDeleted() || shop.getStatus() != Shop.Status.APPROVED || shop.getExpiredAt() < System.currentTimeMillis()) {
        	throw new ApplicationException("Product not found");
        }
        
//        if (product.getVariants() != null && product.getVariants().size() > 0 && values.getVariantId() == null) {
//        	throw new ApplicationException("Variant not found");
//        }

        if (values.getVariantId() > 0) {
        	var variant = variantDao.findById(values.getVariantId());
        	if (variant == null) {
        		throw new ApplicationException("Variant not found");
        	}
            
        	if (!variant.isAvailable()) {
        		throw new ApplicationException("Product variant not available");
        	}
        } else if (!product.isAvailable()) {
        	throw new ApplicationException("Product not available");
        }

        var id = new CartItem.ID(values.getUserId(), values.getProductId(), values.getVariantId()); 
        if (cartItemDao.existsById(id)) {
            return false;
        }
        
        if (values.getQuantity() <= 0) {
        	throw new ApplicationException("Quantity must not less than 1");
        }

        cartItemDao.create(values);
        return true;
    }

}
