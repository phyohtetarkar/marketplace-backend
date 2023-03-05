package com.shoppingcenter.data.product.variant;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.shoppingcenter.data.product.ProductRepo;
import com.shoppingcenter.domain.product.ProductVariant;
import com.shoppingcenter.domain.product.dao.ProductVariantDao;

import lombok.var;

@Repository
public class ProductVariantDaoImpl implements ProductVariantDao {

    @Autowired
    private ProductVariantRepo variantRepo;

    @Autowired
    private ProductRepo productRepo;

    @Override
    public long save(ProductVariant variant) {
        var entity = variantRepo.findById(variant.getId()).orElseGet(ProductVariantEntity::new);
        entity.setTitle(variant.getTitle());
        entity.setPrice(variant.getPrice());
        entity.setSku(variant.getSku());
        entity.setStockLeft(variant.getStockLeft());
        entity.setProduct(productRepo.getReferenceById(variant.getProductId()));
        entity.setOptions(variant.getOptions().stream().map(op -> {
            var variantOption = new ProductVariantOptionData();
            variantOption.setOption(op.getOption());
            variantOption.setValue(op.getValue());
            return variantOption;
        }).collect(Collectors.toSet()));
        var result = variantRepo.save(entity);

        return result.getId();
    }

    @Override
    public void delete(long id) {
        variantRepo.deleteById(id);
    }

    @Override
    public boolean existsById(long id) {
        return variantRepo.existsById(id);
    }

}
