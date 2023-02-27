package com.shoppingcenter.domain.shop.usecase;

public interface ValidateShopMemberUseCase {

    void apply(long shopId, String userId);

}
