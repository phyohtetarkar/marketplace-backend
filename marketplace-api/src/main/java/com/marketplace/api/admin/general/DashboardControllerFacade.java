package com.marketplace.api.admin.general;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketplace.api.admin.AdminDataMapper;
import com.marketplace.domain.general.usecase.GetDashboardDataUseCase;

@Component
public class DashboardControllerFacade {

	@Autowired
	private GetDashboardDataUseCase getDashboardDataUseCase;
	
	@Autowired
    private AdminDataMapper mapper;
	
	public DashboardDataDTO getDashboardData() {
		return mapper.map(getDashboardDataUseCase.apply());
	}
	
}
