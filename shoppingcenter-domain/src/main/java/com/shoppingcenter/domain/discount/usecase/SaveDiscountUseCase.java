package com.shoppingcenter.domain.discount.usecase;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.Utils;
import com.shoppingcenter.domain.discount.Discount;
import com.shoppingcenter.domain.discount.DiscountDao;
import com.shoppingcenter.domain.shop.dao.ShopDao;

public class SaveDiscountUseCase {

    private DiscountDao discountDao;

    private ShopDao shopDao;

    public SaveDiscountUseCase(DiscountDao discountDao, ShopDao shopDao) {
        this.discountDao = discountDao;
        this.shopDao = shopDao;
    }

    public void apply(Discount discount) {
        if (!Utils.hasText(discount.getTitle())) {
            throw new ApplicationException("Required discount title");
        }

        if (!shopDao.existsById(discount.getShopId())) {
            throw new ApplicationException("Shop not found");
        }

        discountDao.save(discount);
    }

}
