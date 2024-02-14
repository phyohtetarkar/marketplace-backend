package com.marketplace.api.admin.general;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/admin/dashboard")
@Tag(name = "Admin")
public class DashboardController {

	@Autowired
	private DashboardControllerFacade dashboardFacade;
	
	@PreAuthorize("hasPermission('DASHBOARD', 'READ')")
	@GetMapping
	public DashboardDataDTO index() {
		return dashboardFacade.getDashboardData();
	}
	
}
