package com.marketplace.data.product;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductVariantAttributeRepo extends JpaRepository<ProductVariantAttributeEntity, ProductVariantAttributeEntity.ID> {

}
