package com.shoppingcenter.domain.shop.usecase;

public interface CheckIsShopMemberUseCase {
    boolean apply(long shopId, String userId);
}