package com.marketplace.api.admin.subscription;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/admin/subscription-plans")
@Tag(name = "Admin")
public class SubscriptionPlanController {

	@Autowired
	private SubscriptionPlanControllerFacade subscriptionPlanFacade;
	
	@PreAuthorize("hasPermission('SUBSCRIPTION_PLAN', 'WRITE')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public SubscriptionPlanDTO create(@RequestBody SubscriptionPlanEditDTO body) {
        return subscriptionPlanFacade.save(body);
    }

	@PreAuthorize("hasPermission('SUBSCRIPTION_PLAN', 'WRITE')")
    @PutMapping
    public SubscriptionPlanDTO update(@RequestBody SubscriptionPlanEditDTO body) {
    	return subscriptionPlanFacade.save(body);
    }

	@PreAuthorize("hasPermission('SUBSCRIPTION_PLAN', 'WRITE')")
    @DeleteMapping("{id:\\d+}")
    public void delete(@PathVariable int id) {
        subscriptionPlanFacade.delete(id);
    }

    @PreAuthorize("hasPermission('SUBSCRIPTION_PLAN', 'WRITE') or hasPermission('SUBSCRIPTION_PLAN', 'READ')")
    @GetMapping("{id:\\d+}")
    public SubscriptionPlanDTO findById(@PathVariable int id) {
        return subscriptionPlanFacade.findById(id);
    }
	
    @PreAuthorize("hasPermission('SUBSCRIPTION_PLAN', 'WRITE') or hasPermission('SUBSCRIPTION_PLAN', 'READ')")
	@GetMapping
    public List<SubscriptionPlanDTO> getSubscriptionPlans() {
        return subscriptionPlanFacade.findAll();
    }
	
}
