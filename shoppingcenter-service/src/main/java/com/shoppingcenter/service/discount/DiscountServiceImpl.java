package com.shoppingcenter.service.discount;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shoppingcenter.data.discount.DiscountEntity;
import com.shoppingcenter.data.discount.DiscountRepo;
import com.shoppingcenter.data.product.ProductRepo;
import com.shoppingcenter.data.shop.ShopRepo;
import com.shoppingcenter.service.ApplicationException;
import com.shoppingcenter.service.Constants;
import com.shoppingcenter.service.ErrorCodes;
import com.shoppingcenter.service.PageData;
import com.shoppingcenter.service.Utils;
import com.shoppingcenter.service.authorization.AuthenticationContext;
import com.shoppingcenter.service.discount.model.Discount;
import com.shoppingcenter.service.shop.ShopMemberService;

@Service
@Transactional
public class DiscountServiceImpl implements DiscountService {

    @Autowired
    private DiscountRepo discountRepo;

    @Autowired
    private ShopRepo shopRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ShopMemberService shopMemberService;

    @Autowired
    private AuthenticationContext authenticationContext;

    @Override
    public void save(Discount discount) {
        if (!shopRepo.existsById(discount.getShopId())) {
            throw new ApplicationException(ErrorCodes.VALIDATION_FAILED, "Shop not found");
        }

        shopMemberService.validateMember(discount.getShopId(), authenticationContext.getUserId());

        DiscountEntity entity = discountRepo.findById(discount.getId()).orElseGet(DiscountEntity::new);
        entity.setTitle(discount.getTitle());
        entity.setValue(discount.getValue());
        entity.setType(discount.getType().name());
        entity.setShop(shopRepo.getReferenceById(discount.getShopId()));

        discountRepo.save(entity);
    }

    @Transactional
    @Override
    public void delete(long id) {
        if (!discountRepo.existsById(id)) {
            throw new ApplicationException(ErrorCodes.VALIDATION_FAILED, "Discount not found");
        }

        DiscountEntity entity = discountRepo.getReferenceById(id);

        shopMemberService.validateMember(entity.getShop().getId(), authenticationContext.getUserId());

        if (productRepo.existsByDiscount_Id(id)) {
            throw new ApplicationException(ErrorCodes.VALIDATION_FAILED, "constraint-violation");
        }

        discountRepo.deleteById(id);
    }

    @Override
    public void applyDiscounts(long discountId, List<Long> productIds) {
        if (!discountRepo.existsById(discountId)) {
            throw new ApplicationException(ErrorCodes.VALIDATION_FAILED, "Discount not found");
        }

        DiscountEntity entity = discountRepo.getReferenceById(discountId);

        shopMemberService.validateMember(entity.getShop().getId(), authenticationContext.getUserId());

        productRepo.applyDiscounts(entity, productIds);

    }

    @Override
    public void applyDiscountAll(long discountId) {
        if (!discountRepo.existsById(discountId)) {
            throw new ApplicationException(ErrorCodes.VALIDATION_FAILED, "Discount not found");
        }

        DiscountEntity entity = discountRepo.getReferenceById(discountId);

        long shopId = entity.getShop().getId();

        shopMemberService.validateMember(shopId, authenticationContext.getUserId());

        productRepo.applyDiscountAll(entity, shopId);
    }

    @Override
    public void removeDiscount(long discountId, long productId) {
        if (!discountRepo.existsById(discountId)) {
            throw new ApplicationException(ErrorCodes.VALIDATION_FAILED, "Discount not found");
        }

        DiscountEntity entity = discountRepo.getReferenceById(discountId);

        shopMemberService.validateMember(entity.getShop().getId(), authenticationContext.getUserId());

        productRepo.removeDiscount(productId);

    }

    @Override
    public void removeDiscountAll(long discountId) {
        if (!discountRepo.existsById(discountId)) {
            throw new ApplicationException(ErrorCodes.VALIDATION_FAILED, "Discount not found");
        }

        DiscountEntity entity = discountRepo.getReferenceById(discountId);

        long shopId = entity.getShop().getId();

        shopMemberService.validateMember(shopId, authenticationContext.getUserId());

        productRepo.removeDiscountAll(shopId, discountId);
    }

    @Override
    public Discount findById(long id) {
        Discount disount = discountRepo.findById(id).map(Discount::create)
                .orElseThrow(() -> new ApplicationException(ErrorCodes.NOT_FOUND, "Discount not found"));
        disount.setTotalProduct(productRepo.countByDiscount_Id(disount.getId()));
        return disount;
    }

    @Override
    public PageData<Discount> findAll(long shopId, Integer page) {
        Sort sort = Sort.by(Order.desc("createdAt"));
        PageRequest request = PageRequest.of(Utils.normalizePage(page), Constants.PAGE_SIZE, sort);

        Page<DiscountEntity> pageResult = discountRepo.findByShopId(shopId, request);

        return PageData.build(pageResult, Discount::create);
    }

}
