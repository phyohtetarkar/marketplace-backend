package com.shoppingcenter.domain.shoppingcart;

import java.util.List;

public interface CartItemDao {

    void save(CartItem item);

    void updateQuantity(long itemId, int quantity);

    void delete(long itemId);

    void deleteByUser(String userId);

    void deleteAll(List<Long> ids);

    void deleteByProduct(long productId);

    boolean existsByIdAndUser(long itemId, String userId);

    boolean existsByUserAndProductAndVariant(String userId, long productId, Long variantId);

    long countByUser(String userId);

    List<CartItem> findByUser(String userId);

}
