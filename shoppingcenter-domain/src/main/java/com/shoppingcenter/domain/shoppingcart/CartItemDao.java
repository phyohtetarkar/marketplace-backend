package com.shoppingcenter.domain.shoppingcart;

import java.util.List;

public interface CartItemDao {

    void save(CartItem item);

    void delete(CartItem item);

    void deleteByUser(long userId);

    void deleteAll(List<CartItem> items);

    void deleteByProduct(long productId);

    boolean exists(long userId, long productId, long variantId);

    long countByUser(long userId);

    List<CartItem> findByUser(long userId);

}
