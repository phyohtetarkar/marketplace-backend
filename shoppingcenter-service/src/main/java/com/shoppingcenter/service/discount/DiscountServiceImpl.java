package com.shoppingcenter.service.discount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.shoppingcenter.data.discount.DiscountEntity;
import com.shoppingcenter.data.discount.DiscountRepo;
import com.shoppingcenter.data.product.ProductRepo;
import com.shoppingcenter.data.shop.ShopRepo;
import com.shoppingcenter.service.ApplicationException;
import com.shoppingcenter.service.Constants;
import com.shoppingcenter.service.ErrorCodes;
import com.shoppingcenter.service.PageData;
import com.shoppingcenter.service.Utils;
import com.shoppingcenter.service.discount.model.Discount;

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
            throw new ApplicationException(ErrorCodes.VALIDATION_FAILED, "Shop not found");
        }

        DiscountEntity entity = discountRepo.findById(discount.getId()).orElseGet(DiscountEntity::new);
        entity.setTitle(discount.getTitle());
        entity.setValue(discount.getValue());
        entity.setType(discount.getType().name());
        entity.setShop(shopRepo.getReferenceById(discount.getShopId()));

        discountRepo.save(entity);
    }

    @Override
    public void delete(long id) {
        if (!discountRepo.existsById(id)) {
            throw new ApplicationException(ErrorCodes.VALIDATION_FAILED, "Discount not found");
        }

        // DiscountEntity entity = discountRepo.getReferenceById(id);

        if (productRepo.existsByDiscount_Id(id)) {
            throw new ApplicationException(ErrorCodes.VALIDATION_FAILED, "Discount referenced by products");
        }

        discountRepo.deleteById(id);
    }

    @Override
    public Discount findById(long id) {
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
