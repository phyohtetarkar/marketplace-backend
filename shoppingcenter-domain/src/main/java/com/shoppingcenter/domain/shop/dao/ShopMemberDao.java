package com.shoppingcenter.domain.shop.dao;

import com.shoppingcenter.domain.shop.ShopMember;

public interface ShopMemberDao {

    void save(ShopMember member);

    void delete(long shopId, long userId);

    boolean existsByShopAndUser(long shopId, long userId);
}
