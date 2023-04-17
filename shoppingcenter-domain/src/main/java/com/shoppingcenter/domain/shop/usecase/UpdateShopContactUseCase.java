package com.shoppingcenter.domain.shop.usecase;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.shop.ShopContact;
import com.shoppingcenter.domain.shop.dao.ShopDao;

public class UpdateShopContactUseCase {

    private ShopDao dao;

    public UpdateShopContactUseCase(ShopDao dao) {
        this.dao = dao;
    }

    public void apply(ShopContact contact) {
        if (!dao.existsById(contact.getShopId())) {
            throw new ApplicationException("Shop not found");
        }

        dao.saveContact(contact);
    }

}
