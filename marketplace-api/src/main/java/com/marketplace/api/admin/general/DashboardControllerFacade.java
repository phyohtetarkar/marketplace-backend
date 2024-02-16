package com.marketplace.api.admin.general;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketplace.api.AbstractControllerFacade;
import com.marketplace.domain.general.usecase.GetDashboardDataUseCase;

@Component
public class DashboardControllerFacade extends AbstractControllerFacade {

	@Autowired
	private GetDashboardDataUseCase getDashboardDataUseCase;
	
	public DashboardDataDTO getDashboardData() {
		return map(getDashboardDataUseCase.apply(), DashboardDataDTO.class);
	}
	
}
