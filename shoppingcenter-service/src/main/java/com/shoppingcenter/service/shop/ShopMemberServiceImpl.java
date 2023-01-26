package com.shoppingcenter.service.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoppingcenter.data.shop.ShopMemberRepo;

@Service
public class ShopMemberServiceImpl implements ShopMemberService {

    @Autowired
    private ShopMemberRepo shopMemberRepo;

    @Override
    public boolean isMember(long shopId, String userId) {
        return shopMemberRepo.existsByShop_IdAndUser_Id(shopId, userId);
    }

}
