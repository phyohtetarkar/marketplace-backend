package com.shoppingcenter.data.subscription;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Repository;

import com.shoppingcenter.data.PageDataMapper;
import com.shoppingcenter.domain.Constants;
import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.subscription.SubscriptionPlan;
import com.shoppingcenter.domain.subscription.SubscriptionPlanDao;

@Repository
public class SubscriptionPlanDaoImpl implements SubscriptionPlanDao {

    @Autowired
    private SubscriptionPlanRepo subscriptionPlanRepo;

    @Override
    public void save(SubscriptionPlan plan) {
        var entity = subscriptionPlanRepo.findById(plan.getId()).orElseGet(SubscriptionPlanEntity::new);
        entity.setTitle(plan.getTitle());
        entity.setPrice(plan.getPrice());
        entity.setDuration(plan.getDuration());
        entity.setPromoUsable(plan.isPromoUsable());

        subscriptionPlanRepo.save(entity);
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
    public PageData<SubscriptionPlan> findAll(int page) {
        var sort = Sort.by(Order.desc("createdAt"));
        var request = PageRequest.of(page, Constants.PAGE_SIZE, sort);

        var pageResult = subscriptionPlanRepo.findAll(request);
        return PageDataMapper.map(pageResult, SubscriptionPlanMapper::toDomain);
    }

}
