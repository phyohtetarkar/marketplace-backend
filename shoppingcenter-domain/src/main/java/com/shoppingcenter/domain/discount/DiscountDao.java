package com.shoppingcenter.domain.discount;

import java.util.List;

import com.shoppingcenter.domain.PageData;

public interface DiscountDao {

    long save(Discount discount);

    void delete(long id);

    void updateProductCount(long discountId, long count);

    void applyDiscounts(long discountId, List<Long> productIds);

    void removeDiscount(long discountId, Long productId);

    boolean existsById(long id);

    Discount findById(long id);

    PageData<Discount> findByShop(long shopId, int page);
}
