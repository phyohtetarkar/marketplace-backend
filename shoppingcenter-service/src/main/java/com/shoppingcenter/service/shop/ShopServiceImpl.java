package com.shoppingcenter.service.shop;

import java.io.File;
import java.util.stream.Collectors;

import org.owasp.html.PolicyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.shoppingcenter.data.shop.ShopContactEntity;
import com.shoppingcenter.data.shop.ShopContactRepo;
import com.shoppingcenter.data.shop.ShopEntity;
import com.shoppingcenter.data.shop.ShopMemberEntity;
import com.shoppingcenter.data.shop.ShopMemberRepo;
import com.shoppingcenter.data.shop.ShopRepo;
import com.shoppingcenter.data.user.UserRepo;
import com.shoppingcenter.service.ApplicationException;
import com.shoppingcenter.service.ErrorCodes;
import com.shoppingcenter.service.UploadFile;
import com.shoppingcenter.service.Utils;
import com.shoppingcenter.service.shop.model.Shop;
import com.shoppingcenter.service.shop.model.ShopContact;
import com.shoppingcenter.service.shop.model.ShopGeneral;
import com.shoppingcenter.service.shop.model.ShopMember;
import com.shoppingcenter.service.storage.FileStorageService;

@Service
@Transactional
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopRepo shopRepo;

    @Autowired
    private ShopContactRepo shopContactRepo;

    @Autowired
    private ShopMemberRepo shopMemberRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private FileStorageService storageService;

    @Autowired
    private PolicyFactory policyFactory;

    @Value("${app.image.base-path}")
    private String imagePath;

    @Value("${app.image.base-url}")
    private String imageUrl;

    @Override
    public void create(Shop shop) {
        try {
            ShopEntity entity = new ShopEntity();
            // String slug = generateSlug(shop.getName().replaceAll("\\s+",
            // "-").toLowerCase());

            if (!StringUtils.hasText(shop.getName())) {
                throw new ApplicationException(ErrorCodes.VALIDATION_FAILED, "Required shop name");
            }

            if (!StringUtils.hasText(shop.getSlug())) {
                throw new ApplicationException(ErrorCodes.VALIDATION_FAILED, "Required shop slug");
            }

            entity.setName(shop.getName());
            entity.setHeadline(shop.getHeadline());
            entity.setStatus(Shop.Status.PENDING.name());

            // Sanitize rich text for XSS attack
            if (shop.getAbout() != null) {
                entity.setAbout(policyFactory.sanitize(shop.getAbout()));
            }

            String prefix = shop.getSlug().replaceAll("\\s+", "-").toLowerCase();
            String slug = Utils.generateSlug(prefix, shopRepo::existsBySlug);
            entity.setSlug(slug);

            ShopEntity result = shopRepo.save(entity);

            if (StringUtils.hasText(shop.getAddress())) {
                ShopContactEntity contact = new ShopContactEntity();
                contact.setAddress(shop.getAddress());
                contact.setShop(result);
                shopContactRepo.save(contact);
            }

            ShopMemberEntity memberEntity = new ShopMemberEntity();
            memberEntity.setUser(userRepo.getReferenceById(result.getCreatedBy()));
            memberEntity.setShop(result);
            memberEntity.setRole(ShopMember.Role.OWNER.name());

            shopMemberRepo.save(memberEntity);

            uploadLogo(result.getId(), shop.getLogoImage());

            uploadCover(result.getId(), shop.getCoverImage());

        } catch (Exception e) {
            throw new ApplicationException(ErrorCodes.EXECUTION_FAILED, e.getMessage());
        }
    }

    @Override
    public Shop updateGeneralInfo(ShopGeneral general) {
        ShopEntity entity = shopRepo.findById(general.getShopId())
                .orElseThrow(() -> new ApplicationException(ErrorCodes.NOT_FOUND, "Shop not found"));
        validateActive(entity);

        if (!Utils.equalsIgnoreCase(entity.getName(), general.getName())) {
            String prefix = general.getSlug().replaceAll("\\s+", "-").toLowerCase();
            String slug = Utils.generateSlug(prefix, shopRepo::existsBySlug);
            entity.setSlug(slug);
        }

        entity.setName(general.getName());
        entity.setHeadline(general.getHeadline());

        if (general.getAbout() != null) {
            entity.setAbout(policyFactory.sanitize(general.getAbout()));
        }

        if (!StringUtils.hasText(entity.getName())) {
            throw new ApplicationException(ErrorCodes.VALIDATION_FAILED, "Required shop name");
        }

        ShopEntity result = shopRepo.save(entity);

        return Shop.createCompat(result, imageUrl);
    }

    @Override
    public void updateContact(ShopContact contact) {
        // TODO: check privilege

        if (!shopRepo.existsById(contact.getShopId())) {
            throw new ApplicationException(ErrorCodes.INVALID_ARGUMENT);
        }
        ShopContactEntity entity = shopContactRepo.findByShop_Id(contact.getShopId()).orElseGet(ShopContactEntity::new);

        validateActive(entity.getShop());

        entity.setShop(shopRepo.getReferenceById(contact.getShopId()));
        entity.setPhones(contact.getPhones().stream().collect(Collectors.joining(",")));
        entity.setAddress(contact.getAddress());
        entity.setLatitude(contact.getLatitude());
        entity.setLongitude(contact.getLongitude());

        shopContactRepo.save(entity);
    }

    @Override
    public void uploadLogo(long shopId, UploadFile file) {
        try {
            if (!shopRepo.existsById(shopId)) {
                throw new RuntimeException("Shop not found");
            }

            if (file != null) {
                String dir = imagePath + File.separator + "shop";

                ShopEntity entity = shopRepo.getReferenceById(shopId);

                validateActive(entity);

                // long millis = System.currentTimeMillis();
                String name = String.format("shop-logo-%d.%s", shopId, file.getExtension());
                // String oldLogo = entity.getLogo();

                String image = storageService.write(file, dir, name);
                entity.setLogo(image);

                // if (StringUtils.hasText(oldLogo)) {
                // storageService.delete(dir, oldLogo);
                // }
            }
        } catch (Exception e) {
            throw new ApplicationException(ErrorCodes.VALIDATION_FAILED, e.getMessage());
        }

    }

    @Override
    public void uploadCover(long shopId, UploadFile file) {
        try {
            if (!shopRepo.existsById(shopId)) {
                throw new RuntimeException("Shop not found");
            }

            if (file != null) {
                String dir = imagePath + File.separator + "shop";

                ShopEntity entity = shopRepo.getReferenceById(shopId);

                validateActive(entity);

                // long millis = System.currentTimeMillis();
                String name = String.format("shop-cover-%d.%s", shopId, file.getExtension());
                // String oldCover = entity.getCover();

                String image = storageService.write(file, dir, name);
                entity.setCover(image);

                // if (StringUtils.hasText(oldCover)) {
                // storageService.delete(dir, oldCover);
                // }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    @Override
    public void updateStatus(long shopId, Shop.Status status) {
        if (status == null) {
            throw new ApplicationException(ErrorCodes.INVALID_ARGUMENT);
        }

        if (!shopRepo.existsById(shopId)) {
            throw new ApplicationException(ErrorCodes.INVALID_ARGUMENT);
        }

        // TODO: check privilege

        shopRepo.updateStatus(shopId, status.name());
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

    public void validateActive(ShopEntity entity) {

        if (Shop.Status.SUBSCRIPTION_EXPIRED.name().equals(entity.getStatus())) {
            throw new ApplicationException(ErrorCodes.VALIDATION_FAILED, "shop-subscription-expired");
        }

        if (Shop.Status.DISABLED.name().equals(entity.getStatus())) {
            throw new ApplicationException(ErrorCodes.VALIDATION_FAILED, "shop-disabled");
        }
    }

}
