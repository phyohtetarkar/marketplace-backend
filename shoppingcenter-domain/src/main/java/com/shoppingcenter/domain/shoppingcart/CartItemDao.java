package com.shoppingcenter.domain.shoppingcart;

import java.util.List;

public interface CartItemDao {

    void create(AddToCartInput data);

    void updateQuantity(long id, int quantity);

    void delete(long id);

    void deleteByUser(long userId);

    void deleteAll(List<Long> items);

    void deleteByProduct(long productId);

    boolean existsById(long id);

    boolean exists(long userId, long productId, Long variantId);

    long countByUser(long userId);
    
    CartItem findById(long id);
    
    List<CartItem> find(List<Long> items);

    List<CartItem> findByUser(long userId);

}
