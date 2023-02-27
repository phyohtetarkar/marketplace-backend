package com.shoppingcenter.app.controller.subscription;

import com.shoppingcenter.app.controller.subscription.dto.SubscriptionPlanDTO;
import com.shoppingcenter.domain.PageData;

public interface SubscriptionPlanFacade {

    void save(SubscriptionPlanDTO plan);

    void delete(long id);

    SubscriptionPlanDTO findById(long id);

    PageData<SubscriptionPlanDTO> findAll(Integer page);

}
