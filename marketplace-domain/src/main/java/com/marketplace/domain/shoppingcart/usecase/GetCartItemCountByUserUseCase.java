package com.marketplace.domain.shoppingcart.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketplace.domain.shoppingcart.CartItemDao;

@Component
public class GetCartItemCountByUserUseCase {

	@Autowired
    private CartItemDao dao;

    public long apply(long userId) {
        return dao.countByUser(userId);
    }

}
