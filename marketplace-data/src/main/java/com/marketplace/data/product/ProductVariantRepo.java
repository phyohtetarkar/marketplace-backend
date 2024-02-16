package com.marketplace.data.product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductVariantRepo extends JpaRepository<ProductVariantEntity, Long> {

	List<ProductVariantEntity> findByProductId(long productId);
	
	List<ProductVariantEntity> findByProductIdAndDeletedFalse(long productId);

	void deleteByProductId(long productId);
	
	@Modifying
	@Query("UPDATE ProductVariant pv SET pv.deleted = :deleted WHERE pv.id = :id")
	void updateDeleted(@Param("id") long id, @Param("deleted") boolean deleted);
}
