package com.shoppingcenter.data.product;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOptionRepo extends JpaRepository<ProductOptionEntity, String> {

    void deleteByProduct_Id(long productId);

}
