package com.shoppingcenter.core.discount;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.shoppingcenter.core.ApplicationException;
import com.shoppingcenter.core.Constants;
import com.shoppingcenter.core.ErrorCodes;
import com.shoppingcenter.core.PageData;
import com.shoppingcenter.core.Utils;
import com.shoppingcenter.core.discount.model.Discount;
import com.shoppingcenter.data.discount.DiscountEntity;
import com.shoppingcenter.data.discount.DiscountRepo;
import com.shoppingcenter.data.product.ProductRepo;
import com.shoppingcenter.data.shop.ShopRepo;

@Service
public class DiscountServiceImpl implements DiscountService {

    @Autowired
    private DiscountRepo discountRepo;

    @Autowired
    private ShopRepo shopRepo;

    @Autowired
    private ProductRepo productRepo;

    @Override
    public void save(Discount discount) {
        if (!shopRepo.existsById(discount.getShopId())) {
            throw new ApplicationException("Shop not found with id: " + discount.getShopId());
        }

        String id = Optional.ofNullable(discount.getId()).orElse("");

        DiscountEntity entity = discountRepo.findById(id).orElseGet(DiscountEntity::new);
        entity.setTitle(discount.getTitle());
        entity.setValue(discount.getValue());
        entity.setType(discount.getType());
        entity.setShop(shopRepo.getReferenceById(discount.getShopId()));

        discountRepo.save(entity);
    }

    @Override
    public void delete(String id) {
        if (!StringUtils.hasText(id) || !discountRepo.existsById(id)) {
            throw new ApplicationException("Discount not found");
        }

        DiscountEntity entity = discountRepo.getReferenceById(id);

        if (productRepo.existsByDiscount(entity)) {
            throw new ApplicationException("Discount referenced by products");
        }

        discountRepo.delete(entity);
    }

    @Override
    public Discount findById(String id) {
        if (!StringUtils.hasText(id)) {
            throw new ApplicationException(ErrorCodes.NOT_FOUND, "");
        }
        return discountRepo.findById(id).map(Discount::create)
                .orElseThrow(() -> new ApplicationException(ErrorCodes.NOT_FOUND, ""));
    }

    @Override
    public PageData<Discount> findAll(long shopId, Integer page) {
        Sort sort = Sort.by(Order.desc("createdAt"));
        PageRequest request = PageRequest.of(Utils.normalizePage(page), Constants.PAGE_SIZE, sort);

        Page<DiscountEntity> pageResult = discountRepo.findAll(request);

        return PageData.build(pageResult, Discount::create);
    }

}
