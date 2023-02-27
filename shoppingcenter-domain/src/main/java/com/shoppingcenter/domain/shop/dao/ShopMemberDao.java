package com.shoppingcenter.domain.shop.dao;

import com.shoppingcenter.domain.shop.ShopMember;

public interface ShopMemberDao {

    long save(ShopMember member);

    void delete(long id);

    boolean existsByShopAndUser(long shopId, String userId);
}
