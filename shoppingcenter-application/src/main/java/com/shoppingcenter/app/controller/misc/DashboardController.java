package com.shoppingcenter.app.controller.misc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcenter.app.controller.misc.dto.DashboardDataDTO;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/dashboard")
@Tag(name = "Dashboard")
public class DashboardController {

	@Autowired
	private DashboardService dashboardService;
	
	@PreAuthorize("hasAnyAuthority('ROLE_OWNER', 'DASHBOARD_READ')")
	@GetMapping
	public DashboardDataDTO index() {
		return dashboardService.getDashboardData();
	}
	
}
