package com.marketplace.data.shoppingcart;

import com.marketplace.data.product.ProductMapper;
import com.marketplace.domain.shoppingcart.CartItem;

public interface CartItemMapper {

	public static CartItem toDomain(CartItemEntity entity) {
		var item = new CartItem();
		item.setUserId(entity.getId().getUserId());
        item.setQuantity(entity.getQuantity());
        if (entity.getProduct() != null) {
        	item.setProduct(ProductMapper.toDomainCompat(entity.getProduct()));	
        }
        if (entity.getVariant() != null) {
            item.setVariant(ProductMapper.toVariant(entity.getVariant()));
        }
        return item;
	}
	
}
