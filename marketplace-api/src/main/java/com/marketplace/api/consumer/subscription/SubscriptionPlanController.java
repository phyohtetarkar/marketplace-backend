package com.marketplace.api.consumer.subscription;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marketplace.domain.subscription.usecase.GetAllSubscriptionPlanUseCase;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/content/subscription-plans")
@Tag(name = "Consumer")
public class SubscriptionPlanController {

	@Autowired
	private GetAllSubscriptionPlanUseCase getAllSubscriptionPlanUseCase;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping
    public List<SubscriptionPlanDTO> getSubscriptionPlans() {
        var source = getAllSubscriptionPlanUseCase.apply();
        
        return modelMapper.map(source, SubscriptionPlanDTO.listType());
    }
	
}
