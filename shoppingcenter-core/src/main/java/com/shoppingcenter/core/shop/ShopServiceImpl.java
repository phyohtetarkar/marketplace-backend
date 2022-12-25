package com.shoppingcenter.core.shop;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shoppingcenter.core.ApplicationException;
import com.shoppingcenter.core.ErrorCodes;
import com.shoppingcenter.core.UploadFile;
import com.shoppingcenter.core.shop.model.Shop;
import com.shoppingcenter.core.shop.model.ShopContact;
import com.shoppingcenter.core.shop.model.ShopGeneralInfo;
import com.shoppingcenter.data.shop.ShopEntity.Status;
import com.shoppingcenter.data.shop.ShopMemberEntity.Role;
import com.shoppingcenter.data.LocationData;
import com.shoppingcenter.data.shop.ShopEntity;
import com.shoppingcenter.data.shop.ShopMemberEntity;
import com.shoppingcenter.data.shop.ShopMemberRepo;
import com.shoppingcenter.data.shop.ShopRepo;

@Service
@Transactional
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopRepo shopRepo;

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
            entity.setAddress(shop.getAddress());

            ShopEntity result = shopRepo.save(entity);

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
    public void updateGeneralInfo(ShopGeneralInfo general) {
        ShopEntity entity = shopRepo.findById(general.getId())
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
        ShopEntity entity = shopRepo.findById(contact.getId())
                .orElseThrow(() -> new ApplicationException(ErrorCodes.NOT_FOUND));
        entity.setPhones(contact.getPhones().stream().collect(Collectors.joining(",")));
        entity.setAddress(contact.getAddress());
        entity.setLocation(new LocationData(contact.getLatitude(), contact.getLongitude()));

        shopRepo.save(entity);
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
        ShopEntity entity = shopRepo.findById(shopId)
                .orElseThrow(() -> new ApplicationException(ErrorCodes.NOT_FOUND));
        entity.setStatus(status);

        shopRepo.save(entity);
    }

    @Override
    public void delete(long id) {
        // TODO Auto-generated method stub

    }

}
