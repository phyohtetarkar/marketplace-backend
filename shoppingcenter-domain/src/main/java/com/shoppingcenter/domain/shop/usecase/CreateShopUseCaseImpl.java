package com.shoppingcenter.domain.shop.usecase;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.Utils;
import com.shoppingcenter.domain.common.AuthenticationContext;
import com.shoppingcenter.domain.common.HTMLStringSanitizer;
import com.shoppingcenter.domain.shop.Shop;
import com.shoppingcenter.domain.shop.ShopContact;
import com.shoppingcenter.domain.shop.ShopMember;
import com.shoppingcenter.domain.shop.ShopMember.Role;
import com.shoppingcenter.domain.shop.dao.ShopDao;

import lombok.Setter;

@Setter
public class CreateShopUseCaseImpl implements CreateShopUseCase {

    private ShopDao shopDao;

    private HTMLStringSanitizer htmlStringSanitizer;

    private SaveShopContactUseCase saveShopContactUseCase;

    private CreateShopMemberUseCase createShopMemberUseCase;

    private UploadShopLogoUseCase uploadShopLogoUseCase;

    private UploadShopCoverUseCase uploadShopCoverUseCase;

    private AuthenticationContext authenticationContext;

    @Override
    public void apply(Shop shop) {
        if (!Utils.hasText(shop.getName())) {
            throw new ApplicationException("Required shop name");
        }

        if (!Utils.hasText(shop.getSlug())) {
            throw new ApplicationException("Required shop slug");
        }

        if (Utils.hasText(shop.getAbout())) {
            var about = shop.getAbout();
            shop.setAbout(htmlStringSanitizer.sanitize(about));
        }

        String userId = authenticationContext.getUserId();

        long shopId = shopDao.create(shop);

        if (Utils.hasText(shop.getAddress())) {
            ShopContact contact = new ShopContact();
            contact.setAddress(shop.getAddress());
            contact.setShopId(shopId);
            saveShopContactUseCase.apply(contact);
        }

        ShopMember member = new ShopMember();
        member.setRole(Role.OWNER);
        member.setShopId(shopId);
        member.setUserId(userId);

        createShopMemberUseCase.apply(member);

        if (shop.getLogoImage() != null && shop.getLogoImage().getSize() > 0) {
            uploadShopLogoUseCase.apply(shopId, shop.getLogoImage());
        }

        if (shop.getCoverImage() != null && shop.getCoverImage().getSize() > 0) {
            uploadShopCoverUseCase.apply(shopId, shop.getCoverImage());
        }

    }

}
