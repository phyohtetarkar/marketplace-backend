package com.shoppingcenter.domain.shop.usecase;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.shop.ShopContact;
import com.shoppingcenter.domain.shop.dao.ShopDao;

public class SaveShopContactUseCaseImpl implements SaveShopContactUseCase {

    private ShopDao dao;

    public SaveShopContactUseCaseImpl(ShopDao dao) {
        this.dao = dao;
    }

    @Override
    public void apply(ShopContact contact) {
        if (!dao.existsById(contact.getShopId())) {
            throw new ApplicationException("Shop not found");
        }

        dao.saveContact(contact);
    }

}
