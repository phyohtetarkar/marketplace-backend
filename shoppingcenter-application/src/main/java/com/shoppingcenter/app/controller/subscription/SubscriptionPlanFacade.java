package com.shoppingcenter.app.controller.subscription;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.shoppingcenter.app.annotation.Facade;
import com.shoppingcenter.app.controller.subscription.dto.SubscriptionPlanDTO;
import com.shoppingcenter.domain.subscription.SubscriptionPlan;
import com.shoppingcenter.domain.subscription.usecase.DeleteSubscriptionPlanUseCase;
import com.shoppingcenter.domain.subscription.usecase.GetAllSubscriptionPlanUseCase;
import com.shoppingcenter.domain.subscription.usecase.GetSubscriptionPlanByIdUseCase;
import com.shoppingcenter.domain.subscription.usecase.SaveSubscriptionPlanUseCase;

@Facade
@Transactional
public class SubscriptionPlanFacade {

    @Autowired
    private SaveSubscriptionPlanUseCase saveSubscriptionPlanUseCase;

    @Autowired
    private DeleteSubscriptionPlanUseCase deleteSubscriptionPlanUseCase;

    @Autowired
    private GetSubscriptionPlanByIdUseCase getSubscriptionPlanByIdUseCase;

    @Autowired
    private GetAllSubscriptionPlanUseCase getAllSubscriptionPlanUseCase;

    @Autowired
    private ModelMapper modelMapper;

    public void save(SubscriptionPlanDTO plan) {
        saveSubscriptionPlanUseCase.apply(modelMapper.map(plan, SubscriptionPlan.class));
    }

    public void delete(long id) {
        deleteSubscriptionPlanUseCase.apply(id);
    }

    public SubscriptionPlanDTO findById(long id) {
    	var result = getSubscriptionPlanByIdUseCase.apply(id);
    	if (result != null) {
    		return modelMapper.map(result, SubscriptionPlanDTO.class);
    	}
    	
    	return null;
        
    }

    public List<SubscriptionPlanDTO> findAll() {
        return modelMapper.map(getAllSubscriptionPlanUseCase.apply(), SubscriptionPlanDTO.listType());
    }

}
