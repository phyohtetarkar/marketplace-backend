package com.shoppingcenter.data.discount;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Repository;

import com.shoppingcenter.data.PageDataMapper;
import com.shoppingcenter.data.product.ProductRepo;
import com.shoppingcenter.data.product.event.ProductUpdateDiscountEvent;
import com.shoppingcenter.data.shop.ShopRepo;
import com.shoppingcenter.domain.Constants;
import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.discount.Discount;
import com.shoppingcenter.domain.discount.DiscountDao;

@Repository
public class DiscountDaoImpl implements DiscountDao {

    @Autowired
    private DiscountRepo discountRepo;

    @Autowired
    private ShopRepo shopRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Override
    public long save(Discount discount) {
        var entity = discountRepo.findById(discount.getId()).orElseGet(DiscountEntity::new);
        entity.setTitle(discount.getTitle());
        entity.setValue(discount.getValue());
        entity.setType(discount.getType().name());
        entity.setShop(shopRepo.getReferenceById(discount.getShopId()));

        var result = discountRepo.save(entity);

        return result.getId();
    }

    @Override
    public void delete(long id) {
        discountRepo.deleteById(id);
    }

    @Override
    public void updateProductCount(long discountId, long count) {
    }

    @Override
    public void applyDiscounts(long discountId, List<Long> productIds) {
        var entity = discountRepo.getReferenceById(discountId);
        if (productIds == null || productIds.size() == 0) {
            productRepo.applyDiscountAll(entity, entity.getShop().getId());
        } else {
            productRepo.applyDiscounts(entity, productIds);
            eventPublisher
                    .publishEvent(new ProductUpdateDiscountEvent(this, productIds, DiscountMapper.toDomain(entity)));
        }
    }

    @Override
    public void removeDiscount(long discountId, Long productId) {
        if (productId == null || productId <= 0) {
            productRepo.removeDiscount(productId);
            eventPublisher.publishEvent(new ProductUpdateDiscountEvent(this, Arrays.asList(productId), null));
        } else {
            var entity = discountRepo.getReferenceById(discountId);
            productRepo.removeDiscountAll(entity.getShop().getId(), discountId);
        }
    }

    @Override
    public boolean existsById(long id) {
        return discountRepo.existsById(id);
    }

    @Override
    public Discount findById(long id) {
        return discountRepo.findById(id).map(DiscountMapper::toDomain).orElse(null);
    }

    @Override
    public PageData<Discount> findByShop(long shopId, int page) {
        Sort sort = Sort.by(Order.desc("createdAt"));
        PageRequest request = PageRequest.of(page, Constants.PAGE_SIZE, sort);

        Page<DiscountEntity> pageResult = discountRepo.findByShopId(shopId, request);

        return PageDataMapper.map(pageResult, DiscountMapper::toDomain);
    }

}
