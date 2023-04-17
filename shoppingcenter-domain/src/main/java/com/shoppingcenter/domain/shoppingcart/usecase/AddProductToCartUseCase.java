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
        if (!productDao.isAvailable(data.getProductId())) {
            throw new ApplicationException("Product not found");
        }

        if (data.getVariantId() > 0 && !variantDao.exists(data.getVariantId())) {
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
