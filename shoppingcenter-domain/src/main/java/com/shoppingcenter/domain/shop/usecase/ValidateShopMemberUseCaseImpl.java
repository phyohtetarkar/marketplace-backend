package com.shoppingcenter.domain.shop.usecase;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.ErrorCodes;
import com.shoppingcenter.domain.Utils;
import com.shoppingcenter.domain.shop.dao.ShopMemberDao;

public class ValidateShopMemberUseCaseImpl implements ValidateShopMemberUseCase {

    private ShopMemberDao dao;

    public ValidateShopMemberUseCaseImpl(ShopMemberDao dao) {
        this.dao = dao;
    }

    @Override
    public void apply(long shopId, String userId) {
        if (!Utils.hasText(userId) || !dao.existsByShopAndUser(shopId, userId)) {
            throw new ApplicationException(ErrorCodes.FORBIDDEN, "Permission denied");
        }
    }

}
