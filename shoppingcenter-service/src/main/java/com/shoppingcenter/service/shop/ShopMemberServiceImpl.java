package com.shoppingcenter.service.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoppingcenter.data.shop.ShopMemberRepo;
import com.shoppingcenter.service.ApplicationException;
import com.shoppingcenter.service.ErrorCodes;

@Service
public class ShopMemberServiceImpl implements ShopMemberService {

    @Autowired
    private ShopMemberRepo shopMemberRepo;

    @Override
    public void validateMember(String shopSlug, String userId) {
        if (!shopMemberRepo.existsByShop_SlugAndUser_Id(shopSlug, userId)) {
            throw new ApplicationException(ErrorCodes.FORBIDDEN, "Permission denied");
        }
    }

}
