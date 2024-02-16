package com.marketplace.data.subscription;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Repository;

import com.marketplace.domain.subscription.SubscriptionPlan;
import com.marketplace.domain.subscription.SubscriptionPlanInput;
import com.marketplace.domain.subscription.dao.SubscriptionPlanDao;

@Repository
public class SubscriptionPlanDaoImpl implements SubscriptionPlanDao {

    @Autowired
    private SubscriptionPlanRepo subscriptionPlanRepo;

    @Override
    public SubscriptionPlan save(SubscriptionPlanInput values) {
        var entity = subscriptionPlanRepo.findById(values.getId()).orElseGet(SubscriptionPlanEntity::new);
        entity.setTitle(values.getTitle());
        entity.setPrice(values.getPrice());
        entity.setDuration(values.getDuration());
        entity.setPromoUsable(values.isPromoUsable());

        var result = subscriptionPlanRepo.save(entity);
        
        return SubscriptionPlanMapper.toDomain(result);
    }

    @Override
    public void delete(long id) {
        subscriptionPlanRepo.deleteById(id);
    }

    @Override
    public SubscriptionPlan findById(long id) {
        return subscriptionPlanRepo.findById(id).map(SubscriptionPlanMapper::toDomain).orElse(null);
    }

    @Override
    public List<SubscriptionPlan> findAll() {
        var sort = Sort.by(Order.asc("price"));
        return subscriptionPlanRepo.findAll(sort).stream().map(SubscriptionPlanMapper::toDomain).toList();
    }

}
