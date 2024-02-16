package com.marketplace.domain.shoppingcart.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.shoppingcart.CartItem;
import com.marketplace.domain.shoppingcart.CartItemDao;
import com.marketplace.domain.shoppingcart.CartItemInput;

@Component
public class UpdateCartItemUseCase {

	@Autowired
    private CartItemDao cartItemDao;

	@Transactional
    public void apply(CartItemInput values) {
    	
    	var id = new CartItem.ID(values.getUserId(), values.getProductId(), values.getVariantId());
        if (!cartItemDao.existsById(id)) {
            throw new ApplicationException("Item not found");
        }
        
//        if (quantity <= 0) {
//            throw new ApplicationException("Quantity must not less than 1");
//        }
        
//        var variantId = Optional.of(item.getVariant()).map(ProductVariant::getId).orElse(0L);
//        
//        var stock = productStockDao.getProductStock(item.getProduct().getId(), variantId);
        		
        if (values.getQuantity() > 0) {
        	cartItemDao.update(values);
//        	throw new ApplicationException("Quantity limit exceeds");
        } 
    }

}
