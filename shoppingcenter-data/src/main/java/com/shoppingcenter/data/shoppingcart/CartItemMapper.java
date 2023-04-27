package com.shoppingcenter.data.shoppingcart;

import com.shoppingcenter.data.product.ProductMapper;
import com.shoppingcenter.data.user.UserMapper;
import com.shoppingcenter.domain.shoppingcart.CartItem;

public class CartItemMapper {

	public static CartItem toDomain(CartItemEntity entity) {
		var item = new CartItem();
        item.setId(entity.getId());
        item.setQuantity(entity.getQuantity());
        item.setProduct(ProductMapper.toDomainCompat(entity.getProduct()));
        item.setUser(UserMapper.toDomain(entity.getUser()));
        if (entity.getVariant() != null) {
            item.setVariant(ProductMapper.toVariant(entity.getVariant()));
        }
        return item;
	}
	
}
