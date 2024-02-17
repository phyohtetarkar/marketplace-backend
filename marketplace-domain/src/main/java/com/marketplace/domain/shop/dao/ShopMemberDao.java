package com.marketplace.domain.shop.dao;

import java.util.List;

import com.marketplace.domain.shop.ShopMember;
import com.marketplace.domain.shop.ShopMemberInput;

public interface ShopMemberDao {

    ShopMember save(ShopMemberInput values);

    void delete(long shopId, long userId);

    boolean existsByShopAndUser(long shopId, long userId);
    
    long getCountByUser(long userId);
    
    ShopMember findByShopAndUser(long shopId, long userId);
    
    List<ShopMember> findByShop(long shopId);
}
