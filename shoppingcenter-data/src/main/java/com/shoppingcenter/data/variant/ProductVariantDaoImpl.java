package com.shoppingcenter.data.variant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shoppingcenter.data.product.ProductRepo;
import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.product.ProductVariant;
import com.shoppingcenter.domain.product.dao.ProductVariantDao;

import lombok.var;

@Repository
public class ProductVariantDaoImpl implements ProductVariantDao {

    @Autowired
    private ProductVariantRepo variantRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public long save(ProductVariant variant) {
        try {
            var entity = variantRepo.findById(variant.getId()).orElseGet(ProductVariantEntity::new);
            entity.setTitle(variant.getTitle());
            entity.setPrice(variant.getPrice());
            entity.setSku(variant.getSku());
            entity.setStockLeft(variant.getStockLeft());
            entity.setProduct(productRepo.getReferenceById(variant.getProductId()));
            entity.setOptions(objectMapper.writeValueAsString(variant.getOptions()));
            var result = variantRepo.save(entity);

            return result.getId();
        } catch (JsonProcessingException e) {
            throw new ApplicationException(e.getMessage());
        }
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
