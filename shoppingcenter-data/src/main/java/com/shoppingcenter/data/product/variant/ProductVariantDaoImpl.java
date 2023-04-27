package com.shoppingcenter.data.product.variant;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.shoppingcenter.data.product.ProductRepo;
import com.shoppingcenter.domain.product.ProductVariant;
import com.shoppingcenter.domain.product.dao.ProductVariantDao;

@Repository
public class ProductVariantDaoImpl implements ProductVariantDao {

    @Autowired
    private ProductVariantRepo variantRepo;

    @Autowired
    private ProductRepo productRepo;

    @Override
    public void saveAll(List<ProductVariant> list) {
        variantRepo.saveAll(list.stream().map(variant -> {
            var entity = new ProductVariantEntity();
            entity.setTitle(variant.getTitle());
            entity.setPrice(variant.getPrice());
            entity.setSku(variant.getSku());
            entity.setStockLeft(variant.getStockLeft());
            entity.setOptions(variant.getOptions().stream().map(op -> {
                var variantOption = new ProductVariantOptionData();
                variantOption.setOption(op.getOption());
                variantOption.setValue(op.getValue());
                return variantOption;
            }).collect(Collectors.toSet()));
            entity.setProduct(productRepo.getReferenceById(variant.getProductId()));
            return entity;
        }).toList());
    }
    
    @Override
    public void updateStockLeft(long id, int stockLeft) {
    	var entity = variantRepo.getReferenceById(id);
    	entity.setStockLeft(stockLeft);
    }

    @Override
    public void deleteAll(List<Long> list) {
        variantRepo.deleteAllById(list);
    }

    @Override
    public boolean exists(long id) {
        return variantRepo.existsById(id);
    }

}
