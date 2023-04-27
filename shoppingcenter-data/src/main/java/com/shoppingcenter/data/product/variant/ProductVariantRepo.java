package com.shoppingcenter.data.product.variant;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductVariantRepo extends JpaRepository<ProductVariantEntity, Long> {

	List<ProductVariantEntity> findByProductId(long productId);

	void deleteByProductId(long productId);
	
	@Modifying
	@Query("UPDATE ProductVariant pv SET pv.stockLeft = :stockLeft WHERE pv.id = :id")
	void updateStockLeft(@Param("id") long id, @Param("stockLeft") int stockLeft);
}
