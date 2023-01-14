package com.shoppingcenter.core.shop;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.shoppingcenter.core.ApplicationException;
import com.shoppingcenter.core.ErrorCodes;
import com.shoppingcenter.core.UploadFile;
import com.shoppingcenter.core.shop.model.Shop;
import com.shoppingcenter.core.shop.model.ShopContact;
import com.shoppingcenter.core.shop.model.ShopGeneral;
import com.shoppingcenter.data.shop.ShopContactEntity;
import com.shoppingcenter.data.shop.ShopContactRepo;
import com.shoppingcenter.data.shop.ShopEntity;
import com.shoppingcenter.data.shop.ShopEntity.Status;
import com.shoppingcenter.data.shop.ShopMemberEntity;
import com.shoppingcenter.data.shop.ShopMemberEntity.Role;
import com.shoppingcenter.data.shop.ShopMemberRepo;
import com.shoppingcenter.data.shop.ShopRepo;

@Service
@Transactional
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopRepo shopRepo;

    @Autowired
    private ShopContactRepo shopContactRepo;

    @Autowired
    private ShopMemberRepo shopMemberRepo;

    @Override
    public void create(Shop shop) {
        try {
            ShopEntity entity = new ShopEntity();
            entity.setName(shop.getName());
            entity.setSlug(shop.getSlug());
            entity.setHeadline(shop.getHeadline());
            entity.setAbout(shop.getAbout());

            ShopEntity result = shopRepo.save(entity);

            if (StringUtils.hasText(shop.getAddress())) {
                ShopContactEntity contact = new ShopContactEntity();
                contact.setAddress(shop.getAddress());
                contact.setShop(result);
                shopContactRepo.save(contact);
            }

            ShopMemberEntity memberEntity = new ShopMemberEntity();
            memberEntity.getId().setUserId(result.getCreatedBy());
            memberEntity.getId().setShopId(result.getId());
            memberEntity.setRole(Role.OWNER);

            shopMemberRepo.save(memberEntity);

            // TODO: upload logo

            // TODO: upload cover
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateGeneralInfo(ShopGeneral general) {
        // TODO: check privilege

        ShopEntity entity = shopRepo.findById(general.getShopId())
                .orElseThrow(() -> new ApplicationException(ErrorCodes.NOT_FOUND));
        entity.setName(general.getName());
        entity.setSlug(general.getSlug());
        entity.setSlug(general.getSlug());
        entity.setHeadline(general.getHeadline());
        entity.setAbout(general.getAbout());

        shopRepo.save(entity);
    }

    @Override
    public void updateContact(ShopContact contact) {
        // TODO: check privilege

        if (!shopRepo.existsById(contact.getShopId())) {
            throw new ApplicationException(ErrorCodes.INVALID_ARGUMENT);
        }
        ShopContactEntity entity = shopContactRepo.findByShop_Id(contact.getShopId()).orElseGet(ShopContactEntity::new);
        entity.setShop(shopRepo.getReferenceById(contact.getShopId()));
        entity.setPhones(contact.getPhones().stream().collect(Collectors.joining(",")));
        entity.setAddress(contact.getAddress());
        entity.setLatitude(contact.getLatitude());
        entity.setLongitude(contact.getLongitude());

        shopContactRepo.save(entity);
    }

    @Override
    public void uploadLogo(long shopId, UploadFile file) {
        // TODO Auto-generated method stub
    }

    @Override
    public void uploadCover(long shopId, UploadFile file) {
        // TODO Auto-generated method stub

    }

    @Override
    public void updateStatus(long shopId, Status status) {
        if (status == null) {
            throw new ApplicationException(ErrorCodes.INVALID_ARGUMENT);
        }

        if (!shopRepo.existsById(shopId)) {
            throw new ApplicationException(ErrorCodes.INVALID_ARGUMENT);
        }

        // TODO: check privilege

        ShopEntity entity = shopRepo.getReferenceById(shopId);
        entity.setStatus(status);
    }

    @Override
    public void delete(long id) {
        // TODO: check privilege

        // TODO: delete members

        // TODO: delete reviews

        // TODO: delete products

        // TODO: delete discounts

        // TODO: delete contact

        // TODO: delete shop

        // TODO: delete cover & logo from storage
    }

}
