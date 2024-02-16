package com.marketplace.domain.discount;

import java.util.List;

import com.marketplace.domain.PageData;
import com.marketplace.domain.common.PageQuery;

public interface DiscountDao {

    Discount save(DiscountInput values);

    void delete(long id);

    void applyDiscounts(long discountId, List<Long> productIds);

    void removeDiscountFromProduct(long productId);

    boolean existsById(long id);

    Discount findById(long id);
    
    List<Discount> findByShop(long shopId);

    PageData<Discount> findByShop(long shopId, PageQuery pageQuery);
}
