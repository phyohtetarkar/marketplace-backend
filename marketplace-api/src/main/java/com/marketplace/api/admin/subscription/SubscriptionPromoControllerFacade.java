package com.marketplace.api.admin.subscription;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketplace.api.AbstractControllerFacade;
import com.marketplace.api.PageDataDTO;
import com.marketplace.domain.subscription.SubscriptionPromoInput;
import com.marketplace.domain.subscription.SubscriptionPromoQuery;
import com.marketplace.domain.subscription.usecase.CreateSubscriptionPromoUseCase;
import com.marketplace.domain.subscription.usecase.DeleteSubscriptionPromoUseCase;
import com.marketplace.domain.subscription.usecase.GetAllSubscriptionPromoUseCase;
import com.marketplace.domain.subscription.usecase.GetSubscriptionPromoByIdUseCase;
import com.marketplace.domain.subscription.usecase.UpdateSubscriptionPromoUseCase;

@Component
public class SubscriptionPromoControllerFacade extends AbstractControllerFacade {

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
	
	public SubscriptionPromoDTO create(SubscriptionPromoEditDTO values) {
		var source = createSubscriptionPromoUseCase.apply(map(values, SubscriptionPromoInput.class));
		
		return modelMapper.map(source, SubscriptionPromoDTO.class);
	}
	
	public SubscriptionPromoDTO update(SubscriptionPromoEditDTO values) {
		var source = updateSubscriptionPromoUseCase.apply(map(values, SubscriptionPromoInput.class));
		
		return modelMapper.map(source, SubscriptionPromoDTO.class);
	}
	
	public void delete(long id) {
		deleteSubscriptionPromoUseCase.apply(id);
	}
	
	public SubscriptionPromoDTO findById(long id) {
		var source = getSubscriptionPromoByIdUseCase.apply(id);
		return modelMapper.map(source, SubscriptionPromoDTO.class);
	}
	
	public PageDataDTO<SubscriptionPromoDTO> findAll(SubscriptionPromoQuery query) {
		var source = getAllSubscriptionPromoUseCase.apply(query);
		
		return map(source, SubscriptionPromoDTO.pageType());
	}
}
