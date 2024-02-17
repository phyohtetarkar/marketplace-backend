package com.marketplace.data.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PaymentDetailRepo extends JpaRepository<PaymentDetailEntity, Long> {

	@Modifying
	@Query("UPDATE PaymentDetail pd SET pd.receiptImage = :receiptImage WHERE pd.id = :id")
	void updateReceiptImage(@Param("id") long id, @Param("receiptImage") String receiptImage);
	
}
