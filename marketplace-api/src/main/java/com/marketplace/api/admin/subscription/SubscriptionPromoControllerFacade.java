package com.marketplace.api.admin.subscription;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketplace.api.PageDataDTO;
import com.marketplace.api.admin.AdminDataMapper;
import com.marketplace.domain.subscription.SubscriptionPromoQuery;
import com.marketplace.domain.subscription.usecase.CreateSubscriptionPromoUseCase;
import com.marketplace.domain.subscription.usecase.DeleteSubscriptionPromoUseCase;
import com.marketplace.domain.subscription.usecase.GetAllSubscriptionPromoUseCase;
import com.marketplace.domain.subscription.usecase.GetSubscriptionPromoByIdUseCase;
import com.marketplace.domain.subscription.usecase.UpdateSubscriptionPromoUseCase;

@Component
public class SubscriptionPromoControllerFacade {

	@Autowired
	private CreateSubscriptionPromoUseCase createSubscriptionPromoUseCase;
	
	@Autowired
	private UpdateSubscriptionPromoUseCase updateSubscriptionPromoUseCase;
	
	@Autowired
	private DeleteSubscriptionPromoUseCase deleteSubscriptionPromoUseCase;
	
	@Autowired
	private GetSubscriptionPromoByIdUseCase getSubscriptionPromoByIdUseCase;
	
	@Autowired
	private GetAllSubscriptionPromoUseCase getAllSubscriptionPromoUseCase;
	
	@Autowired
    private AdminDataMapper mapper;
	
	public SubscriptionPromoDTO create(SubscriptionPromoEditDTO values) {
		var source = createSubscriptionPromoUseCase.apply(mapper.map(values));
		
		return mapper.map(source);
	}
	
	public SubscriptionPromoDTO update(SubscriptionPromoEditDTO values) {
		var source = updateSubscriptionPromoUseCase.apply(mapper.map(values));
		
		return mapper.map(source);
	}
	
	public void delete(long id) {
		deleteSubscriptionPromoUseCase.apply(id);
	}
	
	public SubscriptionPromoDTO findById(long id) {
		var source = getSubscriptionPromoByIdUseCase.apply(id);
		return mapper.map(source);
	}
	
	public PageDataDTO<SubscriptionPromoDTO> findAll(SubscriptionPromoQuery query) {
		var source = getAllSubscriptionPromoUseCase.apply(query);
		
		return mapper.mapSubscriptionPromoPage(source);
	}
}
