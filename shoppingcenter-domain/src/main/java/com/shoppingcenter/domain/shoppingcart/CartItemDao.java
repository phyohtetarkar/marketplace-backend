package com.shoppingcenter.domain.shoppingcart;

import java.util.List;

public interface CartItemDao {

    void create(AddToCartInput data);

    void update(long id, int quantity);

    void delete(long id);

    void deleteByUser(long userId);

    void deleteAll(List<Long> items);

    void deleteByProduct(long productId);

    boolean existsById(long id);

    boolean exists(long userId, long productId, Long variantId);

    long countByUser(long userId);

    List<CartItem> findByUser(long userId);

}
