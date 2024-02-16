package com.marketplace.data.discount;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import com.marketplace.data.PageDataMapper;
import com.marketplace.data.PageQueryMapper;
import com.marketplace.data.product.ProductRepo;
import com.marketplace.data.shop.ShopRepo;
import com.marketplace.domain.PageData;
import com.marketplace.domain.common.PageQuery;
import com.marketplace.domain.discount.Discount;
import com.marketplace.domain.discount.DiscountDao;
import com.marketplace.domain.discount.DiscountInput;

@Repository
public class DiscountDaoImpl implements DiscountDao {

    @Autowired
    private DiscountRepo discountRepo;

    @Autowired
    private ShopRepo shopRepo;

    @Autowired
    private ProductRepo productRepo;

    @Override
    public Discount save(DiscountInput values) {
        var entity = discountRepo.findById(values.getId()).orElseGet(DiscountEntity::new);
        entity.setTitle(values.getTitle());
        entity.setValue(values.getValue());
        entity.setType(values.getType());
        if (entity.getId() <= 0) {
        	entity.setShop(shopRepo.getReferenceById(values.getShopId()));
        }

        var result = discountRepo.save(entity);

        return DiscountMapper.toDomain(result);
    }

    @Override
    public void delete(long id) {
        discountRepo.deleteById(id);
    }

    @Override
    public void applyDiscounts(long discountId, List<Long> productIds) {
        var entity = discountRepo.getReferenceById(discountId);
        productRepo.applyDiscounts(entity, productIds);
        
    }

    @Override
    public void removeDiscountFromProduct(long productId) {
    	productRepo.removeDiscount(productId);
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
    public List<Discount> findByShop(long shopId) {
    	return discountRepo.findByShopId(shopId).stream().map(DiscountMapper::toDomain).toList();
    }

    @Override
    public PageData<Discount> findByShop(long shopId, PageQuery pageQuery) {
        var request = PageQueryMapper.fromPageQuery(pageQuery);

        Page<DiscountEntity> pageResult = discountRepo.findByShopId(shopId, request);

        return PageDataMapper.map(pageResult, DiscountMapper::toDomain);
    }

}
