package com.shoppingcenter.domain.shop.usecase;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.shop.ShopContact;
import com.shoppingcenter.domain.shop.dao.ShopDao;

public class SaveShopContactUseCaseImpl implements SaveShopContactUseCase {

    private ShopDao dao;

    private ValidateShopActiveUseCase validateShopActiveUseCase;

    public SaveShopContactUseCaseImpl(ShopDao dao, ValidateShopActiveUseCase validateShopActiveUseCase) {
        this.dao = dao;
        this.validateShopActiveUseCase = validateShopActiveUseCase;
    }

    @Override
    public void apply(ShopContact contact) {
        if (!dao.existsById(contact.getShopId())) {
            throw new ApplicationException("Shop not found");
        }

        validateShopActiveUseCase.apply(contact.getShopId());

        dao.saveContact(contact);
    }

}
