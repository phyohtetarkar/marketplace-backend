package com.shoppingcenter.service.subscription;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.shoppingcenter.data.subscription.SubscriptionPlanEntity;
import com.shoppingcenter.data.subscription.SubscriptionPlanRepo;
import com.shoppingcenter.service.ApplicationException;
import com.shoppingcenter.service.Constants;
import com.shoppingcenter.service.ErrorCodes;
import com.shoppingcenter.service.PageData;
import com.shoppingcenter.service.Utils;
import com.shoppingcenter.service.subscription.model.SubscriptionPlan;

@Service
public class SubscriptionPlanServiceImpl implements SubscriptionPlanService {

    @Autowired
    private SubscriptionPlanRepo repo;

    @Override
    public void save(SubscriptionPlan plan) {
        SubscriptionPlanEntity entity = repo.findById(plan.getId()).orElseGet(SubscriptionPlanEntity::new);
        entity.setTitle(plan.getTitle());
        entity.setDuration(plan.getDuration());
        entity.setPrice(plan.getPrice());
        entity.setPromoUsable(plan.isPromoUsable());

        repo.save(entity);
    }

    @Override
    public void delete(long id) {
        if (!repo.existsById(id)) {
            throw new ApplicationException(ErrorCodes.VALIDATION_FAILED, "Plan not found");
        }

        repo.deleteById(id);
    }

    @Override
    public SubscriptionPlan findById(long id) {
        return repo.findById(id).map(SubscriptionPlan::create)
                .orElseThrow(() -> new ApplicationException(ErrorCodes.NOT_FOUND, "Plan not found"));
    }

    @Override
    public PageData<SubscriptionPlan> findAll(Integer page) {
        Sort sort = Sort.by(Order.desc("createdAt"));
        PageRequest request = PageRequest.of(Utils.normalizePage(page), Constants.PAGE_SIZE, sort);

        Page<SubscriptionPlanEntity> pageResult = repo.findAll(request);

        return PageData.build(pageResult, e -> SubscriptionPlan.create(e));
    }

}
