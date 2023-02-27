package com.shoppingcenter.data.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.shoppingcenter.domain.product.ProductOption;
import com.shoppingcenter.domain.product.dao.ProductOptionDao;

@Repository
public class ProductOptionDaoImpl implements ProductOptionDao {

    @Autowired
    private ProductOptionRepo optionRepo;

    @Autowired
    private ProductRepo productRepo;

    @Override
    public long save(ProductOption option) {
        var entity = new ProductOptionEntity();
        entity.setId(option.getId());
        entity.setName(option.getName());
        entity.setPosition(option.getPosition());
        entity.setProduct(productRepo.getReferenceById(option.getProductId()));

        var result = optionRepo.save(entity);
        return result.getId();
    }

}
