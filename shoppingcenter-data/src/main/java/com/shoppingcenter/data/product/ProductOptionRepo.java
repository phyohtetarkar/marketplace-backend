package com.shoppingcenter.data.product;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOptionRepo extends JpaRepository<ProductOptionEntity, Long> {

    void deleteByProductId(long productId);

}
