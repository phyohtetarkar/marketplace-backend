package com.shoppingcenter.data.product.variant;

import java.util.List;
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
    public void save(ProductVariant variant) {
        var entity = variantRepo.findById(variant.getId()).orElseGet(ProductVariantEntity::new);
        entity.setTitle(variant.getTitle());
        entity.setPrice(variant.getPrice());
        entity.setSku(variant.getSku());
        entity.setStockLeft(variant.getStockLeft());
        if (variant.getId() == 0) {
            entity.setProduct(productRepo.getReferenceById(variant.getProductId()));
        }

        entity.setOptions(variant.getOptions().stream().map(op -> {
            var variantOption = new ProductVariantOptionData();
            variantOption.setOption(op.getOption());
            variantOption.setValue(op.getValue());
            return variantOption;
        }).collect(Collectors.toSet()));

        variantRepo.save(entity);
    }

    @Override
    public void saveAll(List<ProductVariant> list) {
        variantRepo.saveAll(list.stream().map(variant -> {
            var entity = variantRepo.findById(variant.getId()).orElseGet(ProductVariantEntity::new);
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
            if (entity.getId() == 0) {
                entity.setProduct(productRepo.getReferenceById(variant.getProductId()));
            }
            return entity;
        }).toList());
    }

    @Override
    public void delete(ProductVariant variant) {
        variantRepo.deleteById(variant.getId());
    }

    @Override
    public void deleteAll(List<ProductVariant> list) {
        variantRepo.deleteAllById(list.stream().map(v -> {
            return v.getId();
        }).toList());
    }

    @Override
    public boolean exists(long id) {
        return variantRepo.existsById(id);
    }

}
