package com.marketplace.domain.shoppingcart.usecase;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketplace.domain.shoppingcart.CartItem;
import com.marketplace.domain.shoppingcart.CartItemDao;

@Component
public class RemoveProductFromCartUseCase {

	@Autowired
    private CartItemDao dao;

    public void apply(List<CartItem.ID> items) {
        dao.deleteAll(items);
    }

}
