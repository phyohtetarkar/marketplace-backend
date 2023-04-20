package com.shoppingcenter.data.misc;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OTPAttemptRepo extends JpaRepository<OTPAttemptEntity, OTPAttemptEntity.ID> {

	@Modifying
    @Query("UPDATE OTPAttempt o SET o.attempt = :attempt WHERE o.id = :id")
    void updateAttempt(@Param("id") OTPAttemptEntity.ID id, @Param("attempt") int attempt);
	
}
