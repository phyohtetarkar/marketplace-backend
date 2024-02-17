package com.marketplace.domain.shoppingcart.usecase;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.product.Product;
import com.marketplace.domain.shop.Shop.Status;
import com.marketplace.domain.shoppingcart.CartItem;
import com.marketplace.domain.shoppingcart.CartItemDao;

@Component
public class GetCartItemsByUserUseCase {

	@Autowired
    private CartItemDao dao;

	@Transactional(readOnly = true)
    public List<CartItem> apply(long userId) {
        var items = dao.findByUser(userId);

        return items.stream().filter(v -> {
        	var shop = v.getProduct().getShop();
            var available = !v.getProduct().isDeleted() && v.getProduct().getStatus() == Product.Status.PUBLISHED;
            var shopActive = shop.getStatus() == Status.APPROVED && shop.getExpiredAt() >= System.currentTimeMillis();

            return available && shopActive;
        }).toList();
    }

}
