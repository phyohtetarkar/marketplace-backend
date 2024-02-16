package com.marketplace.domain.shoppingcart;

import java.util.List;

public interface CartItemDao {

    void create(CartItemInput values);

    void update(CartItemInput values);

    void deleteByUser(long userId);

    void deleteAll(List<CartItem.ID> items);

    void deleteByProduct(long productId);

    boolean existsById(CartItem.ID id);

    long countByUser(long userId);
    
    CartItem findById(CartItem.ID id);
    
    List<CartItem> findByUser(long userId);

}
