package com.shoppingcenter.domain.shop.usecase;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.common.HTMLStringSanitizer;
import com.shoppingcenter.domain.shop.ShopGeneral;
import com.shoppingcenter.domain.shop.dao.ShopDao;

import lombok.Setter;

@Setter
public class UpdateShopBasicInfoUseCaseImpl implements UpdateShopBasicInfoUseCase {

    private ShopDao dao;

    private HTMLStringSanitizer htmlStringSanitizer;

    private ValidateShopActiveUseCase validateShopActiveUseCase;

    @Override
    public void apply(ShopGeneral general) {
        if (!dao.existsById(general.getShopId())) {
            throw new ApplicationException("Shop not found");
        }

        validateShopActiveUseCase.apply(general.getShopId());

        general.setAbout(htmlStringSanitizer.sanitize(general.getAbout()));

        dao.updateGeneralInfo(general);
    }

}
