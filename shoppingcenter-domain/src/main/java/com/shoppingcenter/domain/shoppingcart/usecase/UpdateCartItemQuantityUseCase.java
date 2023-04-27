package com.shoppingcenter.domain.shoppingcart.usecase;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.shoppingcart.CartItemDao;

public class UpdateCartItemQuantityUseCase {

    private CartItemDao dao;

    public UpdateCartItemQuantityUseCase(CartItemDao dao) {
        this.dao = dao;
    }

    public void apply(long id, int quantity) {
    	
    	var item = dao.findById(id);
        if (item == null) {
            throw new ApplicationException("Item not found");
        }
        
        if (quantity <= 0) {
            throw new ApplicationException("Quantity must not less than 1");
        }
        
        if (item.getVariant() != null && item.getVariant().getStockLeft() < quantity) {
        	throw new ApplicationException("Quantity limit exceeds");
        } else if (item.getProduct().getStockLeft() < quantity) {
        	throw new ApplicationException("Quantity limit exceeds");
        }
        
        
        dao.updateQuantity(id, quantity);
    }

}
