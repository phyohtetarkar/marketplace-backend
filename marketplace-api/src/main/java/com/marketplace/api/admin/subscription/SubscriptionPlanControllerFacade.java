package com.marketplace.api.admin.subscription;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketplace.api.AbstractControllerFacade;
import com.marketplace.domain.subscription.SubscriptionPlanInput;
import com.marketplace.domain.subscription.usecase.DeleteSubscriptionPlanUseCase;
import com.marketplace.domain.subscription.usecase.GetAllSubscriptionPlanUseCase;
import com.marketplace.domain.subscription.usecase.GetSubscriptionPlanByIdUseCase;
import com.marketplace.domain.subscription.usecase.SaveSubscriptionPlanUseCase;

@Component
public class SubscriptionPlanControllerFacade extends AbstractControllerFacade {

	@Autowired
	private SaveSubscriptionPlanUseCase saveSubscriptionPlanUseCase;

	@Autowired
	private DeleteSubscriptionPlanUseCase deleteSubscriptionPlanUseCase;

	@Autowired
	private GetSubscriptionPlanByIdUseCase getSubscriptionPlanByIdUseCase;

	@Autowired
	private GetAllSubscriptionPlanUseCase getAllSubscriptionPlanUseCase;

	public SubscriptionPlanDTO save(SubscriptionPlanEditDTO values) {
        var source = saveSubscriptionPlanUseCase.apply(map(values, SubscriptionPlanInput.class));
        return map(source, SubscriptionPlanDTO.class);
    }

    public void delete(long id) {
        deleteSubscriptionPlanUseCase.apply(id);
    }

    public SubscriptionPlanDTO findById(long id) {
    	var source = getSubscriptionPlanByIdUseCase.apply(id);
    	return map(source, SubscriptionPlanDTO.class);
        
    }

    public List<SubscriptionPlanDTO> findAll() {
        return modelMapper.map(getAllSubscriptionPlanUseCase.apply(), SubscriptionPlanDTO.listType());
    }
}
