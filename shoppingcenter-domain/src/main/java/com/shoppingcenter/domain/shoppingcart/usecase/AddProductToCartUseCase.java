package com.shoppingcenter.domain.shoppingcart.usecase;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.product.dao.ProductDao;
import com.shoppingcenter.domain.product.dao.ProductVariantDao;
import com.shoppingcenter.domain.shoppingcart.AddToCartInput;
import com.shoppingcenter.domain.shoppingcart.CartItemDao;

import lombok.Setter;

@Setter
public class AddProductToCartUseCase {

    private CartItemDao cartItemDao;

    private ProductDao productDao;

    private ProductVariantDao variantDao;
    
    public boolean apply(AddToCartInput data) {
    	var product = productDao.findById(data.getProductId());
        if (product == null) {
            throw new ApplicationException("Product not found");
        }
        
        var shop = product.getShop();
        
        if (product.isDisabled() || shop.isDisabled() || !shop.isActivated() || shop.isExpired()) {
        	throw new ApplicationException("Product not found");
        }
        
        if (product.getVariants() != null && product.getVariants().size() > 0 && data.getVariantId() == null) {
        	throw new ApplicationException("Variant not found");
        }

        if (data.getVariantId() != null && !variantDao.exists(data.getVariantId())) {
            throw new ApplicationException("Variant not found");
        }

        if (cartItemDao.exists(data.getUserId(), data.getProductId(), data.getVariantId())) {
            return false;
        }
        
        if (data.getQuantity() <= 0) {
        	throw new ApplicationException("Quantity must not less than 1");
        }

        cartItemDao.create(data);
        return true;
    }

}
