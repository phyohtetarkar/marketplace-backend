package com.shoppingcenter.service.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.shoppingcenter.data.shop.ShopMemberRepo;

@Service
public class ShopMemberServiceImpl implements ShopMemberService {

    @Autowired
    private ShopMemberRepo shopMemberRepo;

    @Override
    public void validateMember(long shopId, String userId) {
        if (!StringUtils.hasText(userId) || !shopMemberRepo.existsByShop_IdAndUser_Id(shopId, userId)) {
            throw new AccessDeniedException("Permission denied");
        }
    }

}
