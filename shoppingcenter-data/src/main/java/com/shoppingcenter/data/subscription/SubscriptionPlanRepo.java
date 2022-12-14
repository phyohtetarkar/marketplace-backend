package com.shoppingcenter.data.subscription;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionPlanRepo extends JpaRepository<SubscriptionPlanEntity, Long> {
    List<SubscriptionPlanEntity> findByDeletedFalse(Sort sort);
}
