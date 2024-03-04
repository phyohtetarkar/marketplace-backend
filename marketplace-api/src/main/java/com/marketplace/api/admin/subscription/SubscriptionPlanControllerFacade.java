package com.marketplace.api.admin.subscription;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketplace.api.admin.AdminDataMapper;
import com.marketplace.domain.subscription.usecase.DeleteSubscriptionPlanUseCase;
import com.marketplace.domain.subscription.usecase.GetAllSubscriptionPlanUseCase;
import com.marketplace.domain.subscription.usecase.GetSubscriptionPlanByIdUseCase;
import com.marketplace.domain.subscription.usecase.SaveSubscriptionPlanUseCase;

@Component
public class SubscriptionPlanControllerFacade {

	@Autowired
	private SaveSubscriptionPlanUseCase saveSubscriptionPlanUseCase;

	@Autowired
	private DeleteSubscriptionPlanUseCase deleteSubscriptionPlanUseCase;

	@Autowired
	private GetSubscriptionPlanByIdUseCase getSubscriptionPlanByIdUseCase;

	@Autowired
	private GetAllSubscriptionPlanUseCase getAllSubscriptionPlanUseCase;
	
	@Autowired
    private AdminDataMapper mapper;

	public SubscriptionPlanDTO save(SubscriptionPlanEditDTO values) {
        var source = saveSubscriptionPlanUseCase.apply(mapper.map(values));
        return mapper.map(source);
    }

    public void delete(long id) {
        deleteSubscriptionPlanUseCase.apply(id);
    }

    public SubscriptionPlanDTO findById(long id) {
    	var source = getSubscriptionPlanByIdUseCase.apply(id);
    	return mapper.map(source);
        
    }

    public List<SubscriptionPlanDTO> findAll() {
        return mapper.mapSubscriptionPlanList(getAllSubscriptionPlanUseCase.apply());
    }
}
